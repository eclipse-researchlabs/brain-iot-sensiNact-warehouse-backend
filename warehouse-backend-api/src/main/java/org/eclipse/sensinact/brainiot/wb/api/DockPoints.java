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

public class DockPoints {

    @JsonProperty(value="Docking_Points")   
    private List<DockPoint> dockPoints;

    public DockPoints() {	
    }
    
    public DockPoints(List<DockPoint> dockPoints) {    
    	this.dockPoints = dockPoints;
    }   
    
    public List<DockPoint> getDockPoints() {
        return this.dockPoints;
    }

    public void setDockPoints(List<DockPoint> dockPoints) {
        this.dockPoints = dockPoints;
    }
}
