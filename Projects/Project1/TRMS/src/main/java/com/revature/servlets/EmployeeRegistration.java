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
 * Servlet implementation class TrainerRegistration
 */
public class EmployeeRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		EmployeeDaoImpl dao = new EmployeeDaoImpl();
		
		Employee emp = new Employee();

		emp.setFname(request.getParameter("firstname"));
		emp.setLname(request.getParameter("lastname"));
		emp.setAddress(request.getParameter("address"));
		emp.setCity(request.getParameter("city"));
		emp.setState(request.getParameter("state"));
		emp.setZip(request.getParameter("zip"));
		emp.setSuper_id(Integer.parseInt(request.getParameter("supervisor id")));
		
		System.out.println("Department: " + request.getParameter("department"));
		switch (request.getParameter("department")) {
			case ("Mechanical"):
				emp.setDepartment("Mechanical");
				emp.setDept_id(0);
				break;
			case ("Electrical"):
				emp.setDepartment("Electrical");
				emp.setDept_id(1);				
				break;
			case ("Civil"):
				emp.setDepartment("Civil");
				emp.setDept_id(2);
				break;
			case ("Human Resources"):
				emp.setDepartment("Human Resources");
				emp.setDept_id(3);
				break;
		}
			
		emp.setEmail(request.getParameter("email"));
		emp.setPassword(request.getParameter("password"));

		System.out.println("Title: " + request.getParameter("title"));
		switch (request.getParameter("title")) {
			case ("Department Head"):
				emp.setTitle("Department Head");
				emp.setTitle_id(0);
				break;
			case ("Manager"):
				emp.setTitle("Manager");
				emp.setTitle_id(1);		
				break;
			case ("Employee"):
				emp.setTitle("None");
				emp.setTitle_id(2);
				break;
			case ("Benefit Coordinator"):
				emp.setTitle("Benefit Coordinator");
				emp.setTitle_id(3);
				break;
		}

		emp.setFunds(1000.00);

		System.out.println(emp.toString());
		
		try {
			Employee supervisor = dao.getEmployee(emp.getSuper_id());
			emp.setSuperFirstname(supervisor.getFname());
			emp.setSuperFirstname(supervisor.getLname());

			dao.addEmployee(emp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter pw = response.getWriter();
		pw.write("<header style=\"display:none;\">test</header>");
		pw.write(" <script type=\"text/javascript\" src=\"JavaScript/header.js\"></script>");
		pw.write("<h4>New Employee added!</h4>");

		pw.write("<form action=\"Logout\" method=\"POST\">\r\n" +
					"<input type=\"submit\" value=\"Back to Login Page\"> " +
				"</form>");
		
	}

}
