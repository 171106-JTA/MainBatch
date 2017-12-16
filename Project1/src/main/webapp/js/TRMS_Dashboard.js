function getUsersState(pageData, callback)
{
	$.get('CheckDashboardState/employeeMetadata').done(function(data) {
		$.extend(true, pageData, data);
		callback(null, pageData);
		
	});
}

function getLocationStates(pageData, callback)
{
	$.get('CheckDashboardState/states').done(function (data) { 
		$.extend(true, pageData, data);
		callback(null, pageData);
	});
	
}


function getExpenseTypes(pageData, callback)
{
	$.get('CheckDashboardState/expenseTypes').done(function (data) { 
		$.extend(true, pageData, data);
		callback(null, pageData);
	});
}

function getReimbursementRequests(pageData, callback)
{
	$.get('CheckDashboardState/reimbursementRequests').done(function (data) { 
		$.extend(true, pageData, data);
		callback(null, pageData);
	})
}

function getEmployees(pageData, callback)
{
	$.get('CheckDashboardState/employees').done(function(data) { 
		$.extend(true, pageData, data);
		callback(null, pageData);
	})
}



$(function() {
	var _pageData = {};
	// hide the body 
//	$('body').hide();
	// get page template data from the server
	$.get('CheckDashboardState/manager')
		.done(function(data) { 
			_pageData = data;
			async.waterfall([
				function(callback) {
					getUsersState(_pageData, callback);
				},
				getLocationStates,
				getExpenseTypes,
				getReimbursementRequests,
				getEmployees
			], function(err, results) { 
							// when done, render the body and show it
							$('body').html(Mustache.to_html($('body').html(), results));
			//				$('body').show();
							// hide anything that a data-tab points to 
							$('*').filter(function(){ 
								var dataTabTriggers = $('a').filter(function() {
									return $(this).data('tab') !== 'statusReport' || $(this).data('tab') === undefined; 
								});
								return ($.inArray($(this).attr('id'), dataTabTriggers) !== -1); 
							}).each(function() { $(this).hide(); });
							addListeners();
							$('a[data-tab="statusReport"]').click();
//							
						
					})
		})
});	

function addListeners() { 
	// any logout button should go to log the user out
	$('.logout').click(function(event) {
		$.post('logout').done(function(response) { window.location.href = response.redirect_url; })
	})	
	// any click of a data-tab element should hide everything but the content identified by that data-tab
	$('a').filter(function() { return ($(this).data('tab') !== undefined); }).click(function(event) {
		let tabName = $(this).data('tab');
		$('#' + $(this).data('tab')).parent().children().filter(function() { 
			if ($(this).attr('id') === tabName) $(this).show();
			else $(this).hide();
		})
	})
	// #showFirstPartOfTRMSForm should, well, show the first div element in the user form and hide all the others
	$('#showFirstPartOfTRMSForm').click(function(event) { 
		$('#submitRequest > div').each(function(index, element) {
			if (index === 0) $(this).show().removeClass('hidden');
			else $(this).hide();
		})
		event.preventDefault();
	})
	// #showSecondPartOfTRMSForm should, well, show the second div element in the user form and hide all the others
	$('#showSecondPartOfTRMSForm').click(function(event) { 
		$('#submitRequest > div').each(function(index, element) {
			if (index === 1) $(this).show().removeClass('hidden');
			else $(this).hide();
		})
		event.preventDefault();
	})
	// addToRubric should append to the table 
	$('#addToRubric').click(function(event) { 
		event.preventDefault();
		$('#gradeScales').append(
			Mustache.to_html('<tr class="dirty"><td>{{letterGrade}}</td><td>{{cutoff}}</td></tr>',
				{
					letterGrade : $('#letterGrade').val(),
					cutoff      : $('#cutoff').val()
				})
		);
	})
	// #SubmitTRMSForm should send the TRMS form to the server
	$('#SubmitTRMSForm').click(submitTRMSRequest);
}

function submitTRMSRequest()
{
	// mark the 
	$.post('SubmitDashboardInfo/reimbursementRequest', JSON.stringify(
		  {
			  	reimbursementRequest : {
				  	reimbursableEvent : {
				  		startDate       : $('#startDate').val().trim(),
				  		endDate         : $('#endDate').val().trim(),
				  		type            : {
				  			name        : $('#expenseType').val().trim(),
				  		},
						description     : $('#eventDescription').val().trim(),
						location		: {
							streetAddress     : $('#streetAddress').val().trim(),
							city              : $('#city').val().trim(),
							stateAbbreviation : $('#state').val().trim(),
							zipCode           : $('#zipCode').val().trim()
						},
				  		cost  	        : $('#eventCost').val().trim(),
				  	},
		  			amount: $('#eventCost').val().trim(),
		  			status: 'Filed'
			  	}, 
				gradeScale      : $.makeArray($('#gradeScales > tr').map(function() { 
					var tds = $(this).children('td');
					return { 
						letterGrade : $($(tds)[0]).find('input').val(),
						lowerLimit  : $($(tds)[1]).find('input').val()
					};
				}))
			}), 
			function(data)
			{
				console.log(data);
				// render the template and append its contents to tbody in #statusReport
				var tmpl = '<tr><td>{{id}}</td><td>{{amount}}</td><td>{{status}}</td><td>{{reimbursableEventID}}</td></tr>';
				$('#statusReport tbody').append(Mustache.to_html(tmpl, data));
			}
	).fail(function(err) { 
		console.error(err);
	});
}
