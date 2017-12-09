window.onload = function(){
	displayUsername();
	showUnverifiedUsers();
	displaySupervisors();
	
	document.getElementById("actionType").addEventListener("change", displayAction);
}

function displayAction() {
	
	var action = document.getElementById("actionType").value;
	var assignEmpDiv = document.getElementById("assignEmployeeDiv");
	var requests = document.getElementById("requests");
	
	if (action == "Approve/Deny a Request") {
		requests.setAttribute("style", "display:none");
		assignEmpDiv.setAttribute("style", "visibility:visible");
	} else {
		assignEmpDiv.setAttribute("style", "display:none");
		requests.setAttribute("style", "visibility:visible");
	}
}

username = ""; admintitle = "";

/**
 * Display the functionality if a direct supervisor is logged in.
 * @param div
 */

function displaySupervisors() {
	var url = "GetDirectSupervisors";
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if( xhr.readyState == 4 && xhr.status == 200) {

			var xmlText = xhr.responseXML;
			var response = xmlText.getElementsByTagName("employee"); //grab the employees
			var resultTable = document.getElementById("dirSupTable"); //fill our html table with the data

			//loop through our response text and append the employee data to our table
			for(var i = 0; i < response.length; i++) {
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
			document.getElementById("dirAjaxError").innerHTML="Unable to display DirectSupervisors.";
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