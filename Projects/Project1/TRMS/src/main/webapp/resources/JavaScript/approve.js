var valid;
var count=0;
window.onload = function(){
	var url = "ApprovalSetup";
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){//fill table and create list of valid inputs
			var temp="";
			var xmlText = xhr.responseXML;
			
			var response = xmlText.getElementsByTagName("application");
			var resultTable = document.getElementById("requests");
			if(response.length!=0){
				for(i in response){
					temp+=response[i].childNodes[0].innerHTML+" ";
					count++;
					get("empty").innerHTML="You have " + count + " accounts to review.";
					console.log(typeof(temp));
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
					td2.innerHTML = response[i].childNodes[2].innerHTML;
					td3.innerHTML = response[i].childNodes[3].innerHTML;
					td4.innerHTML = response[i].childNodes[4].innerHTML;
					td5.innerHTML = response[i].childNodes[5].innerHTML;
					td6.innerHTML = response[i].childNodes[6].innerHTML;
					td7.innerHTML = RepayLeft(response[i].childNodes[7].innerHTML,response[i].childNodes[8].innerHTML);
					td8.innerHTML = getAppRepay(response[i].childNodes[5].innerHTML, response[i].childNodes[6].innerHTML)
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
				get("empty").innerHTML="Nothing to approve";
			}
			
		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
		}
	}
	console.log(" hello ");
	xhr.open("POST",url);
	//Tell our xhr how to send the data to the endpoint.
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
}


function RepayLeft(award, pend){//calculate expected repayment left
	var x=parseInt(award);
	var y=parseInt(pend);
	var z= 1000-x-y;
	if(z<0)
		return 0;
	return z;
}

function getAppRepay(cost, type){//calculate actual repayment of app
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

function checkInput(){//check for valid input
	var found=false;
	for(let x=0; x<valid.length-1;x++){
		if(get("activeID").value==valid[x])
			found=true;
	}
	if(found==true){
		get("reviewFiles").disabled=false;
		get("infoHold").disabled=false;
		get("accept").disabled=false;
		get("reject").disabled=false;
		get("cost").disabled=false;
	}
	else
	{
		get("reviewFiles").disabled=false;
		get("infoHold").disabled=true;
		get("accept").disabled=true;
		get("reject").disabled=true;
		get("cost").disabled=true;
	}
}

function get(s){//convenience function
	return document.getElementById(s);
}