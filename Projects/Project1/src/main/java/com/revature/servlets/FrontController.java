package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// /**
	// * @see HttpServlet#HttpServlet()
	// */
	// public FrontController() {
	// super();
	// // TODO Auto-generated constructor stub
	// }
	//
	// /**
	// * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	// response)
	// */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In the Front Controller's doGet()");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In Front Controller's doPost()");

		String url = request.getRequestURI();
		System.out.println(url);

		String[] split = url.split("/");
		System.out.println(split[split.length - 1]);

		// Get the action
		String action = split[split.length - 1].substring(0, split[split.length - 1].length() - 3).toLowerCase();
		System.out.println(action);

		RequestDispatcher rd = null;

		switch (action) {
		case "login":
			rd = request.getRequestDispatcher("Login");
			rd.forward(request, response);
			break;
		case "newreimbursementform":
			rd = request.getRequestDispatcher("newReimbursementForm");
			rd.forward(request, response);
			break;
		case "getuserforms":
			rd = request.getRequestDispatcher("getUserForms");
			rd.forward(request, response);
			break;
		case "getformsneedingapproval":
			System.out.println("In FrontController: getFormsNeedingApproval");
			rd = request.getRequestDispatcher("getFormsNeedingApproval");
			rd.forward(request, response);
			break;
		case "updateformapproval":
			System.out.println("In FrontController; updateFormApproval");
			rd = request.getRequestDispatcher("UpdateFormApproval");
			rd.forward(request, response);
		}
		System.out.println("After Switch Statement");
	}

}
