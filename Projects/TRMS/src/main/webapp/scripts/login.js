function loginHandler(status, response){
	console.log("login handler");
	if(status == 200){
		displayAcount();
	} else if(status == 403){
		displayLoginError();
	} else{
		displayServerError();
	}
}
function displayLogin() {
	document.getElementById("content").innerHTML =
		"<h1>Login</h1>" +
		"<div>" +
			"<ul style='list-style-type: none;'>" +
				"<li><h2>enter credentials</h2></li>" +
	            "<li><input type='text' placeholder='username'></li>" +
	            "<li><input type='text' placeholder='password'></li>" +
	            "<li><button onclick='displayWelcome()'>back</button><button onclick='submitForm(\"LoginServlet\", " + loginHandler + ")'>login</button></li>" +
	        "</ul>" +
	    "</div>";
}
function displayLoginError(){
	document.getElementById("content").innerHTML =
		"<h1 style='color: red'>No Matching Credentials Found</h1>" +
		"<button onclick='displayLogin()'>back</button>" +
		"<button onclick='displayWelcome()'>home</button>";	
}