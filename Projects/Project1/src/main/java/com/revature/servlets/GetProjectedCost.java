package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.service.GetProjectedCostService;

/**
 * Servlet implementation class GetProjectedCost
 */
public class GetProjectedCost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GetProjectedResult");
		String eventName = (String) request.getParameter("eventName");
		double cost = Double.parseDouble(request.getParameter("cost"));
		
		double projectedCost = GetProjectedCostService.getProjectedCost(cost, eventName);
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String myXml = "<root><cost>" + projectedCost + "</cost></root>";
		out.println(myXml);		
	}
}