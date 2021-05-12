/*
*   Copyright (c) 2020 - 2021 Kentyou.
*   All rights reserved. This program and the accompanying materials
*   are made available under the terms of the Eclipse Public License v1.0
*   which accompanies this distribution, and is available at
*   http://www.eclipse.org/legal/epl-v10.html
*  
*   Contributors:
*      Kentyou - initial API and implementation
*/

init = {};
init["lat"] = 39.507938;
init["lng"] = -0.462007;
init["zoom"]= 20;

var redFlag = L.icon({
    iconUrl: 'css/images/flag-red.png',
    iconSize: [29, 29],
    iconAnchor: [20, 41],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [20, 41]
});
icons['redFlag']=redFlag;

var yellowFlag = L.icon({
    iconUrl: 'css/images/flag-yel.png',
    iconSize: [29, 29],
    iconAnchor: [0, 0],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [20, 41]
});
icons['yellowFlag']=yellowFlag;

var blackFlag = L.icon({
    iconUrl: 'css/images/flag-black.png',
    iconSize: [29, 29],
    iconAnchor: [20, 41],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [20, 41]
});
icons['blackFlag']=blackFlag;

var door = L.icon({
    iconUrl: 'css/images/door.png',
    iconSize: [29, 29],
    iconAnchor: [20, 41],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [20, 41]
});
icons['door']=door;

var doorRed = L.icon({
    iconUrl: 'css/images/door-red.png',
    iconSize: [29, 29],
    iconAnchor: [20, 41],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [20, 41]
});
icons['doorRed']=doorRed;

var robot1 = L.icon({
    iconUrl: 'css/images/robot1.png',
    iconSize: [40, 40],
    iconAnchor: [0, 40],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [20, 41]
});
icons['robot1']=robot1;

var robot2 = L.icon({
    iconUrl: 'css/images/robot2.png',
    iconSize: [40, 40],
    iconAnchor: [0, 40],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [20, 41]
});
icons['robot2']=robot2;

var robot3 = L.icon({
    iconUrl: 'css/images/robot3.png',
    iconSize: [40, 40],
    iconAnchor: [0, 40],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [20, 41]
});
icons['robot3']=robot3;

var robot4 = L.icon({
    iconUrl: 'css/images/robot4.png',
    iconSize: [40, 40],
    iconAnchor: [0, 40],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [20, 41]
});
icons['robot4']=robot4;

var robot5 = L.icon({
    iconUrl: 'css/images/robot5.png',
    iconSize: [40, 40],
    iconAnchor: [0, 40],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [20, 41]
});
icons['robot5']=robot5;

var robot6 = L.icon({
    iconUrl: 'css/images/robot6.png',
    iconSize: [40, 40],
    iconAnchor: [0, 40],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [20, 41]
});
icons['robot6']=robot6;

function layerPointToLatLng(x,y) {
  console.log([x, y]);
  // calculate point in xy space
  var pointXY = L.point(x, y);
  console.log("Point in x,y space: " + pointXY);
  // convert to lat/lng space
  var pointlatlng = map.layerPointToLatLng(pointXY);
  console.log("Point in lat,lng space: " + pointlatlng);
  return pointlatlng;
}

function latLngToLayerPoint(lat,lng){
  var ltlg = L.latLng(lat,lng);
  console.log("Point in lat,lng space: " + ltlg);
  // calculate point in xy space
  var pointXY = map.latLngToLayerPoint(ltlg);
  console.log("Point in x,y space: " + pointXY);
  return pointXY;
}

function getDistance(lat1, lng1, lat2, lng2) {
	
   var DEGREES_TO_RADIUS_COEF = Math.PI / 180.0;
   var A = 6378137.0;
    
   var dlong = ((lng2 - lng1) * DEGREES_TO_RADIUS_COEF) / 2;
   var dlat = ((lat2 - lat1) * DEGREES_TO_RADIUS_COEF) / 2;
   var lat1_rad = lat1 * DEGREES_TO_RADIUS_COEF;
   var lat2_rad = lat2 * DEGREES_TO_RADIUS_COEF;
   var c = Math.pow(Math.sin(dlat), 2.0) + Math.pow(Math.sin(dlong), 2.0) * Math.cos(lat1_rad) * Math.cos(lat2_rad);
   var d = 2 * Math.atan2( Math.sqrt(c),Math.sqrt(1 - c));
   return (A * d);
}

