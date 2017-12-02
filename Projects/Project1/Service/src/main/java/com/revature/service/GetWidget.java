package com.revature.service;

import java.io.IOException;
import java.io.InputStream;

import com.revature.dao.util.ConnectionUtil;

public class GetWidget {
	
	/**
	 * Acquire path to appropriate widget based on session role/status
	 * @param role - user status 
	 * @param widget - name of widget
	 * @return path to widget else null
	 */
	public static String getWidgetPath(String role, String widget) {
		switch (role.toUpperCase()) { 
			case "EMPOYEE":
				return getEmployeeWidgetPath(widget);
			case "BENEFIT-COORDINATOR":
				return getBenefitCoordinatorPath(widget);
			case "HEAD-SUPERVISOR":
				return getHeadSupervisorPath(widget);
			case "SUPERVISOR":
				return getSupervisorPath(widget);
			default:
				return null;
		}
	}
	
	public static String getWidget(InputStream stream) {
		StringBuffer buffer = new StringBuffer();
		byte[] data = new byte[1024];
		int read = 0;
		
		try {
			// Read contents 
			while ((read = stream.read(data)) > 0) 
				buffer.append(String.valueOf(data), 0, read);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close stream 
			ConnectionUtil.close(stream);
		}
		
		return buffer.toString();
	}
	
	///
	//	PRIVATE HELPER METHODS 
	///
	
	private static String getEmployeeWidgetPath(String widget) {
		switch (widget.toLowerCase()) {
			
			default:
				return null;
		}
	}
	
	
	private static String getBenefitCoordinatorPath(String widget) {
switch (widget.toLowerCase()) {
			
			default:
				return null;
		}
	}
	
	private static String getHeadSupervisorPath(String widget) {
switch (widget.toLowerCase()) {
			
			default:
				return null;
		}
	}
	
	private static String getSupervisorPath(String widget) {
		switch (widget.toLowerCase()) {
			
			default:
				return null;
		}
	}
	
	
	private static String dashboard = "<body><div is-drawer><div class=\"row\"><div class=\"col-xs-12\"><label>Search: <span info-icon=\"Message Title\"></span></label><input id=\"titleSearch\" type=\"text\" class=\"form-control\" aria-describedby=\"searchBtn\" /></div></div><!-- REQUEST TYPE FILTERING --><div class=\"row\"><div class=\"col-xs-12\"><div><label>Filter By: <span info-icon=\"Reimbursement status\"></span></label><select id=\"optionFilter\" class=\"form-control\"><option>None</option><option>New Requests</option><option>Pending Requests</option><option>Awarded Requests</option><option>Denied Requests</option></select></div></div></div><div style=\"padding-bottom: 5px;\"></div><div class=\"row\"><div class=\"col-sm-12\"><button id=\"btnSearch\" class=\"form-control btn-primary\"><span class=\"fa fa-search\"></span> Search </button></div></div><!-- SPLIT DYNAMIC CONTENT --><div style=\"height: 5px; padding-top: 5px; padding-bottom: 5px;\"><hr class=\"hr-divider\" style=\"margin: 0px;\"></div><div id=\"dynamicSearchContainer\" class=\"row drawer-dynamic-list\"><div id=\"dynamicSearchItems\" class=\"col-sm-12\"><!-- DYNAMIC ITEMS GO HERE --></div></div></div><div is-content></div></body> ";
	
	private static String account = "<body><div is-drawer hide-drawer></div><div is-screen><style> .account-block { overflow-x: hidden; padding-top: 25px; } .info-table { width: 100%; border: 1px solid rgba(40,40,40,0.25); margin-bottom: 5px; } .info-table th { background-color: #0082ff; width: 125px; color: #eeeeee; } .info-table b { padding-left: 5px; </style><script type=\"text/javascript\"> execute = function (params) { var user = params.user; var info = params.info; $(\"#acctStatus\").text(user.role); $(\"#acctUserName\").text(user.username); $(\"#acctFirstName\").text(info.firstName); $(\"#acctLastName\").text(info.lastName); $(\"#acctState\").text(info.state); $(\"#acctCity\").text(info.city); $(\"#acctAddress\").text(info.address); $(\"#acctEmail\").text(info.email); $(\"#accPhoneNumber\").text(info.phoneNumber); } </script><!-- USED TO DISPLAY ACCOUNT INFORMATION --><div class=\"account-block\"><div class=\"input-max-width center-block\"><label style=\"font-size: 2.0em\">Account Information:</label><table class=\"info-table\"><tr><th>Status: </th><td><b><span id=\"acctStatus\"></span></b></td></tr><tr><th>Username: </th><td><b><span id=\"acctUserName\"></span></b></td></tr><tr><th>Password: </th><td><b>***************</b></td></tr><tr><th>First Name: </th><td><b><span id=\"acctFirstName\"></span></b></td></tr><tr><th>Last Name: </th><td><b><span id=\"acctLastName\"></span></b></td></tr><tr><th>State: </th><td><b><span id=\"acctState\"></span></b></td></tr><tr><th>City: </th><td><b><span id=\"acctCity\"></span></b></td></tr><tr><th>Address: </th><td><b><span id=\"acctAddress\"></span></b></td></tr><tr><th>Email: </th><td><b><span id=\"acctEmail\"></span></b></td></tr><tr><th>Phone number: </th><td><b><span id=\"accPhoneNumber\"></span></b></td></tr></table><button class=\"form-control\"><span class=\"fa fa-plus-circle\" disabled></span> Update</button></div></div></div></body>";
		
	private static String settings = "";

}
