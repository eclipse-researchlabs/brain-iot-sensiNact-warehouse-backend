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
package org.eclipse.sensinact.gateway.sthbnd.wb;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import org.eclipse.sensinact.gateway.common.bundle.Mediator;
import org.eclipse.sensinact.gateway.common.interpolator.Interpolator;
import org.eclipse.sensinact.gateway.core.SensiNactResourceModelConfiguration.BuildPolicy;
import org.eclipse.sensinact.gateway.generic.ExtModelConfiguration;
import org.eclipse.sensinact.gateway.generic.ExtModelConfigurationBuilder;
import org.eclipse.sensinact.gateway.generic.InvalidProtocolStackException;
import org.eclipse.sensinact.gateway.generic.local.LocalProtocolStackEndpoint;
import org.eclipse.sensinact.gateway.sthbnd.wb.packet.WarehouseBackendPacket;
import org.eclipse.sensinact.gateway.sthbnd.wb.servlet.IndexFilter;
import org.eclipse.sensinact.gateway.sthbnd.wb.servlet.MirrorServlet;
import org.eclipse.sensinact.gateway.sthbnd.wb.servlet.ResourceFilter;
import org.eclipse.sensinact.gateway.sthbnd.wb.smartbehaviour.UpdateResponseListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

@Component(immediate = true, service=WarehouseBackendComponent.class)
public class WarehouseBackendComponent {

    private static final String WB_ALIAS = "/wb";
    
	@Reference
	private UpdateResponseListener updateResponseListener;
	
    private LocalProtocolStackEndpoint<WarehouseBackendPacket> endpoint;
	private Mediator mediator;

    private void injectPropertyFields() throws Exception {
        this.mediator.debug("Starting introspection in bundle %s", mediator.getContext().getBundle().getSymbolicName());
        Interpolator interpolator = new Interpolator(this.mediator);
        interpolator.getInstance(this);
        for(Map.Entry<String,String> entry:interpolator.getPropertiesInjected().entrySet()){
            if(mediator.getProperty(entry.getKey())==null)
                mediator.setProperty(entry.getKey(),entry.getValue());
        }
    }
    
	@Activate
    public void activate(ComponentContext context) {    	
		this.mediator = new Mediator(context.getBundleContext());
	    try {
			injectPropertyFields();
		} catch (Exception e) {
			e.printStackTrace();
			this.mediator.error(e);
		}
	    MirrorServlet mirror = new MirrorServlet();
        mediator.register(mirror, Servlet.class, new Hashtable() {{
        	this.put(Constants.SERVICE_RANKING, 3);
            this.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, WB_ALIAS);
            this.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,"("+HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME+"=default)");
            }
        });
        mediator.info(String.format("%s servlet registered", WB_ALIAS));
        mediator.register(new IndexFilter(WB_ALIAS), Filter.class, new Hashtable() {{
        	this.put(Constants.SERVICE_RANKING, 3);
            this.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN, WB_ALIAS);
            this.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,"("+HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME+"=default)");
            }
        });
        mediator.info(String.format("%s filter registered", WB_ALIAS));

        mediator.register(mirror, Servlet.class, new Hashtable() {{
        	this.put(Constants.SERVICE_RANKING, 2);
            this.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,  WB_ALIAS+"/*");
            this.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,"("+HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME+"=default)");
            }
        });
        mediator.info(String.format("%s servlet registered",WB_ALIAS+"/*"));
        
        mediator.register(new ResourceFilter(mediator), Filter.class, new Hashtable() {{
        	this.put(Constants.SERVICE_RANKING, 2);
            this.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN, WB_ALIAS+"/*");
            this.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,"("+HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME+"=default)");
            }
        });
        mediator.info(String.format("%s filter registered", WB_ALIAS+"/*"));
        
    	ExtModelConfiguration<WarehouseBackendPacket> config = 
    	ExtModelConfigurationBuilder.instance(mediator, WarehouseBackendPacket.class
   	    ).withResourceBuildPolicy((byte) (BuildPolicy.BUILD_COMPLETE_ON_DESCRIPTION.getPolicy() |BuildPolicy.BUILD_NON_DESCRIBED.getPolicy())
   	    ).withServiceBuildPolicy((byte) (BuildPolicy.BUILD_COMPLETE_ON_DESCRIPTION.getPolicy() |BuildPolicy.BUILD_NON_DESCRIBED.getPolicy())
    	).withStartAtInitializationTime(true
    	).build("resources.xml", Collections.<String,String>emptyMap());
    	
    	this.endpoint = new LocalProtocolStackEndpoint<WarehouseBackendPacket>(mediator);
    	this.endpoint.addInjectableInstance(UpdateResponseListener.class, updateResponseListener);
        try {
			this.endpoint.connect(config);
		} catch (InvalidProtocolStackException e) {
			e.printStackTrace();
			this.mediator.error(e);
		}
    }

	@Deactivate
    public void deactivate(BundleContext context) throws Exception {
        try{
        	this.endpoint.stop();
        }catch (Exception e){
           this.mediator.error(e);
        }
        if (this.mediator != null) 
            this.mediator.deactivate();        
        this.mediator = null;
    }
	
	public LocalProtocolStackEndpoint<WarehouseBackendPacket> getEndpoint(){
		return this.endpoint;
	}

	public Mediator getMediator() {
		return this.mediator;
	}
}