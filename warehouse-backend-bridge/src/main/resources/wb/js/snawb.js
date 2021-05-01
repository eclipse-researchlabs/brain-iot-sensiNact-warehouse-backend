var SNA_CALLBACK = 'http://' + location.host + '/warehouse-backend';
var START_BUTTON = 'http://' + location.host + '/startSystem';

var start = document.getElementById('startbutton');
start.onclick = function(e){
	httpPostRequest(START_BUTTON, '*/*', '{}', function(){alert("Started !");}, function(e){alert("Starting error !");});
}

var pickingPointsRef = document.getElementById('pickingPoints').getElementsByTagName('tbody')[0];
var dockingPointsRef = document.getElementById('dockingPoints').getElementsByTagName('tbody')[0];
var storagePointsRef = document.getElementById('storagePoints').getElementsByTagName('tbody')[0];
var cardStoragesRef = document.getElementById('cardStorages').getElementsByTagName('tbody')[0];

var addPickPointRow = function(id,x,y,z,assigned){
  var parentRow   = pickingPointsRef.insertRow(pickingPointsRef.rows.length);
  var parentCell  = parentRow.insertCell(0);
  var newText  = document.createTextNode(id)
  parentCell.appendChild(newText);
  
  parentCell  = parentRow.insertCell(1);  
  var tbl = document.createElement("table");   
  tbl.style.border ='thin solid #000000';  
  var header = tbl.createTHead();
  var newRow = header.insertRow(0);     
  var newCell = newRow.insertCell(0);
  newText  = document.createTextNode('x')
  newCell.appendChild(newText);     
  newCell = newRow.insertCell(1);
  newText  = document.createTextNode('y')
  newCell.appendChild(newText);    
  newCell = newRow.insertCell(2);
  newText  = document.createTextNode('z')
  newCell.appendChild(newText);
  var tblBody = document.createElement("tbody");
  var tblRow = tblBody.insertRow(0);  
  var tblCell = tblRow.insertCell(0);  
  newText  = document.createTextNode(x)
  tblCell.appendChild(newText);  
  tblCell = tblRow.insertCell(1);
  newText  = document.createTextNode(y)
  tblCell.appendChild(newText);  
  tblCell = tblRow.insertCell(2);
  newText  = document.createTextNode(z)
  tblCell.appendChild(newText);    
  tbl.appendChild(tblBody);
  parentCell.appendChild(tbl);  
  
  newCell  = parentRow.insertCell(2);  
  newText  = document.createTextNode(assigned);
  newCell.appendChild(newText);
}

var addDockPointRow = function(id,x,y,z,xx,xy,xz){
  var parentRow   = dockingPointsRef.insertRow(dockingPointsRef.rows.length);
  var parentCell  = parentRow.insertCell(0);
  var newText  = document.createTextNode(id)
  parentCell.appendChild(newText);
  
  parentCell  = parentRow.insertCell(1);  
  var tbl = document.createElement("table");  
  tbl.style.border ='thin solid #000000';  
  var header = tbl.createTHead();
  var newRow = header.insertRow(0);      
  var newCell = newRow.insertCell(0);
  newText  = document.createTextNode('x')
  newCell.appendChild(newText);      
  newCell = newRow.insertCell(1);
  newText  = document.createTextNode('y')
  newCell.appendChild(newText);    
  newCell = newRow.insertCell(2);
  newText  = document.createTextNode('z')
  newCell.appendChild(newText);
  var tblBody = document.createElement("tbody");
  var tblRow = tblBody.insertRow(0);    
  var tblCell = tblRow.insertCell(0);  
  newText  = document.createTextNode(x)
  tblCell.appendChild(newText);  
  tblCell = tblRow.insertCell(1);
  newText  = document.createTextNode(y)
  tblCell.appendChild(newText);  
  tblCell = tblRow.insertCell(2);
  newText  = document.createTextNode(z)
  tblCell.appendChild(newText);    
  tbl.appendChild(tblBody);
  parentCell.appendChild(tbl);
  
  parentCell  = parentRow.insertCell(2);  
  tbl = document.createElement("table");   
  tbl.style.border ='thin solid #000000';  
  header = tbl.createTHead();
  newRow = header.insertRow(0);      
  newCell = newRow.insertCell(0);
  newText  = document.createTextNode('x')
  newCell.appendChild(newText);      
  newCell = newRow.insertCell(1);
  newText  = document.createTextNode('y')
  newCell.appendChild(newText);    
  newCell = newRow.insertCell(2);
  newText  = document.createTextNode('z')
  newCell.appendChild(newText);
  var tblBody = document.createElement("tbody");
  var tblRow = tblBody.insertRow(0);    
  var tblCell = tblRow.insertCell(0);  
  newText  = document.createTextNode(xx)
  tblCell.appendChild(newText);  
  tblCell = tblRow.insertCell(1);
  newText  = document.createTextNode(xy)
  tblCell.appendChild(newText);  
  tblCell = tblRow.insertCell(2);
  newText  = document.createTextNode(xz)
  tblCell.appendChild(newText);    
  tbl.appendChild(tblBody);
  parentCell.appendChild(tbl);
}

