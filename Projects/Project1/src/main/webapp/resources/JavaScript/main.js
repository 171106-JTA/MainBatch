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
	$.post('getUserForms.do', function(response) {
		console.log("Iside Ajax call");
		console.log(response);
		console.log(response.test1)
//		var info1=response.JSonDataFinal;
//		$('#ajaxGetUserServletResponse').text(responseText);
//		$.each(info1, function(i, field){
//			console.log("Field: " + field + ", i: " + i);
//        });
	});
	
	$('#userName').blur(function(event) {
		var name = $('#userName').val();
		console.log("name: " + name);
		console.log("hello again! 4");
	});
});