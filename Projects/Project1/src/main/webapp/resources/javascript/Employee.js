window.onload = function() {

	displayUsername();
	displayRequests();

	document.getElementById("eventName").addEventListener("change", showProjectedCost);
	document.getElementById("eventCost").addEventListener("keyup", showProjectedCost);
	console.log("hello");
}

function showProjectedCost() {
	console.log("this changed");
	var eventName = document.getElementById("eventName").value;
	var eventCost = document.getElementById("eventCost").value;
	
	if (eventName == null || eventCost == null)
		return;
	
	var url = "GetProjectedCost";
	var xhr = new XMLHttpRequest();
	
	var fd = new FormData();
	fd.append("eventName", eventName);
	fd.append("eventCost", eventCost);

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){

			var xmlText = xhr.responseXML;
			var response = xmlText.getElementsByTagName("root");
			var projectedResult = document.getElementById("projectedResult");
			
			projectedResult.innerHTML = "$" + response[0].childNode[0].innerHTML
		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
		}
	}

	xhr.open("POST", url);
	xhr.send(fd);
}

function displayUsername() {
	var welcome = document.getElementById("welcome");
	var url = "GetUsernameServlet";

	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){
			
			var xmlText = xhr.responseXML;
			var response = xmlText.getElementsByTagName("root");
			
			var fname = response[0].childNodes[0].innerHTML;
			var lname = response[0].childNodes[1].innerHTML;
			var title = response[0].childNodes[2].innerHTML;
			
			welcome.innerHTML = "Welcome " + fname + " " + lname + " (" + title + ")";
		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			welcome.innerHTML="Unable to fetch username";
		}
	}
	
	xhr.open("GET", url);
	xhr.send();
}

function displayRequests() {
	var url = "GetRequestServlet";

	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){

//			var xmlText = xhr.responseXML;
//			var response = xmlText.getElementsByTagName("root");
//			
//			var fname = response[0].childNodes[0].innerHTML;
//			var lname = response[0].childNodes[1].innerHTML;
//			var title = response[0].childNodes[2].innerHTML;
			
//			welcome.innerHTML = "Welcome " + fname + " " + lname + " (" + title + ")";
		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			welcome.innerHTML="Unable to fetch username";
		}
	}

	xhr.open("GET", url);
	xhr.send();
}



function validateLogin(){
	var form = document.forms["registration"];
	var username = form["usernameLogin"].value;
	var password = form["passwordLogin"].value;


	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){




		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AJAXError").innerHTML="Woops";
		}


	}


	xhr.open("POST",url);

	//Tell our xhr how to send the data to the endpoint.
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send("username=" + input);
}