var addStoragePointRow = function(id,x,y,z,xx,xy,xz){
  var parentRow   = storagePointsRef.insertRow(storagePointsRef.rows.length);
  var parentCell  = parentRow.insertCell(0);
  var newText  = document.createTextNode(id)
  parentCell.appendChild(newText);
  
  parentCell  = parentRow.insertCell(1);  
  var tbl = document.createElement("table");  
  tbl.style.border ='thin solid #000000';  
  var header = tbl.createTHead();
  var newRow = header.insertRow(0);      
  var newCell = newRow.insertCell(0);
  newText  = document.createTextNode('x')
  newCell.appendChild(newText);      
  newCell = newRow.insertCell(1);
  newText  = document.createTextNode('y')
  newCell.appendChild(newText);    
  newCell = newRow.insertCell(2);
  newText  = document.createTextNode('z')
  newCell.appendChild(newText);
  var tblBody = document.createElement("tbody");
  var tblRow = tblBody.insertRow(0);    
  var tblCell = tblRow.insertCell(0);  
  newText  = document.createTextNode(x)
  tblCell.appendChild(newText);  
  tblCell = tblRow.insertCell(1);
  newText  = document.createTextNode(y)
  tblCell.appendChild(newText);  
  tblCell = tblRow.insertCell(2);
  newText  = document.createTextNode(z)
  tblCell.appendChild(newText);    
  tbl.appendChild(tblBody);
  parentCell.appendChild(tbl);
  
  parentCell  = parentRow.insertCell(2);  
  tbl = document.createElement("table");   
  tbl.style.border ='thin solid #000000';  
  header = tbl.createTHead();
  newRow = header.insertRow(0);      
  newCell = newRow.insertCell(0);
  newText  = document.createTextNode('x')
  newCell.appendChild(newText);      
  newCell = newRow.insertCell(1);
  newText  = document.createTextNode('y')
  newCell.appendChild(newText);    
  newCell = newRow.insertCell(2);
  newText  = document.createTextNode('z')
  newCell.appendChild(newText);
  var tblBody = document.createElement("tbody");
  var tblRow = tblBody.insertRow(0);    
  var tblCell = tblRow.insertCell(0);  
  newText  = document.createTextNode(xx)
  tblCell.appendChild(newText);  
  tblCell = tblRow.insertCell(1);
  newText  = document.createTextNode(xy)
  tblCell.appendChild(newText);  
  tblCell = tblRow.insertCell(2);
  newText  = document.createTextNode(xz)
  tblCell.appendChild(newText);    
  tbl.appendChild(tblBody);
  parentCell.appendChild(tbl);
}

