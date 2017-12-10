function loginHandler(status, response){
	if(status == 200){
		response = JSON.parse(response);
		employee = EmployeeAccount(response);
		grabCurrentApps("RetrieveReimbursementApplicationsServlet", employee.employeeid);
		console.log(employee);
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
	            "<li><input id='username' type='text' placeholder='username'></li>" +
	            "<li><input id='password' type='text' placeholder='password'></li>" +
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