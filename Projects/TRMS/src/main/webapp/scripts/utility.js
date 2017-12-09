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
function submitForm(servlet, handler){
	var form = document.getElementsByTagName("input");
	if(!validate(form)){
		return;
	}	
	var request = "";
	for(let input of form){
		request += input.id + ":" + input.value + "&";
	}
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		var response = "";
		if(this.readyState == 4){
			handler(this.status, this.responseText);
		}
	}	
	xhr.open("POST", servlet);
	xhr.send(request);
}
function displayServerError() {
	document.getElementById("content").innerHTML =
		"<h1 style='color: red'>Something Didn't Go Right</h1>" +
			"<button onclick='welcome()'>home</button>";
}