function hello(){
	console.log("Hello");
}

function getEmployeeRequests(){
		var url = "GetEmployeeRequest";
		
		var xhr = new XMLHttpRequest();
	
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4 && xhr.status == 200){
				
				var xmlText = xhr.responseXML;
				
				var response = xmlText.getElementsByTagName("emp");
				
				var resultTable = document.getElementById("pendingTable");
				
				for(i in response){
					var row = document.createElement("tr");
					var td1 = document.createElement("td");
					var td2 = document.createElement("td");
					var td3 = document.createElement("td");
					var td4 = document.createElement("td");
					
					td1.innerHTML = response[i].childNodes[0].innerHTML;	
					td2.innerHTML = response[i].childNodes[1].innerHTML;
					td3.innerHTML = response[i].childNodes[2].innerHTML;
					td4.innerHTML = response[i].childNodes[3].innerHTML;
					row.appendChild(td1);
					row.appendChild(td2);
					row.appendChild(td3);
					row.appendChild(td4);
					resultTable.appendChild(row);
				}
				

				
			}else if(xhr.readyState == 4 && xhr.status != 200){
				console.log(xhr.status);
				document.getElementById("AJAXError").innerHTML="Woops";
			}
		}
	
		xhr.open("POST",url);
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhr.send();	
}