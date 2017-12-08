package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.TRMSDao;

/**
 * Servlet implementation class DoesEmployeeExist
 */
@MultipartConfig
public class DoesEmployeeExist extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DoesEmployeeExist");
		final String username = (String) request.getParameter("regUsername");
		System.out.println("username: " + username);
		
		final TRMSDao dao = TRMSDao.getDao();
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String myXml = "<root>";
		
		if (username == null) {
			System.out.println("the username is null");
			myXml += "username is null";
			myXml += "</root>";
			out.println(myXml);
		}
		
		if (dao.doesEmployeeExist(username) == false) {
			System.out.println("the username " + username + " is not taken");
			
			myXml += "usernameIsNew";
		} else {
			System.out.println("the username " + username + " is taken");
			myXml += "usernameIsTaken";
		}
		
		myXml += "</root>";
		out.println(myXml);
	}
}