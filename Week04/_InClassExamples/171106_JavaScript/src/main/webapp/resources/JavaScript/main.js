window.onload = function(){
	
	/*
	 * document is the object that represents the current page
	 * a user is on.
	 * 
	 * window is the object that represents the entire browser.
	 * The above function executes anything inside of it once the page is fully
	 * loaded.
	 */
	//document.getElementById("para1").innerHTML="ALTER VIA JAVASCRIPT!";
	document.getElementById("para1").innerHTML="ALTER VIA JAVASCRIPT!";
	
	var e1 = document.getElementById("para1");
	//addEventListener("action", functionToBeInvoked, useCapturing) NOTE: if not included, false by default
	e1.addEventListener("click", doStuff, false);
	//NOTE: in this context, dont add parenthesis after the function name
	
	/*
	 * Event propagation is the occurrence of nested actions each triggering in a specific order.
	 * Either from the outermost event first towards the innermost (capturing)
	 * Or executing innermost actions first, then working towards outermost (Bubbling)
	 */
	var d1 = document.getElementById("d1");
	var d2 = document.getElementById("d2");
	var d3 = document.getElementById("d3");
	d1.addEventListener("click", d1Click, true);
	d2.addEventListener("click", d2Click, true);
	d3.addEventListener("click", d3Click, true);
}
function doStuff(){
	window.alert("You clicked me!"); //Pops an alert message for the user.
	//NOTE: It is AWFUL, TERRIBLE, DISGUSTING practice to  debug javascript using alert windows.
}
function d1Click(){ //The event object is being passed in, but since we don't recieve it, it gets tossed aside
	window.alert("d1");
}
function d2Click(event2){ //In this version, we provide a use for parameter 1, which will always be the event object
	window.alert("d2");
	event2.stopPropagation(); //Prevent nested events from occurring past this point
}
function d3Click(){
	window.alert("d3");
}

function submitName(){
	var formValue = document.getElementById("inputField").value;
	
	
	var tableRow = document.createElement('tr');
	var tableData1 = document.createElement('td');
	var tableData2 = document.createElement('td');
	var tableData3 = document.createElement('td');
	tableData3.innerHTML="<b id="+ idCount + " onclick='removeRow(" + idCount + ")'>X</b>";
	var td1 = document.createTextNode(idCount);
	var td2 = document.createTextNode(formValue);
	
	
	tableData1.appendChild(td1);
	tableData2.appendChild(td2);
	tableRow.appendChild(tableData1);
	tableRow.appendChild(tableData2);
	tableRow.appendChild(tableData3);
	
	document.getElementById("myTable").appendChild(tableRow);
	
	idCount++;
}

function removeRow(){
	document.getElementById("" + id).parentNode.parentNode.remove();
	/*
	 * DOM manipulation methods let use dynamically change our html on the spot.
	 * You can use these methods to traverse to any node in the dom.
	 * --For adding nodes:
	 * createElement -- create a specific tag node
	 * createTextNode -- create a non parsed string
	 * .innerHTML -- change a nodes innerHTML
	 * .parentNode -- move your cursor up one level.
	 * .childNodes -- grab all children nodes, NOTE: this returns an array of nodes
	 * 
	 * To view an expansive list of all the methods you can use to traverse the DOM
	 * reference:
	 * https://www.w3schools.com/jsref/dom_obj_all.asp
	 */
}

var idCount = 0;