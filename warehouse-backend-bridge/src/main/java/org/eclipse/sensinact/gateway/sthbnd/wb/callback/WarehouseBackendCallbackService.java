/*
 * Copyright (c) 2020-2021 Kentyou.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kentyou - initial API and implementation
 */
package org.eclipse.sensinact.gateway.sthbnd.wb.callback;

import java.io.IOException;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.sensinact.brainiot.wb.api.CartStorage;
import org.eclipse.sensinact.brainiot.wb.api.CartStorages;
import org.eclipse.sensinact.brainiot.wb.api.DockPoint;
import org.eclipse.sensinact.brainiot.wb.api.DockPoints;
import org.eclipse.sensinact.brainiot.wb.api.Mapper;
import org.eclipse.sensinact.brainiot.wb.api.PickPoint;
import org.eclipse.sensinact.brainiot.wb.api.PickPoints;
import org.eclipse.sensinact.brainiot.wb.api.StoragePoint;
import org.eclipse.sensinact.brainiot.wb.api.StoragePoints;
import org.eclipse.sensinact.gateway.common.bundle.Mediator;
import org.eclipse.sensinact.gateway.core.Core;
import org.eclipse.sensinact.gateway.core.DataResource;
import org.eclipse.sensinact.gateway.core.Session;
import org.eclipse.sensinact.gateway.generic.packet.InvalidPacketException;
import org.eclipse.sensinact.gateway.nthbnd.http.callback.CallbackContext;
import org.eclipse.sensinact.gateway.nthbnd.http.callback.CallbackService;
import org.eclipse.sensinact.gateway.nthbnd.http.callback.HttpRequestWrapper;
import org.eclipse.sensinact.gateway.sthbnd.wb.WarehouseBackendComponent;
import org.eclipse.sensinact.gateway.sthbnd.wb.WarehouseBackendTranslator;
import org.eclipse.sensinact.gateway.sthbnd.wb.packet.WarehouseBackendDiscoveryPacket;
import org.eclipse.sensinact.gateway.sthbnd.wb.packet.WarehouseBackendRobotDiscoveryPacket;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extended {@link CallbackService} dedicated warehouse backend updates  
 */
@Component(immediate=true,service= {CallbackService.class,WarehouseBackendAgentCallbackListener.class} )
public class WarehouseBackendCallbackService implements CallbackService, WarehouseBackendAgentCallbackListener {

	private static final Logger LOG = LoggerFactory.getLogger(WarehouseBackendCallbackService.class);
	
	private static final String CALLBACK_ENDPOINT="/warehouse-backend/*";

    private static Map<String, String> processRequestQuery(String queryString) {
        if (queryString == null) 
            return Collections.<String, String>emptyMap();
        
        Map<String, String> queryMap = new HashMap<String,String>();

        char[] characters = queryString.toCharArray();
        int index = 0;
        int length = characters.length;

        boolean escape = false;
        String name = null;
        String value = null;
        StringBuilder element = new StringBuilder();

        for (; index < length; index++) {
            char c = characters[index];
            if (escape) {
                escape = false;
                element.append(c);
                continue;
            }
            switch (c) {
                case '\\':
                    escape = true;
                    break;
                case '=':
                    if (name == null) {
                        name = element.toString();
                        element = new StringBuilder();

                    } else {
                        element.append(c);
                    }
                    break;
                case '&':
                	if(name == null && element.length()>0) {
                    	name = element.toString();
                        queryMap.put(name, Boolean.TRUE.toString());
                	} else {
	                    value = element.toString();
	                    queryMap.put(name, value);
                	}
                    name = null;
                    value = null;
                    element = new StringBuilder();
                    break;
                default:
                    element.append(c);
            }
        }
        if(name == null && element.length()>0) {
        	name = element.toString();
            queryMap.put(name, Boolean.TRUE.toString());
            return queryMap;
        }
        value = element.toString();
        queryMap.put(name, value);
        return queryMap;
    }
    
	@Reference
	private WarehouseBackendComponent warehouseBackendComponent;
	
