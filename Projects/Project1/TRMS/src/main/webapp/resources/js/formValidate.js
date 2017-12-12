//// Wait for the DOM to be ready
//$(function() {
//  // Initialize form validation on the registration form.
//  // It has the name attribute "registration"
//  $("#loginForm").validate({
//	 errorClass: 'errors',
//	  
//    // Specify validation rules
//    rules: {
//      // The key name on the left side is the name attribute
//      // of an input field. Validation rules are defined
//      // on the right side
//      username: "required",
//      password: "required"
//    },
//    // Specify validation error messages
//    messages: {
//      username: "Please enter your username",
//      password: "Please enter your password",
//    },
//    // Make sure the form is submitted to the destination defined
//    // in the "action" attribute of the form when valid
//    submitHandler: function(form) {
//      form.submit();
//    }
//  });
//  
//  $("#regForm").validate({
//		 errorClass: 'errors',
//	  
//	    // Specify validation rules
//	    rules: {
//	      // The key name on the left side is the name attribute
//	      // of an input field. Validation rules are defined
//	      // on the right side
//	      username: "required",
//	      password: {
//	    	  	required: true,
//	    	  	pattern: "^(?=.*\d)(?=.*[A-Z])\w{5,16}$",
//	    	  	minlength: 5,
//	    	  	maxlength: 20
//	      },
//	      superior: {
//	    	  	required: true,
//	    	  	pattern: "\d",
//	      },
//	      type: {
//	    	  	required: true,
//	      }
//	    },
//	    // Specify validation error messages
//	    messages: {
//	      username: "Please enter your username",
//	      password: "Password must be bewtween 5 and 20 characters and have a capital and number",
//	      superior: "Superior id must be a number",
//	      type: "Please select a type",
//	    },
//	    // Make sure the form is submitted to the destination defined
//	    // in the "action" attribute of the form when valid
//	    submitHandler: function(form) {
//	      form.submit();
//	    }
//	  });
//  });
//
