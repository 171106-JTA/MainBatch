function calculateFizzBuzz(){
	
	var number1 = document.getElementById("number1").value;
	var number2 = document.getElementById("number2").value;
	
	function order(){ //Makes number1 hold the lower value & number2 hold the higher
		if (parseInt(number1) > parseInt(number2)){
			var temp = number1;
			number1 = number2;
			number2 = temp;
		}
	}
	order();
	//window.alert("number1: " + number1 + "; number2: " + number2);
	
	function fizzbuzz(){ //loops from number1 to number2
						 //		prints fizz if divisible by 3
						 //		prints buzz if divisible by 5
						 //		prints fizzbuzz if divisible by 3 and 5
		for(var i = number1; parseInt(i) <= parseInt(number2); i++){
				var j = i;
				
 				if (i % 3 == 0){
					//window.alert("divisible by 3: " + i);
					j = "fizz";
				}
				
				if (i % 5 == 0){
					//window.alert("divisible by 5: " + i);
					j = "buzz";
				}
				
				if ((i % 3 == 0) && (i % 5 == 0)){
					//window.alert("divisible by 5: " + i);
					j = "fizzbuzz";
				}

			 	var tableRow = document.createElement('tr');
				var tableData1 = document.createElement('td');
				var td1 = document.createTextNode(j);

				tableData1.appendChild(td1);
				tableRow.appendChild(tableData1);
				
				document.getElementById("myTable").appendChild(tableRow);
		}
		
	}
	fizzbuzz();	
}
