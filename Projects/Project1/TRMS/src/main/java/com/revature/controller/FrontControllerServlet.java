package com.revature.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontControllerServlet() {
		super();
		SessionController.startJDBC();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		if (request.getSession(false) == null) {
			if (request.getRemoteHost().equals("portal.html")) {
				rd = request.getRequestDispatcher(request.getRemoteHost());
				rd.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestStr = request.getRequestURI();
		int code = -1;

		requestStr = ((requestStr.split("/"))[requestStr.length() - 1]);

		switch (requestStr) {
		case "login.do":
			code = SessionController.login(request.getParameter("user"), request.getParameter("pass"),
					request.getParameter("type"));
			break;
		case "register.do":
			code = SessionController.register(request.getParameter("user"), request.getParameter("pass"),
					request.getParameter("super_id"), Integer.parseInt(request.getParameter("type")));
			break;
		case "getRequests.do":
			break;
		case "getEmployees.do":
			break;
		case "getMessages.do":
			break;
		}

		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
