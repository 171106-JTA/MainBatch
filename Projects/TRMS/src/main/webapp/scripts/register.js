function registrationHandler(status, response){
	if(status == 200){
		alert("Account successfully created");
		displayWelcome();
	} else if(status == 204){
		//displayExistsError();
	} else {
		//displayServerError();
	}
}
function displayRegistration(){
	document.getElementById("content").innerHTML = 
		"<h1>Register</h1>" +
		"<div id='registration'>" +
			"<ul style='list-style-type: none;'>" +
				"<li><h2>account information</h2></li>" +
		        "<li><input id='username' type='text' placeholder='new username' maxlength='20'></li>" +
		        "<li><input id='password' type='text' placeholder='new password' maxlength='30'></li>" +
		        "<li><input id='verifypassword' type='text' placeholder='verify password' maxlength='30'></li>" + 
		        "<li><input id='firstname' type='text' placeholder='first name' maxlength='20'></li>" +
		        "<li><input id='middlename' type='text' placeholder='middle name' maxlength='20'></li>" +
		        "<li><input id='lastname' type='text' placeholder='last name' maxlength='20'></li>" +
		        "<li><input id='email' type='text' placeholder='email' maxlength='50'></li>" +
		        "<li><input id='address' type='text' placeholder='street address' maxlength='50'></li>" +
		        "<li><input id='city' type='text' placeholder='city' maxlength='30'></li>" +
		        "<li><input id='state' type='text' placeholder='state' maxlength='2'></li>" +
		        "<li><h2>verify your employment</h2></li>" +
		        "<li><input id='employeeid' type='number' placeholder='employee id'></li>" +
		        "<li><input id='positionid' type='number' placeholder='position id'></li>" +
		        "<li><input id='reportsto' type='number' placeholder='supervisor id'></li>" +
		        "<li><input id='facilityid' type='number' placeholder='facility id'></li>" +
		        "<li><button onclick='displayWelcome()'>back</button>" +
		        "<button id='submitbutton' onclick='submitForm(\"CreateEmployeeAccountServlet\", registrationHandler)'>submit</button></li>" +
		    "</ul>" +
		"</div>";
}
function displayExistsError() {
	document.getElementById("content").innerHTML =
		"<h1 style='color: red'>Account Already Exists</h1>" +
			"<button onclick='displayRegistration()'>back</button>" +
			"<button onclick='login()'>login</button>";
}