var addCardStorageRow = function(stid,cardid){
  var newRow   = cardStoragesRef.insertRow(cardStoragesRef.rows.length);
  
  newCell  = newRow.insertCell(1);
  newText  = document.createTextNode(cardid)
  newCell.appendChild(newText);
  
  var newCell  = newRow.insertCell(0);
  var newText  = document.createTextNode(stid)
  newCell.appendChild(newText);
  
}
/*
var ppsfile = document.getElementById("ppsfile");

var addpps = document.getElementById("addPickPoints");
addpps.onclick = function(e){
	var file = ppsfile.value;
	var path = 'file:'+file;
	console.info(path);
	createPickingPoints(path);
	ppsfile.value = '';
}
*/

var ppid = document.getElementById("ppid");
var pppx = document.getElementById("pppx");
var pppy = document.getElementById("pppy");
var pppz = document.getElementById("pppz");
var ppassigned = document.getElementById("ppassigned");

var addpp = document.getElementById("addPickPoint");
addpp.onclick = function(e){
	var x = pppx.value;
	var y = pppy.value;
	var z = pppz.value;
	var assigned = ppassigned.value;
	var id = ppid.value;
	
	createPickingPoint(id,x,y,z,assigned);
	addPickPointRow(id,x,y,z,assigned);
	pppx.value = '';
	pppy.value = '';
	pppz.value = '';
	ppassigned.value = '';
	ppid.value = '';
}

/*
var dpsfile = document.getElementById("dpsfile");

var adddps = document.getElementById("addDockPoints");
adddps.onclick = function(e){
	var file = dpsfile.value;
	var path = 'file:'+file;
	console.info(path);
	createDockingPoints(path);
	dpsfile.value = '';
}
*/

var dpid = document.getElementById("dpid");
var dppx = document.getElementById("dppx");
var dppy = document.getElementById("dppy");
var dppz = document.getElementById("dppz");
var dpxx = document.getElementById("dpxx");
var dpxy = document.getElementById("dpxy");
var dpxz = document.getElementById("dpxz");

var adddp = document.getElementById("addDockPoint");
adddp.onclick = function(e){
	var x = dppx.value;
	var y = dppy.value;
	var z = dppz.value;
	var xx = dpxx.value;
	var xy = dpxy.value;
	var xz = dpxz.value;
	var id = dpid.value;
	
	createDockingPoint(id,x,y,z,xx,xy,xz);
	addDockPointRow(id,x,y,z,xx,xy,xz);
	dppx.value = '';
	dppy.value = '';
	dppz.value = '';
	dpxx.value = '';
	dpxy.value = '';
	dpxz.value = '';
	dpid.value = '';
}
/*
var spsfile = document.getElementById("spsfile");

var addsps = document.getElementById("addStoragePoints");
addsps.onclick = function(e){
	var file = spsfile.value;
	var path = 'file:'+file;
	console.info(path);
	createStoragePoints(path);
	spsfile.value = '';
}
*/

var spid = document.getElementById("spid");
var sppx = document.getElementById("sppx");
var sppy = document.getElementById("sppy");
var sppz = document.getElementById("sppz");
var spxx = document.getElementById("spxx");
var spxy = document.getElementById("spxy");
var spxz = document.getElementById("spxz");

var addsp = document.getElementById("addStoragePoint");
addsp.onclick = function(e){
	var x = sppx.value;
	var y = sppy.value;
	var z = sppz.value;
	var xx = spxx.value;
	var xy = spxy.value;
	var xz = spxz.value;
	var id = spid.value;
	
	createStoragePoint(id,x,y,z,xx,xy,xz);
	addStoragePointRow(id,x,y,z,xx,xy,xz);
	
	sppx.value = '';
	sppy.value = '';
	sppz.value = '';
	spxx.value = '';
	spxy.value = '';
	spxz.value = '';
	spid.value = '';
}

/*
var csfile = document.getElementById("csfile");

var addcss = document.getElementById("addCardStorages");
addcss.onclick = function(e){
	var file = csfile.value;
	var path = 'file:'+file;
	console.info(path);
	associateCardStorages(path);
	csfile.value = '';
}
*/

