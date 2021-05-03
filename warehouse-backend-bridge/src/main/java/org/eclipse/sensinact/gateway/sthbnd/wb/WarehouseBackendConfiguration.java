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
package org.eclipse.sensinact.gateway.sthbnd.wb;

/**
 * Warehouse backend configuration
 */
public @interface WarehouseBackendConfiguration {
	double latitude();
	double longitude();
	double scale() default 1.0d;
}
