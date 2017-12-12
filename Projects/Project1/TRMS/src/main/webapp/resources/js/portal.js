/**
 * 
 */
$(function() {
	$("#navMain").tabs({
		collapsible : true,
		hide : "slideUp",
		show : "slideDown"
	});
});

$(document).ready(function() {

	$.fn.confirmPassword = function() {
		var pass1 = $('#confirm_password').val();
		var pass2 = $('#enter_password').val();
		if (pass1 != pass2) {
			str = "Passwords do not match: " + pass1;
			$('#password_match').html(str.fontcolor("red"));
		} else
			$('#password_match').empty();
	}

	$('#loginBtn').click(function(event) {
		event.preventDefault();
		$.ajax({
			type : "POST",
			url : "login.do",
			async: false,
			data : $('#loginForm').serialize(),
			dataType : "text",
			error: function(){
				alert("login failed");
			},
			success: function(data){
				console.log("successful call" + data);	
				$.ajax({
					type : "GET",
					url : data,
					dataType : "text",
					error: function(){
						
					},
					success: function(data2){
//						$('html').html(data2);
//						startIntervals();
						window.location.href = 'ManagementLogin.html';
					}
				})
			}
		})
	})
	
		$('#regBtn').click(function(event) {
		event.preventDefault();
		$.ajax({
			type : "POST",
			url : "register.do",
			data : $('#regForm').serialize(),
			dataType : "text",
			error: function(){
				alert("login failed");
			},
			success: function(data){
				console.log("successful call" + data);
				$.ajax({
					type : "GET",
					url : data,
					dataType : "text",
					error: function(){
						
					},
					success: function(data2){
						$('html').html(data2);
					}
				})
			}
		})
	})

	$('#enter_password').keyup($.fn.confirmPassword);
	$('#confirm_password').keyup($.fn.confirmPassword);
});