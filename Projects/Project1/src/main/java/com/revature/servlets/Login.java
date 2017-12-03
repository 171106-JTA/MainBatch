package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		String landingPage = "applicationError.html";
		
		if(permission == null) {
			System.out.println("\tLogin Failed!");
			landingPage = "invalidLogin.html";
		} else {
			HttpSession session = null;
			permission = permission.toLowerCase();
			if (permission.equals("employee")) {
				landingPage = "employeeLandingPage.html";
			} else if (permission.equals("benco")) {
				landingPage = "benCoLandingPage.html";
			} else if (permission.equals("direct_supervisor")) {
				landingPage = "directSupervisorLandingPage.html";
			} else if (permission.equals("department_head")) {
				landingPage = "departmentHeadLandingPage.html";
			}			
		} 
		
		response.sendRedirect(landingPage); 
	}

}
