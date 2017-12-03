"use strict";

/**
 *  @description Global reference to documented code
 */
var data = {
		widget: {}
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
	  
	  // default dashboard widget
	  onDashboardClick();
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
			$("#username").text(" " + data.user.username);
		}
	}, function (error) {
		var backlen = history.length - 1;  
		
		// Return at the beginning
		history.go(-backlen); 
		
		// reset URL
		history.replaceState({}, null, window.location.href.substr(0, window.location.href.lastIndexOf("EAR/") + 4));
	});
}

var initContent = function (){ 
	var dashboard = $("#dashboard");
	var screen = $("#screen");
	var drawer = $("#drawer-pane");
	var dsContainer = $("#dynamicSearchContainer");
	  
	// Ensure handle to node was acquired
	if (drawer.length > 0 && dashboard.length > 0 && 
			  screen.length > 0 && dsContainer.length > 0 ) {
	    
	    // For screen adjustment
	    $(window).resize(onDashboardResize);
	    onDashboardResize();
	}
}

///
//	EVENT HANDLERS
///

var onDashboardResize = function () {
	var dsContainer = $("#dynamicSearchContainer");
	var dashboard = $("#dashboard");
	var height = $(window).height();
	var rect;
	
	// Resize dashboard
	if (dashboard.length > 0) {
		rect = dashboard[0].getBoundingClientRect();
		dashboard.css("height", (height - rect.top) + "px");
	}
	
	// Resize left drawer dynamic content area
	if (dsContainer.length > 0) {
		rect = dsContainer[0].getBoundingClientRect();
		dsContainer.css("height", (height - rect.top) + "px");
	}
}

/**
 * Load dashboard widget when activated 
 */
var onDashboardClick = function () {
	var widget = data.widget;
	
	// Build widget if widget exists else send request for it
	if (widget.dashboard)
		assignWidget(widget.dashboard);
	else {
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
		var backlen = history.length - 1;  
		
		// Return to the beginning
		history.go(-backlen); 
		
		// reset URL
		history.replaceState({}, null, response);
	});
}

/**
 * When user wishes to view account information 
 */
var onAccountClick = function () {
	var widget = data.widget;
	
	// Build widget if widget exists else send request for it
	if (widget.account) {
		assignWidget(widget.account);
		widget.account.screenMethod({ "user": data.user });
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
				widget.account.screenMethod({ "user": data.user });
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
	var html = parser.parseFromString(response,"text/html");
	var widget;
	
	// Extract widget data from response 
	widget = {
			drawer: $(html).find("div[is-drawer]"),
			drawerMethod: function () {}, 
			hideDrawer: $(html).find("div[hide-drawer]").length > 0,
			screen: $(html).find("div[is-screen]"),
			screenMethod: function () {}, 
			hideScreen:  $(html).find("div[hide-screen]") > 0
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
	if (pane.find("script").length > 0)  {
		eval(pane.find("script")[0].innerHTML);
		widget.drawerMethod = execute;
	} 
	
	if (screen.find("script").length > 0) {
		eval(screen.find("script")[0].innerHTML);
		widget.screenMethod = execute;
	}
}






