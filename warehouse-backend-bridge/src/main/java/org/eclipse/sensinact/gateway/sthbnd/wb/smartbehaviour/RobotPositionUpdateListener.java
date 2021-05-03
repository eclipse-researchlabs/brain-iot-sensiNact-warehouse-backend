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
import org.eclipse.sensinact.gateway.sthbnd.wb.packet.WarehouseBackendLocationPacket;
import org.eclipse.sensinact.gateway.sthbnd.wb.WarehouseBackendTranslator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import eu.brain.iot.eventing.annotation.SmartBehaviourDefinition;
import eu.brain.iot.eventing.api.SmartBehaviour;
import eu.brain.iot.robot.events.RobotPosition;


@Component(immediate=true,service=SmartBehaviour.class)
@SmartBehaviourDefinition(consumed = {RobotPosition.class}, filter="(timestamp=*)", name="Robot Position Updater")
public class RobotPositionUpdateListener implements SmartBehaviour<RobotPosition>{
	
	@Reference
	WarehouseBackendComponent warehouseBackendComponent;

	@Reference
	private WarehouseBackendTranslator translator;
	
	@Override
	public void notify(RobotPosition robotPosition) {
		try {			
			warehouseBackendComponent.getEndpoint().process(new WarehouseBackendLocationPacket(
				"robot".concat(String.valueOf(robotPosition.robotID)), this.translator.getDiffLatLng(
					robotPosition.x, robotPosition.y).toString()));			
		} catch (InvalidPacketException e) {
			e.printStackTrace();
		}
	}

}
