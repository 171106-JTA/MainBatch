function displayWelcome() {
	console.log("welcome");
	document.getElementById("content").innerHTML =
			"<h1>Welcome</h1>" +
			"<ul>" + 
				"<h2>login or register</h2>" +
				"<button onclick='displayLogin()'>login</button>" + 
				"<button onclick='displayRegistration()'>register</button>"
			"</ul>";
}