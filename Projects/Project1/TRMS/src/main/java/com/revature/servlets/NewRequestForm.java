package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Employee;
import com.revature.dao.EmployeeDaoImpl;

/**
 * Servlet implementation class NewRequest
 */
public class NewRequestForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	String email = request.getParameter("email");
	int empId = Integer.parseInt(request.getParameter("empId"));
	String password = request.getParameter("password");
	System.out.println(email);
	System.out.println(empId);
	System.out.println(password);
		
	PrintWriter pw = response.getWriter();
	
	EmployeeDaoImpl dao = new EmployeeDaoImpl();
	Employee emp = new Employee();
	
	try {
		emp = dao.getEmployee(empId);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	int status = 0;
	
	switch (emp.getTitle_id()) {	
		case(0):
			status = 4;
			break;
		case(1):
			status = 2;
			break;
		case(2):
			status = 0;
			break;
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
	
	pw.write("<p>Please enter the details of your request below:</p>");
	
	
	pw.write("<form action=\"NewRequest\" method=\"post\" id=\"loginForm\">\r\n" + 
			"	<table>\r\n" + 
			"		<tr>\r\n" + 
			"			<td>Employee ID</td>\r\n" + 
			"			<td><input type=\"number\" name=\"empId\" id=\"empId\" value=\"" + empId + "\"/></td>\r\n" + 
			"		</tr><tr>\r\n" + 
			"			<td>Cost</td>\r\n" + 
			"			<td><input type=\"number\" name=\"cost\" id=\"cost\" placeholder=\"$0.00\" min=\"1\" max=\"1000\"/></td>\r\n" + 
			"		</tr><tr>\r\n" + 
			"			<td>Date</td>\r\n" + 
			"			<td><input type=\"date\" name=\"date\" id=\"date\" min\"2017-12-19\"/></td>\r\n" + 
			"		</tr><tr>\r\n" + 
			"			<td>Street Address</td>\r\n" + 
			"			<td><input type=\"text\" name=\"addr\" id=\"addr\" /></td>\r\n" + 
			"		</tr><tr>\r\n" + 
			"			<td>City</td>\r\n" + 
			"			<td><input type=\"text\" name=\"city\" id=\"city\" /></td>\r\n" + 
			"		</tr><tr>\r\n" + 
			"			<td>State</td>\r\n" + 
			"			<td><input type=\"text\" name=\"state\" id=\"state\" /></td>\r\n" + 
			"		</tr><tr>\r\n" + 
			"		<td>Zip</td>\r\n" + 
			"			<td><input type=\"number\" name=\"zip\" id=\"zip\" /></td>\r\n" + 
			"		</tr><tr>\r\n" + 
			"		<td>Description</td>\r\n" + 
			"			<td><input type=\"text\" name=\"description\" id=\"description\" /></td>\r\n" + 
			"		</tr><tr>\r\n" + 
			"		<td>Event Type</td>\r\n" + 
			"			<td><select name=\"event\">" + 
			"               <option value=\"university\">University Course</option>" +
			"               <option value=\"seminar\">Seminar</option>" +
			"               <option value=\"certificationPrep\">Certification Preparation</option>" +
			"               <option value=\"certification\">Certification</option>" +
			"               <option value=\"technical\">Technical Training</option>" +
			"               <option value=\"other\">Other</option>" +
			"		</tr><tr>\r\n" + 
			"		<td>Grading Format</td>\r\n" + 
			"			<td><select name=\"grading\">" + 
			"	               <option value=\"percentage\">Percentage</option>" +
			"   	           <option value=\"passFail\">Pass Fail</option>" +
			"		</tr><tr>\r\n" + 
			"		<td>Days Missed</td>\r\n" + 
			"			<td><input type=\"number\" name=\"days\" id=\"days\" /></td>\r\n" + 
			"		</tr><tr>\r\n" + 
			"		<td>Justification</td>\r\n" + 
			"			<td><input type=\"text\" name=\"justification\" id=\"justificiation\" size=\"20\"/></td>\r\n" + 
			"		</tr><tr>\r\n" + 
			"		<td></td><td>" +
			"               <input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">" + 
			"			    <input type=\"hidden\" name=\"password\" value=\"" + password + "\">" + 
			"			    <input type=\"hidden\" name=\"email\" value=\"" + email + "\">" +
			"			    <input type=\"hidden\" name=\"status\" value=\"" + status + "\">" +
			"               <input type=\"submit\" value=\"Submit\" name=\"action\"/></td>\r\n" + 
			"		</tr>\r\n" + 
			"	</table>\r\n" + 
			"</form>");
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
