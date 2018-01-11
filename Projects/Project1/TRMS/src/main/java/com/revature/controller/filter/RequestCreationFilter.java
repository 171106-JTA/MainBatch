package com.revature.controller.filter;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;

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
 * Servlet Filter implementation class RequestCreationFilter
 */
public class RequestCreationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public RequestCreationFilter() {
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
			ArrayList<String> attributeNames = new ArrayList<String>();
			Enumeration enumeration = ((HttpServletRequest) request).getSession(false).getAttributeNames();
			while (enumeration.hasMoreElements()) {
				String attributeName = (String) enumeration.nextElement();
				attributeNames.add(attributeName);
				System.out.println("Session: " + ((HttpServletRequest) request).getSession(false).getId() + " on Thread: "
						+ Thread.currentThread().getId() + attributeName + "=>"
						+ ((HttpServletRequest) request).getSession(false).getAttribute(attributeName));
			}
		}
		
		try {
			if (request instanceof HttpServletRequest) {

				try {
					if (ServiceController.createRequest(request.getParameter(Constants.eventId),
							(String) ((HttpServletRequest) request).getSession().getAttribute(Constants.empId).toString(),
							Constants.tuitionRequest.toString(), request.getParameter(Constants.requestAmount).toString()))

						// pass the request along the filter chain
						chain.doFilter(request, response);
				} catch (SQLException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
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
