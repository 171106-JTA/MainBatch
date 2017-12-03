package com.revature.service;

import java.io.IOException;
import java.io.InputStream;

import com.revature.dao.util.ConnectionUtil;

public class GetWidget {
	private static final String WIDGETPATH = "./resource/html/widget/";
	
	/**
	 * Acquire path to appropriate widget based on session role/status
	 * @param role - user status 
	 * @param widget - name of widget
	 * @return path to widget else null
	 */
	public static String getWidgetPath(String role, String widget) {
		switch (role.toUpperCase()) { 
			case "EMPLOYEE":
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
				buffer.append(new String(data), 0, read);
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
			case "dashboard":
				return WIDGETPATH + "dashboard.widget.html";
			case "account":
				return WIDGETPATH + "account.employee.widget.html";
			default:
				return null;
		}
	}
	
	
	private static String getBenefitCoordinatorPath(String widget) {
		switch (widget.toLowerCase()) {
			case "dashboard":
				return WIDGETPATH + "dashboard.widget.html";
			case "account":
				return WIDGETPATH + "account.admin.widget.html";
			default:
				return null;
		}
	}
	
	private static String getHeadSupervisorPath(String widget) {
		switch (widget.toLowerCase()) {
			case "dashboard":
				return WIDGETPATH + "dashboard.widget.html";
			case "account":
				return WIDGETPATH + "account.widget.html";
			default:
				return null;
		}
	}
	
	private static String getSupervisorPath(String widget) {
		switch (widget.toLowerCase()) {
			case "dashboard":
				return WIDGETPATH + "dashboard.widget.html";
			case "account":
				return WIDGETPATH + "account.widget.html";
			default:
				return null;
		}
	}
}
