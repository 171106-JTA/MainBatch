/**
 * 
 */
$(document).ready(function() {

	$.fn.confirmPassword = function() {
		var pass1 = $('#confirm_password').val();
		var pass2 = $('#enter_password').val();
		if (pass1 != pass2)
			$('#password_match').html("Passwords do not match: " + pass1);
		else
			$('#password_match').empty();
	}
	
	$('#enter_password').keyup($.fn.confirmPassword);
	$('#confirm_password').keyup($.fn.confirmPassword);
});