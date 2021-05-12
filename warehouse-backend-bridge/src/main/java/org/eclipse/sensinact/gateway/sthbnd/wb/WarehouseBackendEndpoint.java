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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.sensinact.gateway.common.bundle.Mediator;
import org.eclipse.sensinact.gateway.common.execution.Executable;
import org.eclipse.sensinact.gateway.generic.Task.CommandType;
import org.eclipse.sensinact.gateway.generic.annotation.TaskCommand;
import org.eclipse.sensinact.gateway.generic.annotation.TaskExecution;
import org.eclipse.sensinact.gateway.generic.annotation.TaskInject;
import org.eclipse.sensinact.gateway.sthbnd.wb.smartbehaviour.UpdateResponseListener;
import org.eclipse.sensinact.gateway.util.UriUtils;

import eu.brain.iot.eventing.api.EventBus;
import eu.brain.iot.warehouse.sensiNact.api.SensiNactCommand;
import eu.brain.iot.warehouse.sensiNact.api.UpdateCartStorage;
import eu.brain.iot.warehouse.sensiNact.api.UpdateDockPoint;
import eu.brain.iot.warehouse.sensiNact.api.UpdatePickPoint;
import eu.brain.iot.warehouse.sensiNact.api.UpdateStoragePoint;

/**
 * {@link TaskExecution} annotated POJO, in charge of translating sensiNact 
 * access method invocation into BrainIoT Events
 */
@TaskExecution
public class WarehouseBackendEndpoint {
	
	abstract static class SensiNactCommandBuilder<C extends SensiNactCommand> {
		
		public abstract C build();
		
		public abstract boolean complete();
	} 
	
	private static final class UpdateStoragePointBuilder extends SensiNactCommandBuilder<UpdateStoragePoint> {
		String storageID;
		String storageAUX;
		String storagePoint; 
		
		@Override
		public UpdateStoragePoint build() {
			if(!complete())
				return null;
			UpdateStoragePoint event = new UpdateStoragePoint();
			event.storageID = storageID;
			event.storageAUX = storageAUX;
			event.storagePoint = storagePoint;
			event.isSensiNactCMD = true;
			return event;
		}
		
		@Override
		public boolean complete() {
			return storageID!=null && storageAUX!=null && storagePoint!=null;
		}
		
	}

	private static final class UpdatePickPointBuilder extends SensiNactCommandBuilder<UpdatePickPoint> {
        String pickID;
		String  pickPoint;
		Boolean isAssigned;
		
		@Override
		public UpdatePickPoint build() {
			if(!complete())
				return null;
			UpdatePickPoint event = new UpdatePickPoint();
			event.pickID = pickID;
			event.isAssigned = isAssigned.booleanValue();
			event.pickPoint = pickPoint;
			event.isSensiNactCMD = true;
			return event;
		}

		@Override
		public boolean complete() {
			return pickID!=null && pickPoint!=null && isAssigned!=null;
		}
	}

	private static final class UpdateDockPointBuilder extends SensiNactCommandBuilder<UpdateDockPoint> {
		String robotIP;
		String dockAUX; 
		String dockPoint;  
		
		@Override
		public UpdateDockPoint build() {
			if(!complete())
				return null;
			UpdateDockPoint event = new UpdateDockPoint();
			event.robotIP = robotIP.substring(2);
			event.dockAUX = dockAUX;
			event.dockPoint = dockPoint;
			event.isSensiNactCMD = true;
			return event;
		}

		@Override
		public boolean complete() {
			return robotIP!=null && dockAUX!=null && dockPoint!=null;
		}
	}

	private static final class UpdateCartStorageBuilder extends SensiNactCommandBuilder<UpdateCartStorage> {
		Integer cartID;
		String storageID;
		   
		@Override
		public UpdateCartStorage build() {
			if(!complete())
				return null;
			UpdateCartStorage event = new UpdateCartStorage();
			event.cartID = cartID.intValue();
			event.storageID = storageID;
			event.isSensiNactCMD = true;
			return event;
		}

		@Override
		public boolean complete() {
			return cartID!=null && storageID!=null;
		}
	}
	
	@TaskInject
	private UpdateResponseListener updateResponseListener;
	
	private Mediator mediator;

	private Map<String, SensiNactCommandBuilder<? extends SensiNactCommand>> map;
	
