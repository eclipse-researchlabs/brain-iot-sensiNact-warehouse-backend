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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * JSON data structure Mapper
 */
public abstract class Mapper {

    private static final Logger LOG = LoggerFactory.getLogger(Mapper.class);
	
	public static <E>  E map(Class<E> clazz, String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			E e =mapper.readValue(json, clazz);
			return e;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return null;
	}	

	public static <E> String toJson(E e) {
		ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			String json  = mapper.writeValueAsString(e);
			return json;
		} catch (Exception ex) {
			LOG.error(ex.getMessage(),ex);
		}
		return null;
	}

}
