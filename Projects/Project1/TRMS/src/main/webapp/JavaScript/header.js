"use strict";

//Populate header with buttons and register event listeners.
var header = document.getElementsByTagName("header")[0];

header.innerHTML = '<span style="float:right">' +
				   		'<form action="Logout" method="POST">' +
				   			'<button id="headerLogoutButton" class="btn ">Log Out</button>' + 
				   		'</form></span>' ;

//Logout
document.getElementById("headerLogoutButton").addEventListener("click", function() {
																			window.location.href = "index.html";
																		}
);

