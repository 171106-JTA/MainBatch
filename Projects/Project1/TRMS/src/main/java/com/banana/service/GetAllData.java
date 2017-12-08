package com.banana.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.banana.bean.ReimburseRequest;
import com.banana.dao.SystemDAOImpl;

public class GetAllData {
	
	public static void getAllData(HttpServletResponse response) throws IOException{
		SystemDAOImpl dao = new SystemDAOImpl();
		List<ReimburseRequest> rrList = dao.getAllRequests();
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
			out.println("<root></root>");
		}
		
	}
}
