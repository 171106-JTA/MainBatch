window.onload = function(){

	showUnverifiedUsers();
	displayUsername();
	displayApproveOrAssignEmployee();
}

username = ""; admintitle = "";

function displayUsername() {
	var welcome = document.getElementById("welcome");
	var adminTitle = document.getElementById("adminTitle");
	var url = "GetUsernameServlet";

	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){

			var xmlText = xhr.responseXML;
			var response = xmlText.getElementsByTagName("root");

			var fname = response[0].childNodes[0].innerHTML;
			var lname = response[0].childNodes[1].innerHTML;
			var title = response[0].childNodes[2].innerHTML;
			username = response[0].childNodes[3].innerHTML;
			console.log("we found username: " + username);
			admintitle = title;

			welcome.innerHTML = "Welcome " + fname + " " + lname;
			adminTitle.innerHTML = "(" +	title + ")";
		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AJAXError").innerHTML="Unable to fetch username";
		}
	}

	xhr.open("GET", url);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
}

/**
 * Display functionality to an admin dependending on their role.
 */
function displayApproveOrAssignEmployee() {
	console.log(admintitle);
	if (admintitle = "DIRECT_SUPERVISOR") { 
		directSupervisorSelectEmployee();
	} else if (admintitle = "DEPARTMENTHEAD") {

		var header = document.getElementById("directSupervisorsHeader");
		header.innerHTML="Direct Supervisors";
		displaySupervisors();

		var p = document.createElement("p");
		var desc = document.createTextNode("Enter an employee username and the " +
		"direct supervisor you wish to supervise that employee.");  
		p.appendChild(desc);
		div.append(p);

		var inputEmployee = document.createElement("input");
		inputEmployee.setAttribute('type', 'text');
		var p = document.createElement("p");
		p.innerHTML="Employee";
		div.append(p);
		div.append(inputEmployee);

		var inputDirSup = document.createElement("input");
		inputDirSup.setAttribute('type', 'text');
		var p = document.createElement("p");
		p.innerHTML="Direct Supervisor";
		div.append(p);
		div.append(inputDirSup);

		var submit = document.createElement("input");
		submit.setAttribute('type', 'submit');
		div.appendChild(submit);

	} else {
		console.log("someone else");
	}
}

/**
 * Display the functionality if a direct supervisor is logged in.
 * @param div
 */
function directSupervisorSelectEmployee() {
	var div = document.getElementById("assignEmployee");
	//assign the emp to this direct supervisor
	var p = document.createElement("p");
	var desc = document.createTextNode("Enter an employee username to supervise that employee.");  
	p.appendChild(desc);
	div.append(p);

	var f = document.createElement("form");

	f.setAttribute('method', 'post');
	f.setAttribute("id", "formID");
	f.setAttribute('action', 'javascript:directSupervisorSelectEmployeeAjax();');

	var inputField = document.createElement("input");
	inputField.setAttribute('type', 'text');
	inputField.setAttribute('placeholder', 'username');
	inputField.setAttribute('name', 'empUsername');

	var submit = document.createElement("input");
	submit.setAttribute('type', 'submit');

	f.appendChild(inputField);
	f.appendChild(submit);
	div.append(f);
}

function directSupervisorSelectEmployeeAjax() {

	var f = document.getElementById("formID");

	var usernameField = document.createElement("input");
	usernameField.setAttribute('type', 'hidden');
	usernameField.setAttribute('name', "dirSup");
	usernameField.setAttribute('value', username);
	console.log("the username is " + username);
	f.appendChild(usernameField);

	var formdata = new FormData(f);
	var url = "AssignEmployeeToDirectSupervisor";
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){
			showUnverifiedUsers();
			window.location.reload(false);
		} else if(xhr.readyState == 4 && xhr.status != 200){
		}
	}
	xhr.open("POST", url);
	xhr.send(formdata);
}


function displaySupervisors() {
	var url = "GetDirectSupervisors";

	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if( xhr.readyState == 4 && xhr.status == 200) {

			var xmlText = xhr.responseXML;
			var response = xmlText.getElementsByTagName("employee"); //grab the employees
			var resultTable = document.getElementById("dirSups"); //fill our html table with the data

			var rowh = document.createElement("tr");
			var th1 = document.createElement("th");
			var th2 = document.createElement("th");
			var th3 = document.createElement("th");

			th1.innerHTML = "username";	
			th2.innerHTML = "first name";
			th3.innerHTML = "last name";
			rowh.appendChild(th1);
			rowh.appendChild(th2);
			rowh.appendChild(th3);
			resultTable.appendChild(rowh);

			//loop through our response text and append the employee data to our table
			for(var i = 0; i < response.length; i++){
				var row = document.createElement("tr");
				var td1 = document.createElement("td");
				var td2 = document.createElement("td");
				var td3 = document.createElement("td");

				td1.innerHTML = response[i].childNodes[0].innerHTML;	
				td2.innerHTML = response[i].childNodes[1].innerHTML;
				td3.innerHTML = response[i].childNodes[2].innerHTML;
				row.appendChild(td1);
				row.appendChild(td2);
				row.appendChild(td3);
				resultTable.appendChild(row);
			}

		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AJAXError").innerHTML="Unable to display requests.";
		}
	}
	xhr.open("GET", url);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();

}

/*
 * Show unverified users, if the current admin is a direct supervisor then any employees 
 * they approve will report to that direct supervisor. Any other admin will choose a direct supervisor
 * for that employee.
 */
function showUnverifiedUsers() {
	var url = "GetUnverifiedEmployeesServlet";

	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if( xhr.readyState == 4 && xhr.status == 200) {

			var xmlText = xhr.responseXML;
			var response = xmlText.getElementsByTagName("employee"); //grab the employees
			var resultTable = document.getElementById("unvEmployeeTable"); //fill our html table with the data
			var newTbody = document.getElementById('unvTbody');
			newTbody.setAttribute('id', 'unvTbody');

			//loop through our response text and append the employee data to our table
			for(var i = 0; i < response.length; i++){
				var row = document.createElement("tr");
				var td1 = document.createElement("td");
				var td2 = document.createElement("td");
				var td3 = document.createElement("td");

				td1.innerHTML = response[i].childNodes[0].innerHTML;
				td2.innerHTML = response[i].childNodes[1].innerHTML;
				td3.innerHTML = response[i].childNodes[2].innerHTML;
				row.appendChild(td1);
				row.appendChild(td2);
				row.appendChild(td3);
				newTbody.appendChild(row);
			}
			resultTable.append(newTbody);

		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AJAXError").innerHTML="Unable to display requests.";
		}
	}
	xhr.open("GET", url);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
}