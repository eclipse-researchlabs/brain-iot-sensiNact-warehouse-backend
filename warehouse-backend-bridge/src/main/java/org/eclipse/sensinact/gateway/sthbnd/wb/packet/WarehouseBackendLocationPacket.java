/*
 * Copyright (c) 2020 - 2021 Kentyou.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
*    Kentyou - initial API and implementation
*/
package org.eclipse.sensinact.gateway.sthbnd.wb.packet;

import org.eclipse.sensinact.gateway.generic.packet.InvalidPacketException;
import org.eclipse.sensinact.gateway.generic.packet.annotation.Data;
import org.eclipse.sensinact.gateway.generic.packet.annotation.ResourceID;
import org.eclipse.sensinact.gateway.generic.packet.annotation.ServiceID;
import org.eclipse.sensinact.gateway.generic.packet.annotation.ServiceProviderID;

import eu.brain.iot.robot.events.RobotPosition;
import eu.brain.iot.warehouse.sensiNact.api.PickingPointUpdateNotice;

/**
 * Extended {@link Packet} dedicated to Robot's location update
 */
public class WarehouseBackendLocationPacket implements WarehouseBackendPacket {

	private String location;
	private String robotId;
	
	public WarehouseBackendLocationPacket(String robotId, String location) throws InvalidPacketException {
		this.location = location;
		this.robotId = robotId;
	}

	@ServiceProviderID
	public String getServiceProviderId() {
		return this.robotId;
	}

	@ServiceID
	public String getServiceId() {
		return "admin";
	}

	@ResourceID
	public String getResourceId() {
		return "location";		
	}
	
	@Data
	public Object getData() {
		return this.location;
	}

	@Override
	public byte[] getBytes() {
		return null;
	}
}
