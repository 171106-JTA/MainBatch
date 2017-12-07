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
 * Servlet implementation class RetrieveReimbursementApplications
 */
public class RetrieveReimbursementApplications extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> reimbursementapplications;
		Iterator<String> iterator;
		PrintWriter write = response.getWriter();
		try {
			reimbursementapplications = ReimbursementApplicationDAO.getReimbursementApplications(Integer.parseInt(request.getParameter("employeeid")));
			iterator = reimbursementapplications.iterator();
			int i = 0;
			write.append("{");
			while(iterator.hasNext()) {
				write.append("'application" + i + "':" + iterator.next() + ", ");
			}
			write.append("}");
		} catch (NumberFormatException e) {
			System.err.println("Retrieve reimbursement applications servlet encountered NumberFormatException.");
		} catch (SQLException e) {
			System.err.println("Retrieve reimbursement applications servlet encountered SQLException.");
		} finally {
			CloserUtility.close(write);
		}
	}
}
