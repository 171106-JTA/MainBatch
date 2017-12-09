var loader = null;
var employee = null
window.onload = function() {
	console.log("about to load");
//	loader = load();
//	if(loader){
		displayWelcome();
//	}
}
function load(){
	var xhr = new XMLHttpRequest();
	var response = "";
	console.log("loading");
	xhr.onreadystatechange = function(){
		if(this.readyState == 4){
			if(this.status == 200){
				return new Loader(JSON.parse(this.responseText));
			} else {
				displayCriticalError();
			}
		}
	}
	xhr.open("POST", "LoaderServlet");
	xhr.send("");
}
function displayCriticalError(){
	document.getElementById("content").innerHTML =
		"<h1 style='color: red'>Critical Error Has Occurred</h1>" +
		"The error is being handled and the site will be back up soon.";
}