window.onload = function(){
	$("result").innerHTML="FizzBuzz";
}

function fizzbuzz(){
	var results="";
	var fizz=parseInt($("fizz").value);
	var buzz=parseInt($("buzz").value);
	var x= [fizz,buzz];
	var y=x.sort();
	if(fizz<buzz){
		for(fizz;fizz<=buzz;fizz++)
			results= results + "<br>" + fizzOrBuzz(fizz);
	}
	else if(buzz<fizz){
		for(buzz;buzz<=fizz;buzz++)
			results= results + "<br>" + fizzOrBuzz(buzz);		
	}
	else
		results=fizzOrBuzz(fizz);
	$("result").innerHTML=results;
}

function $(s){
	return document.getElementById(s);
}

function fizzOrBuzz(x){
	if(x%3==0 && x%5==0)
		return "fizzbuzz";
	else if(x%3==0)
		return "fizz";
	else if(x%5==0)
		return "buzz";
	else
		return x;
}