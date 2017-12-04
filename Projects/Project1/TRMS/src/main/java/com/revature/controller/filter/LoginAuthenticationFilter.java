package com.revature.controller.filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.revature.controller.SessionController;
import com.revature.model.Constants;

/**
 * Servlet Filter implementation class LoginAuthenticationFilter
 */
public class LoginAuthenticationFilter implements Filter {
	private Properties prop;
	private FileInputStream fis;
    /**
     * Default constructor. 
     * @throws FileNotFoundException 
     */
    public LoginAuthenticationFilter() throws FileNotFoundException {
//    		prop = new Properties();
//    		fis = new FileInputStream(Constants.servletConfig);
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
		RequestDispatcher rd;
		String user, pass;
		int type;
		
		PrintWriter pw = response.getWriter();
		
		// Access logic layer to check credentials
//		prop.load(fis);
//		user = prop.getProperty(this.getClass().getSimpleName() + Constants.userKey);
//		pass = prop.getProperty(this.getClass().getSimpleName() + Constants.passKey);
		user = request.getParameter("username");
		pass = request.getParameter("password");
		
		type = SessionController.login(user, pass);
		if(type == Constants.AuthFail) {
			pw.write(Constants.LoginFail);
			rd = request.getRequestDispatcher(prop.getProperty(request.getRemoteHost()));
			rd.forward(request, response);
		}
		else {
			request.setAttribute(Constants.SessionType, type);
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
