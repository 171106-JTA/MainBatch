package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Employee;
import com.revature.beans.Title;
import com.revature.dao.TRMSDao;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("LoginServlet");
		RequestDispatcher rd = null;
		HttpSession session = request.getSession();
		
		final String username = (String) request.getParameter("usernameLogin");
		final String password = (String) request.getParameter("passwordLogin");
		
		TRMSDao dao = TRMSDao.getDao();
		
		Employee emp = dao.validateLogin(username, password.hashCode());
		
		if (emp == null) {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h2 style='color:red'>Your username and password did not match any users on file.<h2>");
			return;
		}
		
		Title title = emp.getTitle();
		String fname = emp.getFname();
		String lname = emp.getLname();
		
		if (title == Title.BENCO || title == Title.BENCO_MANAGER ||
				title == Title.CEO || title == Title.DIRECT_SUPERVISOR || title == Title.DEPARTMENTHEAD) {
			rd = request.getRequestDispatcher("Admin.html");
			session.setAttribute("username", username);
			session.setAttribute("title", title.toString());
			session.setAttribute("fname", fname);
			session.setAttribute("lname", lname);
			rd.include(request, response);
		} else if (title == Title.EMPLOYEE) {
			rd = request.getRequestDispatcher("Employee.html");
			rd.include(request, response);
			session.setAttribute("username", username);
			session.setAttribute("title", title.toString());
			session.setAttribute("fname", fname);
			session.setAttribute("lname", lname);
		} else if (title == Title.UNVERIFIED) {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h2 style='color:red'>Your account has not yet been approved.<h2>");
		}
	}
}
