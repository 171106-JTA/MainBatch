package com.revature.controller.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

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
 * Servlet Filter implementation class EventCreationFilter
 */
public class EventCreationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public EventCreationFilter() {
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
		List<String> emps = new LinkedList<String>();
		List<String> keys = new LinkedList<String>();
		List<String> vals = new LinkedList<String>();

		if (request instanceof HttpServletRequest) {
			ArrayList<String> parameterNames = new ArrayList<String>();
			Enumeration enumeration = request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String parameterName = (String) enumeration.nextElement();
				parameterNames.add(parameterName);
				if (parameterName.contains(Constants.empPattern))
					emps.add(request.getParameter(parameterName));
				if (parameterName.contains(Constants.grdPattern))
					keys.add(request.getParameter(parameterName));
				if (parameterName.contains(Constants.pntPattern))
					vals.add(request.getParameter(parameterName));
				// System.out.println(parameterName + "=>" +
				// request.getParameter(parameterName));
			}

			if (keys.size() == vals.size()) {

				if (ServiceController.createEvent(emps, request.getParameter(Constants.evtType),
						request.getParameter(Constants.critType), request.getParameter(Constants.departmentId),
						request.getParameter(Constants.startD), request.getParameter(Constants.endD), keys, vals, request.getParameter(Constants.eventAmount).toString()))

					// pass the request along the filter chain
					chain.doFilter(request, response);
			}
		}
	}

	// }

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
