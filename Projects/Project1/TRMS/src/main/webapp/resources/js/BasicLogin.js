file = "<div class=\"nav nav-pills navEmp\"><ul><li><a href=\"#requestsEmp{0}\" class=\"navTabs2\" id=\"requestEmpNav{1}\">Requests</a></li>" +
		"<li><a href=\"#eventsEmp{2}\" class=\"navTabs2\" id=\"eventEmpNav{3}\">Events</a></li></ul><div class=\"tab-pane\"><div class=\"tab-content\" " +
		"id=\"requestsEmp{4}\"></div></div><div class=\"tab-pane\"><div class=\"tab-content\" " +
		"id=\"eventsEmp{5}\"></div></div></div>"

empSignature = "<tr><th>Employee:</th><td><input type=\"text\" name=\"employeeIds[{0}]\" id=\"employeeId[{1}]\" style=\"width: 50px;\" required></td></tr>";

removeEmpBtn = "<td><button id=\"removeEmpBtn\">Remove Employee</button></td>";

critGrdSignature = "<tr><th>Grade:</th><td><input type=\"text\" id=\"critKeys[{0}]\" name=\"critKeys[{1}]\" style=\"width: 50px;\" required></td>";

critPntSignature = "<th>Minimum:</th><td><input type=\"text\" id=\"critVals[{2}]\" name=\"critVals[{3}]\" style=\"width: 50px;\" required></td></tr>";

critRow = critGrdSignature + critPntSignature;

evtAccrd = "	<div class=\"accordion\" id=\"eventAccordionEmp{0}\"></div>"

reqAccrd = "	<div class=\"accordion\" id=\"requestAccordionEmp{0}\"></div>";

//file = "<div class=\"nav nav-pills navEmp\"><ul><li><a href=\"#requests2\" class=\"navTabs2\" id=\"requestEmpNav\">Requests</a></li><li><a href=\"#events2\" class=\"navTabs2\"id=\"eventEmpNav\">Events</a></li></ul><div class=\"tab-pane\"><div class=\"tab-content\" id=\"requests2\"></div><div class=\"tab-pane\"><div class=\"tab-content\" id=\"events2\"></div></div></div></div>";

inboxAccrd = "<div class=\"accordion\" id=\"inboxAccordion\"></div>"

empAccrd = "<div class=\"accordion\" id=\"employeeAccordion\"></div>"

removeCritBtn = "<td><button id=\"removeCritBtn\">Remove Criteria</button></td>";

gets = ["getRequests.do", "getEvents.do", "getMessages.do"];

posts = ["createRequest.do", "createEvent.do", "createMessage.do"];

getEmp = "getEmployees.do";

postEmp = "createEmployee.do";

btns = ["#requestBtn", "#eventBtn", "#inboxBtn"];

forms = ["#requestForm, #eventForm, #inboxForm"];

templates = ["/TRMS/RequestTemplate.html", "/TRMS/EventTemplate.html", "TRMS/InboxTemplate.html"];

evtNavClicked = false;
reqNavClicked = false;
mailNavClicked = false;

var requestInterval = null;
var eventInterval = null;
var empInterval = null;

var opened1, opened2;

clearIntervals = function () {
	if (requestInterval != null)
		clearInterval(requestInterval);
	if (eventInterval != null)
		clearInterval(eventInterval);
	if (empInterval != null)
		clearInterval(empInterval);
}

startIntervals = function(){
//	setInterval(startRequest, 1000);
//	setInterval(startEvent, 1000);
//	setInterval(startEmployee, 1000);
//	setInterval(startInbox, 1000);
//	setInterval(monitorSession, 10);
}

String.prototype.format = function () {
	a = this;
	for (k in arguments) {
		a = a.replace("{" + k + "}", arguments[k])
	}
	return a
}

buildAccordion = function () {
	$('.accordion').accordion({
		collapsible: true,
		active: false,
		heightStyle: 'content',
		fillSpace: true,
		clearStyle: true
	});
	$('.accordion').accordion('refresh');
}

