function clearTable(table){
	var rows = table.rows.length;
	for(let i = 1;i < rows;i++){
		table.deleteRow(1);
	}
}
function getAllRequests(){
	var url = "GetEmployeeRequest";
	
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			var xmlText = xhr.responseXML;
			if(xmlText === null){
				return;
			}
			
			var response = xmlText.getElementsByTagName("emp");
			var resultTable = document.getElementById("pendingTable");
			
			clearTable(resultTable);
			//TODO fix typeError
			for(i in response){
				var row = document.createElement("tr");
				var td1 = document.createElement("td");
				var td2 = document.createElement("td");
				var td3 = document.createElement("td");
				var td4 = document.createElement("td");
				var td5 = document.createElement("td");
				
				td1.innerHTML = response[i].childNodes[0].innerHTML;	
				td2.innerHTML = response[i].childNodes[1].innerHTML;
				td3.innerHTML = response[i].childNodes[2].innerHTML;
				td4.innerHTML = response[i].childNodes[3].innerHTML;
				td5.innerHTML = response[i].childNodes[4].innerHTML;
				row.appendChild(td1);
				row.appendChild(td2);
				row.appendChild(td3);
				row.appendChild(td4);
				row.appendChild(td5);
				resultTable.appendChild(row);
			}
			

			
		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AJAXError").innerHTML="Woops";
		}
	}

	xhr.open("GET",url + "?datachoice=all");
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();	
}