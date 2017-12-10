function rejectApp(e){
	var request = "";
	for(let a in apps){
		if(("reject" + apps[a].applicationid) == e.target.id){
			apps[a].ispassed = 1;
			apps[a].isapproved = 0;
			var app = new ReimbursementApplication(apps[a]);
			request += app.toAJAX();
		}
	}
	console.log("request " + request);
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				alert("Application Updated");
				grabProcessingApps("PullReimbursementApplicationsServlet", employee.employeeid);
				displayAdminControls();
			} else {
				console.log("SOMETHING WENT WRONG");
			}
		}
	}	
	xhr.open("POST", "UpdateReimbursementApplicationServlet");
	xhr.send(request);
}
function approveApp(e){	
	var request = "";
	for(let a in apps){
		if(("approve" + apps[a].applicationid) == e.target.id){
			apps[a].statusid++;
			if(apps[a].statusid >= 3){
				apps[a].isapproved = 1;
			}
			var app = new ReimbursementApplication(apps[a]);
			request += app.toAJAX();		
		}
	}
	console.log("request " + request);
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				alert("Application Updated");
				grabProcessingApps("PullReimbursementApplicationsServlet", employee.employeeid);
				displayAdminControls();
			} else {
				console.log("SOMETHING WENT WRONG");
			}
		}
	}	
	xhr.open("POST", "UpdateReimbursementApplicationServlet");
	xhr.send(request);
}
function displayAdminControls(){
	document.getElementById("content").innerHTML = 
		"<h1>Admin Controls</h1>" +
		"<ul>" +
			"<h3>applications in process</h3>" +
			viewReimbursementApplications() +
		"</ul>" +
		"<button onclick='grabCurrentApps(\"RetrieveReimbursementApplicationsServlet\"," + employee.employeeid + "); displayAccount();'>back</button>";
	var btns = document.getElementsByTagName("button");
	for(let b of btns){
		console.log("BUTTON VALUE " + b.value);
		if(b.value == "approve"){
			b.addEventListener("click", approveApp);
		} else if(b.value == "reject"){
			b.addEventListener("click", rejectApp);
		} else{
			b.addEventListener("click", viewDets);
		}	
	}
}