function displayLogin() {
	document.getElementById("content").innerHTML =
		"<h1>Login</h1>" +
		"<form action='LoginServlet' method='POST'>" +
			"<ul style='list-style-type: none;'>" +
				"<li><h2>enter credentials</h2></li>" +
	            "<li><input type='text' placeholder='username'></li>" +
	            "<li><input type='text' placeholder='password'></li>" +
	            "<li><input type='submit' value='submit'></li>" +
	        "</ul>" +
	    "</form>";
}