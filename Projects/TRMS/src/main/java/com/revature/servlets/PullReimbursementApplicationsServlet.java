package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
		BufferedReader content = request.getReader();
		String[] contents = content.readLine().split("&");
		String[] keyvalue = new String[2];
		Map<String, String> info = new HashMap<String, String>();
		for(int i = 0; i < contents.length; i++) {
			keyvalue = contents[i].split(":");
			info.put(keyvalue[0], keyvalue[1]);
			System.out.println(keyvalue[0] + " : " + keyvalue[1]);
		}
		try {
			reimbursementapplications = ReimbursementApplicationDAO.pullReimbursementApplications(info.get("employeeid"));
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
