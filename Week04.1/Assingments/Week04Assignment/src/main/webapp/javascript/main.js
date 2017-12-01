window.onload = function(){
	var button = document.getElementById("submit");
	button.addEventListener("click", fizzbuzz);
}
function fizzbuzz(){
	console.log("Enter fizzbuzz");
	var low = document.getElementById("leftIn").value;
	var high = document.getElementById("rightIn").value;
	var result = document.getElementById("result");
	result.innerHTML = "";
	if(low > high){
		let n = low;
		low = high;
		high = n;
	}
	for(;low <= high; low++){
		if(low%15 == 0){
			console.log("fizzbuzz");
			result.innerHTML += "fizzbuzz<br>";		
		} else if(low%5 == 0){
			console.log("buzz");
			result.innerHTML += "buzz<br>";
		} else if(low%3 == 0){
			console.log("fizz");
			result.innerHTML += "fizz<br>"; 
		} else {
			console.log("" + low);
			result.innerHTML += low + "<br>";
		}
	}
}