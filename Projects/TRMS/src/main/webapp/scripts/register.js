function register() {
    var element = document.getElementById("content");
    element.innerHTML = displayRegistration(); 
    var verifypassword = document.getElementById("verifypassword");
    verifypassword.addEventListener("focusout", () => {
    	var password = document.getElementById("password");
    	if(verifypassword.value != password.value){
    		verifypassword.style.color = "red";
    	} else if(verifypassword.style.color != "black"){
    		verifypassword.style.color = "black";
    	}
    })
}
function displayRegistration(){
	return	"<h1>Register</h1>" +
			"<form id='registration' action='submitForm()' method='POST' onsubmit='return validateRegistration()'>" +
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
		        "<li><input id='submitbutton' type='submit' value='submit'></li>" +
		    "</ul>" +
		"</form>";   
}
function submitForm(){
	var forms = document.getElementsByTagName("input");
	var request = "";
	var response = "";
	for(let form of forms){
		if(form.id != "submitbutton"){
			request += form.id + ":" + form.value + "&";
		}
	}
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if(this.readyState == 4){
			if(this.status == 200){
				response = this.responseText;
			} else if (this.status == 204){
				
			} else {
				
			}
		}
	}
	xhr.open("POST", "CreateEmployeeAccountServlet");
	xhr.send(request);
	return response;
}