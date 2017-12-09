"use strict";

// AJAX request call for cases
(function callToTable(){
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'AdminReimbursementTable');
	xhr.onload = () => {
		if (xhr.status === 200) {
			var tableData = JSON.parse(xhr.responseText);
			var count = 0;
			for(var data in tableData){
			
				var tableRow = document.createElement('tr');
				var tableData1 = document.createElement('td'); 
				var tableData2 = document.createElement('td');
				var tableData3 = document.createElement('td');
				var tableData4 = document.createElement('td');
				var tableData5 = document.createElement('td');
				var tableData6 = document.createElement('td');
				var tableData7 = document.createElement('td');
			
				var td1 = document.createTextNode(tableData[data].case_id);
				var td2 = document.createTextNode(tableData[data].request_date);
				var td3 = document.createTextNode(tableData[data].case_status);
				var td4 = document.createTextNode(tableData[data].gradingformat);
				var td5 = document.createTextNode(tableData[data].supervisor_id);
				var td6 = document.createTextNode(tableData[data].event_date);
				tableData7.innerHTML="<button data-toggle='collapse' data-target='#accordion"+ count +"' class='btn btn-info btn-xs'>View Detail</button>"
			
				tableData1.appendChild(td1);
				tableData2.appendChild(td2);
				tableData3.appendChild(td3);
				tableData4.appendChild(td4);
				tableData5.appendChild(td5);
				tableData6.appendChild(td6);
	
				tableRow.appendChild(tableData1);
				tableRow.appendChild(tableData2);
				tableRow.appendChild(tableData3);
				tableRow.appendChild(tableData4);
				tableRow.appendChild(tableData5);
				tableRow.appendChild(tableData6);
				tableRow.appendChild(tableData7);
				document.getElementById("caseDataTable").appendChild(tableRow);
				var reimValue =  parseInt( afterPercent(tableData[data].cost, tableData[data].eventType));
				var detailTableRow = document.createElement('tr');
				detailTableRow.innerHTML="<td colspan='12'>" +
					"<div id='accordion"+count+"' class='collapse'>" +
						"<form action='ApproveCase' method='POST'>" +
						"<table><tbody>" +
						"<tr><th>Data</th><th>Details</th></tr>" +
						"<tr><td><b>Employee ID</b></td><td>"+tableData[data].employeeId+"</td></tr>" +
						"<tr><td><b>Supervisor ID</b></td><td>"+tableData[data].supervisor_id+"</td></tr>"+
						"<tr><td><b>Description</b></td><td>"+tableData[data].description+"</td></tr>"+
						"<tr><td><b>Grading Format</b></td><td>"+tableData[data].gradingFormat+"</td></tr>"+
						"<tr><td><b>Requested Amount</b></td><td>"+tableData[data].cost.toFixed(2)+"</td></tr>"+
						"<tr class='approveAmt'><td><b>Approval Amount</b></td><td>$<input name='approvalAmt' type=number min='0.00' max='1000.00' step='0.01' value='"+reimValue+"' /></td></tr>"+
						"<tr><input type='hidden' name='status' value='"+tableData[data].case_status+"'></tr>"+
						"<tr><input type='hidden' name='caseId' value='"+tableData[data].case_id+"'></tr>"+
						"<tr><td><input class='btn btn-primary' type='submit' name='decision' value='Approve'></td>" +
						"<td><input class='btn btn-danger' type='submit' name='decision' value='Disapprove'></td></tr>" +
						"</tbody></table>"+
						"</form>"+"</div></td>";
				document.getElementById("caseDataTable").appendChild(detailTableRow);
			
				if(tableData[data].case_status != "Department Head Approved"){
					$(".approveAmt").css("display","none");
				}
				count++;
				
			}
			
		} else {
			console.log('Request failed.  Returned status of ' + xhr.status);
		}
	};
	xhr.send();	
})();

function afterPercent(cost, eventType){
	var val = Number(cost.toString().replace(/[^0-9\.]+/g,""));
	eventType = eventType.toLowerCase();
	
	if(eventType=="techtraining"){
		return val *.9;
	}
	else if(eventType =="university"){
		return val * .8;
	}
	else if(eventType == "seminar"){
		return val * .6;
	}
	else if(eventType == "cert"){
		return val;
	}
	else if(eventType == "certprep"){
		return val * .75;
	}
	else{
		return val *.3;
	}
}
