window.onload = function(){
	var url = "GetUserInfo";
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			
			var xmlText = xhr.responseXML;
			
			var response = xmlText.getElementsByTagName("user");
			
			
			for(i in response){
				get("fname").innerHTML = response[i].childNodes[0].innerHTML;	
				get("lname").innerHTML = response[i].childNodes[1].innerHTML;
				get("repay").innerHTML = RepayLeft(response[i].childNodes[2].innerHTML,response[i].childNodes[3].innerHTML);
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

function RepayLeft(award, pend){
	
	var x=parseInt(award);
	var y=parseInt(pend);
	console.log(x + " || " + y);
	var z= 1000-x-y;
	return z;
}

function get(s){
	return document.getElementById(s);
}