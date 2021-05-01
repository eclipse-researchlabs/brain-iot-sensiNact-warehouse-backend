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

public class StoragePoint {

    @JsonProperty("STid")
    private String sTid;
    
    @JsonProperty("storageAUX")
    private XYZPoint storageAUX;
    
    @JsonProperty("storagePose")
    private XYZPoint storagePose;

    public String getSTid() {
        return sTid;
    }

    public void setSTid(String sTid) {
        this.sTid = sTid;
    }

    public XYZPoint getStorageAUX() {
        return storageAUX;
    }

    public void setStorageAUX(XYZPoint storageAUX) {
        this.storageAUX = storageAUX;
    }

    public XYZPoint getStoragePose() {
        return storagePose;
    }

    public void setStoragePose(XYZPoint storagePose) {
        this.storagePose = storagePose;
    }
}
