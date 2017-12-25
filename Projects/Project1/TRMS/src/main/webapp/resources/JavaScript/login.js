
window.onload = function(){//update files based on date
	var url = "DateCheck";
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		
		if(xhr.readyState == 4 && xhr.status == 200){
			
		}
	}
	xhr.open("POST",url);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
}

