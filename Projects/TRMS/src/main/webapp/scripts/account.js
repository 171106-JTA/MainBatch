function viewDetails(application){
	var ele = document.getElementById("det" + application);
	if(ele.value == false){
		ele.value = true;
		ele.innerHTML =
			"<ul style='list-style-type: none'>" +
				"<li>event: bleh</li>" +
				"<li>amount: 200</li>" +
				"<li>passed: n/a</li>" +
				"<li>approved: n/a</li>" +
			"</ul>";
	} else {
		ele.value = false;
		ele.innerHTML = "";
	}
	
		
}
function viewReimbursementApplications(){
	var data = "";
	for(let i =0; i < 3; i++){
		data += "<div id='app" + i + "'>" +
					"<h3>application " + i + "</h3><button onclick='viewDetails(" + i + ")'>details</button>" +
					"<div>status: training" +
					"<div id='det" + i + "' value='false'></div>" +
				"</div>"
	}
	return data;
}
function displayAccount() {
	document.getElementById("content").innerHTML =
		"<h1>Hello, " + employee.firstname + "</h1>" +
		viewReimbursementApplications() +
		"<button onclick='displayReimbursementApplication()'>apply</button>"
}