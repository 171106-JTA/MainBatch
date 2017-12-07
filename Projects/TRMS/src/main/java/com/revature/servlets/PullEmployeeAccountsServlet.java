package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.daos.EmployeeAccountDAO;
import com.revature.utilities.CloserUtility;

/**
 * Servlet implementation class PullEmployeeAccountsServlet
 */
public class PullEmployeeAccountsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> employeeaccounts;
		Iterator<String> iterator;
		PrintWriter write = response.getWriter();
		try {
			employeeaccounts = EmployeeAccountDAO.pullEmployeeAccounts(request.getParameter("employeeid"));
			iterator = employeeaccounts.iterator();
			int i = 0;
			write.append("{");
			while(iterator.hasNext()) {
				write.append("'employee" + i++ + "':" + iterator.next() + ", ");
			}
			write.append("}");
			response.setStatus(200);
		} catch (NumberFormatException e) {
			System.err.println("Pull employee account servlet encountered NumberFormatException.");
			response.setStatus(500);
		} catch (SQLException e) {
			System.err.println("Pull employee account servlet encountered SQLException.");
			response.setStatus(500);
		} finally {
			CloserUtility.close(write);
		}
	}
}
