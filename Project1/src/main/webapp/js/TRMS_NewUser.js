function checkRegistrationForm()
{
	var inputValid = true;
	// check all the fields
	$('input').filter(function() { return $.inArray($(this).attr('type'), ['text', 'password', 'email']) !== -1; }).each(function() {
		// if the field is empty, we render that field empty to the user
		var val = $(this).val().trim(),
			fieldName = $(this).attr('name').toLowerCase();
			
			
		if (val == '') 
		{
			if (!$(this).hasClass('ng-invalid')) $(this).addClass('ng-invalid');
			renderError(this, { errors: [ EMPTY_FIELD ] });
			inputValid = false;
		}
		
		// if the field is for some type of name (excluding username, which is really email), and there's numeric characters, flag them
		if (((fieldName.indexOf('name') !== -1) || (fieldName == 'manager')) && (val.search(/[ \d]/g) !== -1))
		{
			renderError(this, { errors: [ INVALID_NAME ] } );
			if (!$(this).hasClass('ng-invalid')) $(this).addClass('ng-invalid');
			inputValid = false;
		}
		// if the field is some type of email address and isn\'t a valid email
		if ((fieldName.includes('email')) && (!emailPattern.test(val)))
		{
			renderError(this, { errors: [ NOT_AN_EMAIL ] } );
			if (!$(this).hasClass('ng-invalid')) $(this).addClass('ng-invalid');
			inputValid =  false;
		}
	});
	return inputValid;
}

/** Renders the error tooltip for a field.
 *	Parameters:
 *	@param field   : the jQuery object that is the field with errors
 *	@param errorObj: an error object containing the user input errors
 *
 **/
function renderError(field, errorObj)
{
	// if field isn't pointing to anything, throw error
	if ($(field).length == 0) throw new TypeError('field isn\'t a valid jQuery object');
	var tmpl = '<span class="{{^errors}}hidden {{/errors}}tooltip">'+ 
			'{{#.}}' + 
				'{{#errors}}' + 
				'<ul>' + 
					'<li>{{.}}</li>' + 
				'</ul>' + 
				'{{/errors}}' + 
			'{{/.}}' + 
		'</span>'
	$($(field).nextAll('.tooltip')[0]).replaceWith(Mustache.to_html(tmpl, errorObj));
}

/**
 *	Adds listener to departments field, that hits the server for department roles
 **/
function listenForDepartments()
{
	// ...on input on #dept
	$('#dept').on('input', function(event) { 
		// get the value of #dept
		var department = $(this).val();
		// clear the departmentRoles field
		$('#deptRole').val('');
		// match it against the options in the departments data list
		// if there's a match
		if ($('#depts option').filter(function() { 
			return $(this).val() === department;
		}).length) { 
			// field is no longer invalid
			renderError($(this).removeClass('ng-invalid'), {});
			// hit the server for list of department roles for that match
			$.get('FetchDepartments/departmentRoles?department=' + department)
				// upon success ...
				.done(function(data) {
					var tmpl = '{{#departmentRoles}}' +
						'<option value="{{departmentRoleName}}"></option>' +  
					'{{/departmentRoles}}';
					// ... render the template of that datalist
					$('#deptRoles').html(Mustache.to_html(
							tmpl, 
							{ departmentRoles : (data === data.toString() ? JSON.parse(data) : data) }
						));
					// show the row that contains it
					$('#deptRoles').parents('span').removeClass('hidden');
				});
			// same thing for manager data
			$.get('FetchEmployees/managers?department=' + department)
				.done(function(data){
					var tmpl = '{{#managers}}' + 
						'<option value="{{firstName}} {{lastName}}" {{#employeeID}}data-manager-id="{{employeeID}}"{{/employeeID}}>' + 
					'{{/managers}}';
					$('#managers').html(Mustache.to_html(
							tmpl,
							{ managers : (data === data.toString() ? JSON.parse(data) : data) }
						));					
				});
		}
	});
}


$(function() {
	var dataObj = {};
	var fetchDeptsXHR    = $.get('FetchDepartments');
	// make request to servlet for the department names
	fetchDeptsXHR.done(function(data) { 
		// on success, convert the data back into an object and append it to dataObj
		dataObj.departments = (data === data.toString() ? JSON.parse(data) : data);
		// pre-render the departments datalist
		$('#depts').html(Mustache.to_html($('#depts').html(), dataObj));
	})
	// when that's done, listen for departments
	.done(listenForDepartments);
	
	// a press of 'ENTER' on an input field should submit the form
	$('form').submit(function(event) {
		event.preventDefault();
		// check the user registration form
		checkRegistrationForm();
		// disable the submit button
		$('input[type="submit"]').val('Submitting...').attr('disabled', 'disabled');
		// get the form data in the form
		var formData = {};
		$('form').find('input:not([type="submit"]):not([type="reset"])')
			.each(function(index, value) { 
				formData[$(value).attr('name')] = $(value).val()
			})
			$.post('RegisterNewUser',
					formData,
					function(response) { 
						console.log('response == %s', JSON.stringify(response, null, '\t'));
						// should redirect
						if (response.url)
						{
							console.log('user registered')
							window.location.href = response.url;
						}
					})
				.fail(function(response) {
					console.log('user registration failed');
					console.log('response == %s', JSON.stringify(response, null, '\t'));
					if (response.message)
					{
						console.log('server said our data was invalid');
						console.log(response.message);
					}
					
				})
				.always(function(response) { 
					// enable the submit button
					$('input[type="submit"]').val('Submit').removeAttr('disabled');
				
				});
	})
});