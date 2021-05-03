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

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

@Component(
		immediate = true, 
		service=WarehouseBackendTranslator.class,
		configurationPid = {"org.eclipse.sensinact.brainiot.warehouse.backend.config"},
		configurationPolicy = ConfigurationPolicy.REQUIRE)
public class WarehouseBackendTranslator {
    
	public static final double DEGREES_TO_RADIUS_COEF = Math.PI / 180.0d;
	public static final double RADIUS_TO_DEGREES_COEF = 180.0d / Math.PI;
	public static final double A = 6378137.0d;
    
	public class LatLng {
		public double lat;
		public double lng;
		@Override
		public String toString() {
			return new StringBuilder().append(this.lat).append(":").append(this.lng).toString();
		}
	}

	private WarehouseBackendConfiguration config;
	private LatLng origin;
	private double scale;
	
	
	@Activate
    public void activate(ComponentContext context, WarehouseBackendConfiguration config) {    	
		this.config = config;
		this.origin  = new LatLng();
		this.origin.lat = this.config.latitude();
		this.origin.lng = this.config.longitude();
		this.scale = config.scale();
    }

	public LatLng getDiffLatLng(double dx, double dy) {
		return this.getDiffLatLng(this.origin, dx*this.scale, dy*this.scale);
	} 
	
	private LatLng getDiffLatLng(LatLng origin, double dx, double dy) {
		if(dx==0d && dy==0d)
			return  origin;

	    double agl = 0d;
	    double brg = 0d;
		
		if(dy == 0)
			agl = dx >= 0?0:180;
		else if(dx == 0)
			agl = dy >= 0?90:270;
		else 	{
			agl = RADIUS_TO_DEGREES_COEF * Math.atan((dy/dx));
			agl = agl + (dy<0 && dx<0?180:0);
		}
		double bearing=agl;
		if(agl < 90)
			bearing = 90-agl;
		else if(agl <180)
			bearing = 270+(180-agl);
		else if(agl < 270)
			bearing = 180+(270-agl);
		else
			bearing = 90+(360-agl);		
		brg = DEGREES_TO_RADIUS_COEF * bearing;
		double distance = Math.sqrt((Math.pow(Math.abs(dx),2)+ Math.pow(Math.abs(dy),2)));
		
		double lat1 = origin.lat * DEGREES_TO_RADIUS_COEF;
		double lng1 = origin.lng * DEGREES_TO_RADIUS_COEF;

		double s1 = distance / A;

		double lat2 = Math.asin(Math.sin(lat1) * Math.cos(s1) + Math.cos(lat1) * Math.sin(s1) * Math.cos(brg));
		double lng2 = lng1 + Math.atan2(Math.sin(brg) * Math.sin(s1) * Math.cos(lat1), Math.cos(s1) - Math.sin(lat1) * Math.sin(lat2));

	    lat2 = lat2 * RADIUS_TO_DEGREES_COEF;
	    lng2 = lng2 * RADIUS_TO_DEGREES_COEF;
	    LatLng destination = new LatLng();
	    destination.lat = lat2;
	    destination.lng = lng2;
	    return destination;
	} 

	
	
}