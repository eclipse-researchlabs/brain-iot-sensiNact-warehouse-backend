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

import com.fasterxml.jackson.annotation.JsonProperty;

public class PickPoint {

    @JsonProperty("PPid")
    private String pPid;
    
    @JsonProperty("pose")
    private XYZPoint pose;
    
    @JsonProperty("isAssigned")
    private boolean isAssigned;

    public PickPoint() {
    }
    
    public PickPoint(String pPid, XYZPoint pose, boolean isAssigned) {
    	this.pPid = pPid;
    	this.pose = pose;
    	this.isAssigned = isAssigned;
    }
    
    public String getPPid() {
        return pPid;
    }

    public void setPPid(String pPid) {
        this.pPid = pPid;
    }

    public XYZPoint getPose() {
        return pose;
    }

    public void setPose(XYZPoint pose) {
        this.pose = pose;
    }

    public boolean getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }
}
