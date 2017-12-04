package com.revature.credentials;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Employee;
import com.revature.services.GetWork;

@WebServlet("/login")
public class Existence extends HttpServlet {

	    @EJB
	    private GetWork userService;

	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        Map<String, String> messages = new HashMap<String, String>();

	        if (username == null || username.isEmpty()) {
	            messages.put("username", "Please enter username");
	        }

	        if (password == null || password.isEmpty()) {
	            messages.put("password", "Please enter password");
	        }

	        if (messages.isEmpty()) {
	            Employee user = userService.find(username, password);

	            if (user != null) {
	                request.getSession().setAttribute("user", user);
	                response.sendRedirect(request.getContextPath() + "/home");
	                return;
	            } else {
	                messages.put("login", "Unknown login, please try again");
	            }  
	        }

	        request.setAttribute("messages", messages);
	        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	    }
	}