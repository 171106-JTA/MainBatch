function EmployeeAccount(response){
	console.log("inside employeeaccount " + response.username);
	return {
		username: response.username,
		firstname: response.firstname,
		middlename: response.middlename,
		lastname: response.lastname,
		email: response.email,
		address: response.address,
		city: response.city,
		state: response.state,
		islocked: response.islocked,
		isadmin: response.isadmin,
		employeeid: response.employeeid,
		toAJAX: ()=>{
						return 	  "username:" + response.username + "&"
								+ "firstname:" + response.firstname + "&"
								+ "middlename:" + response.middlename + "&"
								+ "lastname:" + response.lastname + "&"
								+ "email:" + response.email + "&"
								+ "address:" + response.address + "&"
								+ "city:" + response.city + "&"
								+ "state:" + response.state + "&"
								+ "islocked:" + response.islocked + "&"
								+ "isadmin:" + response.isadmin + "&"
								+ "employeeid:" + response.employeeid;
					}
	};
}