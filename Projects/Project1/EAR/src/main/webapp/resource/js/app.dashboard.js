"use strict";

/**
 *  @description Global reference to documented code
 */
var data = {
		user: {},
		widget: {},
		update: function () {
			$("#username").text(" " + this.user.username);
		}
};

/**
 *  @description Call method on window init to set drawer shown on start
 *  @param {JSONArray} json - [in] content used to construct navigation list
 */
var initializeDashboard = function () {
  // Ensure we have a dashboard
  if ($("#dashboard").length > 0) {
	  initUser();
	  initContent();
  }
}

///
//	INITIALIZERS
///

var initUser = function () {
	Util.send({ 'transtype': "GETUSERINFO" }, function (response){
		if (response != null) {
			// Cache user information 
			data.user = response;
			data.update();
		}
	}, function (error) {
		window.open(window.location.href.substr(0, window.location.href.lastIndexOf("EAR/") + 4), "_self");
	});
}

var initContent = function (){ 
	var dashboard = $("#dashboard");
	  
	// Ensure handle to node was acquired
	if (dashboard.length > 0) {
	    
	    // For screen adjustment
	    $(window).resize(onDashboardResize);
	    onDashboardResize();
	    onDashboardClick();
	}
}

///
//	EVENT HANDLERS
///

var onDashboardResize = function () {
	var dsContainer = $("#dynamicSearchContainer");
	var dashboard = $("#dashboard");
	var height = $(window).height();
	var padding = 0;
	var rect;
	
	// Resize dashboard
	if (dashboard.length > 0) {
		rect = dashboard[0].getBoundingClientRect();
		dashboard.css("height", (height - (rect.top + padding)) + "px");
	}
	
	// Resize left drawer dynamic content area
	if (dsContainer.length > 0) {
		rect = dsContainer[0].getBoundingClientRect();
		dsContainer.css("height", (height -  (rect.top + padding)) + "px");
	}
}

/**
 * Load dashboard widget when activated 
 */
var onDashboardClick = function () {
	var widget = data.widget;
	
	// Build widget if widget exists else send request for it
	if (widget.dashboard) {
		assignWidget(widget.dashboard = parseWidgetData(widget.dashboard.response));	
		widget.dashboard.execute("drawer");
		widget.dashboard.execute("screen");
	}else {
		var options;
		
		// Build request options 
		options = {
			"transtype": "GETWIDGET",
			"widget": "dashboard" 
		}
		
		// send request
		Util.send(options, function (response) {
			// ensure non-null response
			if (response != null) {
				assignWidget(widget.dashboard = parseWidgetData(response));
				widget.dashboard.execute("drawer");
				widget.dashboard.execute("screen");
			}
		}, function (error){
			console.log(error);
		});
	}
}

/**
 * Used to log user out of account
 * Update backspace history to prevent reaccess of account
 */
var onSignOut = function () {
	Util.send({ "transtype": "SIGNOUT" }, function (response){
		window.open(response, "_self");
	});
}

/**
 * When user wishes to view account information 
 */
var onAccountClick = function () {
	var widget = data.widget;
	
	// Build widget if widget exists else send request for it
	if (widget.account){
		assignWidget(widget.account = parseWidgetData(widget.account.response));	
		widget.account.execute("drawer");
		widget.account.execute("screen");
	}else {
		var options;
		
		// Build request options 
		options = {
			"transtype": "GETWIDGET",
			"widget": "account" 
		}
		
		// send request
		Util.send(options, function (response) {
			// ensure non-null response
			if (response != null) {
				assignWidget(widget.account = parseWidgetData(response));	
				widget.account.execute("drawer");
				widget.account.execute("screen");
			}
		}, function (error){
			console.log(error);
		});
	}
}

///
//	HELPER METHODS 
///

/**
 * @description Generates dynamic content based on json received from the server
 * @param {Json} json - response from server  
 */
var buildNavigationFromJson = function (json) {
	
}

var parseWidgetData = function (response) {
	var parser = new DOMParser();
	var html = $(parser.parseFromString(response,"text/html"));
	var widget;
	
	// Extract widget data from response 
	widget = {
			response: response,
			script: html.find("script[type='text/javascript']")[0].innerHTML,
			execute: function () { /* do nothing */ },
			drawer: html.find("div[is-drawer]"),
			hideDrawer: html.find("div[hide-drawer]").length > 0,
			screen: html.find("div[is-screen]"),
			hideScreen:  html.find("div[hide-screen]") > 0
	};
	
	return widget;
}

var assignWidget = function (widget) {
	var main = $("#main");
	var drawer = $("#drawer");
	var screen = $("#screen");
	var pane = $("#drawer-pane");
	var execute;
	
	// do we show the drawer?
	if (widget.hideDrawer) {
		pane.html("");
		drawer.hide();
		main.removeClass("col-sm-10");
		main.addClass("col-sm-12");
	} else {
		pane.html(widget.drawer);
		main.removeClass("col-sm-12");
		main.addClass("col-sm-10");
		drawer.show();
	} 
		
	// Do we show the screen?
	if (widget.hideScreen) {
		screen.html("");
		main.hide();
	} else {
		screen.html(widget.screen);
		main.show();
	} 
	
	// Eval scripts 
	if (widget.script)  {
		widget.execute = eval(widget.script);
	}
}

