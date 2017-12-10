/**
 * 
 */
$(document).ready(function() {		
	$.post('getFormsNeedingApproval.do', function(response) {
		console.log("Iside Ajax call");
		console.log(response);
		
		var array_data = [];
		$.each(response, function(i, field){
			console.log(field);
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
				field.reimbursementID,
				field.description, 
				field.cost, 
				field.month + "-" + field.day + "-" + field.year,
				field.street + " " + field.city + ", " + field.state + " " + field.zip,
				hour + ":" + field.minute + " " + am_pm,
				field.gradingFormat,
				field.eventType, 
				field.approvalByDirectSupervisor,
				field.approvalByDepartmentHead,
				field.approvalByBenCo
				])
        });
		console.log(array_data);
		
		var table = $('#ReimbursementFormTable').DataTable({
			data : array_data, 
			columns : [
				{ title : "Form ID", "visible": true},
				{ title : "Description"}, 
				{ title : "Cost"},
				{ title : "Date"}, 
				{ title : "Address"},
				{ title : "Start Time"},
				{ title : "Grading Format"}, 
				{ title : "Event Type"},
				{ title : "Direct Supervisor Approval", "visible": false}, 
				{ title : "Department Head Approval", "visible": false}, 
				{ title : "Benefits Coordinator Approval", "visible": false},
				{
			      "data": null,
			      "defaultContent": 
			    	  "<button class='btn-approve'>Approve</button>" +
			    	  "<button class='btn-deny'>Deny</button>",
			      bSortable: false
			    }
			]
		});
		
		//Approve Button
		$('#ReimbursementFormTable tbody').on( 'click', '.btn-approve', function () {
			var data = table.row( $(this).parents('tr') ).data();
			
	        //Change Department Head's approval to 1
	        //Keep the other approvals the same
	        $.post('updateFormApproval.do', 
	        	{ 
	        		reimbursementID : data[0],
	        		approvalByDirectSupervisor : data[8],
	        		approvalByDepartmentHead: 1, //data[9] 
	        		approvalByBenCo : data[10]
	        	});
	        
	        //Remove the approved Reimbursement from the displayed table
	        table
    		.row( $(this).parents('tr') )
            .remove()
            .draw();
	    });
		
		//Deny Button
		$('#ReimbursementFormTable tbody').on( 'click', '.btn-deny', function () {
			var data = table.row( $(this).parents('tr') ).data();
			console.log("reimbursementID: " + data[0]);
			//Save Reimbursement ID to session
			$.post('saveReimbursementID.do', 
				{
					reimbursementID : data[0]
				});
			location.href = "getDenialReason.html";
	    });
	});
});