	/**
	 * Constructor
	 * @param mediator the {@link Mediator} allowing the WarehouseBackendEndpoint to be instantiated
	 * to interact with the OSGi host environment
	 */
	public WarehouseBackendEndpoint(Mediator mediator) {
		this.mediator = mediator;
		this.map = Collections.<String, SensiNactCommandBuilder<? extends SensiNactCommand>>synchronizedMap(new HashMap<>());
	}

	@TaskCommand(target="/*/storing/*", synchronization=TaskCommand.SynchronizationPolicy.SYNCHRONOUS, method=CommandType.SET)
	public Object setStoring(String uri, String attributeName, Object value) {
		String[] uriElements = UriUtils.getUriElements(uri);
		boolean complete = false;
		switch(uriElements[2]) {
			case "pos":
			case "aux":
				UpdateStoragePointBuilder builder = (UpdateStoragePointBuilder) this.map.get(uriElements[0]);
				if(builder == null) {
					builder = new UpdateStoragePointBuilder();
					builder.storageID = uriElements[0];
					this.map.put(uriElements[0], builder);
				}
				String str = toString((double[]) value);
				if("pos".equals(uriElements[2])) 
					builder.storagePoint = 	str;		
				else if("aux".equals(uriElements[2]))
					builder.storageAUX  = str;
				complete = builder.complete();
				break;
			case "card":
				UpdateCartStorageBuilder updateCartStorageBuilder = new UpdateCartStorageBuilder();
				updateCartStorageBuilder.storageID = uriElements[0];
				updateCartStorageBuilder.cartID = Integer.valueOf((int)value);
				uriElements[0] = uriElements[0].concat("_cs");
				this.map.put(uriElements[0], updateCartStorageBuilder);
				complete = true;
				break;
		}
		if(complete)
			send(uriElements[0]);
		return null;
	}

	@TaskCommand(target="/*/picking/*", synchronization=TaskCommand.SynchronizationPolicy.SYNCHRONOUS, method=CommandType.SET)
	public Object setPicking(String uri, String attributeName, Object value) {
		String[] uriElements = UriUtils.getUriElements(uri);
		boolean complete = false;
		UpdatePickPointBuilder builder = (UpdatePickPointBuilder) this.map.get(uriElements[0]);
		if(builder == null) {
			builder = new UpdatePickPointBuilder();
			builder.pickID = uriElements[0];
			this.map.put(uriElements[0], builder);
		}
		switch(uriElements[2]) {
			case "pos":
				builder.pickPoint = toString((double[])value);
				break;
			case "assigned":
				builder.isAssigned = Boolean.valueOf((boolean)value);
				break;
		}
		complete = builder.complete();
		builder = null;
		if(complete)
			send(uriElements[0]);
		return null;
	}

	@TaskCommand(target="/*/docking/*", synchronization=TaskCommand.SynchronizationPolicy.SYNCHRONOUS, method=CommandType.SET)
	public Object setDocking(String uri, String attributeName, Object value) {
		String[] uriElements = UriUtils.getUriElements(uri);
		boolean complete = false;
		UpdateDockPointBuilder builder = (UpdateDockPointBuilder) this.map.get(uriElements[0]);
		if(builder == null) {
			builder = new UpdateDockPointBuilder();
			builder.robotIP = uriElements[0];
			this.map.put(uriElements[0], builder);
		}
		String str = toString((double[]) value);
		if("pos".equals(uriElements[2])) 
			builder.dockPoint = str;		
		else if("aux".equals(uriElements[2]))
			builder.dockAUX  = str;
		complete = builder.complete();
		builder = null;
		if(complete)
			send(uriElements[0]);
		return null;
	}
	
	private String toString(double[] value) {
		StringBuilder builder = new StringBuilder();
		for(int i=0;i < value.length;i++) {
			builder.append(String.valueOf(value[i]));
			if(i < value.length-1)
				builder.append(",");
		}
		return builder.toString();
	}
	
	private synchronized void send(String device) {
		SensiNactCommandBuilder<? extends SensiNactCommand> builder = this.map.remove(device);
		if(builder == null)
			return;
		final SensiNactCommand event= builder.build();
		if(event == null)
			return;
		mediator.callService(EventBus.class, new Executable<EventBus,Void>(){
			@Override
			public Void execute(EventBus bus) throws Exception {
				bus.deliver(event);
				return null;
			}			
		});
		
	}
}
