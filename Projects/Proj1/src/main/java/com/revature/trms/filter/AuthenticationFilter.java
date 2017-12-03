package com.revature.trms.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.trms.dao.EmployeeDAO;
import com.revature.trms.model.Employee;

public class AuthenticationFilter implements Filter {
	private FilterConfig config;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		System.out.println("Instance created of " + getClass().getName());
		this.config = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Filter trigger!");
		String loginUsername = request.getParameter("username");
		String loginPassword = request.getParameter("password");
		EmployeeDAO ed = new EmployeeDAO();
		Employee loginUser = ed.selectEmployeeByUsername(loginUsername);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		HttpSession session = ((HttpServletRequest) request).getSession();
		ServletContext context = config.getServletContext();

		context.log("doFilter called in: " + config.getFilterName() + " on " + (new java.util.Date()));

		// log the session ID
		context.log("session ID: " + session.getId());

		// Find out whether the logged-in session attribute is set
		String logged = (String) session.getAttribute("logged-in");
		if (logged == null)
			session.setAttribute("logged-in", "no");

		// log a message about the log-in status
		context.log("log-in status: " + (String) session.getAttribute("logged-in"));
		context.log("");
		chain.doFilter(request, response);

		
		if (loginUser == null) {
			out.println("<h3>User could not be found</h3>");
			out.println("<hr>" + "<a href='index.html'>BACK</a>");
		} else if (!loginUser.getPassword().equals(loginPassword)) {
			out.println("<h3>Invalid Password</h3>");
			out.println("<hr>" + "<a href='index.html'>BACK</a>");

		} else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
