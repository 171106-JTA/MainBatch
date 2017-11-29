
window.onload = function() { 
	
	document.getElementById("para1").innerHTML = "ALTER VIA JAVASCRIPT!";
	
	var e1 = document.getElementById("para1");
	// addEventListener("action", functionToBeInvoked, useCapturing); // third argument defaults to false
	e1.addEventListener("click", doStuff, false);
	// NOTE: In this context, don't add parenthesis after the function name. 
	
	/*
	 * Event propagation is the occurrence of nested actions each triggering in a specific order.
	 * Either from the outermost event first towards the inner (capturing)
	 * or executing innermost actions first, then working towards outermost (bubbling)
	 */
	var d1 = document.getElementById("d1"),
		d2 = document.getElementById("d2"),
		d3 = document.getElementById("d3");
	d1.addEventListener("click", d1Click, true);
	d2.addEventListener("click", d2Click, true);
	d3.addEventListener("click", d3Click, true);
}

function doStuff() { 
	window.alert("You clicked me!");
	// NOTE: It is horrible practice to debug JavaScript using alert windows.
}

function d1Click() // The event object is being passed in, but since we don't receive it, it gets tossed aside
{
	window.alert("d1"); 
}
function d2Click(event) 
{ 
	window.alert("d2");
	event.stopPropagation();	// prevent nested events from occurring past this point
}
function d3Click() 
{ 
	console.log(arguments);
	window.alert("d3");
}

function submitName()
{
	var formValue = document.getElementById("inputField").value;
	
	
	var tableRow = document.createElement('tr');
	var tableData1 = document.createElement('td');
	var tableData2 = document.createElement('td');
	var tableData3 = document.createElement('td');
	tableData3.innerHTML = '<b id=' + idCount +  ' onclick="removeRow(this.id);" style="text-align: right;">X</b>';
	var td1 = document.createTextNode(idCount++);
	var td2 = document.createTextNode(formValue);
	
	tableData1.appendChild(td1);
	tableData2.appendChild(td2);
	
	tableRow.appendChild(tableData1);
	tableRow.appendChild(tableData2);
	tableRow.appendChild(tableData3);
	
	document.getElementById("myTable").appendChild(tableRow);
	
}

function removeRow(id)
{
	document.getElementById(id).parentNode.parentNode.remove();
	/*
	 * DOM manipulation methods let us dynamically change our html on the spot. 
	 * You can use these methods to traverse to any node in the DOM.
	 * • For adding nodes:
	 * 	• createElement  -- create a specific tag node
	 * 	• createTextNode -- create non parsed string
	 * 	• .innerHTML	 -- change a node's innerHTML
	 * 	• .parentNode	 -- move your cursor up one level
	 * 	• .childNodes	 -- grab all children nodes; // NOTE; this returns array of nodes
	 * 
	 */
}

var idCount = 0;

function generateFizzBuzz()
{
	// clear the list
	document.getElementById('fizzBuzzList').innerHTML = '';
	// get the input of the two fields
	var firstFieldNumber  = Number(document.getElementById('firstNumber').value),
		secondFieldNumber = Number(document.getElementById('secondNumber').value);
	if (isNaN(firstFieldNumber)) firstFieldNumber = 0;
	if (isNaN(secondFieldNumber)) secondFieldNumber = 0;
	var firstNumber  = (firstFieldNumber < secondFieldNumber) ? firstFieldNumber : secondFieldNumber,
		secondNumber = (firstFieldNumber == firstNumber) ? secondFieldNumber : firstFieldNumber;
	// populate the fizzBuzzList with the generated fizzbuzz
	for (let x = firstNumber; x <= secondNumber; x++)
	{
		let currentListElement = document.createElement('li'),
			textToInsert = '';
		if ((x % 3 == 0) || (x % 5 == 0))
		{
			if (x % 3 == 0)
			{
				textToInsert += 'fizz';
			}	
			if (x % 5 == 0)
			{
				textToInsert += 'buzz';
			}			
		}
		else textToInsert += x.toString();
		currentListElement.append(document.createTextNode(textToInsert));
		document.getElementById('fizzBuzzList').append(currentListElement);
	}
}

function triggerError()
{
	/*
	 * JavaScript does not have Exceptions. It has Errors.
	 * There are 6 main errors in JavaScript:
	 * 	• EvalError 	: this error is deprecated and replaced with SyntaxError.
	 * 	• RangeError	: Input is outside the declared range
	 * 	• ReferenceError: reference of undeclared variable
	 * 	• SyntaxError	: 
	 * 	• TypeError		: occurs whenever you try to do an illegal type conversion
	 * 	• URIError		: Having illegal characters in a URI
	 */
	try 
	{
		p++;
	}
	catch (container)
	{
		console.log(container.message);
	}
	finally
	{
		console.log("finally always executes");
	}
	
	
	
}


function validateRegistration()
{
	var form = document.forms['registration'];
	var password1 = form['password'].value,
		password2 = form['password2'].value;
	console.log("%s %s", password1, password2);
	return false;
}

function sendAJAX()
{
	var input = document.getElementById('input').value,
		url = "http://pokeapi.co/api.v2/pokemon/" + input + "/";
	
	var xhr = new XMLHttpRequest();
	/*
	 * There exist 5 states of an XMLHttpRequest object:
	 * • 0: request is not initialized
	 * 	• We created our XMLHttpRequest object, but have NOT called open() method
	 * • 1: Request has been set up
	 * 	• We have called open(), but not send()
	 * • 2: Request has been sent
	 * 	• We have called send()
	 * • 3: Request is being processed
	 * 	• Communication with the server has been established.
	 * 	• Yet, we have not received the full response yet
	 * • 4: Request is complete
	 * 	• We have received the response content.  
	 */
	xhr.onreadystatechange = function () { 
		if (xhr.readyState == 4 && xhr.status == 200)
		{
			console.log(xhr.responseText);
		}
		else
		{
			document.getElementById("AJAXError").innerHTML = "Woops";
		}
		
	}
	/*
	 * The open method is used to configure the actual request.
	 * This includes which endpoint we are hitting, and whether or not to use an asynchronous call (why NOT!?)
	 * .open("HTTP_METHOD", "endpoint", booleanForUsingAsynchronous) 
	 */
	xhr.open("GET", url, true);
//	xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
	xhr.send();
}