buildTabs = function () {
	$(".navEmp").tabs({
		active: false,
		collapsible: true,
		hide: "slideUp",
		show: "slideDown",
	});
}

var opened, opened2;

$(document).ready(function () {

	$(function () {
		$(".navMain").tabs({
			active: false,
			collapsible: true,
			hide: "slideUp",
			show: "slideDown"
		});
	});

	$(".accordion").sortable({
		axis: "y",
		handle: "h3",
		items: "div",
		receive: function (event, ui) {
			$(ui.item).removeClass();
			$(ui.item).removeAttr("style");
			$(".accordion").accordion("add", "<div>" + ui.item.hmtl() + "</div>");
		}
	});

	var reqExec = false;
	$('#requestNav').click(function(){
//	startRequest = setInterval(function () {
		if (reqExec == false) {
			reqExec = true;
			$.ajax({
				type: "GET",
				url: 'getRequests.do',
				dataType: "json",
				error: function (xhr, stat, err) {
				},
				success: function (data) {
					console.log("successful request fetch");
					console.log(JSON.stringify(data));
					$.each(data, function (idx) {
						json = JSON.parse(data[idx]);
						keyz = JSON.parse(json[0]);
						valz = JSON.parse(json[1]);

						var containsID = $('#reqRow' + valz.values[0]);

						if (containsID.length == 0) {
							$('#requestAccordion1').append("<h3 id=\"reqRow" + valz.values[0] + "\">" + keyz.keys[0] + ":  " + valz.values[0] + "</h3>");
							table = "<table><tr>";
							for (i = 0; i < keyz.keys.length; i++) {
								table += "<th>" + keyz.keys[i] + "</th>";
							}
							table += "</tr><tr>";
							for (i = 0; i < valz.values.length; i++) {
								table += "<td>" + valz.values[i] + "</td>";
							}
							table += "<td><button name=\"approved\" value=\"1\" class=\"reqBtns\"" + valz.values[0] + " id=\"approveBtn" + valz.values[0] + "\">Approve</button></td>";
							table += "<td><button name=\"rejected\" value=\"-1\" class=\"reqBtns\"" + valz.values[0] + " id=\"rejectBtn" + valz.values[0] + "\">Reject</button></td>",
							table += "</tr></table>";
							$('#requestAccordion1').append("<div>" + table + "</div>");
							var status = 0;
							$('#rejectBtn' + valz.values[0]).click(function(){
								$.ajax({
									type: "POST",
									url: "processRequest.do",
									data: {
										reqId: $('.ui-accordion-header-active').attr('id'),
										status: -1
									},
									error: function(xhr, stat, err){
										
									},
									success: function() {
										alert("Data updated successfully");
									}
								})
							})
							
							$('#approveBtn' + valz.values[0]).click(function(){
								$.ajax({
									type: "POST",
									url: "processRequest.do",
									data: {
										reqId: $('.ui-accordion-header-active').attr('id'),
										status: 1
									},
									error: function(xhr, stat, err){
										
									},
									success: function() {
										alert("Data updated successfully");
									}
								})
							})
//							$('.reqBtns' + valz.values[0]).click(function(){
//							})
							buildAccordion();
						}
					});
				},
				complete: function () {
					reqExec = false;
				}
			});
		}
//	}, 10000);
	});
	$.ajax({
		type: "GET",
		dataType: "text",
		url: "/TRMS/RequestTemplate.html",
		success: function (data) {
			$('#requestAccordion1').append("<h3>New Request</h3>");
			$('#requestAccordion1').append(data);
			$('#requestBtn').click(function () {
				$.ajax({
					type: "POST",
					data: $('#requestForm').serialize(),
					url: 'createRequest.do',
					error: function (xhr, ajaxOpts, err) {
					},
					success: function () {
						$(this).html('Request created');
					}
				})
			})
		}
	});
	//					}, 300);

	// Monitor DB for incoming messages
	var eventExec = false;
	$('#eventNav').click(function(){
//	startEvent = setInterval(function () {
		if (eventExec == false) {
			eventExec = true;
			$.ajax({
				type: "GET",
				url: 'getEvents.do',
				dataType: "json",
				error: function (xhr, stat, err) {
				},
				success: function (data) {
					console.log("successful event fetch");
					console.log(JSON.stringify(data));
					$.each(data, function (idx) {
						json = JSON.parse(data[idx]);
						keyz = JSON.parse(json[0]);
						valz = JSON.parse(json[1]);

						var containsID = $('#evtRow' + valz.values[0]);

						if (containsID.length == 0) {
							$('#eventAccordion1').append("<h3 id=\"evtRow" + valz.values[0] + "\">" + keyz.keys[0] + ":  " + valz.values[0] + "</h3>");
							table = "<table><tr>";
							//						console.log("#mailRow" + valz.values[0]).text();
							for (i = 0; i < keyz.keys.length; i++) {
								table += "<th>" + keyz.keys[i] + "</th>";
							}
							table += "</tr><tr>";
							for (i = 0; i < valz.values.length; i++) {
								table += "<td>" + valz.values[i] + "</td>";
							}
							table += "</tr></table>";
							$('#eventAccordion1').append("<div>" + table + "</div>");
							buildAccordion();
						}
					});
				},
				complete: function () {
					eventExec = false;
				}
			});
		}
//	}, 10000);
	})
	//			}, 300);

	$.ajax({
		type: "GET",
		dataType: "text",
		url: "/TRMS/InboxTemplate.html",
		success: function (data) {
			$('#inboxAccordion').append("<h3>New Message</h3>");
			$('#inboxAccordion').append(data);
			buildAccordion();
			$('#messageBox').keyup(function () {
				$('#charCounter').text("Characters left: " + ($('#messageBox').attr('maxlength') - $(this).val().length));
			});
			$('#mailBtn').click(function (event) {
				event.preventDefault();
				$.ajax({
					type: "POST",
					data: $('#mailForm').serialize(),
					url: 'createMessage.do',
					error: function () {
					},
					success: function () {
						$(this).html('Message created');
					}
				})
			})
		}
	});

	// Monitor DB for incoming messages
	var inboxExec = false;
	$('#inboxNav').click(function(){
//	startInbox = setInterval(function () {
		if (inboxExec == false) {
			inboxExec = true;
			$.ajax({
				type: "GET",
				url: 'getMessages.do',
				dataType: "json",
				error: function (xhr, stat, err) {
				},
				success: function (data) {
					console.log("successful mail fetch");
					console.log(JSON.stringify(data));
					$.each(data, function (idx) {
						json = JSON.parse(data[idx]);
						keyz = JSON.parse(json[0]);
						valz = JSON.parse(json[1]);

						var containsID = $('#mailRow' + valz.values[0]);

						if (containsID.length == 0) {
							$('#inboxAccordion').append("<h3 id=\"mailRow" + valz.values[0] + "\">" + keyz.keys[0] + ":  " + valz.values[0] + "</h3>");
							table = "<table><tr>";
							for (i = 0; i < keyz.keys.length; i++) {
								table += "<th>" + keyz.keys[i] + "</th>";
							}
							table += "</tr><tr>";
							for (i = 0; i < valz.values.length; i++) {
								table += "<td>" + valz.values[i] + "</td>";
							}
							table += "</tr></table>";
							$('#inboxAccordion').append("<div>" + table + "</div>");
							buildAccordion();
						}
					});
				},
				complete: function () {
					inboxExec = false;
				}
			});
		}
//	}, 10000);
	})

	//
	//		// Monitor Session activity
	//		sessionInterval = setInterval(
	sesExec = false;
//	monitorSession = setInterval(function () {
		if (sesExec == false) {
			sesExec = true
			$.ajax({
				type: "GET",
				url: 'getSession.do',
				dataType: 'json',
				error: function () {

				},
				success: function (data) {
					data.value;
				},
				complete: function () {
					sesExec = false;
				}
			})
		}
//	}, 100);

});