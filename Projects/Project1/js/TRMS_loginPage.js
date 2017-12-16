
var MAX_LOGIN_ATTEMPTS = 5;

/* Checks if field is empty
 * Parameters: 
 *  • jQuery object representing the field, the id of the field, or the field's value itself
 * Returns: 
 *	• true if field is empty or false otherwise. 
 */
function isEmpty(field)
{
	// if the type of field is 'string' 
	if (field === field.toString())
	{
		// ... and it is not a valid HTML object ...
		if ($(field).length == 0)
		{
			// ... we treat it like a value
			return (field.trim() === '');
		}
	}
	return ($(field).val().trim() === '');	
}

/* Same as above, but checks for valid email
 */
function isEmail(field)
{
	var email;
	if ((field === field.toString()) && ($(field).length == 0))
	{
		email = field;
	}
	else
	{
		email = $(field).val();
	}
	return (email.indexOf('@') !== -1) && (email.indexOf('.') !== -1);
}

/* Checks if username is valid
 * Returns:
 *	• true if username is in the data store or false if it either isn't or is empty
 */
function validUsername()
{
	// for now, usernames are valid iff they aren't empty
	var usernameNotEmpty = !isEmpty($('#username'));
	
	return usernameNotEmpty && isEmail($('#username'));
}

/* Checks if password is valid
 * Returns:
 *	• true if password is in the data store or false if it either isn't or violates the password rules
 */
function validPassword()
{
	// for now, passwords are valid iff they aren't empty
	var passwordNotEmpty = !isEmpty($('#password'));
	
	return passwordNotEmpty;
}

/* Does login logic, logging the user in
 * 
 */
function login() { 
	// user authentication logic goes here....
	// if the username is invalid
	if (!validUsername())
	{
		// add '.ng-invalid' class to the $('#username')
		if (!$('#username').hasClass('ng-invalid')) $('#username').addClass('ng-invalid');
		
	}
	// otherwise if $('#username') has class 'ng-invalid'
	else if ($('#username').hasClass('ng-invalid'))
	{
		// remove it
		$('#username').removeClass('ng-invalid');
	}
	// same logic for password field
	if (!validPassword())
	{
		if (!$('#password').hasClass('ng-invalid')) $('#password').addClass('ng-invalid');
	}	
	else if ($('#password').hasClass('ng-invalid'))
	{
		$('#password').removeClass('ng-invalid');
	}
	// TODO: AJAX logic to send to the server
	// TODO: on error, re-render errors or if allotted login attempts exceeded, redirect to locked out screen
	// TODO: on success, redirect to dashboard
}


$(function() { 
	// a click of the new user button should take the user to the new user screen
	$('#newUserButton').click(function(event) { 
		window.location.href = 'TRMS_NewUser.html';
		event.preventDefault();
	});
	// a click of the login button should log the user in
	$('input[type="submit"]').click(function(event) { 
		login();
		event.preventDefault();
	});
	$('input[type="text"],input[type="password"]').keypress(function(event) {
		// make an 'ENTER' on any of the text fields trigger a submission of the form
		if (event.key.toUpperCase() == 'ENTER')
		{
			$('input[type="submit"]').click();
			// prevent default action
			event.preventDefault();
		}
	});
	// validation of input fields
	$('input[type="text"],input[type="password"]').keyup(function(event) {
		console.log('value of this field: ' + $(this).val());
		// if the field is empty
		if ($(this).val().trim() === '')
		{
			// give it a class of ng-invalid, if it doesn't have one already
			if (!$(this).hasClass('ng-invalid')) $(this).addClass('ng-invalid');
		}
		// otherwise
		else
		{
			// remove the ng-invalid class from it
			if ($(this).hasClass('ng-invalid')) $(this).removeClass('ng-invalid');
		}
	});
	// clear button should also remove any classes from $('#username'),$('#password')
	$('input[type="reset"]').click(function(event) { 
		$('#username,#password').removeClass('ng-invalid');
	});
	
});