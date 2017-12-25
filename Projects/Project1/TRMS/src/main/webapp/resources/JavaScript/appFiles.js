var valid;
window.onload = function(){
	var url = "appFileSetup";
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){//fill table and create list of valid inputs
		if(xhr.readyState == 4 && xhr.status == 200){
			var temp="";
			var xmlText = xhr.responseXML;
			
			var response = xmlText.getElementsByTagName("file");
			var resultTable = document.getElementById("files");
			if(response.length!=0){
				for(i in response){
					temp+=response[i].childNodes[0].innerHTML+" ";
					console.log(temp);
					var row = document.createElement("tr");
					var td1 = document.createElement("td");
					var td2 = document.createElement("td");
					var td3 = document.createElement("td");

					td1.innerHTML = response[i].childNodes[0].innerHTML;	
					td2.innerHTML = response[i].childNodes[1].innerHTML;
					td3.innerHTML = response[i].childNodes[2].innerHTML;

					row.appendChild(td1);
					row.appendChild(td2);
					row.appendChild(td3);
					
					valid=temp.split(" ");
					console.log(valid);
					resultTable.appendChild(row);
				}
				
			}
			else{
				get("empty").innerHTML="Nothing to approve";
			}
			
		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
		}
	}

	xhr.open("POST",url);
	//Tell our xhr how to send the data to the endpoint.
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
}

function checkInput(){//check input for validity
	var found=false;
	for(let x=0; x<valid.length-1;x++){
		if(get("activeID").value==valid[x])
			found=true;
	}
	if(found==true)
		get("review").disabled=false;
	else
		get("review").disabled=true;
}

function get(s){//convenience function
	return document.getElementById(s);
}