var cardId = document.getElementById("cardid");
var storageId = document.getElementById("stid");

var addcs = document.getElementById("addCardStorage");
addcs.onclick = function(e){
	var card = cardId.value;
	var STid = storageId.value;
	
	associateCardStorage(STid,card);
	addCardStorageRow(STid,card);
	
	cardId.value = '';
	storageId.value = '';
}

var associateCardStorages = function(jsonFile){
    var xhr = httpRequest();
	xhr.open("GET", jsonFile, true);
	xhr.onreadystatechange = function() {
	   if (xhr.readyState == 4){
	       var json = JSON.parse(xhr.responseText);
	       httpPostRequest(SNA_CALLBACK + '/card', '*/*', json, function(response){alert(response);}, function(err){alert(err);}); 
	       for(i=0;i<Object.keys(json['Cart_Storages']).length;i++){
	          addCardStorageRow(json['Cart_Storages'][i]['storageID'],json['Cart_Storages'][i]['cartID']);
	       }
	   }
	};
	xhr.send();
}

var associateCardStorage = function(STid, card){
	var cardStorage = {};
	cardStorage['storageID']=STid;
	cardStorage['cardID']= card;
	
	var cardStorages = {};
	cardStorages['Cart_Storages']=[];
	cardStorages['Cart_Storages'][0] = cardStorage;
	httpPostRequest(SNA_CALLBACK + '/card', '*/*', cardStorages, function(response){alert(response);}, function(err){alert(err);});
} 

var createPickingPoints = function(jsonFile){
    var xhr = httpRequest();
	xhr.open("GET", jsonFile, true);
	xhr.onreadystatechange = function() {
	   if (xhr.readyState == 4){
	       var json = JSON.parse(xhr.responseText);
	       console.info(json);
	       httpPostRequest(SNA_CALLBACK + '/pick', '*/*', json, function(response){alert(response);}, function(err){alert(err);}); 
	       for(i=0;i<Object.keys(json['Picking_Points']).length;i++){
	       	   var pick = json['Picking_Points'][i];
	       	   addPickPointRow(pick['PPid'], pick['pos']['x'],pick['pos']['y'],pick['pos']['z'], pick['isAssigned']);
	       }
	   }
	};
	xhr.send();
}

var createPickingPoint = function(PPid,x,y,z,assigned){
	var pickingPoint = {};
	pickingPoint['PPid']=PPid;
	pickingPoint['pose']={};
	pickingPoint['pose']['x']=x;
	pickingPoint['pose']['y']=y;
	pickingPoint['pose']['z']=z;
	pickingPoint['isAssigned']=assigned;
	
	var pickingPoints = {};
	pickingPoints['Picking_Points']=[];
	pickingPoints['Picking_Points'][0] = pickingPoint;
	httpPostRequest(SNA_CALLBACK + '/pick', '*/*', pickingPoints, function(response){alert(response);}, function(err){alert(err);});
} 

var createStoragePoints = function(jsonFile){
    var xhr = httpRequest();
	xhr.open("GET", jsonFile, true);
	xhr.onreadystatechange = function() {
	   if (xhr.readyState == 4){
	      var json = JSON.parse(xhr.responseText);
	      httpPostRequest(SNA_CALLBACK + '/store', '*/*', json, function(response){alert(response);}, function(err){alert(err);});	            
	      for(i=0;i<Object.keys(json['Picking_Points']).length;i++){
	      	  var storage = json['Storage_Points'][i];
	      	  addStoragePointRow(storage['STid'], storage['storagePose']['x'],storage['storagePose']['y'],storage['storagePose']['z'],
	       	  storage['storageAUX']['x'],storage['storageAUX']['y'],storage['storageAUX']['z'] );
	      }	       	   	 
	   }
	};
	xhr.send();
}

