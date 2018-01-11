package com.revature.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.model.Constants;

/**
 * Servlet Filter implementation class SessionFilter
 */
public class SessionFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public SessionFilter() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		RequestDispatcher rd = null;

		if (!(request instanceof HttpServletRequest)) {
			rd = request.getRequestDispatcher(Constants.mainFile);
			rd.forward(request, response);
		}

		if ((((HttpServletRequest) request).getSession(false) == null
				&& !(((HttpServletRequest) request).getRequestURI().contains("login.do")
						|| ((HttpServletRequest) request).getRequestURI().contains("register.do")))) {
			if (!request.getRemoteHost().equals(Constants.mainFile)) {
				rd = request.getRequestDispatcher(Constants.mainFile);
				rd.forward(request, response);
			}
		}

		chain.doFilter(request, response);

		if (((HttpServletRequest) request).getSession(false) == null) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			rd.forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
