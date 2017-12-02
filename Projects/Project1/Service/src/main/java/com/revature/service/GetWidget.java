package com.revature.service;


public class GetWidget {
	public static String getWidget(String widget) {
		switch (widget.toLowerCase()) {
			case "dashboard":
				return dashboard;
			case "account":
				return account;
			case "settings":
				return settings;
			default:
				return null;
		}
	}
	
	private static String dashboard = "<body><div is-drawer><div class=\"row\"><div class=\"col-xs-12\"><label>Search: <span info-icon=\"Message Title\"></span></label><input id=\"titleSearch\" type=\"text\" class=\"form-control\" aria-describedby=\"searchBtn\" /></div></div><!-- REQUEST TYPE FILTERING --><div class=\"row\"><div class=\"col-xs-12\"><div><label>Filter By: <span info-icon=\"Reimbursement status\"></span></label><select id=\"optionFilter\" class=\"form-control\"><option>None</option><option>New Requests</option><option>Pending Requests</option><option>Awarded Requests</option><option>Denied Requests</option></select></div></div></div><div style=\"padding-bottom: 5px;\"></div><div class=\"row\"><div class=\"col-sm-12\"><button id=\"btnSearch\" class=\"form-control btn-primary\"><span class=\"fa fa-search\"></span> Search </button></div></div><!-- SPLIT DYNAMIC CONTENT --><div style=\"height: 5px; padding-top: 5px; padding-bottom: 5px;\"><hr class=\"hr-divider\" style=\"margin: 0px;\"></div><div id=\"dynamicSearchContainer\" class=\"row drawer-dynamic-list\"><div id=\"dynamicSearchItems\" class=\"col-sm-12\"><!-- DYNAMIC ITEMS GO HERE --></div></div></div><div is-content></div></body> ";
	
	private static String account = "<body><div is-drawer hide-drawer></div><div is-screen><style> .account-block { overflow-x: hidden; padding-top: 25px; } </style><script type=\"text/javascript\"> function assembleUserData (params) { var user = params.user; var info = params.info; $(\"#acctStatus\").text(user.role); $(\"#acctUserName\").text(user.username); $(\"#acctFirstName\").text(info.firstname); $(\"#acctLastName\").text(info.lastname); $(\"#acctState\").text(info.state); $(\"#acctCity\").text(info.city); $(\"#acctAddress\").text(info.address); $(\"#acctEmail\").text(info.email); $(\"#accPhoneNumber\").text(info.phonenumber); } </script><!-- USED TO DISPLAY ACCOUNT INFORMATION --><div class=\"account-block\"><div class=\"input-max-width center-block\"><label style=\"font-size: 2.0em\">Account Information:</label><p>Status: <b></b><span id=\"acctStatus\"></span></b></p><p>Username: <b></b><span id=\"acctUserName\"></span></b></p><p>Password: <b></b>***************</b></p><p>First Name: <b></b><span id=\"acctFirstName\"></span></b></p><p>Last Name: <b></b><span id=\"acctLastName\"></span></b></p><p>State: <b></b><span id=\"acctState\"></span></b></p><p>City: <b></b><span id=\"acctCity\"></span></b></p><p>Address: <b></b><span id=\"acctAddress\"></span></b></p><p>Email: <b></b><span id=\"acctEmail\"></span></b></p><p>Phone number: <b></b><span id=\"accPhoneNumber\"></span></b></p><button class=\"form-control btn-primary\"><span class=\"fa fa-plus-circle\"></span> Update</button></div></div></div></body>";
	
	
	private static String settings = "";
	
	
	
	
}
