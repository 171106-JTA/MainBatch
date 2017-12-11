package com.project1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project1.classes.Employee;
import com.project1.dao.EmployeeDao;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("employeeID");
		String password = request.getParameter("password");
		EmployeeDao dao = new EmployeeDao();
		Employee currEmp = dao.getEmp(id);

		// Creating a Session
		HttpSession session = request.getSession();

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (!session.isNew()) {
			currEmp = (Employee) session.getAttribute("currEmp");
			id = currEmp.getEmployeeID();
			password = currEmp.getPassword();
		}
		QuickFunction.insertBoostrap(response);
		if (id.equals(currEmp.getEmployeeID()) && password.equals(currEmp.getPassword())) {
			QuickFunction.insertBoostrap(response);
			QuickFunction.insertNavBar(response);
			session.setAttribute("currEmp", currEmp);
			out.println("<h3>Welcome " + currEmp.getFname() + "!</h3>");
			System.out.println("The current account number is: " + currEmp.getEmpPos());
			if (currEmp.getEmpPos() == 3) {
				out.println("<h4>What would you like to do today?</h4>" + "<div class='well input'>"
						+ "<form action='BenReview'>" + "<input type='submit' value='View Requests'>" + "</form>"
						+ "</div>");
			} else if (currEmp.getEmpPos() != 0) {
				out.println("<h4>What would you like to do today?</h4>" + "<div class='well input'>"
						+ "<form action='DSReview'>" + "<input type='submit' value='View Requests'>" + "</form>"
						+ "</div>");
			} else if (currEmp.getEmpPos() == 0) {
				out.println("<h4>What would you like to do today?</h4>" + "<div class='well input'>"
						+ "<form action='CreateForm'>" + "<input type='submit' value='Create Form'>" + "</form>"
						+ "<form action='ViewCurrentRem'>" + "<input type='submit' value='View All Form'>" + "</form>"
						+ "<form action='ViewAlteredRem'>" + "<input type='submit' value='View Altered Rem'>" + "</form>"
						+ "</div>");
			}

			out.println("<div class = 'well input'>" + "<form action='Logout'>" + "<input type='submit' value='Logout'>"
					+ "</form>" + "</div>");

		} else {
			session.invalidate();
			out.println("<h3>Invalid ID/Password </h3>" + "<h4>Please check your ID and password and try again.</h4>"
					+ "</div>");
			out.println("<hr>" + "<a href='index.html'>BACK</a>");
		}
		out.println();
	}

}
