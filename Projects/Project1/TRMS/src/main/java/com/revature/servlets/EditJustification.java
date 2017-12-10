package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Employee;
import com.revature.beans.Request;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.dao.RequestDaoImpl;

/**
 * Servlet implementation class EditJustification
 */
public class EditJustification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		int empId = Integer.parseInt(request.getParameter("empId"));
		String password = request.getParameter("password");
		int reqId = Integer.parseInt(request.getParameter("reqId"));
		String justification = request.getParameter("justification");
		String questionTo = request.getParameter("questionTo");
		Double cost =Double.parseDouble(request.getParameter("cost"));
		System.out.println(email);
		System.out.println(empId);
		System.out.println(password);
		System.out.println(reqId);
		System.out.println(justification);
		System.out.println(questionTo);
		System.out.println(cost);
		
		PrintWriter pw = response.getWriter();
		
		RequestDaoImpl reqDao = new RequestDaoImpl(); 
		Request req = null;
		
		EmployeeDaoImpl empDao = new EmployeeDaoImpl();
		Employee emp = null;
		
		int status = 0;
		
		switch(questionTo) {
			case("Employee"):
				status = 8;
				break;
			case("Supervisor"):
				status = 1;
				break;
			case("Department Head"):
				status = 3;
				break;
			case("Benefits Coordinator"):
				status = 5;
				break;
		}
		
		try {
			req = reqDao.getRequest(reqId);
			req.setJustification(justification);
			req.setStatus(status);
			req.setCost(cost);
			reqDao.modifyRequestJustification(reqId, req);
			emp = empDao.getEmployee(empId);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		pw.write("<header>test</header>");
		pw.write(" <script type=\"text/javascript\" src=\"JavaScript/header.js\"></script>");
		pw.write("<form action=\"Login\" method=\"POST\">\r\n" +
				"<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">" +
				"<input type=\"hidden\" name=\"password\" value=\"" + password + "\">" +
				"<input type=\"hidden\" name=\"email\" value=\"" + email + "\">" +
				"<input type=\"submit\" value=\"Return to Home\"> " +
			"</form>");
		pw.write("<hr>");
	
		pw.write("<h3>Request ID: " + req.getId() + "</h3>");
		pw.write("<hr>");
		
		pw.write("<h3>Justification Edited.</h3>");
		pw.write("Revised Justification: " + justification);
	}

}
