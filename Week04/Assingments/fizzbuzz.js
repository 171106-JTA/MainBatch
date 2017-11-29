function fizzBuzz(){
	var low = Number(document.getElementById("inputlow").value);
	var high = Number(document.getElementById("inputhigh").value);
	
	if(low > high){
		let tmp = low;
		low = high;
		high = tmp;
	}
	
	var result = "";
	var value = 0;
	for(let i=low;i<=high;i++){
		if(i%3 == 0 && i%5 == 0){
			value = "fizzbuzz";
		}
		else if(i%3 == 0){
			value = "fizz";
		}
		else if(i%5 == 0){
			value = "buzz";
		}
		else{
			value = i;
		}
		result = result + value + "<br>";
	}
	
	document.getElementById("results").innerHTML = result;
}