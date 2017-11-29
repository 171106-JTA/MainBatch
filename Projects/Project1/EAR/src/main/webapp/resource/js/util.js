/**
 * @author Antony Lulciuc
 * @description Common functionality/features used throughout the application
 */

// Utility instance 
var Util = {};

///
//	INITIALIZERS 
///

/**
 * Used to initialize screen 
 * @returns
 */
function init() {
	initUtil();
	initEventHandlers();
	initInfoIcons();
}

/**
 * Creates Utility object
 */
var initUtil = function () {
	Util = {
			/**
			 * @description Acquires parent or node specified in params
			 * @param {Object} params - Contains child node we wish to acquire parent of
			 * @property {JQuery} params.node - node to acquire parent of (required)
			 * @property {StringTuple[]} params.attributes - attributes associated with parent (optional)
			 * @property {String[]} params.properties - properties associated with parent (optional)
			 * @property {String} params.parentTagName - parent tag name  (required)
			 * @returns JQuery instance of parent node if found else null  
			 */
			getParent: function (params) {
				var node = params.node;
				var parentTagName = params.parentTagName;
				var found = false;
				var args;
				
				// if parent and tage name supplied 
				if (node != null && parentTagName != null) {
					// Make copy 
					args = $.extend(true, {}, params);
					
					// Cache parent
					args.parent = node.parent();
					
					// Go though each parent node to find it
					while (args.parent != null && !(found = checkParentAgainstParams(args)))
						args.parent = args.parent.parent();
				}
		
				// return parent node if found 
				return found ? args.parent : null;
			}
	};
}

/**
 * Assigns event handlers to appropriate elements in page
 */
var initEventHandlers = function () {
	// Assign on clear form event handler 
	$("button[class~='clear']").on("click", onClearForm);
	
	// Assign on submit form to check required input boxes
	$("button[class~='submit']").on("click", onSubmitForm);
	
}

/**
 * Generates information icons for all span tags with attribute info-icon
 */
var initInfoIcons = function () {
	var spans = $("span[info-icon]");
	var options;
	var title;
	var rect

	// Add fa-info-circle
	spans.addClass("fa fa-info-circle");
	
	$.each(spans.toArray(), function (i, span) {
		title = $(span).attr("info-icon");
		rect = span.getBoundingClientRect();
		
		// Set options arguments for creating tooltip
		options = { 
			'title': title,
			'placement': rect.top < 50 ? 'bottom' : 'top',
			'trigger': 'click'
		};

		// create tooltip
		$(span).tooltip(options);
	});
}

///
//	EVENT HANDLERS 
///

var onSubmitForm = function (e) {
	var form = Util.getParent({ "node": $(this), "parentTagName": "form" });
	var isOk = true;
	
	// If form found
	if (form != null) {
		var required = form.find("input[required]");
		
		// Go through each required node ad determine if has data 
		$.each(required.toArray(), function (i, node){
			if (node.val() == null || node.val().length() === 0) {
				// Set failure flag
				isOk = false;
				
				// Add notification to element that field required 
				addRequiredIndicator(node);
			}
		});
	}
	
	// If we are missing data then prevent request from happening 
	if (!isOk)
		e.preventDefault();
}

/**
 * Removes all data from form
 */
var onClearForm = function (e) {
	var form = Util.getParent({ "node": $(this), "parentTagName": "form" });
	var inputs = form.find("input");
	
	// Clear all input from form
	inputs.val("");
	
	// Prevent required icon message display
	e.preventDefault();
}

///
//	HELPER FUNCTIONS
///

/**
 * Appends required field indicator to label of input element
 * @param {JQuery} node - handle to required input element
 */
var addRequiredIndicator = function (node) {
	var parent = node.parent();
	var label = parent.find("label");
	var indicator = "<b><font style='color:red;'>*</font></b>";
	
	// Append required symbol if label found and does not already exist 
	if (label != null && label.search(indicator) < 0)
		label.text(indicator + label.text());
}


var checkParentAgainstParams = function (params) {
	var parent = params.parent;
	var name = params.parentTagName;
	var attributes = params.attributes;
	var properties = params.properties;
	var found = false;
	
	// if parent not null
	if (parent != null && name != null) {
		// check tag name
		found = parent.prop("tagName").toLowerCase() === name.toLowerCase();
		
		// check attributes 
		if (found && attributes != null) {
			// Go through each attribute and locate value
			$.each(attributes, function (i, attr){
				if (parent.attr(attr.key) == null)
					return found = false;
				if (parent.attr(attr.key).search(attr.value) < 0)
					return found = false;
			});
		}
		
		// check properties
		if (found && properties != null){
			// Go through each property and determine if has
			$.each(properties, function (i, prop) {
				// if not found then failed 
				if (parent.prop(prop) == null)
					return found = false;
			});
		}
	}
	
	return found;
}







///
//	WINDOW
///

window.onload = init;














///
//	DEFINITIONS
///


/**
 * @typedef StringTuple
 * @description Key value pair such that key and pair of type string
 * @property {String} this.key - key
 * @property {String} this.value - key value
 */














