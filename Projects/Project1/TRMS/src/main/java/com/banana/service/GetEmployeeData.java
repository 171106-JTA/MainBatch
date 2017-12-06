package com.banana.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.banana.bean.ReimburseRequest;
import com.banana.dao.SystemDAOImpl;

public class GetEmployeeData {
	
	public static void getEmployeeData(HttpServletResponse response, int empId) throws IOException{
		SystemDAOImpl dao = new SystemDAOImpl();
		List<ReimburseRequest> rrList = dao.getEmployeeRequests(empId);
		String responseXML = null;
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		if(rrList != null) {
			responseXML = "<root>";
			
			for(ReimburseRequest r: rrList) {
				responseXML += "<emp><id>" + r.getRrID() + "</id><date>" + r.getDate() + "</date><event>" +
						r.getEventName() + "</event><cost>" + r.getCost() + "</cost><projected-reimburse>" + 5 + "</projected-reimburse></emp>";
			}
			
			responseXML += "</root>";
			
			out.println(responseXML);
		}
		else {
			out.println("<root></root>");
		}
		
	}
}
