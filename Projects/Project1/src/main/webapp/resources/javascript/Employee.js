window.onload = function() {

	displayUsername();
	displayRequests();

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