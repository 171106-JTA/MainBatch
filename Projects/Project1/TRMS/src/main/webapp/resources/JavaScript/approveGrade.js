var valid;
var count=0;
window.onload = function(){//fill table and create list of valid inputs
	var url = "GradeSetup";
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			var temp="";
			var xmlText = xhr.responseXML;
			
			var response = xmlText.getElementsByTagName("grade");
			console.log(response);
			var resultTable = document.getElementById("grades");
			
			if(response.length!=0){
				for(i in response){
					temp+=response[i].childNodes[0].innerHTML+" ";
					count++;
					document.getElementById("empty").innerHTML="You have " + count + " accounts to review.";
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
					
					valid=temp.split(" ");
					resultTable.appendChild(row);
				}
			}
			else{
				document.getElementById("empty").innerHTML="Nothing to approve";
			}
			
		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
		}
	}

	xhr.open("POST",url);
	//Tell xhr how to send the data to the endpoint.
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
}

function checkInput(){//check if input is valid
	var found=false;
	for(let x=0; x<valid.length-1;x++){
		if(get("activeID").value==valid[x])
			found=true;
	}
	if(found==true){
		get("approve").disabled=false;
		get("approve").disabled=false;
	}
	else
	{
		get("approve").disabled=false;
		get("approve").disabled=true;
	}
}