/**
 * Clears the table of old data
 * 
 * @param table to be cleared
 *
 */
function clearTable(table){
	var rows = table.rows.length;
	for(let i = 1;i < rows;i++){
		table.deleteRow(1);
	}
}

/**
 * Creates the table data and rows
 * 
 * @param numOfRows to be created
 * @returns array of table elements
 */
function createTableData(numOfRows){
	var arr = [];
	for(var i = 0; i < numOfRows;i++){
		arr[i] = document.createElement("td");
	}
	return arr;
}

/**
 * Populates table with data
 * 
 * @param arr array of table data elements
 * @param i index of responses
 * @param row to be appended to
 * @param table
 * @param response the XML elements
 * @returns
 */
function populateData(arr, i, row, table, response){
	let n = 0;
	for(td of arr){
		td.innerHTML = response[i].childNodes[n].innerHTML;
		row.appendChild(td);
		n++;
	}
	table.appendChild(row);
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
				var tDatas = createTableData(13); 
				populateData(tDatas, i, row, resultTable, response);
			}
			

			
		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AllRequestError").innerHTML="Woops";
		}
	}

	xhr.open("GET",url + "?datachoice=all");
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();	
}

function getAllInfoRequests(){
	var url = "GetInfoRequest";
	
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			var xmlText = xhr.responseXML;
			if(xmlText === null){
				return;
			}
			
			var response = xmlText.getElementsByTagName("req");
			var resultTable = document.getElementById("infoTable");
			
			clearTable(resultTable);
			//TODO fix typeError
			for(i in response){
				var row = document.createElement("tr");
				var tDatas = createTableData(6); 
				populateData(tDatas, i, row, resultTable, response);
			}
			

			
		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AllInfoError").innerHTML="Woops";
		}
	}

	xhr.open("GET", url + "?from=admin");
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();	
}