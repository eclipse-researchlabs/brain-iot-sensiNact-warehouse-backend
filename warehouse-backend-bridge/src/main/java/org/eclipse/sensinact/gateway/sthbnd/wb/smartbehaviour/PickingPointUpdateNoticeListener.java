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
package org.eclipse.sensinact.gateway.sthbnd.wb.smartbehaviour;

import org.eclipse.sensinact.gateway.generic.packet.InvalidPacketException;
import org.eclipse.sensinact.gateway.sthbnd.wb.WarehouseBackendComponent;
import org.eclipse.sensinact.gateway.sthbnd.wb.packet.WarehouseBackendPickingPacket;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import eu.brain.iot.eventing.annotation.SmartBehaviourDefinition;
import eu.brain.iot.eventing.api.SmartBehaviour;
import eu.brain.iot.warehouse.sensiNact.api.PickingPointUpdateNotice;

@Component(immediate=true,service=SmartBehaviour.class)
@SmartBehaviourDefinition(consumed = {PickingPointUpdateNotice.class}, filter="(timestamp=*)", name="Pick Point Updater")
public class PickingPointUpdateNoticeListener implements SmartBehaviour<PickingPointUpdateNotice>{
	
	@Reference
	WarehouseBackendComponent warehouseBackendComponent;
	
	@Override
	public void notify(PickingPointUpdateNotice event) {
		try {
			warehouseBackendComponent.getEndpoint().process(new WarehouseBackendPickingPacket(event));
		} catch (InvalidPacketException e) {
			e.printStackTrace();
		}
	}

}
