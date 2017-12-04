package com.revature.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.model.Constants;

/**
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dest;
		RequestDispatcher rd;
//		prop.load(fis);

		HttpSession session = request.getSession();
		if (session.isNew()) {
//			rd = request.getRequestDispatcher(prop.getProperty(this.getClass().getSimpleName() + Constants.ToFile));
			if(((int) request.getAttribute(Constants.SessionType)) == Constants.BasicSession)
				dest = "BasicLogin.html";
			else
				dest = "ManagementLogin.html";
		}
		else {
			// error handling for multiple sessions
			dest = "portal.html";
 		}
		
		rd = request.getRequestDispatcher(dest);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
