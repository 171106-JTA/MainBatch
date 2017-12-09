window.onload = function() {
	displayUsername();
	getRequests();
	document.getElementById("approveOption").addEventListener("change", checkDescription);
	document.getElementById("actionType").addEventListener("change", displayAction);
}

function displayAction() {
	
	var action = document.getElementById("actionType").value;
	
	if (action === "Approve") {
		var infoDiv = document.getElementById("requestInfoDiv");
		var reqForm = document.getElementById("approveRequestForm");
		infoDiv.setAttribute("style", "display:none");
		reqForm.setAttribute("style", "visibility:visible");
	} else {
		var infoDiv = document.getElementById("requestInfoDiv");
		var reqForm = document.getElementById("approveRequestForm");
		infoDiv.setAttribute("style", "visibility:visible");
		reqForm.setAttribute("style", "display:none");
	}
	
}

function checkDescription() {
	var approveOption = document.getElementById("approveOption");
	var option = approveOption.value;
	
	var denyReasonDiv = document.getElementById("denyReasonDiv");
	var denyReasonInput = document.getElementById("denyReasonInput");
	
	if (option === "Deny") {
		denyReasonDiv.setAttribute("style", "visibility:visible");
		denyReasonInput.required = true;
		
	} else {
		denyReasonDiv.setAttribute("style", "visibility:hidden");
		denyReasonInput.required = false;
	}
}

/**
 * 
 * @returns
 */
function getRequests() {
	console.log("get all requests");
	var url = "GetRequestServlet";

	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){

			var xmlText = xhr.responseXML;

			var response = xmlText.getElementsByTagName("request");

			var resultTable = document.getElementById("requestTable");

			for (var i = 0; i < response.length; i++) {
				
				var row = document.createElement("tr");
				var td1 = document.createElement("td");
				var td2 = document.createElement("td");
				var td3 = document.createElement("td");
				var td4 = document.createElement("td");
				var td5 = document.createElement("td");
				var td6 = document.createElement("td");
				var td7 = document.createElement("td");
				var td8 = document.createElement("td");
				var td9 = document.createElement("td");
				var td10 = document.createElement("td");
				var td11 = document.createElement("td");
				var id = document.createElement("td");
				
				td1.innerHTML = response[i].childNodes[0].innerHTML;
				td2.innerHTML = response[i].childNodes[1].innerHTML;
				td3.innerHTML = response[i].childNodes[2].innerHTML;
				td4.innerHTML = response[i].childNodes[3].innerHTML;
				td5.innerHTML = response[i].childNodes[4].innerHTML;
				td6.innerHTML = "$" + response[i].childNodes[5].innerHTML;
				td7.innerHTML = response[i].childNodes[6].innerHTML;
				td8.innerHTML = response[i].childNodes[7].innerHTML;
				td9.innerHTML = response[i].childNodes[8].innerHTML;
				td10.innerHTML = response[i].childNodes[9].innerHTML;
				td11.innerHTML = response[i].childNodes[10].innerHTML;
				id.innerHTML = response[i].childNodes[11].innerHTML;
				
				row.appendChild(id);
				row.appendChild(td1);
				row.appendChild(td2);
				row.appendChild(td3);
				row.appendChild(td4);
				row.appendChild(td5);
				row.appendChild(td6);
				row.appendChild(td7);
				row.appendChild(td8);
				row.appendChild(td9);
				row.appendChild(td10);
				row.appendChild(td11);
				resultTable.appendChild(row);
			}
 		

		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AJAXErrorRequests").innerHTML="Unable to display requests.";
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