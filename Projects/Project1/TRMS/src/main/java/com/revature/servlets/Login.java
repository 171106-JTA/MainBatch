package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.auth.AuthorizeEmployee;
import com.revature.beans.Employee;
import com.revature.dao.EmployeeDaoImpl;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		AuthorizeEmployee auth = new AuthorizeEmployee();
		int empId = auth.login(request);
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		System.out.println(empId);
		System.out.println(password);
		
		EmployeeDaoImpl dao = new EmployeeDaoImpl();
		Employee emp = new Employee();
		
		PrintWriter pw = response.getWriter();
		
		if(empId != -1) {
			try {
				emp = dao.getEmployee(empId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			pw.write("<header>test</header>");
			pw.write(" <script type=\"text/javascript\" src=\"JavaScript/header.js\"></script>");
			pw.write("<h2>Home</h2>");
			
			pw.write("<hr>");
			pw.write("<h4>Welcome " + emp.getFname() + " " + emp.getLname() + "<h4>");
			
			if(emp.getTitle().equals("BENEFIT COORDINATOR")){
				pw.write("<hr><h3>Benefits Cooridinator</h3><hr>");
				
			} else if (emp.getTitle().equals("DEPARTMENT HEAD")){
				pw.write("<hr><h3>Department Head</h3><hr>");
				pw.write("<h5>Available Reimbursement: $" + emp.getFunds() + "</h5>");
			} else if (emp.getTitle().equals("MANAGER")){
				pw.write("<hr><h3>Manager</h3><hr>");
				pw.write("<h5>Available Reimbursement: $" + emp.getFunds() + "</h5>");
			} else {
				pw.write("<hr><h3>Employee</h3><hr>");
				pw.write("<h5>Available Reimbursement: $" + emp.getFunds() + "</h5>");
			}
			
			if(!emp.getTitle().equals("BENEFIT COORDINATOR")){
				pw.write("<p>Create a New Tuition Reimbursement Request: </p>");
				pw.write("<form action=\"NewRequestForm\" method=\"get\">\r\n" +
						"   <input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">" +
						"   <input type=\"hidden\" name=\"password\" value=\"" + password + "\">" +
						"   <input type=\"hidden\" name=\"email\" value=\"" + email + "\">" +
						"	<input type=\"submit\" value=\"Create Request\"> " +
						"</form>");
			}
			pw.write("<hr>");
			
			//Your Tuition Reimbursement Requests:
			pw.write("<p>View Pending Tuition Reimbursement Requests: </p>");
			pw.write("<form action=\"ViewYourRequests\" method=\"get\">\r\n" +
					"   <input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">" +
					"   <input type=\"hidden\" name=\"password\" value=\"" + password + "\">" +
					"   <input type=\"hidden\" name=\"email\" value=\"" + email + "\">" +
					"	<input type=\"submit\" value=\"View Requests\"> " +
					"</form>");
			pw.write("<hr>");
		
		} else {
			pw.write("{\"alert\": \"Failed to login.\"}");
			response.setStatus(302);
			response.setHeader("Location", "index.html");
		}
	}
}
