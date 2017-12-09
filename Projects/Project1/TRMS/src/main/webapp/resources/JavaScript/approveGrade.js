window.onload = function(){
	var url = "GradeSetup";
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			
			var xmlText = xhr.responseXML;
			
			var response = xmlText.getElementsByTagName("application");
			var resultTable = document.getElementById("files");
			
			if(response.length!=0){
				for(i in response){
					var row = document.createElement("tr");
					var td1 = document.createElement("td");
					var td2 = document.createElement("td");
					var td3 = document.createElement("td");
					var td4 = document.createElement("td");
					var td5 = document.createElement("td");
					var td6 = document.createElement("td");
					var td7 = document.createElement("td");
					var td8 = document.createElement("td");

					td1.innerHTML = response[i].childNodes[0].innerHTML;	
					td2.innerHTML = response[i].childNodes[1].innerHTML;
					td3.innerHTML = response[i].childNodes[2].innerHTML;
					td4.innerHTML = response[i].childNodes[3].innerHTML;	
					td5.innerHTML = response[i].childNodes[4].innerHTML;
					td6.innerHTML = response[i].childNodes[5].innerHTML;
					td7.innerHTML = response[i].childNodes[6].innerHTML;	
					td8.innerHTML = response[i].childNodes[7].innerHTML;
					
					row.appendChild(td1);
					row.appendChild(td2);
					row.appendChild(td3);
					row.appendChild(td4);
					row.appendChild(td5);
					row.appendChild(td6);
					row.appendChild(td7);
					row.appendChild(td8);

					resultTable.appendChild(row);
				}
			}
			else{
				document.getElementById("empty").innerHTML="Nothing to approve";
			}
			
		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			document.getElementById("AJAXError").innerHTML="Woops";
		}
	}

	xhr.open("POST",url);
	//Tell our xhr how to send the data to the endpoint.
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
}