function applicationHandler(status){
	if(status == 200){
		alert("Application submitted");
		grabCurrentApps("RetrieveReimbursementApplicationsServlet", employee.employeeid);
		displayAccount();
	} else {
		//displayServerError();
	}
}
function processEventId(id){
	var ele = document.getElementById("eventid");
	var e = document.getElementById(id); 
	ele.value = e.value;
}
function displayReimbursementApplication() {
	document.getElementById("content").innerHTML = 
			"<h1>Event Reimbursement Application</h1>" +
			"<ul>" +
				"<input type='number' id='employeeid' placeholder='employee id' min='1000000' max='9999999'><br>" +
				"<input type='number' id='amount' placeholder='amount' min='1' max='1000'><br>" +
				"<input type='text' id='eventid' value='' placeholder='select event type' onkeypress='return false;'>" +
				"<ul>" +	
					"<button id='uc' class='event' onclick='processEventId(\"uc\")' value=0>University Course</button><br>" +
					"<button id='s' class='event' onclick='processEventId(\"s\")' value=1>Seminar</button><br>" +
					"<button id='cp' class='event' onclick='processEventId(\"cp\")' value=2>Certification Preparation Class</button><br>" +
					"<button id='c' class='event' onclick='processEventId(\"c\")' value=3>Certification</button><br>" +
					"<button id='tt' class='event' onclick='processEventId(\"tt\")' value=4>Technical Training</button><br>" +
					"<button id='o' class='event' onclick='processEventId(\"o\")' value=5>Other</button><br>" +
				"</ul>" +
				"<br>" +
				"<button onclick='displayAccount()'>back</button>" +
				"<button onclick='submitForm(\"NewReimbursementApplicationServlet\", " + applicationHandler + ")'>submit</button>" +
			"</ul>";
}