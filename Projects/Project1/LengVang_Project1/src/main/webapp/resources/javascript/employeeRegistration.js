/**
 * 
 */

function validateRegistration(){
	var form = document.forms["registerEmployee"];
	var password1 = form["password"].value;
	var password2 = form["password2"].value;
	console.log(password1 + " " + password2);
	var error = document.getElementById("error");
	
	if(password1 != password2){
		error.innerHTML ="Passwords do not match";
		return false;
	}
	if(password1.length < 8){
		error.innerHTML = "Passwords require at least 8 characters";
		return false;
	}
	return true;
}