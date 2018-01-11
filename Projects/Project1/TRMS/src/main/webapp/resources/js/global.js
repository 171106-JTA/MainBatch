$(document).ready(function() {
	
	$('a.redirect').click(function(event){
		event.preventDefault();
		link = $(this).attr('href');
		$.ajax({
			url: link,
			type: "GET",
			dataType: 'text',
			error: function(){
				redurTo404();
			},
			success: function(data){
				$('body').html(data);
			}
		});
	});
});