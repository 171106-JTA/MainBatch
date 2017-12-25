var valid;
window.onload = function(){
	var url = "SubmitFile";
	var xhr = new XMLHttpRequest();
	var count=0;

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){//fill table and create list of valid inputs
			
			var xmlText = xhr.responseXML;
			var temp="";
			var response = xmlText.getElementsByTagName("application");
			var resultTable = document.getElementById("requests");
			if(response.length!=0){
				for(i in response){
					count++;
					temp+=response[i].childNodes[0].innerHTML+" ";
					var row = document.createElement("tr");
					var td1 = document.createElement("td");
					var td2 = document.createElement("td");
					var td3 = document.createElement("td");
					var td4 = document.createElement("td");
					var td5 = document.createElement("td");
					var td6 = document.createElement("td");
					var td7 = document.createElement("td");
					var td8 = document.createElement("td");
					var td9 = document.createElement("td");
					var td10 = document.createElement("td");
					var td11 = document.createElement("td");
					var td12 = document.createElement("td");
					td1.innerHTML = response[i].childNodes[0].innerHTML;	
					td2.innerHTML = response[i].childNodes[1].innerHTML;
					td3.innerHTML = response[i].childNodes[2].innerHTML;
					td4.innerHTML = response[i].childNodes[3].innerHTML;
					td5.innerHTML = response[i].childNodes[4].innerHTML;
					td6.innerHTML = getAppRepay(response[i].childNodes[3].innerHTML, response[i].childNodes[4].innerHTML);
					td7.innerHTML = response[i].childNodes[5].innerHTML;	
					td8.innerHTML = response[i].childNodes[6].innerHTML;
					td9.innerHTML = response[i].childNodes[7].innerHTML;
					td10.innerHTML = response[i].childNodes[8].innerHTML;
					td11.innerHTML = response[i].childNodes[9].innerHTML;
					td12.innerHTML = response[i].childNodes[10].innerHTML;
					row.appendChild(td1);
					row.appendChild(td2);
					row.appendChild(td3);
					row.appendChild(td4);
					row.appendChild(td5);
					row.appendChild(td6);
					row.appendChild(td7);
					row.appendChild(td8);
					row.appendChild(td9);
					row.appendChild(td10);
					row.appendChild(td11);
					row.appendChild(td12);
					valid=temp.split(" ");
					get("empty").innerHTML="You have " + count + " accounts to review.";
					resultTable.appendChild(row);
				}
				
			}
			else{
				get("empty").innerHTML="Nothing to approve";
			}
			
		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			get("empty").innerHTML="Woops something went wrong";
		}
	}

	xhr.open("POST",url);
	//Tell our xhr how to send the data to the endpoint.
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
}


function checkInput(){//check for valid input disabling and enabling submits accordingly
	var found=false;
	for(let x=0; x<valid.length-1;x++){
		if(get("activeID").value==valid[x])
			found=true;
	}
	if(found==true){
		get("cancel").disabled=false;
		get("submit").disabled=false;
	}
	else
	{
		get("cancel").disabled=true;
		get("submit").disabled=true;
	}
}

function getAppRepay(cost, type){//find how much a application will actually pay
	var x=parseInt(cost);
	var y=parseInt(type);
	if(y==1)
		x=x*.8;
	else if(y==2)
		x=x*.6;
	else if(y==2)
		x=x*.6;
	else if(y==3)
		x=x*.75;
	else if(y==5)
		x=x*.9;
	else if(y==6)
		x=x*.3;
	return x;
}
function get(s){//function for convenience
	return document.getElementById(s);
}