package com.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trms.obj.PrivilegesSummary;
import com.trms.services.Services;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
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
		String loginUserId = request.getParameter("username"); 
		String loginPassword = request.getParameter("password"); 
		
		HttpSession session = request.getSession();
		RequestDispatcher rd = null; 
		
		session.setAttribute("issue",null); 
		if(Services.verifyCredentials(loginUserId,  loginPassword)) {
			session.setAttribute("username", loginUserId);
			session.setAttribute("password", loginPassword);
			PrivilegesSummary privs = Services.getPrivilegesByUserId(loginUserId);

			session.setAttribute("firstName", privs.getFirstName());
			session.setAttribute("lastName", privs.getLastName());
			session.setAttribute("isSupervisor", privs.isSupervisor() ? new Boolean(true) : new Boolean(false));
			session.setAttribute("isDeptHead", privs.isDeptHead() ? new Boolean(true) : new Boolean(false));
			session.setAttribute("isBenCo", privs.isBenCo() ? new Boolean(true) : new Boolean(false));
			session.setAttribute("department", privs.isDeptHead() ? privs.getDepartment() : "N/A");		
			if((boolean) session.getAttribute("isSupervisor"))
			
			rd = request.getRequestDispatcher("home.html"); 
			rd.include(request, response);
		}
		else {
			session.setAttribute("issue", "INVALID CREDENTIALS");
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<h1 style='color:red'>" + session.getAttribute("issue") + "</h1>"); 
			rd = request.getRequestDispatcher("index.html"); 
			rd.include(request, response);
			
		}
	}

}
