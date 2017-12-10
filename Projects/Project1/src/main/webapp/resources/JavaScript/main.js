$(document).ready(function(){
	$("#login").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		// Checking for blank fields.
		if( username =='' || password ==''){
			$('input[type="text"],input[type="password"]').css("border","2px solid red");
			$('input[type="text"],input[type="password"]').css("box-shadow","0 0 3px red");
			alert("Please fill all fields!");
			}else {
				$.post("Login.do",{ 
					username : username, 
					password : password },
					function(data) {
						var json_data = JSON.parse(data);
						if(json_data.LoginResult=="FailedLogin") {
							alert('Incorrect Username and Password');
						} else if(json_data.LoginResult=="SuccessfulLogin"){
							location.href = json_data.LandingPage;
						}
				});
			}	
		});
	});