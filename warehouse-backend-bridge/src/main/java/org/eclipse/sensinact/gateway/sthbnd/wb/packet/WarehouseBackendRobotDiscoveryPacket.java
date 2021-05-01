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
import org.eclipse.sensinact.gateway.generic.packet.annotation.HelloMessage;
import org.eclipse.sensinact.gateway.generic.packet.annotation.ProfileID;
import org.eclipse.sensinact.gateway.generic.packet.annotation.ServiceProviderID;

/**
 * Extended {@link Packet} dedicated to robot service provider discovering
 */
public class WarehouseBackendRobotDiscoveryPacket implements WarehouseBackendPacket {

	private String id;
	
	public WarehouseBackendRobotDiscoveryPacket(String id) throws InvalidPacketException {
		this.id = id;
	}
	
	@ServiceProviderID
	public String getServiceProviderId() {
		return this.id;
	}

	@HelloMessage
	public boolean isHelloMessage() {
		return true;
	}

	@Override
	public byte[] getBytes() {
		return null;
	}
}
