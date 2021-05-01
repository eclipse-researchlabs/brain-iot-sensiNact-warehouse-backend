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

import org.eclipse.sensinact.gateway.common.bundle.Mediator;
import org.eclipse.sensinact.gateway.common.execution.Executable;
import org.eclipse.sensinact.gateway.core.DataResource;
import org.eclipse.sensinact.gateway.core.message.AgentRelay;
import org.eclipse.sensinact.gateway.core.message.MidCallbackException;
import org.eclipse.sensinact.gateway.core.message.SnaFilter;
import org.eclipse.sensinact.gateway.core.message.SnaMessage;
import org.eclipse.sensinact.gateway.core.message.SnaUpdateMessageImpl;
import org.eclipse.sensinact.gateway.core.message.annotation.Filter;
import org.eclipse.sensinact.gateway.core.message.whiteboard.AbstractAgentRelay;
import org.eclipse.sensinact.gateway.util.UriUtils;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Agent listening for pick points assignation updates
 */
@Filter(handled = {SnaMessage.Type.UPDATE}, sender = "/[^/]+/picking/assigned(/value)?", isPattern = true)
@Component(immediate=true,service=AgentRelay.class)
public class WarehouseBackendAgentCallback extends AbstractAgentRelay {

	private static final Logger LOG = LoggerFactory.getLogger(WarehouseBackendAgentCallback.class);
	
	private Mediator mediator;
	
	@Activate
	public void activate(ComponentContext componentContext) {
		this.mediator = new Mediator(componentContext.getBundleContext());
	}
	
	@Override
	public void doHandle(SnaUpdateMessageImpl message) throws MidCallbackException {
		System.out.println(message.getJSON());
		try {
			final String id = UriUtils.getUriElements(message.getPath())[0];
			final boolean assigned = message.getNotification(boolean.class, DataResource.VALUE);
			
			this.mediator.callServices(WarehouseBackendAgentCallbackListener.class, 
			new Executable<WarehouseBackendAgentCallbackListener,Void>(){
				public Void execute(WarehouseBackendAgentCallbackListener warehouseBackendAgentCallbackListener) 
				throws Exception{
					warehouseBackendAgentCallbackListener.updateAssignation(id, assigned);
					return null;
				}
			});
		} catch(Exception e) {
			LOG.error(e.getMessage(),e);
			throw new MidCallbackException(e);
		}
	}
	
}
