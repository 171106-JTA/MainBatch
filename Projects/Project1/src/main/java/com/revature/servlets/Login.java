package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In the Login Servelt's doGet()");
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In the Login Servelt's doPost()");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		response.setContentType("text/html");

		LoginValidation lv = new LoginValidation();
		String permission = lv.validateLoginCredentials(username, password);

		String result = null;
		String landingPage = null;
		JSONObject myObject = new JSONObject();
		
		if (permission == null) {
			result = "FailedLogin";
			System.out.println("Login Failed!");
			
		} else {
			result = "SuccessfulLogin";
			permission = permission.toLowerCase();
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("permission", permission);
			session.setAttribute("visit", 0);
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
		myObject.put("LoginResult", result);
		myObject.put("LandingPage", landingPage);
		PrintWriter out = response.getWriter();
		out.println(myObject);
		
	}

}
