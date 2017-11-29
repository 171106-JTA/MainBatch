function doFizzBuzz() { 
	//get values from input boxes
	var low = document.getElementById("input1").value; 
	var high = document.getElementById("input2").value;
	
	//input checking
	if(Number(low) == NaN || Number(high) == NaN
		|| low %1 != 0 || high %1 != 0) {
		window.alert("Improper Input!")
		return; 
	}
	
	//ordering low-high
	if(low > high) {
		let temp = low; 
		low = high;
		high = temp; 
	}
	
	//create list of fizzbuzz results
	let list = document.createElement('ul'); 
	for(low; low <= high; low++) {
		var listItem = document.createElement('li'); 
		var fizz = "fizz"; 
		var buzz = "buzz"; 
		var str = low; 
		if(low %3 == 0 || low %5 == 0) {
			str = "";
			if(low %3 == 0)
				str += fizz; 
			if(low %5 == 0)
				str += buzz; 
		}
		
		//append to list
		var entry = document.createTextNode(str); 
		listItem.appendChild(entry); 
		document.getElementById("fizzbuzzlist").appendChild(listItem); 
		
	}

	
	
	
	
}