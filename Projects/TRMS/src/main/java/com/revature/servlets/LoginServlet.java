package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.EmployeeAccount;
import com.revature.daos.EmployeeAccountDAO;
import com.revature.utilities.CloserUtility;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeAccount employeeaccount;
	private PrintWriter write;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		employeeaccount = null;
		try {
			employeeaccount = EmployeeAccountDAO.getEmployeeAccount(request.getParameter("username"), request.getParameter("password"));
			if(employeeaccount == null) {
				response.setStatus(204);
			} else {
				write = response.getWriter();
				write.append(employeeaccount.toJSON());
				response.setStatus(200);
			}
		} catch(IOException e) {
			System.err.println("Login servlet encountered IOException.");
			response.setStatus(500);
		} catch(SQLException e) {
			System.err.println("Login servlet encountered SQLException.");
			response.setStatus(500);
		} finally {
			CloserUtility.close(write);
		}
	}
}
