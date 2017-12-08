package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.bean.ReimbursementForm;
import com.revature.dao.TrmsDaoImplement;

/**
 * Servlet implementation class getUserForms
 */
public class getUserForms extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In getUserforms");
		
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		List<ReimbursementForm> RFs = null;

		if (session.isNew()) {
			System.out.println("Not logged in!");
			out.println("You are not logged in!");
			out.println("<hr>" + "<a href='index.html'>BACK</a>");
			session.invalidate(); // Clears the session entirely
		} else {
			String userName = (String) session.getAttribute("username");
			
			String greetings = "Hello There! " + userName;
			System.out.println(greetings);
			response.setContentType("text/plain");
			response.getWriter().write(greetings);
			
			TrmsDaoImplement dao = new TrmsDaoImplement();
			RFs = dao.getUserForms(userName);
			
			System.out.println(RFs);
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