function getDiffLatLng(origin,dx,dy) {
	
	var DEGREES_TO_RADIUS_COEF = Math.PI / 180.0;
	var RADIUS_TO_DEGREES_COEF = 180.0 / Math.PI;
	var A = 6378137.0;
    var COEF = 1.0;//2.609881.0;
    
	if(dx==0.0 && dy==0.0)
		return  origin;

    var agl = 0.0;
    var brg = 0.0;
	
	if(dy == 0)
		agl = dx >= 0?0:180;
	else if(dx == 0)
		agl = dy >= 0?90:270;
	else 	{
		agl = RADIUS_TO_DEGREES_COEF * Math.atan((dy/dx));
		agl = agl + (dy<0 && dx<0?180:0);
	}
	var bearing=agl;
	if(agl < 90)
		bearing = 90-agl;
	else if(agl <180)
		bearing = 270+(180-agl);
	else if(agl < 270)
		bearing = 180+(270-agl);
	else
		bearing = 90+(360-agl);		
	brg = DEGREES_TO_RADIUS_COEF * bearing;
	var distance = Math.sqrt((Math.pow(Math.abs(dx),2)+ Math.pow(Math.abs(dy),2)));
	
	var lat1 = origin.lat * DEGREES_TO_RADIUS_COEF;
	var lng1 = origin.lng * DEGREES_TO_RADIUS_COEF;

	var s1 = distance / A;

	var lat2 = Math.asin(Math.sin(lat1) * Math.cos(s1) + Math.cos(lat1) * Math.sin(s1) * Math.cos(brg));
	var lng2 = lng1 + Math.atan2(Math.sin(brg) * Math.sin(s1) * Math.cos(lat1), Math.cos(s1) - Math.sin(lat1) * Math.sin(lat2));

    lat2 = lat2 * RADIUS_TO_DEGREES_COEF;
    lng2 = lng2 * RADIUS_TO_DEGREES_COEF;
    var destination = L.latLng(lat2,lng2);
    return destination;
}     

function updateDeviceLocationOld(lat, lng, deviceName, iconName) {		
	var markerCache = markers[deviceName]; 
    if (markerCache === undefined) {
        var iconObject = getIcon(iconName)
		var content = "<p><b>" + deviceName + "</b></p><p> please wait during information refresh...</p>";
		var marker = L.marker([lat, lng], {draggable:false, title:deviceName, icon:iconObject}).bindPopup(content,{maxWidth:600});
		marker.on('click',function(e) {updatePopupAtInit(e,deviceName);} );
		map.addLayer(marker);
		markers[deviceName] = marker;
    } else {
    	markerCache.setLatLng(new L.latLng(lat, lng));
    	markerCache.update();
    }
    return Boolean("true");
}

function updateDeviceLocationNew(x, y, deviceName, iconName) {		
	var devicePoint = L.point(mapOrigin.x + (x * scale), mapOrigin.y + (y * scale * -1));
	var deviceLocation = layerPointToLatLng(devicePoint.x,devicePoint.y);
    var lat = deviceLocation.lat;
    var lng = deviceLocation.lng;
    	
	var markerCache = markers[deviceName]; 
    if (markerCache === undefined) {
        var iconObject = getIcon(iconName)
		var content = "<p><b>" + deviceName + "</b></p><p> please wait during information refresh...</p>";
		var marker = L.marker([lat, lng], {draggable:false, title:deviceName, icon:iconObject}).bindPopup(content,{maxWidth:600});
		marker.on('click',function(e) {updatePopupAtInit(e,deviceName);} );
		brainiotGroup.addLayer(marker);
		markers[deviceName] = marker;
    } else {
    	markerCache.setLatLng(new L.latLng(lat, lng));
    	markerCache.update();
    }
    return Boolean("true");
}

