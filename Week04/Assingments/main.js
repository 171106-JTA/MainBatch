window.onload = function() {
	/*
	 * Document is the object that represents the current page a user is on.
	 * 
	 * window is the object that represents the entire browser.
	 * The above function executes anything inside of it once the page is fully loaded.
	 */
	document.getElementById("para1").innerHTML="ALTER VIA JAVASCRIPT!";
	
	var element1 = document.getElementById("para1");
	
	//addEventListener("action", functionToBeInvoked, useCapturing); NOTE: if not included, false by default
	element1.addEventListener("click", doStuff, false); //no parentheses on doStuff in this context
	
	var d1 = document.getElementById("d1");
	var d2 = document.getElementById("d2");
	var d3 = document.getElementById("d3");
	
	/*
	 * Event propagation is the occurrenct of nested actions each triggering in a specific order.
	 * Either from the outermost even first towards the innermost (capturing)
	 * Or executing innermost actions first, then working towards outermost (bubbling)
	 */
	d1.addEventListener("click", d1Click, true);
	d2.addEventListener("click", d2Click, true);
	d3.addEventListener("click", d3Click, true);
	
}

function doStuff() {
	window.alert("You clicked me!"); //Pops an alert message for the user.
	//NOTE: It is awful, terrible, practice to debug javascrip using alert windows.
}

function d1Click() {
	window.alert("D1");
}

function d2Click(event2) { //in this  version we provide a user for parameter 1 which will always be the event object
	window.alert("D2");
	event2.stopPropagation();
}

function d3Click() {
	window.alert("D3");
}

function submitName() {
	var formValue = document.getElementById("inputField").value;

	var tableRow = document.createElement('tr');
	var tableData1 = document.createElement('td');
	var tableData2 = document.createElement('td');
	var td1 = document.createTextNode(idCount++);
	var td2 = document.createTextNode(formValue);
	
	tableData1.appendChild(td1);
	tableData2.appendChild(td2);
	tableRow.appendChild(tableData1);
	tableRow.appendChild(tableData2);
	
	document.getElementById("myTable").appendChild(tableRow);
}

var idCount = 1;

function submitNumbers() {
	
	var n1 = parseInt(document.getElementById("input1").value);
	var n2 = parseInt(document.getElementById("input2").value);
	var p = document.getElementById("fizzbuzzresult");
	
	if (n1 > n2) {
		var result = fizzbuzz(n2, n1);
	} else {
		var result = fizzbuzz(n1, n2);
	}
	
	p.innerHTML="Result = " + result;
}

function fizzbuzz(n1, n2) {
	var result = " ";
	
	for (var i = n1; i <= n2; i++) {
		
		if (i % 3 == 0 && i % 5 == 0)
			result += "fizzbuzz"
		else if (i % 3 == 0)
			result += "fizz";
		else if (i % 5 == 0)
			result += "buzz";
		else
			result += " " + i;

		result += " ";
		console.log(result);
	}
	return result;
}




