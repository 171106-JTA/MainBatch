/**
 * 
 */
function submit() {
	
	var pass1 = document.getElementById("regPassword1");
	var pass2 = document.getElementById("regPassword2");
	
	var username = document.getElementById("regUsername");
	
	isNameTaken(username);
	
	//passwords must be equal
	if (pass1 != pass2) {
		console.log("these passwords do not match");
	}
	
	
	
}

function isNameTaken(username) {
	
	var infoMessage = document.getElementById("infoMessage");
	var url = "DoesEmployeeExist";

	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){

			var xmlText = xhr.responseXML;
			var response = xmlText.getElementsByTagName("root");
			
			var result = response[0].childNodes[0].innerHTML;
			console.log("success: " + success);
			
			if (result == "usernameIsNew") {
				console.log("usernameIsNew");
			} else if (result == "usernameIsTaken") {
				console.log("usernameIsTaken");
			} else {
				console.log("some other result??");
			}

			infoMessage.innerHTML = result;
		} else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
		}
	}
	userdata = {"regUsername": "username"};
	xhr.open("POST", url);
	xhr.send(userdata);
}