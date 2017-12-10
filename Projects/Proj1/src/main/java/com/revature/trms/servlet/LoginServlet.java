package com.revature.trms.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.revature.trms.dao.EmployeeDAO;
import com.revature.trms.model.Employee;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(LoginServlet.class); 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		EmployeeDAO empData = new EmployeeDAO();
		Employee emp = empData.selectEmployeeByUsername(request.getParameter("username"));
		System.out.println(emp.getUserType());
		RequestDispatcher rd = null;
		// Save data to servlet so later calls in the application can remeber user and their data
		if (session.isNew()) {
			session.setAttribute("username", request.getParameter("username"));
			//session.setAttribute("password", request.getParameter("password"));
			session.setAttribute("employeeid", emp.getUserId());
			session.setAttribute("usertype", emp.getUserType());
			session.setAttribute("visit", 0);
			log.info("new session has been created by user: " + request.getParameter("username"));
		}

		rd = request.getRequestDispatcher("user_dashboard.html");
		rd.include(request, response);
	}

}
