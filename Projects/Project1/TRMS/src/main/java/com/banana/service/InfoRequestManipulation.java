package com.banana.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.banana.bean.InfoRequest;
import com.banana.dao.EmployeeDAOImpl;
import com.banana.dao.UpdateDAOImpl;
import com.revature.log.Logging;

public class InfoRequestManipulation {
	public static void insert(int rrId) {
		UpdateDAOImpl udao = new UpdateDAOImpl();
		EmployeeDAOImpl dao = new EmployeeDAOImpl();
		int empId = dao.getEmployeeIdFromRequest(rrId);
		udao.insertInfoRequest(rrId, empId);
	}
	
	public static void update(int irId, Part part, String addedinfo) {
		UpdateDAOImpl udao = new UpdateDAOImpl();
		udao.updateInfoRequest(irId, addedinfo, part);
		Logging.startLogging("Information Request: " + irId + " has been updated");
	}
	
	public static void getInfoRequests(HttpServletResponse response, int requesteeId) throws IOException{
		
		EmployeeDAOImpl dao = new EmployeeDAOImpl();
		List<InfoRequest> irList = dao.getInfoRequests(requesteeId);
		String responseXML = null;
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		if(irList != null) {
			responseXML = "<root>";
			
			for(InfoRequest ir: irList) {
				responseXML += "<req><ird>" + ir.getIrId() + "</ird><rrid>" + ir.getRrId()+ "</rrid></req>";
			}
			
			responseXML += "</root>";
			
			out.println(responseXML);
			Logging.startLogging("Info requests for " + requesteeId + " has been requested");
		}
		else {
			Logging.startLogging("No info requests present for " + requesteeId);
			out.println("<root></root>");
		}
		
	}
	

	public static void getAllInfoRequests(HttpServletResponse response) throws IOException{
		
		EmployeeDAOImpl dao = new EmployeeDAOImpl();
		List<InfoRequest> irList = dao.getAllInfoRequests();
		String responseXML = null;
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		if(irList != null) {
			responseXML = "<root>";
			
			for(InfoRequest ir: irList) {
				responseXML += "<req><ird>" + ir.getIrId() + "</ird><rrid>" + ir.getRrId()+ "</rrid><requesteeid>" +
						ir.getRequesteeId() + "</requesteeid><info>" + ir.getInfo() + "</info><filename>" + ir.getFileName() +
						"</filename><blob>" + ir.getBlob() + "</blob></req>";
			}
			
			responseXML += "</root>";
			
			out.println(responseXML);
			Logging.startLogging("Info requests Requested");
		}
		else {
			Logging.startLogging("No info requests present");
			out.println("<root></root>");
		}
		
	}
}
