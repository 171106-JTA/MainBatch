window.onload = function() {

	displayUsername();
	displayRequests();

	document.getElementById("eventName").addEventListener("change", showProjectedCost);
	document.getElementById("eventCost").addEventListener("keyup", showProjectedCost);

	document.getElementById("radioButtons").addEventListener("click", displayView);
}

function displayView() {
	var viewMessages = document.getElementById("View Messages");
	var viewRequests = document.getElementById("View Requests");
	var submitRequest = document.getElementById("Submit a Request");

	var messages = document.getElementById("messages");
	var submitRequestDiv = document.getElementById("submitRequestDiv");
	var requests = document.getElementById("requests");

	if (viewMessages.checked==true) {
		submitRequestDiv.setAttribute("style", "display:none");
		messages.setAttribute("style", "visibility:visible");
		requests.setAttribute("style", "display:none");
	} else if (submitRequest.checked==true) {
		messages.setAttribute("style", "display:none");
		submitRequestDiv.setAttribute("style", "visibility:visible");
		requests.setAttribute("style", "display:none");
	} else {
		submitRequestDiv.setAttribute("style", "display:none");
		messages.setAttribute("style", "display:none");
		requests.setAttribute("style", "visibility:visible");
	}
}

function showProjectedCost() {
	var eventName = document.getElementById("eventName").value;
	var eventCost = document.getElementById("eventCost").value;

	if (eventCost == 0) {
		eventCost = 0;
	}
	var url = "GetProjectedCost";
	var xhr = new XMLHttpRequest();

	var fd = new FormData();
	fd.append("eventName", eventName);
	fd.append("eventCost", eventCost);

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {

			var xmlText = xhr.responseXML;
			var response = xmlText.getElementsByTagName("cost");
			var projectedResult = document.getElementById("projectedResult");

			projectedResult.innerHTML = "$" + response[0].childNodes[0].nodeValue;
		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
		}
	}

	xhr.open("POST", url);
	xhr.send(fd);
}

function displayUsername() {
	var welcome = document.getElementById("welcome");
	var titleD = document.getElementById("titleD");
	var url = "GetUsernameServlet";

	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){

			var xmlText = xhr.responseXML;
			var response = xmlText.getElementsByTagName("root");

			var fname = response[0].childNodes[0].innerHTML;
			var lname = response[0].childNodes[1].innerHTML;
			var title = response[0].childNodes[2].innerHTML;

			welcome.innerHTML = "Welcome " + fname + " " + lname;
			titleD.innerHTML = " (" + title + ")";
		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			welcome.innerHTML="Unable to fetch username";
		}
	}

	xhr.open("GET", url);
	xhr.send();
}

function displayRequests() {
	var url = "EmployeeGetRequests";

	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){

			var xmlText = xhr.responseXML;
			var response = xmlText.getElementsByTagName("request");

			var resultTable = document.getElementById("requestsTable");
			var resultTableBody = document.getElementById("empRequestsTBody");
			
			for (var i = 0; i < response.length; i++) {
				
				var row = document.createElement("tr");
				var id = document.createElement("td");
				var event = document.createElement("td");
				var location = document.createElement("td");
				var cost = document.createElement("td");
				var description = document.createElement("td");
				var gradingFormat = document.createElement("td");
				var submissionDate = document.createElement("td");
				var eventDate = document.createElement("td");
				var file = document.createElement("td");
				var status = document.createElement("td");
				
				id.innerHTML = response[i].childNodes[0].innerHTML;
				event.innerHTML = response[i].childNodes[1].innerHTML;
				location.innerHTML = response[i].childNodes[2].innerHTML;
				cost.innerHTML = "$" + response[i].childNodes[3].innerHTML;
				description.innerHTML = response[i].childNodes[4].innerHTML;
				gradingFormat.innerHTML = response[i].childNodes[5].innerHTML;
				submissionDate.innerHTML = response[i].childNodes[6].innerHTML;
				eventDate.innerHTML = response[i].childNodes[7].innerHTML;
				file.innerHTML =  "";
				status.innerHTML = response[i].childNodes[9].innerHTML;
				
				row.appendChild(id);
				row.appendChild(event);
				row.appendChild(location);
				row.appendChild(cost);
				row.appendChild(description);
				row.appendChild(gradingFormat);
				row.appendChild(submissionDate);
				row.appendChild(eventDate);
				row.appendChild(file);
				row.appendChild(status);
				resultTableBody.appendChild(row);
			}
			resultTable.append(resultTableBody);

		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
		}
	}

	xhr.open("GET", url);
	xhr.send();
}