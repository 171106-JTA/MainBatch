package com.revature.trms.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.revature.trms.dao.EmployeeDAO;
import com.revature.trms.model.Employee;

public class AuthenticationFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

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
