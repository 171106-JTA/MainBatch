function fizzBuzz(a, b){

	try {

		checkNum(a);
		checkNum(b);

		validateRange(a, b);

		out = "";
		for(i = a; (i - b) <= 0; i++){
			s = "";
			if(i % 3 == 0)
				s += "fizz";
			if(i % 5 == 0)
				s += "buzz";
			if(s == "")
				s += i;

			out += s + "<br>";
		}

		document.getElementById('output').innerHTML = out;
	}

	 catch(ex) {
        console.log(ex.message, ex.name);
        if(document.getElementById('output'))
            document.getElementById('output').innerHTML = ex.message.fontcolor("red");
        throw new Error(message);
    }
}

function checkNum(num){
	if(!num)
		throw new NaNInputException("null");
	if(isNaN(num))
		throw new NaNInputException(num);
}

function validateRange(a, b){
	if(a - b > 0)
		throw new InvalidRangeException(a, b);
}

function InvalidRangeException(a, b){
	name = "InvalidRangeException";
	var message = "Invalid range: "a + " > " + b;
	this.message = message;
	this.name = name;
}

function NaNInputException(num){
	name = "NaNInputException: ";
	var message = num + " is not a number";
	this.message = message;
	this.name = name;
}