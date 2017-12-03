package com.revature.ear.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.businessobject.CodeList;
import com.revature.businessobject.CompanyEmployee;
import com.revature.businessobject.User;
import com.revature.service.GetCodeList;
import com.revature.service.GetUserInfo;
import com.revature.service.Login;
import com.revature.service.RegisterEmployee;
import com.revature.service.util.ServiceUtil;

/**
 * Servlet implementation class FrontController
 */
@SuppressWarnings("unused")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> mapping = new TreeMap<>();
     
    /**
     * @see HttpServlet#HttpServlet()
     */
    @Override
	public void init() {
    	try {
			super.init();
		} catch (ServletException e) {
			e.printStackTrace();
		}
    	
    	initController();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		String action = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf(".do"));
		int statusCode = 200;
		Class<?> clazz;
		Method method;
		
		try {
			// Do we have a mapping to appropriate action?
			if (!mapping.containsKey(action)) 
				statusCode = 500;
			else {
				clazz = this.getClass();
				
				// Get mapped method
				method = clazz.getDeclaredMethod((String)mapping.get(action), HttpServletRequest.class, HttpServletResponse.class);
				
				// set accessibility 
				method.setAccessible(true);
				
				// process request
				statusCode = (int) method.invoke(this, request, response);
				
				// reset accessibility 
				method.setAccessible(false);
			}
		} catch (IllegalAccessException | IllegalArgumentException |InvocationTargetException| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} finally {
			if (statusCode != 200) {
				response.sendError(statusCode);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	/**
	 * Initializes controller mapping
	 */
	private void initController() {
		 Enumeration e = this.getInitParameterNames();
	     String action;
	        
	     while (e.hasMoreElements()) {
	    	 action = (String)e.nextElement();
	       	mapping.put(action, this.getInitParameter(action));
	     }
	}
	
	///
	//	CONTROLLER METHODS 
	///
	
	private int loginHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String submit = request.getParameter("submit");
		int status = 200;

		// destroy if not new
		if (!session.isNew())
			session.invalidate();
		
		// If no issue on client side
		if (submit == null) 
			status = 500;
		else {
			switch (submit.toLowerCase()) {
				case "login":
					status = loginExistingUser(request, response);
					break;
				case "register":
					status = registerUser(request, response);
					break;
				default:
					status = 500;
					break;
			}
		}
		
		return status;
	}
	
	///
	//	HELPER METHODS 
	///
	
	private int loginExistingUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		List<CodeList> codes = GetCodeList.getCodeListByCode("USER-ROLE");
		RequestDispatcher rd;
		PrintWriter out;
		User user = null;
		int status = 401;
		
		// Attempt to log into user account
		if ((user = Login.login(username, password)) != null) {
			HttpSession session = request.getSession();
			
			// save user info
			for (CodeList code : codes) {
				if (code.getId().equals(user.getRoleId())) {
					session.setAttribute("id", user.getId());
					session.setAttribute("username", username);
					session.setAttribute("role", code.getValue());
					session.setAttribute("userinfo", ServiceUtil.toJson(GetUserInfo.getUserInfoByUserId(user.getId())));
					session.setAttribute("status", "login");
					rd = request.getRequestDispatcher("Dashboard");
					rd.forward(request, response);
					break;
				}
			}
		}
		
		if (status == 401) {
			response.setContentType("text/html");
			out = response.getWriter();
			out.println("<div style='margin-top:1%;width:80%;height:35px;background-color:pink;color:red;position: absolute;;vertical-align:center;'>" + 
					"				<label style=\"padding-top: 6px;padding-left: 5px;font-size:1.25em;\"><b>INVALID USERNAME OR PASSWORD</b></label>" + 
					"		</div>");
			rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
			
		
		return 200;
	}
	
	private int registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rq = request.getRequestDispatcher("./resource/html/register.html");
		rq.forward(request, response);
		return 200;
	}
	
	private int createAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String employeeId = request.getParameter("employeeid");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		CompanyEmployee employee;
		RequestDispatcher rd;
		String message;
		int status = 500;
		
		// Validate Company employee
		if ((employee = RegisterEmployee.validateEmployee(employeeId != null ? Integer.parseInt(employeeId) : -1, firstname, lastname)) != null)  {
			// Attempt to create account
			if (RegisterEmployee.registerAccount(employee, username, password, email)) {
				message = "<div style='margin-top:1%;width:80%;height:35px;background-color:lightgreen;color:green;position: absolute;;vertical-align:center;'>\r\n" + 
						"				<label style=\"padding-top: 6px;padding-left: 5px;font-size:1.25em;\"><b>SUCCESSFULLY CREATED ACCOUNT</b></label>\r\n" + 
						"		</div>";
				status = 200;
			} else {
				// failed to create account
				message = "<div style='margin-top:1%;width:80%;height:35px;background-color:pink;color:red;position: absolute;;vertical-align:center;'>" + 
						"				<label style=\"padding-top: 6px;padding-left: 5px;font-size:1.25em;\"><b>FAILED TO CREATE ACCOUNT</b></label>" + 
						"		</div>";
			}
		} else {
			// failed to find employee
			message = "<div style='margin-top:1%;width:80%;height:35px;background-color:pink;color:red;position: absolute;;vertical-align:center;'>" + 
					"				<label style=\"padding-top: 6px;padding-left: 5px;font-size:1.25em;\"><b>INVALID CREDENTIALS</b></label>" + 
					"		</div>";
		}
		
		// Return to landing page and notify user of result
		rd = request.getRequestDispatcher("index.html");
	
		// redirection
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(message);
		rd = request.getRequestDispatcher("index.html");
		rd.include(request, response);
		
		
		return 200;
	}
	
	
	
	
	private int processDashboardRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rq = request.getRequestDispatcher("Dashboard");
		rq.forward(request, response);
		return 200;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
