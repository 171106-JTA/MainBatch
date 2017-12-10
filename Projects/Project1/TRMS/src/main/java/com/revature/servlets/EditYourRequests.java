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
 * Servlet implementation class EditYourRequests
 */
public class EditYourRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		int empId = Integer.parseInt(request.getParameter("empId"));
		String password = request.getParameter("password");
		String selection = request.getParameter("selection");
		System.out.println(email);
		System.out.println(empId);
		System.out.println(password);
		System.out.println(selection);
		
		int reqId = Integer.parseInt(request.getParameter("reqId"));
		System.out.println(reqId);
		
		PrintWriter pw = response.getWriter();
		
		RequestDaoImpl reqDao = new RequestDaoImpl(); 
		Request req = null;
		EmployeeDaoImpl empDao = new EmployeeDaoImpl(); 
		Employee emp = null;
		
		try {
			req = reqDao.getRequest(reqId);
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
		
		switch (selection) {
		case("Approve"):
			System.out.println("\n\n\nTitle: " + emp.getTitle());
			pw.write("<h5>Request Approved.</h5>");
			if (emp.getTitle().equals("BENEFIT COORDINATOR")) {
				req.setStatus(6); // 6 -> approve-pending
			} else if (emp.getTitle().equals("DEPARTMENT HEAD")) {
				req.setStatus(4); //submit to BenCo
			} else if (emp.getTitle().equals("MANAGER")) {
				req.setStatus(2); //submit to Department Head
			}
			break;
		case("Deny"):
			pw.write("<h5>Request Denied</h5>");
			req.setStatus(7); // 7 -> deny
			break;
		case("Inquiry"):
			pw.write("<h4>Inquiry - Edit Justification</h4>");
			pw.write("<p><b>Current Justification: </b></p>");
			pw.write("<p> " + req.getJustification() + "</p>");
			pw.write("<h4>Request Clarification:</h4>");
			pw.write("<form action=\"EditJustification\" method=\"post\">\r\n"
					+ " <p>Revise Compensation Amount</p>"
					+ " <input type=\"number\" name=\"cost\" id=\"cost\" placeholder=\"$" + req.getCost() + "\"/>"
					+ "<hr>"
					+ " <select name=\"questionTo\">\r\n"
					+ "		<option value=\"Employee\">Employee</option>\r\n" 
					+ "		<option value=\"Supervisor\">Supervisor</option>\r\n" 
					+ "		<option value=\"Department Head\">Department Head</option>\r\n" 
					+ "		<option value=\"Benefits Coordinator\">Benefits Coordinator</option>\r\n" 
					+ "	</select>"
					+ "	<input type=\"text\" name=\"justification\" "
					+ "                      id=\"justification\" "
					+ "                      value=\"" + req.getJustification() + "\"/>\r\n"  
					+ " <input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
					+ " <input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
					+ " <input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
					+ " <input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
					+ " <input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
					+ "	<input type=\"submit\" value=\"Submit\" name=\"action\"/>\r\n" + 
					"</form>");
			pw.write("<hr>");
			break;
		case("Reply"):
			pw.write("<h4>Reply - Edit Justification</h4>");
			pw.write("<p><b>Current Justification: </b></p>");
			pw.write("<p> " + req.getJustification() + "</p>");
			pw.write("<form action=\"EditYourRequests\" method=\"post\">\r\n"
					+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
					+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
					+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
					+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
					+ "	   		<input type=\"submit\" name=\"selection\" value=\"Approve\">"
					+ "</form>");
			pw.write("<form action=\"EditYourRequests\" method=\"post\">\r\n"
					+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
					+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
					+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
					+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
					+ "	   		<input type=\"submit\" name=\"selection\" value=\"Deny\">"
					+ "</form>");
			pw.write("<h4>Request Clarification:</h4>");
			pw.write("<form action=\"EditJustification\" method=\"post\">\r\n"
					+ " <p>Revise Compensation Amount</p>"
					+ " <input type=\"number\" name=\"cost\" id=\"cost\" placeholder=\"$" + req.getCost() + "\"/>"
					+ "<hr>"
					+ " <select name=\"questionTo\">\r\n"
					+ "		<option value=\"Employee\">Employee</option>\r\n" 
					+ "		<option value=\"Supervisor\">Supervisor</option>\r\n" 
					+ "		<option value=\"Department Head\">Department Head</option>\r\n" 
					+ "		<option value=\"Benefits Coordinator\">Benefits Coordinator</option>\r\n" 
					+ "	</select>"
					+ "	<input type=\"text\" name=\"justification\" "
					+ "                      id=\"justification\" "
					+ "                      value=\"" + req.getJustification() + "\"/>\r\n"  
					+ " <input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
					+ " <input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
					+ " <input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
					+ " <input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
					+ " <input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
					+ "	<input type=\"submit\" value=\"Submit\" name=\"action\"/>\r\n" + 
					"</form>");
			pw.write("<hr>");
			break;
		}
		
		try {
			System.out.println("\n\n\n" + req.getStatus());
			reqDao.modifyRequestStatus(reqId, req.getStatus());
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
