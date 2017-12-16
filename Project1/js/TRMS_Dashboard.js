$(function() {
	// get page template data from the server
	$.get('CheckDashboardState/managers')
		.done(function(data) { 
			console.log(data);
			$('body').html(Mustache.to_html($('body'), data));
		})
	// when done, render the body 
});