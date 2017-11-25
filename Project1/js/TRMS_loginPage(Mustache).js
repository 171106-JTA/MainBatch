// constants used throughout this app
var EMPTY_FIELD = "field is empty",
	USER_NOT_FOUND = "User is not in our system";

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
	var usernameNotEmpty = !isEmpty($('#username'));
	
	if (!usernameNotEmpty) 
	{
		throw {
			field : 'username',
			errors: [ EMPTY_FIELD ]
		}
	}
	return true;
}

/* Checks if password is valid
 * Returns:
 *	• true if password is in the data store or false if it either isn't or violates the password rules
 */
function validPassword()
{
	// for now, passwords are valid iff they aren't empty
	var passwordNotEmpty = !isEmpty($('#password'));
	
	if (!passwordNotEmpty)
	{
		throw {
			field : 'password',
			errors: [ EMPTY_FIELD ]
		};
	}
	return true;
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
	var errorObj = {
		username : {},
		password : {}
	};
	var validCredentials = false;
	try
	{
		validCredentials = validUsername();
		console.log('validCredentials == ' + validCredentials);
	}
	catch (customException)
	{
		validCredentials = false;
		errorObj[customException.field] = customException;
		console.log(JSON.stringify(errorObj, null, '\t'));
	}
	finally
	{
		try 
		{
			validCredentials &= validPassword();
			console.log('validCredentials == ' + validCredentials);
		}
		catch (otherException)
		{
			validCredentials = false;
			errorObj[otherException.field] = otherException;
			console.log(JSON.stringify(errorObj, null, '\t'));
		}
		finally
		{
			if (validCredentials)
			{
				// page redirect logic goes here
				console.log('You have entered valid credentials!');
			}
			else
			{
				renderErrors(errorObj);
			}
		}
	}
}

/* Renders any errors on any of the fields
 * Parameters:
 *	• errorObj : the error object
 */
function renderErrors(errorObj)
{
	console.log('errorObj == ' + JSON.stringify(errorObj, null, '\t'));
	// TODO: replace with $.get() requests
	var badUserTmpl = '<span class="{{^errors}}hidden {{/errors}}tooltip">' + 
		'{{#errors}}' + 
			'<ul>' + 
				'<li>{{.}}</li>' + 
			'</ul>' + 
		'{{/errors}}' + 
	'</span>', 
		badPassTmpl = badUserTmpl;
	// render the templates
	var renderedUserToolTip = Mustache.to_html(badUserTmpl, errorObj.username),
		renderedPassToolTip = Mustache.to_html(badPassTmpl, errorObj.password);
	// if there is tooltip right next to the fields, replace them. otherwise, append to the fields
	if ($('#username').next('.tooltip').length === 0) $('#username').append(renderedUserToolTip);
	else $('#username').next('.tooltip') = renderedUserToolTip;
	
	if ($('#password').next('.tooltip').length === 0) $('#password').append(renderedPassToolTip);
	else $('#password').next('.tooltip') = renderedPassToolTip;
	
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
	
});