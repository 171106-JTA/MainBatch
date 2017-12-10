package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Employee;
import com.revature.dao.TRMSDao;
import com.revature.service.GetProjectedCostService;

/**
 * Servlet implementation class GetProjectedCost
 */
@MultipartConfig
public class GetProjectedCost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GetProjectedResult");
		String eventName = (String) request.getParameter("eventName");
		System.out.println("cost: " + request.getParameter("eventCost"));
		double cost = Double.parseDouble(request.getParameter("eventCost"));
		
		TRMSDao dao = TRMSDao.getDao();
		HttpSession session = request.getSession();
		Employee emp = dao.getEmployeeByUsername((String)session.getAttribute("username"));
		
		double projectedCost = GetProjectedCostService.getProjectedCost(cost, eventName) - emp.getMoneyReimbursed();
		if (projectedCost < 0)
			projectedCost = 0;
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String myXml = "<root><cost>" + projectedCost + "</cost></root>";
		out.println(myXml);		
	}
}