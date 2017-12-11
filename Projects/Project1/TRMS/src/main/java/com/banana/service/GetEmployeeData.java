package com.banana.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.banana.bean.ReimburseRequest;
import com.banana.dao.EmployeeDAOImpl;
import com.banana.dao.ReimbursementDAOImpl;
import com.revature.log.Logging;

public class GetEmployeeData {
	
	/**
	 * Gets the Employee data and writes the data to XML
	 * AJAX is used to request this data and parse it for display
	 * 
	 * @param response object that is sent back to the client
	 * @param empId id of employee
	 * 
	 * @throws IOException
	 */
	public static void getEmployeeData(HttpServletResponse response, int empId) throws IOException{
		
		ReimbursementDAOImpl rdao = new ReimbursementDAOImpl();
		List<ReimburseRequest> rrList = rdao.getEmployeeRequests(empId);
		String responseXML = null;
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		if(rrList != null) {
			responseXML = "<root>";
			
			for(ReimburseRequest r: rrList) {
				responseXML += "<emp><id>" + r.getRrID() + "</id><date>" + r.getDate() + "</date><event>" +
						r.getEventName() + "</event><projected-reimburse>" + r.getCost() + "</projected-reimburse><status>" + r.getStatus() + "</status></emp>";
			}
			
			responseXML += "</root>";
			
			out.println(responseXML);
			Logging.startLogging("Reimbursement requests of " + empId + " had been requested");
		}
		else {
			Logging.startLogging("No requests for " + empId);
			out.println("<root></root>");
		}
		
	}
	
	/**
	 * Gets all the reimbursement request data and sends it via AJAX
	 * 
	 * @param response object contains the XML results
	 * @throws IOException
	 */
	public static void getAllData(HttpServletResponse response) throws IOException{
		
		ReimbursementDAOImpl rdao = new ReimbursementDAOImpl();
		List<ReimburseRequest> rrList = rdao.getAllRequests();
		String responseXML = null;
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		if(rrList != null) {
			responseXML = "<root>";
			
			for(ReimburseRequest r: rrList) {
				responseXML += "<emp><rrid>" + r.getRrID() + "</rrid><eid>" + r.getEmpID() + "</eid><fname>" +
						r.getFname()+ "</fname><lname>" + r.getLname() + "</lname><place>" + r.getLocation() + "</place><eventname>" + 
						r.getEventName() + "</eventname><date>" + r.getDate() + "</date><cost>" + r.getCost() + "</cost><justify>" +
						r.getJustification() + "</justify><descript>" + r.getDescription() + "</descript><dsa>" + r.getDsApproval() + "</dsa><dha>" +
						r.getDhApproval() + "</dha><bca>" + r.getBcApproval() +"</bca></emp>";
			}
			
			responseXML += "</root>";
			
			out.println(responseXML);
		}
		else {
			Logging.startLogging("No requests found");
			out.println("<root></root>");
		}
		
	}
}
