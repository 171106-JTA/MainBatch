package com.revature.trms.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.trms.dao.EmployeeDAO;
import com.revature.trms.model.Employee;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		EmployeeDAO empData = new EmployeeDAO();
		Employee emp = empData.selectEmployeeByUsername( request.getParameter("username"));
		System.out.println(emp.getUserId());
		if(session.isNew()){
			out.println("SESSION CREATED FOR " + request.getParameter("username").toUpperCase());
			session.setAttribute("username", request.getParameter("username"));
			session.setAttribute("password", request.getParameter("password"));
			session.setAttribute("employeeid", emp.getUserId());
			session.setAttribute("visit", 0);	
		}
		else {
			out.println("no new session created");
		}
		
		 RequestDispatcher rd = request.getRequestDispatcher("user_dashboard.html");
		 rd.include(request, response);
	}

}
