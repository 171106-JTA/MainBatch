package com.trms.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trms.obj.ContactInformation;
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
		
		String firstName = request.getParameter("firstName"); 
		String lastName = request.getParameter("lastName"); 
		String userLoginId = request.getParameter("loginUserId");
		String password = request.getParameter("loginPassword"); 
		String email = request.getParameter("email"); 
		String phone = request.getParameter("phone"); 
		String streetAddr = request.getParameter("streetAddr"); 
		String city = request.getParameter("city"); 
		String state = request.getParameter("state"); 
		String zipCode = request.getParameter("zipCode"); 
		String department = request.getParameter("department"); 
		String position = request.getParameter("position"); 
		String supervisor = request.getParameter("supervisor"); 
		
		//public Employee(String firstName, String lastName, String position, String department, String loginUserId, String loginPassword) {
		Employee e = new Employee(firstName, lastName, userLoginId, password, 
				department, position, supervisor,
				new ContactInformation(
						firstName, lastName, email, phone,
						streetAddr, city, state, zipCode));
		
		
		int rslt = Services.userExists(e.getLoginUserId(), e.getContactInfo().getEmail()); 
		if(rslt == 1) {
			out.println("<h2>Username unavailable.</h2>");
		}
		else if(rslt == 2) {
			out.println("<h2>Email already in use.</h2>");
		}
		else {
			out.println("<div>Username and email available, attempted to insert your information into our database...</div><br>");
			boolean successful = Services.insertEmployee(e); 
			if(successful) {
				out.println("<h2>Success</h2>");
			}
			else {
				out.println("<h2>Username/Email available; Error on Insert/Update</h2>"); 
			}
		}
		out.println("<a href='registeruser.html'>BACK</a>"); 
		
		e = null; 
		
	}

}
