$(document).ready(function() {
	
	// Monitor DB for changes to requests
	setInterval($.ajax({
		type : "GET",
		url: 'RequestMonitorServlet',
		dataType : 'Array',
		error : function() {
		},
		success : function(data) {
			table = "<table>";
			data.each(function(idx, value) {
				table += "<tr id=\"tableRow" + idx + "\">";
				for (x in value) {
					if (idx % 2 == 1)
						table += "<td>" + x + "</td>";
					else
						table += "<th>" + x + "</th>";
				}
				table += "<td><button class=\"deleteButton\"></td></tr>";
			});
			table += "</table>";
		}
	}), 300);
	
	// Monitor DB for incoming messages
	setInterval($.ajax({
		type : "GET",
		url: 'InboxMonitorServlet',
		dataType : "Array",
		error : function() {
		},
		success : function(data) {
			data.length;
		}
	}), 300);
});