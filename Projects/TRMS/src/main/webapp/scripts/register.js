function validate(form) {
	console.log("In validate()");
    var passed = true;
    for(let input of form) {
    	if(!check(input)) {
    		passed = false;
    	}
    }
    return passed;
}
function check(input) {
	console.log("In check()");
	switch(input.id){
		case "password":
			if((document.getElementById("verifypassword").value != input.value) || (input.value == "")) {
				input.style.color = "red";
				console.log(input.id + " red");
				return false;
			} else if(input.style.color == "red") {
				input.style.color = "black";
			}
			return true;
		case "verifypassword":
			if((document.getElementById("password").value != input.value) || (input.value == "")) {
				input.style.color = "red";
				console.log(input.id + " red");
				return false;
			} else if(input.style.color == "red") {
				input.style.color = "black";
			}
			return true;
		case "middlename":
			return true;
		default:
			if(input.value == "") {
				input.style.color = "red";
				return false;
			} else if(input.style.color == "red"){
				input.style.color = "red";
			}
			return true;
	}
}
function submitForm(){
	console.log("In submitForm()");
	var form = document.getElementsByTagName("input");
	if(!validate(form)){
		return;
	}
	
	var request = "";
	for(let input of form){
		request += input.id + ":" + input.value + "&";
	}
	console.log(request);
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		var response = "";
		if(this.readyState == 4){
			if(this.status == 200){
				welcome();
			} else if (this.status == 204){
				displayExistsError();
			} else {
				displayServerError();
			}
		}
	}
	
	xhr.open("POST", "CreateEmployeeAccountServlet");
	xhr.send(request);
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
		        "<li><button id='submitbutton' onclick='submitForm()'>submit</button></li>" +
		    "</ul>" +
		"</div>";
}
function displayExistsError() {
	document.getElementById("content").innerHTML =
		"<h1>Account Already Exists</h1>" +
			"<button onclick='displayRegistration()'>back</button>" +
			"<button onclick='login()'>login</button>";
}
function displayServerError() {
	document.getElementById("content").innerHTML =
		"<h1>Could Not Create Account</h1>" +
			"<button onclick='displayRegistration()'>back</button>" +
			"<button onclick='welcome()'>home</button>";
}