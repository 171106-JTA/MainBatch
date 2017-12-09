/**
 * 
 */
$(document).ready(function() {		
	$.post('getFormsNeedingApproval.do', function(response) {
		console.log("Iside Ajax call");
		console.log(response);
		
		var array_data = [];
		$.each(response, function(i, field){
			console.log("Field.description: " + field.description + ", Field.cost: " + field.cost + 
					", Filed.urgency: " + field.urgency + ", i: " + i);
			
			//Set-Up start time variables
			//(convert from military to standard time
			var hour = field.hour;
			var am_pm = "AM";
			if(hour > 12) {
				hour = hour - 12;
				am_pm = "PM";
			}
			
			array_data.push([
				field.description, 
				field.cost, 
				field.month + "-" + field.day + "-" + field.year,
				field.street + " " + field.city + ", " + field.state + " " + field.zip,
				hour + ":" + field.minute + " " + am_pm,
				field.gradingFormat,
				field.eventType
				])
        });
		console.log(array_data);
		
		var table = $('#ReimbursementFormTable').DataTable({
			data : array_data, 
			columns : [
				{ title : "Description"}, 
				{ title : "Cost"},
				{ title : "Date"}, 
				{ title : "Address"},
				{ title : "Start Time"},
				{ title : "Grading Format"}, 
				{ title : "Event Type"}, 
				{
			      "data": null,
			      "defaultContent": "<button>Approve</button>",
			      bSortable: false
			    }
			]
		});
		
		$('#ReimbursementFormTable tbody').on( 'click', 'button', function () {
			var data = table.row( $(this).parents('tr') ).data();
	        console.log("Row Data[0]: " + data[ 0 ] );
	    } );
	});
});