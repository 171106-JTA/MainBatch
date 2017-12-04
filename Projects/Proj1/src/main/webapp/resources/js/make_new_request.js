function validateNewRequest(){
	let form = document.forms['registration'];
	let eventDuration = form['eventDuration'];
	let cost = form['cost'];
	let error = document.getElementById("error");
	
	if(eventDuration < 0){
		error.innerHTML = "Duration must be a positive number.";
		return false;
	}
	else if(cost < 0){
		error.innerHTML = "Cost must be a positive number.";
		return false;
	}
	return true;
}