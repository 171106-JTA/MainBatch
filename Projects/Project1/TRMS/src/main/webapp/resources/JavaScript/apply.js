window.onload = function(){
	var url = "ApplicationSetup";
	get("custom").style.visibility = "hidden";
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
		}
	}

	xhr.open("POST",url);
	//Tell our xhr how to send the data to the endpoint.
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
}

function update(){//find actual repayment
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

function customGrade(){//show or hide based on checkbox value
	var type=get("gradeType").value;
	if(type==0)
		get("custom").style.visibility = "visible";
	else
	{
		get("custom").style.visibility = "hidden";
	}	
}

function dateCheck(){//get current date and compare it to input date
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth(); //January is 0!

	var yyyy = today.getFullYear();
	if(dd<10){
	    dd='0'+dd;
	} 
	if(mm<10){
	    mm='0'+mm;
	}
	var curDate= new Date(yyyy,mm,dd);
	var ev_date= new Date(get("eventDate").value);
	var difference=getDifference(curDate, ev_date);
	
	console.log(difference);
	if(difference>7)
		get("submitApp").disabled=false;
	else
	{
		get("submitApp").disabled=true;
	}	
}


function getDifference( date1, date2 ) {//find difference between two dates in days
	  //Get 1 day in milliseconds
	  var one_day=1000*60*60*24;

	  // Convert both dates to milliseconds
	  var date1_ms = date1.getTime();
	  var date2_ms = date2.getTime();

	  // Calculate the difference in milliseconds
	  var difference_ms = date2_ms - date1_ms;
	    
	  // Convert back to days and return
	  return Math.round(difference_ms/one_day); 
	}

function RepayLeft(award, pend){//show expected amount of repayment left
	var x=parseInt(award);
	var y=parseInt(pend);
	var z= 1000-x-y;
	if(z<0)
		return 0;
	return z;
}

function get(s){//convenience function
	return document.getElementById(s);
}