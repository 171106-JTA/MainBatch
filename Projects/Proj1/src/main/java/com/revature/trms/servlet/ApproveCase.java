package com.revature.trms.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.dao.ReimCaseDAO;

/**
 * Servlet implementation class ApproveCase
 */
public class ApproveCase extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String approvalDecision = request.getParameter("decision");
		ReimCaseDAO reimData = new ReimCaseDAO();
		PrintWriter out = response.getWriter();
		if ("Approve".equals(approvalDecision)) {
			if (reimData.updateCaseStatus(approvalDecision, request)) {
				out.println("<h1>Approved!</h1>");
			} else {
				out.println("You don't have the rights to.");
			}
		} else if ("Disapprove".equals(approvalDecision)) {
			if (reimData.updateCaseStatus(approvalDecision, request)) {
				out.println("<h1>Disapproved!</h1>");
			} else {
				out.println("You don't have the rights to.");
			}

		}
		out.println("<hr>" + "<a href='make_approval.html'>BACK</a>");
	}

}
