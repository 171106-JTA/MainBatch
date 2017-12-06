window.onload = function(){
	var url = "ApplicationSetup";
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			
			var xmlText = xhr.responseXML;
			
			var response = xmlText.getElementsByTagName("user");
			
			
			for(i in response){
				get("fname").value = response[i].childNodes[0].innerHTML;	
				get("lname").value = response[i].childNodes[1].innerHTML;
				get("repay").innerHTML = RepayLeft(response[i].childNodes[2].innerHTML,response[i].childNodes[3].innerHTML);
			}
			
		}else if(xhr.readyState == 4 && xhr.status != 200){
			console.log(xhr.status);
			get("AJAXError").innerHTML="Woops";
		}
	}

	xhr.open("POST",url);
	//Tell our xhr how to send the data to the endpoint.
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
}

function update(){
	var percent=get("eventType").value;
	var cost=get("cost").value;
	if(percent==1)
		cost=cost*.8;
	else if(percent==2)
		cost=cost*.6;
	else if(percent==2)
		cost=cost*.6;
	else if(percent==3)
		cost=cost*.75;
	else if(percent==5)
		cost=cost*.9;
	else if(percent==6)
		cost=cost*.3;
	get("repayment").innerHTML=cost;
	
}

function RepayLeft(award, pend){
	var x=parseInt(award);
	var y=parseInt(pend);
	var z= 1000-x-y;
	return z;
}

function get(s){
	return document.getElementById(s);
}