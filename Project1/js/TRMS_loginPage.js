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

/* Checks if username is valid
 * Returns:
 *	• true if username is in the data store or false if it either isn't or is empty
 */
function validUsername()
{
	// for now, usernames are valid iff they aren't empty
	return !isEmpty($('#username'));
}

/* Checks if password is valid
 * Returns:
 *	• true if password is in the data store or false if it either isn't or violates the password rules
 */
function validPassword()
{
	// for now, passwords are valid iff they aren't empty
	return !isEmpty($('#password'));
}

/* Does login logic, logging the user in
 * 
 */
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
	
});