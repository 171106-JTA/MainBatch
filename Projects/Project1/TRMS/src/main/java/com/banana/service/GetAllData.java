package com.banana.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.banana.bean.ReimburseRequest;
import com.banana.dao.EmployeeDAOImpl;
import com.banana.dao.ReimbursementDAOImpl;

public class GetAllData {
	
	public static void getAllData(HttpServletResponse response) throws IOException{
		EmployeeDAOImpl empdao = new EmployeeDAOImpl();
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
			out.println("<root></root>");
		}
		
	}
}
