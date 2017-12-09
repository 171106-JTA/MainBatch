package com.revature.trms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.trms.services.ViewData;

/**
 * Servlet implementation class ReimbursementTable
 */
public class AdminReimbursementTable extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userType = (String) session.getAttribute("usertype");
		if (userType.equals("Admin")) {
			ViewData.getAllReimbursementCases(response);
		} else if (userType.equals("Department_Head")) {
			ViewData.getRCasesByStatus(response);
		} else if (userType.equals("Supervisor")) {
			ViewData.getRCasesBySup(request, response);
		}
	}
}
