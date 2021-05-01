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

public class XYZPoint {

    @JsonProperty(value="x")
    private Double x;
    
    @JsonProperty(value="y")
    private Double y;
    
    @JsonProperty(value="z")
    private Double z;

    public XYZPoint() {
    }
    
    public XYZPoint(Double x,Double y,Double z) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    }
    
    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }
    
    @Override
    public String toString() {
    	return getX()+","+getY()+","+getZ();
    }
    
    public double[] toArray() {
    	return new double[] {getX().doubleValue(),getY().doubleValue(),getZ().doubleValue()};
    }
}
