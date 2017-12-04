window.onload = function() {
	
	var welcome = document.getElementById("welcome");
	var url = "GetUsernameServlet";
	
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){
			
			var xmlText = xhr.responseXML;
			var response = xmlText.getElementById("username");
			
			welcome.innerHTML = "Welcome " + response.innerHTML;
			
			
		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AJAXError").innerHTML="Unable to fetch username";
		}
	}
	
	xhr.open("GET", url);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
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