package com.trms.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trms.obj.Employee;
import com.trms.services.Services; 


/**
 * Servlet implementation class CreateUserRequest
 */
public class CreateUserRequest extends HttpServlet {
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
		PrintWriter out = response.getWriter(); 
		response.setContentType("text/html");
		
		String userLoginId = request.getParameter("username");
		String email = request.getParameter("email"); 
		String password = request.getParameter("password"); 
		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname"); 
		//public Employee(String firstName, String lastName, String position, String department, String loginUserId, String loginPassword) {
		Employee e = new Employee(
				request.getParameter("firstName"),
				request.getParameter("lastName"),
				request.getParameter("loginUserId"),
				request.getParameter("loginPassword"),
				request.getParameter("email"));
		
		int rslt = Services.userExists(e.getLoginUserId(), e.getContactInfo().getEmail()); 
		if(rslt == 1) {
			out.println("<h2>Username unavailable.</h2>");
		}
		else if(rslt == 2) {
			out.println("<h2>Email already in use.</h2>");
		}
		else {
			int rowsAffected = Services.insertEmployee(e); 
			if(rowsAffected > 0) {
				out.println("<h2>Success</h2>");
			}
			else {
				out.println("<h2>Username/Email available; Error on Insert/Update</h2>"); 
			}
		}
		out.println("<a href='registeruser.html'>BACK</a>"); 
		
		
		System.out.println(userLoginId);
		
	}

}
