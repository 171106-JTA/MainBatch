function ReimbursementApplication(response){
	console.log("inside employeeaccount " + response.username);
	return {
		applicationid: response.applicationid,
		employeeid: response.employeeid,
		statusid: response.statusid,
		eventid: response.eventid,
		amount: response.amount,
		ispassed: response.ispassed,
		isapproved: response.isapproved,
		toAJAX: ()=>{
						return 	  "applicationid:" + response.applicationid + "&"
								+ "employeeid:" + response.employeeid + "&"
								+ "statusid:" + response.statusid + "&"
								+ "eventid:" + response.eventid + "&"
								+ "amount:" + response.amount + "&"
								+ "ispassed:" + response.ispassed + "&"
								+ "isapproved:" + response.isapproved;
					}
	};
}