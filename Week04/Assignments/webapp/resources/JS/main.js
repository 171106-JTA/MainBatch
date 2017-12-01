function generateBuzz(){
	var firstNum = document.getElementById('num1').value;
	var secondNum = document.getElementById('num2').value;
	firstNum = parseInt(firstNum);
	secondNum = parseInt(secondNum);
	var i = (firstNum < secondNum) ? firstNum : secondNum; //stores the lesser value in i


	var tableRow = null;
	var tableData = null;


	if (firstNum > secondNum) {
		var hold = firstNum;
		firstNum = secondNum;
		secondNum = hold;
	} //orders input

	for (i; i <= secondNum; i++){
		if ((i%15)==0) {
			tableData = document.createElement('td');		//create cell node
			tableRow = document.createElement('tr');		//create row node
			tableData.innerHTML="<b>fizzbuzz</b>";
			tableRow.appendChild(tableData);

		}	else if ((i%5)==0) {
			tableData = document.createElement('td');
			tableRow = document.createElement('tr');		//create row element
			tableData.innerHTML="<b>buzz</b>";
			tableRow.appendChild(tableData);

		} else if ((i%3)==0) {
			tableData = document.createElement('td');
			tableRow = document.createElement('tr');		//create row element
			tableData.innerHTML="<b>fizz</b>";
			tableRow.appendChild(tableData);

		} else {
			tableData = document.createElement('td');
			tableRow = document.createElement('tr');		//create row element
			tableData.appendChild(document.createTextNode(i));
			tableRow.appendChild(tableData);
		}
		document.getElementById("ResultTable").appendChild(tableRow);
		//append child does nothing but pass ref
	}
}

function clearAll(){
	var node = document.getElementById("ResultTable");
	while (node.hasChildNodes()) {
  	node.removeChild(node.lastChild);
	}

	document.getElementById("num1").value = "";
	document.getElementById("num2").value = "";
	document.getElementById("num1").focus();
}
