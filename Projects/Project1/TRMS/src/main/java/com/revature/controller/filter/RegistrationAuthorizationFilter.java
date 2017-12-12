package com.revature.controller.filter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
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
 * Servlet Filter implementation class RegistrationAuthorizationFilter
 */
public class RegistrationAuthorizationFilter implements Filter {
	private Properties prop;
	private FileInputStream fis;

	/**
	 * Default constructor.
	 */
	public RegistrationAuthorizationFilter() {
		// prop = new Properties();
		// fis = new FileInputStream(Constants.servletConfig);
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		RequestDispatcher rd;
		String user, pass, type, superId;
		int code;

		PrintWriter pw = response.getWriter();

		// Access logic layer to check credentials
		// prop.load(fis);
		// user = prop.getProperty(this.getClass().getSimpleName() + Constants.userKey);
		// pass = prop.getProperty(this.getClass().getSimpleName() + Constants.passKey);
		user = request.getParameter("username");
		pass = request.getParameter("enter_password");
		superId = request.getParameter("super_id");
		type = request.getParameter("type");

		try {
			int id  = SessionController.register(user, pass, superId, type);

			if (id == Constants.AuthFail) {
				pw.write(Constants.RegFail);
				rd = request.getRequestDispatcher(request.getRemoteHost());
				rd.forward(request, response);
			} else {
				request.setAttribute(Constants.empId, id);
				request.setAttribute(Constants.SessionType, Integer.parseInt(type));
				chain.doFilter(request, response);
			}
		} catch (NumberFormatException | InvalidKeySpecException | NoSuchAlgorithmException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
