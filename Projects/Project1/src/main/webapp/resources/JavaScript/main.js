/**
 * 
 */

window.onload = function() {
	$('#button1').click(function() {
		alert("jquery event! #5")
		$('#div1').load
	})
	
//	$('#userName').blur(function(event) {
//		var name = $('#userName').val();
//		$.get('getUserForms', {
//			userName : name
//		}, function(responseText) {
//			$('#ajaxGetUserServletResponse').text(responseText);
//			alert("Test 3");
//		});
//	});
}

$(document).ready(function() {
	$.post('getUserForms.do')
	
	$('#userName').blur(function(event) {
		var name = $('#userName').val();
		alert("name: " + name);
		$.get('getUserForms.do', {
			userName : name
		}, function(responseText) {
			$('#ajaxGetUserServletResponse').text(responseText);
		});
	});
});