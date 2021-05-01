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

public class StoragePoints {

    @JsonProperty(value="Storage_Points")   
    private List<StoragePoint> storagePoints;

    public StoragePoints() {	
    }
    
    public StoragePoints(List<StoragePoint> storagePoints) {    
    	this.storagePoints = storagePoints;
    }   
    
    public List<StoragePoint> getStoragePoints() {
        return this.storagePoints;
    }

    public void setStoragePoints(List<StoragePoint> storagePoints) {
        this.storagePoints = storagePoints;
    }
}
