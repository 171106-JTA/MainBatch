package com.revature.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.revature.beans.Title;
import com.revature.dao.TRMSDao;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
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
		
		RequestDispatcher rd = null;
		final String username = (String) request.getParameter("usernameLogin");
		final String password = (String) request.getParameter("passwordLogin");
		
		TRMSDao dao = TRMSDao.getDao();
		
		Title title = dao.validateLogin(username, password.hashCode());
		if (title == Title.BENCO || title == Title.BENCO_MANAGER ||
				title == Title.CEO || title == Title.EMP_MANAGER || title == Title.DEPARTMENTHEAD) {
			System.out.println("going to Admin.html");
			rd = request.getRequestDispatcher("Admin.html");
			rd.include(request, response);
		} else if (title == Title.EMPLOYEE) { 
			System.out.println("going to employee.html");
			chain.doFilter(request, response);
		} else {
			rd = request.getRequestDispatcher("index.html");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h2>Your username and password did not match any users on file.<h2>");
			rd.include(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
