function validateNewRequest(){
	let fileName = document.getElementById("theFile");
	var error = document.getElementById("error");
	if(fileName.value != "" && fileName.value.split('.').pop().toLowerCase() != 'png'){
		error.innerHTML ="Only excepting png files";
		return false;
	} 	
	return true;
}