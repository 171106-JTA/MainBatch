package com.revature.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.revature.controller.ServiceController;
import com.revature.model.Constants;

/**
 * Servlet Filter implementation class MessageCreationFilter
 */
public class MessageCreationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public MessageCreationFilter() {
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

		if (request instanceof HttpServletRequest) {

			if (ServiceController.formatMessage(request.getParameter(Constants.toId),
					(String) ((HttpServletRequest) request).getSession(false).getAttribute(Constants.empId).toString(),
					request.getParameter(Constants.subject), request.getParameter(Constants.messageBox),
					Boolean.parseBoolean(request.getParameter(Constants.urgentCheck))))

				// pass the request along the filter chain
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
