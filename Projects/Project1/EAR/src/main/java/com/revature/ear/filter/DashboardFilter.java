package com.revature.ear.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.service.Login;

/**
 * Servlet Filter implementation class DashboardFilter
 */
public class DashboardFilter implements Filter {

    /**
     * Default constructor. 
     */
    public DashboardFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		HttpServletResponse res = (HttpServletResponse)response;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String submit = request.getParameter("submit");
		
		// If no session and credentials are invalid then not allowed to continue
		if (session == null &&  (submit == null || (!submit.equals("register") && !submit.equals("login"))) && Login.login(username, password) == null) {
			// No logged-in user found, so redirect to login page.
			res.sendRedirect("index.html");
			
			 // HTTP 1.1.
			res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			
			// HTTP 1.0.
			res.setHeader("Pragma", "no-cache"); 
			res.setDateHeader("Expires", 0);
	    } else {
	        chain.doFilter(request, response);  
	    }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	
	
}
