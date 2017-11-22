function validUsername()
{
	
	
}

function validPassword()
{
	
}

function login() { 
	// no user field should be blank
	if (($('#username').val().trim() == '') && ($('#password').val().trim() == ''))
	{
		console.log("no fields should be blank");
		return;
	}
	// user authentication logic goes here....
}

$(function() { 
	// a click of the 'New User?' button should summon the 'New User' page
	$('#newUserButton').click(function(event) { 
		window.location.href = 'TRMS_NewUser.html';
		console.log(window.location.href);
		event.preventDefault();
	});
	
	// a click of the login button should log the user in
	$('input[type="submit"]').click(function(event) { 
		console.log('User tried to log in');
		event.preventDefault();
	});
	// make an 'ENTER' on any of the text fields trigger a submission of the form
	$('input[type="text"],input[type="password"]').keypress(function(event) {
		if (event.key.toUpperCase() == 'ENTER')
		{
			$('input[type="submit"]').click();
			// prevent default action
			event.preventDefault();
		}
	});
});