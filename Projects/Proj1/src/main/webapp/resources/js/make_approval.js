"use strict";

// AJAX request call for cases
(function callToTable(){
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'AdminReimbursementTable');
	xhr.onload = () => {
		if (xhr.status === 200) {
			var tableData = JSON.parse(xhr.responseText);
			console.log(xhr.responseText);
			var count = 0;
			for(var data in tableData){
			console.log(tableData[data]);
			
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
			var td3 = document.createTextNode(tableData[data].eventType);
			var td4 = document.createTextNode(tableData[data].gradingformat);
			var td5 = document.createTextNode(tableData[data].case_id);
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
			count++;
			}
		} else {
			console.log('Request failed.  Returned status of ' + xhr.status);
		}
	};
	xhr.send();	
})();
