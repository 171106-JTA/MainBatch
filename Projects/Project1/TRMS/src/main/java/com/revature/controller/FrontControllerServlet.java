package com.revature.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.model.Constants;

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
	}

	@Override
	public void init() {
		SessionController.startJDBC();
		ServiceController.startMonitors();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		String out = null;
		String toFile, emp, req, evnt, mail, file, id;
		String requestStr = request.getRequestURI();
		int type = -1;

		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String[] dirs = requestStr.split("/");
		requestStr = dirs[dirs.length - 1];

		switch (requestStr) {
		// actions that start a new session
		case "register.do":
		case "login.do":
			response.setContentType("text/html");
			type = (int) request.getAttribute(Constants.SessionType);
			toFile = type == Constants.BasicSession ? "BasicLogin.html" : "ManagementLogin.html";
			request.getSession(true).setAttribute(Constants.empId, request.getAttribute(Constants.empId));
			// rd = request.getRequestDispatcher(toFile);
			// rd.forward(request, response);
			if (request instanceof HttpServletRequest) {
				ArrayList<String> attributeNames = new ArrayList<String>();
				Enumeration enumeration = request.getSession().getAttributeNames();
				while (enumeration.hasMoreElements()) {
					String attributeName = (String) enumeration.nextElement();
					attributeNames.add(attributeName);
					System.out.println("Session: " + request.getSession(false).getId() + " on Thread: "
							+ Thread.currentThread().getId() + attributeName + "=>"
							+ request.getSession().getAttribute(attributeName));
				}
			}
			pw.write(toFile);
			return;
		// actions that refresh the page
		case "createEvent.do":
			System.out.println("Seomthing");
			break;
		case "createRequest.do":
			if (request instanceof HttpServletRequest) {
				ArrayList<String> attributeNames = new ArrayList<String>();
				Enumeration enumeration = ((HttpServletRequest) request).getSession(false).getAttributeNames();
				while (enumeration.hasMoreElements()) {
					String attributeName = (String) enumeration.nextElement();
					attributeNames.add(attributeName);
					System.out.println("Session: " + ((HttpServletRequest) request).getSession(false).getId()
							+ " on Thread: " + Thread.currentThread().getId() + attributeName + "=>"
							+ request.getParameter(attributeName));
				}
			}
			break;
		case "createMessage.do":
			break;
		case "uploadFile.do":
			break;

		// actions that don't take user input
		// or only take button input
		// these actions directly impact sessions
		case "updateEmployee.do":
			emp = request.getParameter("employee");
			// probably gonna need another 10 fields
			SessionController.updateEmployee(emp);
			break;
		case "processRequest.do":
			req = request.getParameter(Constants.requestId);
			req = req.substring(req.length() - 1);
			String status = request.getParameter(Constants.status).toString();
			ServiceController.processRequest(req, status);
			break;

		// actions that will be called by user request
		// as well as AJAX calls
		// case "getRequests.do":
		// SessionController.getRequests();
		// break;
		case "getRequests.do":
			emp = request.getSession().getAttribute(Constants.empId).toString();
			out = SessionController.getRequests(emp);
			break;
		case "getRequestsForEmployee.do":
			emp = request.getParameter("employee");
			out = SessionController.getRequests(emp);
			break;
		case "getEmployees.do":
			out = SessionController.getEmployees(request.getSession().getAttribute(Constants.empId).toString());
			break;
		case "getEvents.do":
			out = SessionController.getEvents();
			break;
		case "getCriteria.do":
			Enumeration<?> e = request.getParameterNames();
			String param = null, name;
			while(e.hasMoreElements()) {
				name = (String) e.nextElement();
				param = request.getParameter(name);
			}
			out = SessionController.getEventsForEmp(param);
			break;
		case "getMessages.do":
			emp = request.getSession(false).getAttribute(Constants.empId).toString();
			out = SessionController.getMessages(emp);
			break;
		case "getFiles.do":
//			evnt = request.getParameter();
//			out = SessionController.getFileAttachments(evnt);
			break;
		case "getFile.do":
			file = request.getParameter("file");
			out = SessionController.getFileContents(file);
			break;
		case "getSession.do":
			out = SessionController.validateSession(request.getSession(false) != null);
			if (out == null) {
				response.setContentType("text/html");
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
			} else {
				pw.write(out);
			}
			return;
		}
		if (request.getSession(false) == null) {
			response.setContentType("text/html");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		if (out != null) {
			response.setContentType("application/json");

			response.resetBuffer();
			pw.write(out);
			pw.flush();
			pw.close();
			response.setStatus(HttpServletResponse.SC_OK);
		}
		else {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			pw.write(HttpServletResponse.SC_OK);
			pw.flush();
			pw.close();
		}
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
