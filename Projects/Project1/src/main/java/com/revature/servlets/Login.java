package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.service.LoginValidation;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In the Login Servelt's doGet()");
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		System.out.println("In the Login Servelt's doPost()");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		response.setContentType("text/html");

//		PrintWriter out = response.getWriter();
//		out.println("<h3>Hello From Login Servlet's Post</h3>");
//		RequestDispatcher rd = null;
		
		
		LoginValidation lv = new LoginValidation();
		String permission = lv.validateLoginCredentials(username, password);
		System.out.println("In Login.java");
		System.out.println("\tpermission: " + permission);
//		out.println("permission: " + permission);
		
		if(permission == null) {
//			out.println("Login Failed!");
			System.out.println("\tLogin Failed!");
		} else {			
			permission = permission.toLowerCase();
			String landingPage = null;
			if (permission.equals("employee")) {
				landingPage = "employeeLandingPage.html";
			} else if (permission.equals("benco")) {
				landingPage = "benCoLandingPage.html";
			} else if (permission.equals("direct_supervisor")) {
				landingPage = "directSupervisorLandingPage.html";
			} else if (permission.equals("department_head")) {
				landingPage = "departmentHeadLandingPage.html";
			} else 
			{
				System.out.println("\t Should never see this! ");
			}
			
			//Need something here to handle landingPage = null 
			//(i.e. something went fatally wrong between reading database and the above permission test)
			response.sendRedirect(landingPage); 
		} 
	}

}
