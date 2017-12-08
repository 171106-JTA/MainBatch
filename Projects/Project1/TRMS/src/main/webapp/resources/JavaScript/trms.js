
function validateRequestForm(){
	var form = document.forms["reimburserequest"];
	var cost = form["cost"].value;
	var date = form["datetime"].value;
	var error = document.getElementById("formError");
	
	var splitDateTime = date.split(" ");
	
	var splitDate = splitDateTime[0].split("-");
	console.log(splitDate);
	var splitTime = splitDateTime[1].split(":");
	
	//Date Validations
	if(splitDate.length !=3){
		error.innerHTML = "Invalid Date: Date should be in MM-DD-YYYY format";
		return false;
	}
	
	if(splitDate[0].length != 2 || splitDate[1].length != 2 || splitDate[2].length != 4){
		error.innerHTML = "Invalid Date: Format is MM-DD-YYYY";
		return false;
	}
	
	if(splitDate[0] > 12 || splitDate[1] > 31 || splitDate[3] < 2017){
		error.innerHTML = "Invalid Date: Number have exceeded the limits";
		return false;
	}
	
	if(splitDate[0] === "2" && splitDate[1] > 28){
		error.innerHTML = "Invalid Date: Feburary";
		return false;
	}
	
	//Time Validation
	if(splitTime[0].length != 2 || splitTime[1].length != 2){
		error.innerHTML = "Invalid Time: Time should be in HH:MM format";
		return false;
	}
	if(splitTime[0] > 12 || splitTime[1] > 59){
		error.innerHTML = "Invalid Time: Time is over limit";
		return false;
	}
	
	//Cost Validation
	if(cost.isNaN){
		error.innerHTML = "Not a number in cost";
		return false;
	}
	return true;
}
function clearTable(table){
	var rows = table.rows.length;
	for(let i = 1;i < rows;i++){
		table.deleteRow(1);
	}
}

function createTableData(numOfRows){
	var arr = [];
	for(var i = 0; i < numOfRows;i++){
		arr[i] = document.createElement("td");
	}
	return arr;
}

function populateData(arr, i, row, table, response){
	let n = 0;
	for(td of arr){
		td.innerHTML = response[i].childNodes[n].innerHTML;
		row.appendChild(td);
		n++;
	}
	table.appendChild(row);
}

function getEmployeeRequests(){
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
					var tDatas = createTableData(5); 
					populateData(tDatas, i, row, resultTable, response);
				}
				

				
			}else if(xhr.readyState == 4 && xhr.status != 200){
				console.log(xhr.status);
				document.getElementById("AJAXError").innerHTML="Woops";
			}
		}
	
		xhr.open("POST",url);
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		//xhr.send("datachoice=single");
		xhr.send();
}

function getInfoRequests(){
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
				var tDatas = createTableData(2); 
				populateData(tDatas, i, row, resultTable, response);
			}
			

			
		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AJAXError").innerHTML="Woops";
		}
	}

	xhr.open("GET", url);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();	
}