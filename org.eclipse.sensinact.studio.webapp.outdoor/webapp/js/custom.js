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

// For debug purpose, for the eclipse webview...
function toStr(obj) {
  var output="";
  for(var i in obj) {
    var v = obj[i];
    output += i + ":" + v + "\n"
  }
  return output;
}

function initialization(){
 try{
   return eval('(' + synchronusGET("/webapp/outdoor/init") + ')');
 } catch(err){
   console.error(err);
   var tmp = {};
   tmp["lat"] = 39.507938;
   tmp["lng"] = -0.462007;
   tmp["zoom"]= 20;
   return tmp;
 }
}

var init = initialization();

var map = L.map('map',{maxZoom:25}).setView([init.lat,init.lng], init.zoom);
var rawLayer = L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	maxZoom: 25, attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors - <a href="#" onclick="window.location.reload()">reload</a>'
});

rawLayer.addTo(map);

var markers = {};
var icons = {};

var defaultIcon = L.icon({
    iconUrl: 'css/images/marker-icon.png',
    iconSize: [25, 41],
    iconAnchor: [13, 41],
    popupAnchor: [0, -40],
    shadowUrl: 'css/images/marker-shadow.png',
    shadowSize: [41, 41],
    shadowAnchor: [13, 41]
});
icons['default']=defaultIcon;

function synchronusGET(url){
    return $.ajax({
        type: "GET",
        url: url,
        cache: false,
        async: false
    }).responseText;
}

function computeCoordinates(x, y) {
    var point = L.point(x, y);
    var latlng = map.containerPointToLatLng(point);
    var lat = latlng.lat;
    var lng = latlng.lng;
    var latlngArray = new Array();
    latlngArray[0] = lat;
    latlngArray[1] = lng;
    return latlngArray;
}

function updateDeviceIcon(deviceName, iconName) {	
 try{        
		var markerCache = markers[deviceName];
		if (markerCache === undefined) {
		    return Boolean("false");
		}
		var iconObject = getIcon(iconName)
		markerCache.setIcon(iconObject);
		markerCache.update();
		return Boolean("true");
    }catch(error){
    	console.error(error);
    }
 }

function getIcon(iconName) {
	var ic; 
	if(iconName!==undefined)
    	ic = icons[iconName];
    if (ic === undefined) 
        return icons['default'];       
    return ic;  
}

function deleteMarker(deviceName) {
    var marker = markers[deviceName];
    if (marker !== undefined) {
      map.removeLayer(marker);
      markers[deviceName] = undefined;
    }
    return Boolean("true");
}


function markerDrag(e) {
  var text = e.target._popup._content;
  var latlng = e.target._latlng;  
  var deviceName = e.target.options.title;
    
  var marker = markers[deviceName];

  $.ajax
  ({
      contentType : 'application/json',
      type: "POST",
      url: "/webapp/updatelocation/" + deviceName,
      async: false,
      data: JSON.stringify({"lat": latlng.lat, "lng": latlng.lng }),
      error : function(resultat, statut, erreur) {
          var oldLatLng = JSON.parse(resultat.responseText);
          var oldLat = oldLatLng.lat;
          var oldLng = oldLatLng.lng;
          updateDeviceLocation(oldLat, oldLng, deviceName);
          alert("The location of " + deviceName + " device is not updatable");
      }
  });
}

function updatePopupAtInit(e, deviceName) {
  updatePopup(markers[deviceName].getPopup(), deviceName);
}

function updatePopupOnClick(deviceName) {
  var popup = markers[deviceName].getPopup();
  updatePopup(popup, deviceName);
}

function updatePopup(popup, deviceName) {
  $.get("/webapp/deviceinfo/" + deviceName, function(data) {
    popup.setContent(data);
    popup.update();
  });
}

function updatePopupOnClick(deviceName, serviceName) {
   $.get("/webapp/deviceinfo/" + deviceName + "/" + serviceName, function(data) {
    markers[deviceName].closePopup();
    markers[deviceName].setPopupContent(data);
    markers[deviceName].getPopup().update();
    markers[deviceName].openPopup();
  });
}

function performAction(gatewayName, deviceName, serviceName, resourceName) {
  $.get("/webapp/performAction/" + gatewayName + "/" + deviceName + "/" + serviceName + "/" + resourceName, function(data) {
    var result = JSON.parse(data);
    var title = result.title
    var message = result.message;
    var status = result.status;
    
	if (status != "SUCCESS") {    
    	var msg = '[' + status + ']' + ' ' + title + '\n\n' + message;
    	alert(msg);
    } else {
      document.getElementById("notif").style.visibility = "visible";
      window.setTimeout(function() {document.getElementById("notif").style.visibility = "hidden";}, 1000);
    }
  });
}

function showImage(imageUrl) {
  var content = '<a href="#" onclick="closeImage();"><img src="' + imageUrl + '" /></a>';
  document.getElementById("image").innerHTML = content;
  document.getElementById("image").style.visibility = "visible";
}

function closeImage(imageUrl) {
  document.getElementById("image").style.visibility = "hidden";
}

var geo = null;
var geo_json = null;
var geo_markers_arr = {};
var geo_layers_arr = {};

var plus = document.getElementById("plus");
var plus_button = document.getElementById("plus_button");

var less = document.getElementById("less");
var less_button = document.getElementById("less_button");

var geo_extra = document.getElementById("geo_extra");
var geodef = document.getElementById("geodef");		

var validator = document.getElementById("validator");
var geodef_name = document.getElementById("geodef_name");
var geo_markers = document.getElementById("geo_markers");

plus_button.onclick=function(e){
	less.style.display="table-cell";
	plus.style.display="none";
	geo_extra.style.display="flex";
}

less_button.onclick=function(e){
	plus.style.display="table-cell";
	less.style.display="none";
	geo_extra.style.display="none";
	geodef.value = '';
}

validator.onclick = function(e){
	geo_json = JSON.parse(geodef.value);			   
	var name = geodef_name.value;
	var length = Object.keys(geo_markers_arr).length;
	   
	if(name===undefined || name==='')
	    name = "GeoJSON_"+length;
	   		
	geodef_name.value = '';
	geodef.value = '';

	geo_markers_arr[name] = geo_json;
	   			   
	var block_to_insert = document.createElement( 'a' );
	block_to_insert.ping = false;
	block_to_insert.name = name;
	block_to_insert.style="display:table-cell"
	block_to_insert.type = "geoanchor";
	block_to_insert.innerHTML = name ;
	block_to_insert.onclick = function(e){
		try{
			var src = event.srcElement;
	  		var enabled_ = (src.ping==='true');
	   		var name_  = src.name;
	   		if(enabled_){
	   			 src.ping=false;
	   			 src.type="geoanchor";
				 geo_layers_arr[name_].clearLayers();
				 geo_layers_arr[name_]=null;
	   		} else {
	   			 src.ping=true;
	   			 src.type="geoanchor-enabled";
	   			 geo_layers_arr[name_]= L.geoJSON(geo_markers_arr[name_]).addTo(map);
	   		}			   		
		} catch(err){
			console.error(err);
		}
	} 			  
	geo_markers.appendChild( block_to_insert ); 
	   		   
}
