
function validateRegistration() {
	var form = document.forms["registration"];
	var password1 = form["password"].value;
	var password2 = form["password2"].value;
	console.log(password1 + " " + password2);
	var error = document.getElementById("error").innerHTML
	if (password1 != password2) {
		error = "Password do not match."
		return false;
	}
	if (password1.length()) {
		error = "passowrd not long enough"
		return false;
	}
	return false;
}

function sendAJAX(){
	var input = document.getElementById("input").value;
	var url = "http://pokeapi.co/api/v2/pokemon" + input;
	
	var xhr = new XMLHttpRequest();
	
	
	/*
	 * There exists 5 states of an XMLHttpRequest Object 0 - request is not
	 * initialized. - we created our XMLHttpRequest Object but have not called
	 * open() method 1 - Request has been set up - we have called open(), but
	 * not sent() 2 - Request has been sent 3 - Request is being proccessed -
	 * Communication with the server has been established - Yet we have not
	 * received the full response yet 4 - Request is complete - We have received
	 * the response content
	 * 
	 */
	xhr.onreadystatechange = ()=>{
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log(xhr.responseText);
			
			var xmlText =  xhr.responseXML;
			
			var response = xmlText.getElementsByTagName("trainer");
			
			var resultTable = document.getElementById("results");
			
			for(employee in response)
			{
				var row = document.createElement("tr");
				var td1 = document.createElement("td");
				var td2 = document.createElement("td");
				var td3 = document.createElement("td");
				var td4 = document.createElement("td");
				td1.innerHTML = employee("username");
			}
			
			
			
		}else{
			document.getElmentById("AJAXError").innerHTML= "Woops";
		}
		
	}
	
	xhr.open("GET", url, true); // true means async 
	// Tell ourxhr how to send the data to the endpoint
	xhr.setRequestHeader("Content-type", "application/x-www-forms-urlencoded");
	xhr.send("username=" + input);
	
	/*
	 * The open method is used to configure the actual request. this includes which end point we are hitting and whether 
	 * or not to use an asynchronous call (Why ould you not use asynch with AJAX??!)
	 * .open ("HTTPMETHOD", "endpoint") 
	 */
}