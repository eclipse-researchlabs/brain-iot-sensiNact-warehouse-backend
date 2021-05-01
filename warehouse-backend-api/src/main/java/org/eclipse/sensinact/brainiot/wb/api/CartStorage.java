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

public class CartStorage {
	
	@JsonProperty(value="cardID")
    private int cartID;
	
	@JsonProperty(value="storageID")
    private String storageID;

	public CartStorage() {
	}
	
	public CartStorage(int cartID,String storageID) {
		this.cartID = cartID;
		this.storageID = storageID;
	}
	
    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public String getStorageID() {
        return storageID;
    }

    public void setStorageID(String storageID) {
        this.storageID = storageID;
    }
}
