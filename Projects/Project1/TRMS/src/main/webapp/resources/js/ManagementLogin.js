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
					error: function (xhr, stat, err) {
						alert('Request failed to create ' + stat);					},
					success: function () {
						alert('Request created');
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

	$.ajax({
		type: "GET",
		dataType: "text",
		url: "/TRMS/EventTemplate.html",
		success: function (data) {
			$('#eventAccordion1').append('<h3>New Event</h3>')
			$('#eventAccordion1').append(data);
			var len = $('#addEmpsTable tr').length;
			buildAccordion();
			$('#addEmpsBtn').click(function (event) {
				event.preventDefault();
				$('#addEmpsTable').append(empSignature.format(len, len));
				len += 1;
				if (len == 2) {
					$('#empBtnsRow').append(removeEmpBtn);
					$('#removeEmpBtn').click(function (event) {
						event.preventDefault();
						$('#addEmpsTable tr:last').remove();
						len -= 1;
						if (len < 2)
							$('#removeEmpBtn').remove();
					})
				}
			})

			var critlen
			$('input[name=critType]').change(function () {
				var val = $('input[name=critType]:checked').val();
				if (val == 0) {
					$.ajax({
						type: "GET",
						dataType: "text",
						url: "/TRMS/CriteriaTemplate.html",
						success: function (data) {
							$('#addCritsTable').append(data);
							critlen = $('#addCritsTable tr').length;
							$('#addCritsBtn').click(function (event) {
								event.preventDefault();
								critlen += 1;
								$('#addCritsTable').append(critRow.format(critlen - 2, critlen - 2, critlen - 2, critlen - 2));
								if (critlen == 3) {
									$('#critBtnsRow').append(removeCritBtn);
									$('#removeCritBtn').click(function (event) {
										event.preventDefault();
										$('#addCritsTable tr:last').remove();
										critlen -= 1;
										if (critlen < 3)
											$('#removeCritBtn').remove();
									})
								}
							})
						}
					});
				}
				else {
					$('#addCritsTable').empty();
				}
			});
			$('#evtBtn').click(function (event) {
				event.preventDefault();
				var values = critlen - 2;
				var data = $('#createEventForm').serializeArray();
				data.push(
					{ name: "emp", value: len },
					{ name: "crits", value: critlen - 1 }
				);
				$.ajax({
					type: "POST",
					data: data,
					url: 'createEvent.do',
					error: function (xhr, stat, err) {
						alert('Event failed to create ' + stat);					},
					success: function () {
						alert('Event created');
					}
				})
			})
		}
	});
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
					error: function (xhr, stat, err) {
						alert('Message failed to create ' + stat);					},
					success: function () {
						alert('Message created');
					}
				})
			})
		}
	});

	// Monitor DB for incoming messages
	var inboxExec = false;
	$('#inboxNav').click(function(){
//	startInbox = setInterval(function () {
		$('#inboxNav').removeClass('colorize')
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
							$('#inboxNav').addClass('colorize');
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

	registerEmpAccrdAction();
});

