window.onload = function() {
	/*
	 * document is what represents the current page the user is on. window is
	 * the object that represents the entire browser. The above function
	 * executes anything inside of it once the page is fully loaded.
	 */
	document.getElementById("para1").innerHTML="Altered via javascript";
	var e1 = document.getElementById("para1");
	// addEvenListend("action" , fucntionToBeInvoked, useCapturing) note : if
	// not included, false by default
	e1.addEventListener("click", doStuff, false);
	// NOTE: in this context, dont add parehesis after the functionname
	
	/*
	 * Event Propagation is the occurence of nested actions each triggering in a
	 * specific order Either from the outermost even first towards the
	 * innermost(Capturing) Or executing innermost action first, then working
	 * towards outermost (bubbling)
	 */


	var d1 = document.getElementById("d1");
	var d2 = document.getElementById("d2");
	var d3 = document.getElementById("d3");
	
	d1.addEventListener("click", d1Click, true);
	d2.addEventListener("click", d2Click, true);
	d3.addEventListener("click", d3Click, true);
	
}

function doStuff(){
	window.alert("You Clicked me!");
}

function d1Click() {
	window.alert("d1");
}
function d2Click(event2) {
	window.alert("d2");
	event2.stopPropagation();
}
function d3Click() {
	window.alert("d3");
}

function submitName(){
	var form = document.getElementById("inputField").value;
	var tableRow = document.createElement('tr');
	var tableData1 = document.createElement('td');
	var tableData2 = document.createElement('td');
	var tableData3 = document.createElement('td');
	tableData3.innerHTML="<b id="+ idCount + " onclick='removeRow(" + idCount + ")'>X</b>";
	var td1 = document.createTextNode(idCount);
	var td2 = document.createTextNode(form);
	
	tableData1.appendChild(td1);
	tableData2.appendChild(td2);
	tableRow.appendChild(tableData1);
	tableRow.appendChild(tableData2);
	tableRow.appendChild(tableData3);
	
	document.getElementById("myTable").appendChild(tableRow);
	idCount++;
}

function removeRow(id) {
	document.getElementById("" + id).parentNode.parentNode.remove();
	/*
	 * DOM Manipulation methods let use dynamycally change our html on the spot.
	 * You can use thesemethods to traverse to any node inthe DOM -- for adding
	 * nodes: createElement -- create a specific tag node createTextNode --
	 * create a non parsed string -innerHTML -- change a node innerHTML
	 * -parentNode -- move you cursor up on level .childNodes -- grab all
	 * children nodes, NOTE: this returns an array of nodes To view an expansive
	 * list of all the method you can use to traverse the DOM reference : w3 dom
	 * obj all
	 */
	
	
}

var idCount = 0;