var counter = (() => {
    var pCounter = 0;
    return {
        increment: function () {
            pCounter++;
        },
        decrement: () => {
            pCounter--;
        },
        value: () => {
            return pCounter;
        }
    }
})()

// look up closure js with 's'

//variable hoisting means that if there are any variables in the script,
// it will be interpreted first as if it was written above others.
// variable declaration are done first BUT assignment are still done procedurally
"use strict";
// strict mode is on, variable must be declared with keywords

window.onload = function() {
	/*
	 * document is what represents the current page the user is on. window is
	 * the object that represents the entire browser. The above function
	 * executes anything inside of it once the page is fully loaded.
	 */
	document.getElementById("para1").innerHTML = "Altered via javascript";
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

function doStuff() {
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

function submitName() {
	var form = document.getElementById("inputField").value;
	var tableRow = document.createElement('tr');
	var tableData1 = document.createElement('td');
	var tableData2 = document.createElement('td');
	var tableData3 = document.createElement('td');
	tableData3.innerHTML = "<b id=" + idCount + " onclick='removeRow("
			+ idCount + ")'>X</b>";
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

function triggerError() {
	/*
	 * js has errors not Exceptions There are 6 main errors; eval error - call
	 * eval and is an error. deprecated. replaced with SyntaxError range error -
	 * Input is outside declare range reference error - when you reference
	 * undeclared variable syntax error - self explanatory type error - occurs
	 * when you do illegal type conversion URI error - having illegal character
	 * in URI
	 * 
	 */
	try {
		console.log(p++)

	} catch (container) {
		console.log(container.message);
	} finally {
		console.log("There is finally in js too?")
	}

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

function Employee(){
    var name = "bob";
}
undefined
var a = Employee()
undefined
a
undefined
typeof a
"undefined"
var a =  new Employee()
undefined
typeof a
"object"
emp1 = new Employee();
Employee {}
emp1.a
undefined
function Employee(){
    var name = "default name";
}
undefined
emp1 = new Employee();
Employee {}
emp1.a
undefined
a
Employee {}
emp1.a
undefined
typeof a
"object"
Employee.prototype.a = "default name"
"default name"
a.name
undefined
Employee.a
undefined
function Employee(name, age){
    if(name){
        this.name= name}
    if(age){
        this.age = age;
    }
}
undefined
emp1 = new Employee();
Employee {}
Employee.prototype.name = "default name";
"default name"
Employee.prototype.age = "default age";
"default age"
emp1.name
"default name"
emp1.age
"default age"
emp2 = new Employee(5, "hammmer")
Employee {name: 5, age: "hammmer"}
emp2
Employee {name: 5, age: "hammmer"}
Employe.prototype.name = "Hanss"
VM943:1 Uncaught ReferenceError: Employe is not defined
    at <anonymous>:1:1
(anonymous) @ VM943:1
Employee.prototype.name = "Hanss"
"Hanss"
emp1.name
"Hanss"
emp2.name
5