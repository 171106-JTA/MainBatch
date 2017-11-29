window.onload = function() {
	
}

function fizzBuzz() {
	var value1 = document.getElementById("FirstNumber").value;
	var value2 = document.getElementById("SecondNumber").value;
	
	var start = Math.min(value1,value2);
	var end = Math.max(value1, value2);	
	
	
	
	for(var i = start; i<= end; i++) {
		var tableRow = document.createElement('tr');
		var tableData = document.createElement('td');
		
		
		var to_print = "";
		if((i%3) == 0){
			to_print += "fizz";
		}
		if((i%5) == 0){
			to_print += "buzz";
		}
		if((i%3) != 0 && (i%5) != 0) {
			to_print = i;
		}
		console.log(to_print + "\n");
		var td = document.createTextNode(to_print);
		
		tableData.appendChild(td);
		tableRow.appendChild(td);
		document.getElementById("FizzBuzzTable").appendChild(tableRow);
	}
}