function fizzBuzz(){
	var lowNum = Number(document.getElementById("lowNum").value);
	var highNum = Number(document.getElementById("highNum").value);
	var result = "";
	console.log("lowNum: " + lowNum + " highNum: " + highNum);
	
	if(lowNum<highNum){
		for(let i = lowNum; i <= highNum; i++){
			if(i%3 == 0 && i % 5 == 0){
				result += "\n fizzbuzz";
			}else if(i % 5 == 0){
				result += "\n buzz";
			}else if(i % 3 == 0){
				result += "\n fizz";
			}else{
				result = result + "\n" + i;
			}
		}
	}
	
	document.getElementById("result").innerHTML = result;
}