	var main = function() {
		  $('.btn').click(function() {
		    var post = $('.status-box').val();
		    $('<li>').text(post).prependTo('.posts');
		    $('.status-box').val('');
		    $('.counter').text('2500');
		    $('.btn').addClass('disabled'); 
		  });
		  
		  $('.status-box').keyup(function() {
		    var postLength = $(this).val().length;
		    var charactersLeft = 2500 - postLength;
		    $('.counter').text(charactersLeft);
		  
		    if(charactersLeft < 0) {
		      $('.btn').addClass('disabled'); 
		    }
		    else if(charactersLeft == 300) {
		      $('.btn').addClass('disabled');
		    }
		    else {
		      $('.btn').removeClass('disabled');
		    }
		  });}