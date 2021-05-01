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

import eu.brain.iot.warehouse.sensiNact.api.PickingPointUpdateNotice;

/**
 * Extended {@link Packet} dedicated to pick point assignation
 */
public class WarehouseBackendPickingPacket implements WarehouseBackendPacket {

	private boolean assigned;
	private String pickID;
	
	public WarehouseBackendPickingPacket(PickingPointUpdateNotice pickingPointUpdateNotice) throws InvalidPacketException {
		this.assigned = pickingPointUpdateNotice.isAssigned;
		this.pickID = pickingPointUpdateNotice.pickID;
	}

	@ServiceProviderID
	public String getServiceProviderId() {
		return this.pickID;
	}

	@ServiceID
	public String getServiceId() {
		return "picking";
	}

	@ResourceID
	public String getResourceId() {
		return "assigned";		
	}
	
	@Data
	public Object getData() {
		return this.assigned;
	}

	@Override
	public byte[] getBytes() {
		return null;
	}
}
