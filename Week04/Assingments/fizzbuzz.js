// make a simple html page that takes two inputs from a user 
// Both are numbers 
// once submitted generate fizzbuzz from lownumber to highnumber 
// fizz buzz(2,16)
// 3 is fizz 5 are buzz
// 2, fizz, 4, buzz, ... 14, fizzbuzz, 16, 17
//

function fizzbuzz(){
	let n1 = document.getElementById("num1").value;
	let n2 = document.getElementById("num2").value;
	var start, end;
	n1 > n2 ? (start = n2, end = n1 ): (start = n1, end = n2);
	let myDiv = document.getElementById("myFizzbuzz")
	myDiv.innerHTML="";
	for(let i = start; i <= end; i++){
		if(i%3 == 0 && i%5 == 0){
			myDiv.innerHTML+="fizzbuzz<br>"
		}
		else if(i%5 == 0){
			myDiv.innerHTML+=("buzz<br>")
		}
		else if(i%3 == 0){
			myDiv.innerHTML+=("fizz<br>")
		}
		else{
			myDiv.innerHTML+=(i+"<br>");
		}
	}
}