window.onload = function(){
	document.getElementById("para1").innerHTML="ALTER VIA JAVASCRIPT!";
	
	var e1 = document.getElementById("para1");
	var d1 = document.getElementById("d1");
	e1.addEventListener("click", doStuff, true);
	d1.addEventListener("click", d1Click, true);
}

function doStuff(){
	window.alert("you clicked me");
}

function d1Click(){
	window.alert("d1");
	//event.stopPropogation();
}

function submitName(){
	var formValue = document.getElementById("inputfield").value;
	idCount = 0;
	var tableRow = document.createElement('tr');
	var tableData1 = document.createElement('td');
	var tableData2 = document.createElement('td');
	
	var td1 = document.createTextNode(++idCount);
	var td2 = document.createTextNode(formValue);
	
	tableData1.appendChild(td1);
	tableData2.appendChild(td2);
	
	tableRow.appendChild(tableData1);
	tableRow.appendChild(tableData2);
	
	document.getElementById("mytable").appendChild(tableRow);


}

function fizzbuzz(){
	var begin = document.getElementById("begin").value;
	var end  = document.getElementById("end").value;
	var section = document.getElementById("section");
	var p = document.createElement('p');
	
	for(i = begin; i <= end; i++){
		if(i%5 === 0 && i%3 == 0){
			p = "fizzbuzz\n";
			section.appendChild(document.createTextNode(p));
			//document.write("fizzbuzz<br>");
			
		}
			
		else if(i%3 == 0){
			p = "fizz\n";
			section.appendChild(document.createTextNode(p));
			//document.write("fizz<br>");
		}
		else if(i%5 == 0){
			p = "buzz\n";
			section.appendChild(document.createTextNode(p));
			//document.write("buzz<br>");
			
		}
			
		else{
			p = i + "\n";
			section.appendChild(document.createTextNode(p));
			//document.write(i + "<br>");
			
		}
			
	}
}


function triggerError(){
	try{
		console.log(p++);
	}catch(container){
		console.log(container.message);
	}
}