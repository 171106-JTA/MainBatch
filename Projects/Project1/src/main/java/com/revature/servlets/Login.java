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
			System.out.println("\t else statment ");
			System.out.println("\t permission: " + permission);
			
			permission = permission.toLowerCase();
			System.out.println("\t" + (permission == "employee"));
			System.out.println("\t" + permission.equals("employee"));
			if (permission.equals("employee")) {
				System.out.println("\temployee logged in!");
//				out.println("Logged in as employee");
				response.sendRedirect("employeeLandingPage.html"); 
//				rd = request.getRequestDispatcher("employeeLandingPage.html"); 
//				rd.forward(request, response);
			} else if (permission.equals("benco")) {
				System.out.println("\tbenco logged in!");
//				out.println("Logged in as BenCo");
				response.sendRedirect("benCoLandingPage.html"); 
			} else if (permission.equals("direct_supervisor")) {
				System.out.println("\tdirect_supervisor logged in!");
//				out.println("Loggd in as Direct Supervisor");
				response.sendRedirect("directSupervisorLandingPage.html");
			} else if (permission.equals("department_head")) {
//				out.println("Loggd in as Benefits Coordinator");
				System.out.println("\tdepartment_head logged in!");
				response.sendRedirect("departmentHeadLandingPage.html");
			} else 
			{
				System.out.println("\t Should never see this! ");
			}
		} 
		
//		out.println("<hr><a href='index.html'>Back</a>");
	}

}
