function fizzBuzz() {
	
	var value1 = Number(document.getElementById("first_number").value); 
	var value2 = Number(document.getElementById("second_number").value); 

	var bigger; 
	var smaller;
	var str = ""; 
	if(value1 > value2) {
		bigger = value1
		smaller = value2
	} else {
		bigger = value2
		smaller = value1
	}




	while(smaller <= bigger) {
		if(smaller % 5 == 0 && smaller % 3 == 0){

			str += "fizzbuzz \n"; 

		} else if(smaller % 5 == 0) {

			str+= "buzz "; 

		} else if (smaller % 3 == 0) {

			str+= "fizz ";

		} else {
			str+= smaller + " \n"; 
		}
		smaller++; 
	}

	document.getElementById("result").innerHTML = str; 
}