function updateDeviceLocation(lat, lng, deviceName, iconName) {		
	var markerCache = markers[deviceName]; 
    if (markerCache === undefined) {
        var iconObject = getIcon(iconName)
		var content = "<p><b>" + deviceName + "</b></p><p> please wait during information refresh...</p>";
		var marker = L.marker([lat, lng], {draggable:false, title:deviceName, icon:iconObject}).bindPopup(content,{maxWidth:600});
		marker.on('click',function(e) {updatePopupAtInit(e,deviceName);} );
		brainiotGroup.addLayer(marker);
		markers[deviceName] = marker;
    } else {
    	markerCache.setLatLng(new L.latLng(lat, lng));
    	markerCache.update();
    }
    return Boolean("true");
}
var westSthLt = 39.507938;
var westSthLng = -0.462007;

var ptpxcoef = 1.25;
var cmpxcoef = 37.7953;

var naturalWidth = 544;
var naturalHeight = 544;

var lwidth = 208.5;
var lheight = lwidth * (naturalHeight/naturalWidth);

var coef = lwidth/naturalWidth;
var scale= coef * (1/0.05);

var originx = 12.200000 * scale;
var originy = -13.800000 * scale;

var origin = {xvar:originx, yvar:originy, zvar:0.000000};

var tmpEast = latLngToLayerPoint(L.latLng(westSthLt, westSthLng));
tmpEast.x = tmpEast.x + lwidth/2;
tmpEast.y = tmpEast.y - lheight/2;

var newTmpEast = layerPointToLatLng(tmpEast.x, tmpEast.y);
var eastNthLt = newTmpEast.lat;
var eastNthLng = newTmpEast.lng;

var westSth = latLngToLayerPoint(L.latLng(westSthLt, westSthLng));
westSth.x = westSth.x - lwidth/2;
westSth.y = westSth.y + lheight/2;

var newWestSth = layerPointToLatLng(westSth.x, westSth.y);
westSthLt = newWestSth.lat;
westSthLng = newWestSth.lng;

var mapOriginx = westSth.x + originx;
var mapOriginy = westSth.y + originy;
var mapOrigin = {x:mapOriginx,y:mapOriginy};

var MyOrigin = layerPointToLatLng(mapOriginx, mapOriginy);
console.info("ORIGIN LAT:" + MyOrigin.lat);
console.info("ORIGIN LNG:" + MyOrigin.lng);

var tmpMiddle = layerPointToLatLng(westSth.x+lwidth/4, westSth.y-lheight/4);

var imageBoundsBrainIoT = [[westSthLt,westSthLng],[eastNthLt,eastNthLng]];
var brainiot = L.imageOverlay('images/brainiot.png', imageBoundsBrainIoT );
var brainiotGroup = L.layerGroup([brainiot],{collapsed: false, opacity: 1});

map.setView([tmpMiddle.lat,tmpMiddle.lng]);

var removed = true;
map.on('zoomend', function(e) {
  if(e.target._zoom >= 20 && removed) {
	brainiotGroup.addTo(map);
	removed = false;
  } else if(e.target._zoom < 20 && !removed){
	brainiotGroup.remove();
	removed = true;
  }
});

//updateDeviceLocationOld(westSthLt,westSthLng,'west_south','default');
//updateDeviceLocationOld(eastNthLt,eastNthLng,'east_north','default');

var PP1= {x:6.802, y:-0.690,z:-0.661}; 
var PP2= {x:8.119, y:-0.893,z:-0.660};
var PP3= {x:8.520, y:-0.594,z:0.050};
var PP4= {x:8.520, y:-0.594,z:0.050};
var PP5= {x:7.684, y:0.729, z:0.739};
var PP6= {x:6.271, y:0.558, z:0.711};

//updateDeviceLocationNew(PP1.x, PP1.y, 'pp1', 'yellowFlag');
//updateDeviceLocationNew(PP2.x, PP2.y, 'pp2', 'yellowFlag');
//updateDeviceLocation(PP3.x, PP3.y, 'pp3', 'yellowFlag');
//updateDeviceLocation(PP4.x, PP4.y, 'pp4', 'yellowFlag');
//updateDeviceLocation(PP5.x, PP5.y, 'pp5', 'yellowFlag');
//updateDeviceLocation(PP6.x, PP6.y, 'pp6', 'yellowFlag');

