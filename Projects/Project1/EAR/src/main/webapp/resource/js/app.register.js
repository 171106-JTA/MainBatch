"user strict";

function validateRegistration() {
	var inputs = $("input[type='password']");
	var pwerror = $("#pwerror");
	
	if (inputs[0].value === inputs[1].value) 
		return true;
	else{
		pwerror.text("Passwords must match");
		return false;
	}	
}