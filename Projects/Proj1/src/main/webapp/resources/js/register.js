function validateRegistration(){
	let form = document.forms['registration'];
	let pw = form['password'].value;
	let pwConfirm = form['password2'].value;
	console.log(pw + " " + pwConfirm);
	var error = document.getElementById("error");
	
	if(pw != pwConfirm){
		error.innerHTML ="Passwords do not match";
		return false;
	}
	else if(pw.length < 3){
		error.innerHTML = "Passwords require at least 4 characters";
		return false;
	}
	return true;
}