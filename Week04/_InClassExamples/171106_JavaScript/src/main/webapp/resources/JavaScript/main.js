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

function triggerError(){
	/*
	 * Javascript does not have Exceptions, it has errors.
	 * There are 6 main errors in javascript:
	 * EvalError - this error is deprecated, and replaced with SyntaxError
	 * RangeError - Input is outside the declared range
	 * ReferenceError - Any time you reference an undeclared variable
	 * SyntaxError - 
	 * TypeError - TypeErrors occur whenever you try to do an illegal type conversion
	 * URIError - Having illegal characters in a URI
	 */
	try{
		console.log(p++);
	}catch(container){
		console.log(container.message);
	}finally{
		console.log("finally always executes!");
	}
	
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

function validateRegistration(){
	var form = document.forms["registration"];
	var password1 = form["password"].value;
	var password2 = form["password2"].value;
	console.log(password1 + " " + password2);
	var error = document.getElementById("error");
	
	if(password1 != password2){
		error.innerHTML ="Passwords do not match";
		return false;
	}
	if(password1.length < 8){
		error.innerHTML = "Passwords require at least 8 characters";
		return false;
	}
	return true;
}

var idCount = 0;

function sendAJAX(){
	var input = document.getElementById("input").value;
	var url = "GetUsers";
	
	var xhr = new XMLHttpRequest();
	/*
	 * There exists 5 states of an XMLHttpRequest object.
	 * 0 - Request is not initialized
	 * 		-We created out XMLHttpRequest Object, but have NOT called open() method
	 * 1 - Request has been set up
	 * 		-We have called open(), but not send()
	 * 2 - Request has been sent
	 * 		-We have called send()
	 * 3 - Request is being processed
	 * 		-Communication with the server has been established.
	 * 		-Yet we have not received the full response yet.
	 * 4 - Request is complete
	 * 		-We have received the response content.
	 */
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			
			var xmlText = xhr.responseXML;
			
			var response = xmlText.getElementsByTagName("trainer");
			
			var resultTable = document.getElementById("results");
			
			for(i in response){
				var row = document.createElement("tr");
				var td1 = document.createElement("td");
				var td2 = document.createElement("td");
				var td3 = document.createElement("td");
				var td4 = document.createElement("td");
				
				td1.innerHTML = response[i].childNodes[0].innerHTML;	
				td2.innerHTML = response[i].childNodes[1].innerHTML;
				td3.innerHTML = response[i].childNodes[2].innerHTML;
				td4.innerHTML = response[i].childNodes[3].innerHTML;
				row.appendChild(td1);
				row.appendChild(td2);
				row.appendChild(td3);
				row.appendChild(td4);
				resultTable.appendChild(row);
			}
			

			
		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AJAXError").innerHTML="Woops";
		}
	}
	/*
	 * The open method is used to configure the actual request.
	 * This includes which endpoint we are hitting and whether or not to use
	 * an asynchronous call (Why would you not use asynchronosity with AJAX?!)
	 * .open("HTTPMETHOD", "endpoint", booleanForUsingAsynchronous)
	 */
	xhr.open("POST",url);
	//Tell our xhr how to send the data to the endpoint.
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send("username=" + input);
}