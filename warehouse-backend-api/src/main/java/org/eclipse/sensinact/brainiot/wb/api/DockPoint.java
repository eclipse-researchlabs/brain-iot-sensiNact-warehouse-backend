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

public class DockPoint {

    @JsonProperty(value="IPid")   
    private String iPid;
   
    @JsonProperty(value="dockAUX")   
    private XYZPoint dockAUX;
    
    @JsonProperty(value="dockPose")
    private XYZPoint dockPose;

    public DockPoint() {	
    }
    
    public DockPoint(String iPid, XYZPoint dockAUX, XYZPoint dockPose) {    
    	this.iPid = iPid;
    	this.dockAUX = dockAUX;
    	this.dockPose = dockPose;
    }   
    
    public String getIPid() {
        return iPid;
    }

    public void setIPid(String iPid) {
        this.iPid = iPid;
    }

    public XYZPoint getDockAUX() {
        return dockAUX;
    }

    public void setDockAUX(XYZPoint dockAUX) {
        this.dockAUX = dockAUX;
    }

    public XYZPoint getDockPose() {
        return dockPose;
    }

    public void setDockPose(XYZPoint dockPose) {
        this.dockPose = dockPose;
    }
}
