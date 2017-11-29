package com.revature.ear.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.businessobject.User;
import com.revature.service.util.Login;

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
		String submit = request.getParameter("submit");
		int status = 200;
		
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
		Login login = new Login();
		User user = null;
		int status = 401;
		
		// Attempt to log into user account
		if ((user = login.login(username, password)) != null) {
			// TODO : add code to load appropriate screen based on user Credentials 
		}
		
		return status;
	}
	
	private int registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rq = request.getRequestDispatcher("register.html");
		rq.forward(request, response);
		return 200;
	}
	
	private int createAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
