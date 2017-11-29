function validateLogin(){
	console.log("test2");
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(this.readyState == 4 && this.status== 200){
			var error = this.responseText;
			var e1 = document.createElement('div');
			e1.innerHTML = "<div class='alert alert-danger'>" + error + "</div>";
			document.getElementById("loginForm").appendChild(e1);
			window.location = "index.html";
		}

		
	}
	xhr.open("POST", "TrainerLogin", true);
	xhr.send();
}