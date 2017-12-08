window.onload = function() {

	document.getElementById("regPassword1").addEventListener("keyup", checkPasswordLength);
	document.getElementById("regPassword2").addEventListener("keyup", checkPasswords);
	document.getElementById("regUsername").addEventListener("focusout", checkNameIsTaken);
	
	//document.getElementById("regForm").addEventListener("submit", submit, false);
}

goodUsername = false; passwordsMatch = false; passwordsLength = false;

function submit() {
	
	
	
}

function checkPasswordLength() {
	var alertMessage = document.getElementById("regPasswordLengthCheck");
	var pass1 = document.getElementById("regPassword1").value;
	
	if (pass1.length < 5 || pass1.length > 15) {
		alertMessage.innerHTML = "Your password must be between 5 and 15 characters."
		return false;
	} else {
		return true;
	}
}

/**
 * 
 */
function checkPasswords(event) {
	var alertMessage = document.getElementById("regPasswordEqualityCheck");
	var pass1 = document.getElementById("regPassword1").value;
	var pass2 = document.getElementById("regPassword2").value;
	console.log(pass1);
	console.log(pass2);
	
	//passwords must be equal
	if (pass1 !== pass2) {
		alertMessage.innerHTML = "These paswords do not match. ";
		return false;
	} else {
		alertMessage.innerHTML = "";
		return true;
	}
}

function checkNameIsTaken(username) {
	var usernameField = document.getElementById("regUsername");
	var username = usernameField.value;
	
	var infoMessage = document.getElementById("regUsernameSpan");
	var url = "DoesEmployeeExist";

	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){

			var xmlText = xhr.responseXML;
			var response = xmlText.getElementsByTagName("root");
			
			var result = response[0].childNodes[0].nodeValue;
			
			if (result !== "usernameIsNew") {
				infoMessage.innerHTML="This username has been taken";
			} else {
				infoMessage.innerHTML="";
			}
		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
		}
	}
	
	var fd = new FormData();
	fd.append("regUsername", username);
	
	xhr.open("POST", url);
	xhr.send(fd);
}