var createStoragePoint = function(STid,xp,yp,zp,xx,yx,zx){
	var storagePoint = {};
	storagePoint['STid']=STid;
	storagePoint['storagePose']={};
	storagePoint['storagePose']['x']=xp;
	storagePoint['storagePose']['y']=yp;
	storagePoint['storagePose']['z']=zp;
	storagePoint['storageAUX']={};
	storagePoint['storageAUX']['x']=xx;
	storagePoint['storageAUX']['y']=yx;
	storagePoint['storageAUX']['z']=zx;
	
	var storagePoints = {};
	storagePoints['Storage_Points']=[];
	storagePoints['Storage_Points'][0]=storagePoint;
	httpPostRequest(SNA_CALLBACK + '/store', '*/*', storagePoints, function(response){alert(response);}, function(err){alert(err);});
} 

var createDockingPoints = function(jsonFile){
    var xhr = httpRequest();
	xhr.open("GET", jsonFile, true);
	xhr.onreadystatechange = function() {
	   if (xhr.readyState == 4){
	      var json = JSON.parse(xhr.responseText);
	      httpPostRequest(SNA_CALLBACK + '/dock', '*/*', json, function(response){alert(response);}, function(err){alert(err);});             
	      for(i=0;i<Object.keys(json['Docking_Points']).length;i++){
	          var dock = json['Docking_Points'][i];
	      	  addDockPointRow(dock['IPid'], dock['dockPose']['x'],dock['dockPose']['y'],dock['dockPose']['z'],
	      	  dock['dockAUX']['x'],dock['dockAUX']['y'],dock['dockAUX']['z'] );
	      }
	   }
	};
	xhr.send();
}

var createDockingPoint = function(IPid,xp,yp,zp,xx,yx,zx){
	var dockingPoint = {};
	dockingPoint['IPid']=IPid;
	dockingPoint['dockPose']={};
	dockingPoint['dockPose']['x']=xp;
	dockingPoint['dockPose']['y']=yp;
	dockingPoint['dockPose']['z']=zp;
	dockingPoint['dockAUX']={};
	dockingPoint['dockAUX']['x']=xx;
	dockingPoint['dockAUX']['y']=yx;
	dockingPoint['dockAUX']['z']=zx;
	
	var dockingPoints = {};
	dockingPoints['Docking_Points']=[];	
	dockingPoints['Docking_Points'][0]=dockingPoint;	
	httpPostRequest(SNA_CALLBACK + '/dock', '*/*', dockingPoints, function(response){alert(response);}, function(err){alert(err);});
} 

function httpRequest(){
	var xhr = null;		
	if(window.XMLHttpRequest || window.ActiveXObject){
		if(window.ActiveXObject){
			try{
				xhr = new ActiveXObject("Msxml2.XMLHTTP");
			}catch(e){
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			}
		} else {
			xhr = new XMLHttpRequest(); 
		}
	} else {
		alert("XMLHTTPRequest not supported");
		return;
	}
	return xhr;
}

function httpGetRequest(url, accept, callback, error){
    var xhr = httpRequest();
	xhr.open("GET", url, true);
	if(accept === undefined)
		xhr.setRequestHeader('Accept', '*/*');
	else
		xhr.setRequestHeader('Accept', accept);
	xhr.onreadystatechange = function() {
	   if (xhr.readyState == 4){
	       if(xhr.status == 200) 
	           callback(xhr.responseText); 
	       else 
	   	  	   error(xhr.responseText);
	   }
	};
	xhr.send();
}

function httpPostRequest(url, accept, content, callback, error){
	console.info(url);
	console.info(content);
    var xhr = httpRequest();
	xhr.open("POST", url, true);
	if(accept === undefined)
		xhr.setRequestHeader('Accept', '*/*');
	else
		xhr.setRequestHeader('Accept', accept);
	xhr.setRequestHeader('Content-Type', 'application/json');
	xhr.onreadystatechange = function() {
	   if (xhr.readyState == 4){
	       if(xhr.status == 200) 
	           callback(xhr.responseText); 
	       else 
	   	  		error(xhr.responseText);
	   }
	};
	xhr.send(JSON.stringify(content));
}