function registerEmpAccrdAction() {
	$.ajax({
		type: "GET",
		url: "/TRMS/EmployeeTemplate.html",
		dataType: "html",
		error: function (xhr, stat, err) {

		},
		success: function (data) {
			$('#employeeAccordion').append("<h3>New Employee</h3>");
			$('#employeeAccordion').append(data);
			buildAccordion();
		}
	});


//	startEmployee = 
	$('#empNav').click(function(){
	$.ajax({
		type: "GET",
		url: "getEmployees.do",
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

				var containsID = $('#empRow' + valz.values[0]);

				// append employee JSON table as usual
				if (containsID.length == 0) {
					$('#employeeAccordion').append("<h3 id=\"empRow" + valz.values[0] + "\">" + keyz.keys[0] + ":  " + valz.values[0] + "</h3>");
					div = "<div>";
					table = "<table><tr>";
					for (i = 0; i < keyz.keys.length; i++) {
						table += "<th>" + keyz.keys[i] + "</th>";
					}
					table += "</tr><tr>";
					for (i = 0; i < valz.values.length; i++) {
						table += "<td>" + valz.values[i] + "</td>";
					}
					table += "</tr></table>";

					div += table;

					
					div += file.format(valz.values[0], valz.values[0], valz.values[0], valz.values[0],
					valz.values[0], valz.values[0], valz.values[0], valz.values[0]);

					$('#employeeAccordion').append(div + "</div>");
					$('#requestsEmp' + valz.values[0]).html(reqAccrd.format(valz.values[0]));
					$('#eventsEmp' + valz.values[0]).html(evtAccrd.format(valz.values[0]));

					buildAccordion();

					buildTabs();
					// add subtree of employee requests/events

					var reqExec2 = false;
					startRequest2 = function () {
						if (reqExec2 == false) {
							reqExec2 = true;
							$.ajax({
								type: "GET",
								url: 'getRequests.do',
								dataType: "json",
								error: function (xhr, stat, err) {
								},
								success: function (data) {
									console.log("successful request fetch");
									console.log(JSON.stringify(data));
									$.each(data, function (idx2) {
										json2 = JSON.parse(data[idx2]);
										keyz2 = JSON.parse(json2[0]);
										valz2 = JSON.parse(json2[1]);

										var containsID2 = $('#req2Row' + valz2.values[0]);

										if (containsID2.length == 0) {
											$('#requestAccordionEmp' + valz.values[0]).append("<h3 id=\"req2Row" + valz2.values[0] + "\">" + keyz2.keys[0] + ":  " + valz2.values[0] + "</h3>");
											reqTable = "<table><tr>";
											for (i = 0; i < keyz2.keys.length; i++) {
												reqTable += "<th>" + keyz2.keys[i] + "</th>";
											}
											reqTable += "</tr><br><hr><tr>";
											for (i = 0; i < valz2.values.length; i++) {
												reqTable += "<td>" + valz2.values[i] + "</td>";
											}
											reqTable += "</tr></table>";
											$('#requestAccordionEmp' + valz.values[0]).append("<div>" + reqTable + "</div>");
											buildAccordion();
										}
									});
								},
								complete: function () {
									reqExec2 = false;
								}

							});
						}
					}

					// Monitor DB for incoming messages
					var eventExec2 = false;
					startEvent2 = function () {
						if (eventExec2 == false) {
							eventExec2 = true;
							$.ajax({
								type: "GET",
								url: 'getEvents.do',
								dataType: "json",
								error: function (xhr, stat, err) {
								},
								success: function (data) {
									console.log("successful event fetch");
									console.log(JSON.stringify(data));
									$.each(data, function (idx2) {
										json2 = JSON.parse(data[idx2]);
										keyz2 = JSON.parse(json2[0]);
										valz2 = JSON.parse(json2[1]);

										var containsID2 = $('#evt2Row' + valz2.values[0]);

										if (containsID2.length == 0) {
											accrdEmp = '#eventAccordionEmp' + valz.values[0]
											$(accrdEmp).append("<h3 id=\"evt2Row" + valz2.values[0] + "\">" + keyz2.keys[0] + ":  " + valz2.values[0] + "</h3>");
											evtTable = "<table><tr>";
											//										console.log("#mailRow" + valz.values[0]).text();
											for (i = 0; i < keyz2.keys.length; i++) {
												evtTable += "<th>" + keyz2.keys[i] + "</th>";
											}
											evtTable += "</tr><br /><hr><tr>";
											for (i = 0; i < valz2.values.length; i++) {
												evtTable += "<td>" + valz2.values[i] + "</td>";
											}
											evtTable += "</tr></table>";
											$(accrdEmp).append("<div>" + evtTable + "</div>");
											buildAccordion();
										}
									});
								},
								complete: function () {
									eventExec2 = false;
								}
							});
						}
					}
				}
//				setInterval(startRequest2, 10000);
//				setInterval(startEvent2, 10000);
			});
		
		}
	});
	});
}