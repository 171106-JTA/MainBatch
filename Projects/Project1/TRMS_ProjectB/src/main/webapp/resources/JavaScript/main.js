function login(){
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	
	alert("User: " + username + "\nPass:" + password);
}

function calc( ){
	 var uc = document.getElementById("uc").value;
	 var sem = document.getElementById("sem").value;
	 var cpc = document.getElementById("cpc").value;
	 var cert = document.getElementById("cert").value;
	 var tt = document.getElementById("tt").value;
	 var other = document.getElementById("other").value;
	 
	 var rem = ((uc*.8) + (sem*.6) + (cpc*.75) + cert + (tt*.9) + (other*.3));
	 
	 if((1000 - rem) < 0){
		 alert("You get full $1000!");
	 }
	 else{
		 var total = parseFloat(rem).toFixed(2);
		 alert("You get $" + total + "!");
	 }
}

function upload(){
	var file = document.getElementById("filename");
	alert(file);
}