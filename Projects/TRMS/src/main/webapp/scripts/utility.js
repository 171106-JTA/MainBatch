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
		case "eventid":
			if(input.value == ""){
				input.style.color = "red";
				return false
			} else if(input.style.color == "red"){
				input.style.color = "black";
			}
			return true;
		case "password":
			var ele = document.getElementById("verifypassword");
			if((ele == null) && (input.value == "")){
				input.style.color = "red";
				return false;
			} else if((ele != null) && (ele.value != input.value) || (input.value == "")) {
				input.style.color = "red";
				console.log(input.id + " red");
				console.log("fail");
				return false;
			} else if(input.style.color == "red") {
				input.style.color = "black";
			}
			return true;
		case "verifypassword":
			if((document.getElementById("password").value != input.value) || (input.value == "")) {
				input.style.color = "red";
				console.log(input.id + " red");
				console.log("fail");
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
				input.style.color = "black";
			}
			return true;
	}
}
function submitForm(servlet, handler){
	console.log("submit");
	console.log(servlet);
	console.log(handler);
	var form = document.getElementsByTagName("input");
	if(!validate(form)){
		console.log("fail submit");
		return;
	}
	console.log("pass submit");
	var request = "";
	for(let input of form){
		request += input.id + ":" + input.value + "&";
	}
	console.log("request " + request);
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		var response = "";
		if(xhr.readyState == 4){
			console.log("response " + xhr.responseText);
			console.log("status " + xhr.status);
			handler(xhr.status, xhr.responseText);
		}
	}	
	xhr.open("POST", servlet);
	xhr.send(request);
}
function grabProcessingApps(servlet, id){
	var request = "employeeid:" + id;
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				apps = null;
				console.log("QWER");
				console.log(xhr.responseText);
				if(xhr.responseText != ""){
					apps = JSON.parse(xhr.responseText);
				}
				displayAdminControls();
			} else {
			}
		}
	}	
	xhr.open("POST", servlet);
	xhr.send(request);
}
function grabCurrentApps(servlet, id){
	console.log("submit");
	console.log(servlet);
	console.log("pass submit");
	var request = "employeeid:" + id;
	console.log("request " + request);
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				apps = null;
				if(xhr.responseText != ""){
					apps = JSON.parse(xhr.responseText);
				}
				displayAccount();
			} else {
			}
		}
	}	
	xhr.open("POST", servlet);
	xhr.send(request);
}
function submitUpdate(object){
	
}
function displayServerError() {
	document.getElementById("content").innerHTML =
		"<h1 style='color: red'>Something Didn't Go Right</h1>" +
			"<button onclick='displayWelcome()'>home</button>";
}