package com.banana.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.banana.bean.ReimburseRequest;
import com.banana.dao.EmployeeDAOImpl;
import com.banana.dao.ReimbursementDAOImpl;

public class GetEmployeeData {
	
	public static void getEmployeeData(HttpServletResponse response, int empId) throws IOException{
		EmployeeDAOImpl empdao = new EmployeeDAOImpl();
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
		}
		else {
			System.out.println("Empty");
			out.println("<root></root>");
		}
		
	}
}
