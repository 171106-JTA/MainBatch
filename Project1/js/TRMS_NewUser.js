function checkRegistrationForm()
{
	// check all the fields
	$('input').filter(function() { return $.inArray($(this).attr('type'), ['text', 'password', 'email'] !== -1); }).each(function() {
		// if the field is empty, we render that field empty to the user
		var val = $(this).val().trim(),
			fieldName = $(this).attr('name').toLowerCase();
			
			
		if (val == '') 
		{
			if (!$(this).hasClass('ng-invalid')) $(this).addClass('ng-invalid');
			renderError(this, { errors: [ EMPTY_FIELD ] });
			return false;
		}
		
		// if the field is for some type of name (excluding username, which is really email), and there's numeric characters, flag them
		if (((fieldName.indexOf('name') !== -1) || (fieldName == 'manager')) && (val.search(/[ \d]/g) !== -1))
		{
			renderError(this, { errors: [ INVALID_NAME ] } );
			if (!$(this).hasClass('ng-invalid')) $(this).addClass('ng-invalid');
			return false;
		}
		// if the field is some type of email address and isn\'t a valid email
		if ((fieldName.includes('email')) && (!emailPattern.test(val)))
		{
			renderError(this, { errors: [ NOT_AN_EMAIL ] } );
			if (!$(this).hasClass('ng-invalid')) $(this).addClass('ng-invalid');
			return false;
		}
	});
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
	var tmpl = $(field).next('.tooltip');
	$(field).next('.tooltip').replaceWith(Mustache.to_html(tmpl, errorObj)).removeClass('hidden');
}


$(function() {
	// make request to servlet for the department names
	// on success, render #depts with the data
//	$.get('FetchDepartments/departments')
	$.get('FetchDepartments')
		.done(console.log);
		// pre-render the departments datalist
	//	$('#departments').replaceWith($
	// a press of 'ENTER' on an input field should submit the form
});