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

import org.osgi.service.component.annotations.Component;

import eu.brain.iot.eventing.annotation.SmartBehaviourDefinition;
import eu.brain.iot.eventing.api.SmartBehaviour;
import eu.brain.iot.warehouse.sensiNact.api.UpdateResponse;

@Component(immediate=true,service= {SmartBehaviour.class,UpdateResponseListener.class})
@SmartBehaviourDefinition(consumed = {UpdateResponse.class}, filter="(timestamp=*)", name="Update Response Listener")
public class UpdateResponseListener implements SmartBehaviour<UpdateResponse> {
	
	@Override
	public void notify(UpdateResponse event) {
		System.out.println(event);
	}

}