var ST1x= {x:5.274, y:-0.292, z:-0.995};
var ST2x= {x:5.274, y:-0.292, z:-0.995};
var ST3x= {x:5.274, y:-0.292, z:-0.995};
var ST4x= {x:5.274, y:-0.292, z:-0.995};
var ST5x= {x:5.274, y:-0.292, z:-0.995};
var ST6x= {x:5.274, y:-0.292, z:-0.995};

//updateDeviceLocation(ST1x.x, ST1x.y, 'st1Aux', 'doorRed');
//updateDeviceLocation(ST2x.x, ST2x.y, 'st2Aux', 'doorRed');
//updateDeviceLocation(ST3x.x, ST3x.y, 'st3Aux', 'doorRed');
//updateDeviceLocation(ST4x.x, ST4x.y, 'st4Aux', 'doorRed');
//updateDeviceLocation(ST5x.x, ST5x.y, 'st5Aux', 'doorRed');
//updateDeviceLocation(ST6x.x, ST6x.y, 'st6Aux', 'doorRed');

var ST1= {x:0.285, y:-2.057, z:-0.995};
var ST2= {x:0.347, y:-1.146, z:-0.997};
var ST3= {x:-0.194, y:-0.326,z:-0.995};
var ST4= {x:0.036, y:0.435, z:-0.998};
var ST5= {x:3.240, y:-1.982, z:-0.643};
var ST6= {x:2.360, y:1.177, z:0.756};

//updateDeviceLocation(ST1.x, ST1.y, 'st1', 'redFlag');
//updateDeviceLocation(ST2.x, ST2.y, 'st2', 'redFlag');
//updateDeviceLocation(ST3.x, ST3.y, 'st3', 'redFlag');
//updateDeviceLocation(ST4.x, ST4.y, 'st4', 'redFlag');
//updateDeviceLocation(ST5.x, ST5.y, 'st5', 'redFlag');
//updateDeviceLocation(ST6.x, ST6.y, 'st6', 'redFlag');

var DP1x= {x:3.725,y:-0.465, z:0.07};
var DP2x= {x:3.725,y:-0.465, z:0.07};
var DP3x= {x:3.725,y:-0.465, z:0.07};
var DP4x= {x:3.725,y:-0.465, z:0.07};
var DP5x= {x:3.725,y:-0.465, z:0.07};
var DP6x= {x:3.725,y:-0.465, z:0.07};

//updateDeviceLocation(DP1x.x, DP1x.y, 'dp1Aux', 'door');
//updateDeviceLocation(DP2x.x, DP2x.y, 'dp2Aux', 'door');
//updateDeviceLocation(DP3x.x, DP3x.y, 'dp3Aux', 'door');
//updateDeviceLocation(DP4x.x, DP4x.y, 'dp4Aux', 'door');
//updateDeviceLocation(DP5x.x, DP5x.y, 'dp5Aux', 'door');
//updateDeviceLocation(DP6x.x, DP6x.y, 'dp6Aux', 'door');

var DP1= {x:5.480, y:1.593, z:-1.40};
var DP2= {x:4.917, y:1.473, z:-1.42};
var DP3= {x:4.994, y:0.620, z:-0.07};
var DP4= {x:5.003, y:-1.223,z:0.14};
var DP5= {x:5.304, y:-2.055,z:1.82};
var DP6= {x:5.918, y:-1.992,z:1.63};

//updateDeviceLocation(DP1.x, DP1.y, 'dp1', 'blackFlag');
//updateDeviceLocation(DP2.x, DP2.y, 'dp2', 'blackFlag');
//updateDeviceLocation(DP3.x, DP3.y, 'dp3', 'blackFlag');
//updateDeviceLocation(DP4.x, DP4.y, 'dp4', 'blackFlag');
//updateDeviceLocation(DP5.x, DP5.y, 'dp5', 'blackFlag');
//updateDeviceLocation(DP6.x, DP6.y, 'dp6', 'blackFlag');
