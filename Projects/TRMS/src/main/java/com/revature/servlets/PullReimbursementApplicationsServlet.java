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

import com.revature.daos.ReimbursementApplicationDAO;
import com.revature.utilities.CloserUtility;

/**
 * Servlet implementation class ProcessReimbursementApplicationsServlet
 */
public class PullReimbursementApplicationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> reimbursementapplications;
		Iterator<String> iterator;
		PrintWriter write = response.getWriter();
		try {
			reimbursementapplications = ReimbursementApplicationDAO.pullReimbursementApplications(request.getParameter("employeeid"));
			iterator = reimbursementapplications.iterator();
			int i = 0;
			write.append("{");
			while(iterator.hasNext()) {
				write.append("'application" + i++ + "':" + iterator.next() + ", ");
			}
			write.append("}");
			response.setStatus(200);
		} catch (NumberFormatException e) {
			System.err.println("Process reimbursement applications servlet encountered NumberFormatException.");
			response.setStatus(500);
		} catch (SQLException e) {
			System.err.println("Process reimbursement applications servlet encountered SQLException.");
			response.setStatus(500);
		} finally {
			CloserUtility.close(write);
		}
	}
}
