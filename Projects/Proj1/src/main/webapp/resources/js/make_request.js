"user strict";


(function callToTable(){
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'RequesterTable');
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
			var tableData8 = document.createElement('td');
			var tableData9 = document.createElement('td');
			
			var td1 = document.createTextNode(tableData[data].case_id);
			var td2 = document.createTextNode(tableData[data].request_date);
			var td3 = document.createTextNode(tableData[data].case_status);
			var td4 = document.createTextNode(tableData[data].supervisorId);
			var td5 = document.createTextNode(tableData[data].cost);
			var td6 = document.createTextNode(tableData[data].event_date);
			var td7 = document.createTextNode(tableData[data].duration_days);
			var td8 = document.createTextNode(tableData[data].gradingformat);			
			tableData9.innerHTML="<button data-toggle='collapse' data-target='#accordion"+ count +"' class='btn btn-info btn-xs'>View Detail</button>"
			
			tableData1.appendChild(td1);
			tableData2.appendChild(td2);
			tableData3.appendChild(td3);
			tableData4.appendChild(td4);
			tableData5.appendChild(td5);
			tableData6.appendChild(td6);
			tableData7.appendChild(td7);
			tableData8.appendChild(td8);

			
			tableRow.appendChild(tableData1);
			tableRow.appendChild(tableData2);
			tableRow.appendChild(tableData3);
			tableRow.appendChild(tableData4);
			tableRow.appendChild(tableData5);
			tableRow.appendChild(tableData6);
			tableRow.appendChild(tableData7);
			tableRow.appendChild(tableData8);
			tableRow.appendChild(tableData9);
			document.getElementById("caseDataTable").appendChild(tableRow);
			
			var detailTableRow = document.createElement('tr');
			detailTableRow.innerHTML="<td colspan='12'>" +
					"<div id='accordion"+count+"' class='collapse'>" +
						"<form action='ApproveCase' method='POST'>" +
						"<table><tbody>" +
						"<tr><th>Data</th><th>Details</th></tr>" +
						"<tr><td><b>Employee ID</b></td><td>"+tableData[data].employeeId+"</td></tr>" +
						"<tr><td><b>Supervisor ID</b></td><td>"+tableData[data].supervisorId+"</td></tr>"+
						"<tr><td><b>Description</b></td><td>"+tableData[data].description+"</td></tr>"+
						"<tr><input type='hidden' name='status' value='"+tableData[data].case_status+"'></tr>"+
						"<tr><input type='hidden' name='caseId' value='"+tableData[data].case_id+"'></tr>"+
						"<tr><td><input class='btn btn-primary' type='submit' name='decision' value='Approve'></td>" +
						"<td><input class='btn btn-danger' type='submit' name='decision' value='Disapprove'></td></tr>" +
						"</tbody></table>"+
						"</form>"+"</div></td>"
			document.getElementById("caseDataTable").appendChild(detailTableRow);
			count++;
			}
		} else {
			console.log('Request failed.  Returned status of ' + xhr.status);
		}
	};
	xhr.send();	

})();
