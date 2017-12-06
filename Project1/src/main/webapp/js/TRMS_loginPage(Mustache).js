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
/* Checks if field is valid email
 */
function isValidEmail(email)
{
	var pattern = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	return $.trim(email).match(pattern);
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

/* Validates the username, asynchronously. 
 * Parameters:
 *	• err     : the initial error object 
 *	• callback: the callback to invoke after this function completes
 */
function validateUsername(err, callback)
{
	// usernames should not be empty
	var usernameEmpty = isEmpty($('#username'));
	if (usernameEmpty)
	{
		err.username = { errors : [ EMPTY_FIELD ] };
		return callback(err);
	}
	// usernames should be emails
	if (!isValidEmail($('#username').val()))
	{
		err.username = { errors: [ INVALID_EMAIL ] };
	}
	callback(err);
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

/* Validates password asynchronously
 */
function validatePassword(err, callback)
{
	var passwordEmpty = isEmpty($('#password'));
	if (passwordEmpty)
	{
		err.password = { errors : [ EMPTY_FIELD ] };
	}
	
	callback(err);
}

/* Does login logic, logging the user in
 * 
 */
function login() { 
	// user authentication logic goes here....
	var errorObj = {
		username : {},
		password : {}
	};
	// callback hell!
	validateUsername({}, 
		function (err) 
		{
			validatePassword (err, function(e)
			{
				if (e)
				{
					// render the tool tips here
					renderErrors(e);
				}
				// deactivate the button
				$('input[type="submit"]').val('Logging in....')
					.attr('disabled', 'disabled');
				// AJAX logic to send to the server
				$.post('login',
					{
						user : $('#username').val(),
						pass : $('#password').val()
					},
					// on success, redirect to dashboard
					function (response)
					{	
						// let's console.log() for right now
						console.log(response);
						if (response.authenticated)
						{
							window.location.href = response.redirect_url;
						}
					}
				)
				// TODO: on error, re-render errors or if allotted login attempts exceeded, redirect to locked out screen
				.fail(function(err) { 
					console.error('Authentication failed');
					console.error(JSON.stringify(err, null, '\t'));	
				})
				// reactivate the button
				.always(function(data) { 
					$('input[type="submit"]').val('login').removeAttr('disabled');
				});
			})
		}
	);
}

/* Renders any errors on any of the fields
 * Parameters:
 *	• errorObj : the error object
 */
function renderErrors(errorObj)
{
	// TODO: replace with $.get() requests
	var badUserTmpl = '<span class="{{^.}}hidden {{/.}}tooltip">' + 
		'{{#.}}' + 
			'{{#errors}}' + 
				'<ul>' + 
					'<li>{{.}}</li>' + 
				'</ul>' + 
			'{{/errors}}' + 
		'{{/.}}' +
	'</span>', 
		badPassTmpl = badUserTmpl;
	// render the templates
	var renderedUserToolTip = Mustache.to_html(badUserTmpl, errorObj.username),
		renderedPassToolTip = Mustache.to_html(badPassTmpl, errorObj.password);
	// if there is tooltip right next to the fields, replace them. otherwise, append to the fields
	if ($('#username').next('.tooltip').length === 0) $('#username').after(renderedUserToolTip);
	else $('#username').next('.tooltip').replaceWith(renderedUserToolTip);
	
	if ($('#password').next('.tooltip').length === 0) $('#password').after(renderedPassToolTip);
	else $('#password').next('.tooltip').replaceWith(renderedPassToolTip);
	
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
	
	$('input[type="reset"]').click(function(event) { 
		$('#username,#password').removeClass('ng-invalid');
	})
});