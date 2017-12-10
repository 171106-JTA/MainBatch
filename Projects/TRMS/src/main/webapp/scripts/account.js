function adminButton(){
	if(employee.isadmin == true){
		return "<button onclick='grabProcessingApps(\"PullReimbursementApplicationsServlet\", " + employee.employeeid + ")'>admin</button>";
	} else{
		return "";
	}
}
function viewDets(e) {
	for(let a in apps){
		if(apps[a].applicationid == e.target.id){
			var ele = document.getElementById("det" + apps[a].applicationid);
			if(ele.value == true){
				ele.value = false;
				ele.innerHTML =																																																																														
					"<ul>" +
						"event: " + apps[a].eventid + "<br>" +
						"amount: $" + apps[a].amount + "<br>" +
						"status: " + apps[a].statusid + "<br>" +
						"rejected: " + apps[a].ispassed + "<br>" +
						"approved: " + apps[a].isapproved + "<br>" +
					"</ul>";
				if(employee.employeeid >= 1110000){
					ele.innerHTML +=
						"<button value='approve' id=approve" + apps[a].applicationid + ">approve</button>" +
						"<button value='reject' id=reject" + apps[a].applicationid + ">reject</button>";
				}
			} else {
				ele.value = true;
				ele.innerHTML = "";
			}
		}
	}
	var btns = document.getElementsByTagName("button");
	for(let b of btns){
		if(b.value == "approve"){
			b.addEventListener("click", approveApp);
		} else if(b.value == "reject"){
			b.addEventListener("click", rejectApp);
		}
	}
}
function viewReimbursementApplications(){
	if(apps == null){
		return;
	}
	var data = "";
	for(let a in apps){
		var app = apps[a];
		data += "<div id='app" + app.applicationid + "'>" +
					"<div><h3>application " + app.applicationid + "</h3><button id='" + app.applicationid + "'>details</button></div>" +
					"<div id='det" + app.applicationid + "' value=false></div>" +
				"</div>";
	}
	return data;
}
function displayAccount() {
	document.getElementById("content").innerHTML =
		"<h1>Hello, " + employee.firstname + "</h1>" +
		"<ul>" +
			"<h3>current applications</h3>" +
			viewReimbursementApplications() +
			"<h3>new application</h3>" +
			"<button onclick='displayWelcome()'>back</button>" +
			adminButton() +
			"<button onclick='displayReimbursementApplication()'>apply</button>" +
		"</ul>";
	var btns = document.getElementsByTagName("button");
	for(let b of btns){
		b.addEventListener("click", viewDets);
		
	}
}