function welcome() {
	document.getElementById("content").innerHTML =
			"<h1>Welcome</h1>" +
			"<h2>login or register</h2>" +
			"<button onclick='login()'>login</button>" + 
			"<button onclick='register()'>register</button>";
}