	@Reference
	private WarehouseBackendTranslator translator;
	
	private Map<String,Boolean> assignations;  
	
	private String registration;
	
	@Activate
	public void activate() {
		 this.assignations = Collections.<String,Boolean>synchronizedMap(new HashMap<>());
	}
	
	@Deactivate
	public void deactivate() {
		this.assignations.clear();
		this.assignations = null;
		this.registration = null;
	}

	@Override	
    public int getCallbackType() {
	   return CallbackService.CALLBACK_SERVLET;
    }
   
	
	@Override	
	public String getPattern() {
		return CALLBACK_ENDPOINT;
	}

	@Override
	public Dictionary getProperties() {
		return new Hashtable() {{
			this.put("pattern", CALLBACK_ENDPOINT);
		}};
	}

	@Override
	public void updateAssignation(String id, boolean assigned) {
		this.assignations.put(id, Boolean.valueOf(assigned));
	}

	@Override
	public synchronized void process(CallbackContext context) {
		
		HttpRequestWrapper request = (HttpRequestWrapper)context.getRequest();
		Session session= context.getSession();
		String path = request.getRequestURI().substring(CALLBACK_ENDPOINT.length()-1);
		Map<String,String> queryMap = processRequestQuery(request.getQueryString());
		
		String content = null;
		String requestContent = null;
		try {
			requestContent = request.getContent();
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		String method = request.getMethod();
		switch(path) {
			case "assignation":
				if(!"GET".equals(method))
					break;
				String id = queryMap.get("id");
				if(id != null) {
					Boolean assigned = this.assignations.get(id);
					if(assigned == null) {
						setError(context, 404, "Unknown picking point");
						return;
					}
					content = String.format("{\"PPid\":\"%s\",\"assigned\":%s}", id, assigned.booleanValue());
				}
				break;
			case "pick":
				if(!"POST".equals(method) || requestContent ==null)
					break;
				List<? extends PickPoint> pPoints = Mapper.map(PickPoints.class, requestContent).getPickPoints();
				if(pPoints.isEmpty())
					break;
				for(PickPoint p : pPoints){
					try {
						double[] poseArray = p.getPose().toArray();
						warehouseBackendComponent.getEndpoint().process(new WarehouseBackendDiscoveryPacket("pick", p.getPPid()));
						session.set(p.getPPid(), "admin", "location", DataResource.VALUE, 
								this.translator.getDiffLatLng(poseArray[0],poseArray[1]).toString());
						session.set(p.getPPid(), "admin", "icon", DataResource.VALUE, "yellowFlag");
						session.set(p.getPPid(), "picking", "pos", DataResource.VALUE, p.getPose().toArray());
						session.set(p.getPPid(), "picking", "assigned", DataResource.VALUE, p.getIsAssigned());
						
						this.assignations.put(p.getPPid(), Boolean.valueOf(p.getIsAssigned()));
					} catch (Exception e) {
						LOG.error(e.getMessage(),e);
						setError(context, 520 , e.getMessage());
						return;
					}
				};
				content = "{\"message\": \"Picking point(s) created\"}";
				break;
			case "store":
				if(!"POST".equals(method) || requestContent ==null)
					break;
				List<? extends StoragePoint> sPoints = Mapper.map(StoragePoints.class, requestContent).getStoragePoints();
				if(sPoints.isEmpty())
					break;
				for(StoragePoint p :sPoints){
					try {
						double[] poseArray = p.getStoragePose().toArray();
						double[] poseAuxArray = p.getStorageAUX().toArray();
						
						warehouseBackendComponent.getEndpoint().process(new WarehouseBackendDiscoveryPacket("store", p.getSTid()));
						session.set(p.getSTid(), "admin", "location", DataResource.VALUE, 
								this.translator.getDiffLatLng(poseArray[0],poseArray[1]).toString());
						session.set(p.getSTid(), "admin", "icon", DataResource.VALUE, "redFlag");
						session.set(p.getSTid(), "storing", "pos", DataResource.VALUE, poseArray);
						session.set(p.getSTid(), "storing", "aux", DataResource.VALUE, poseAuxArray);
						
						warehouseBackendComponent.getEndpoint().process(new WarehouseBackendDiscoveryPacket("store", p.getSTid().concat("_AUX")));
						session.set(p.getSTid().concat("_AUX"), "admin", "location", DataResource.VALUE, 
							this.translator.getDiffLatLng(poseAuxArray[0],poseAuxArray[1]).toString());
						session.set(p.getSTid().concat("_AUX"), "admin", "icon", DataResource.VALUE, "doorRed");
					} catch (Exception e) {
						LOG.error(e.getMessage(),e);
						setError(context, 520 , e.getMessage());
						return;
					}
				};
				content = "{\"message\": \"Storing point(s) created\"}";
				break;
			case "card":
				if(!"POST".equals(method) || requestContent ==null)
					break;
				List<? extends CartStorage> cstorages = Mapper.map(CartStorages.class, requestContent).getCartStorages();
				if(cstorages.isEmpty())
					break;
				for(CartStorage c : cstorages){
					session.set(c.getStorageID(), "storing", "card", DataResource.VALUE, c.getCartID());
				};
				content = "{\"message\": \"Card identifier(s) defined\"}";
				break;
			case "dock":
				if(!"POST".equals(method) || requestContent == null)
					break;
				List<? extends DockPoint> dPoints = Mapper.map(DockPoints.class, requestContent).getDockPoints();
				if(dPoints.isEmpty())
					break;
				for(DockPoint p : dPoints){
					try {
						double[] poseArray = p.getDockPose().toArray();
						double[] poseAuxArray = p.getDockAUX().toArray();
						String dpID = "dp".concat(p.getIPid());
						String robotID = "robot".concat(p.getIPid());
						String dpIDAux = dpID.concat("_AUX");
						warehouseBackendComponent.getEndpoint().process(new WarehouseBackendDiscoveryPacket("dock", dpID ));
						session.set(dpID, "admin", "location", DataResource.VALUE, this.translator.getDiffLatLng(poseArray[0],poseArray[1]).toString());
						session.set(dpID, "admin", "icon", DataResource.VALUE, "blackFlag");
						session.set(dpID, "docking", "pos", DataResource.VALUE, poseArray);
						session.set(dpID, "docking", "aux", DataResource.VALUE, poseAuxArray);
						
						warehouseBackendComponent.getEndpoint().process(new WarehouseBackendDiscoveryPacket("dock", dpIDAux));
						session.set(dpIDAux, "admin", "location", DataResource.VALUE,this.translator.getDiffLatLng(poseAuxArray[0],poseAuxArray[1]).toString());
						session.set(dpIDAux, "admin", "icon", DataResource.VALUE, "door");
						
						if(session.get(robotID, "admin", "icon",DataResource.VALUE).getStatusCode()==404)
							warehouseBackendComponent.getEndpoint().process(new WarehouseBackendRobotDiscoveryPacket(robotID));
						session.set(robotID, "admin", "icon", DataResource.VALUE, robotID);
						
					} catch (Exception e) {
						LOG.error(e.getMessage(),e);
						setError(context, 520 , e.getMessage());
						return;
					}
				};
				content = "{\"message\": \"Docking point(s) created\"}";
				break;
		}
		try {
			context.getResponse().setContent(content.getBytes());
			context.getResponse().setResponseStatus(200);		
			context.getResponse().flush();
		} catch (Exception e) {
			setError(context, 520, new StringBuilder().append("Internal server error :\n").append(e.getClass().getName()
					).append("\n").append(e.getMessage()).toString());
		}
	}
	
	private void setError(CallbackContext context, int status, String message) {
		try {
			context.getResponse().setContent(message.getBytes());
			context.getResponse().setResponseStatus(status);		
			context.getResponse().flush();
		} catch (Exception ex) {
			LOG.error(ex.getMessage(),ex);
		}
	}
}
