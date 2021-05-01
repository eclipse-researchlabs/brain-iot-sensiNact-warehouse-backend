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
package org.eclipse.sensinact.brainiot.wb.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PickPoints {

    @JsonProperty(value="Picking_Points")   
    private List<PickPoint> pickPoints;

    public PickPoints() {	
    }
    
    public PickPoints(List<PickPoint> pickPoints) {    
    	this.pickPoints = pickPoints;
    }   
    
    public List<PickPoint> getPickPoints() {
        return this.pickPoints;
    }

    public void setPickPoints(List<PickPoint> pickPoints) {
        this.pickPoints = pickPoints;
    }
}
