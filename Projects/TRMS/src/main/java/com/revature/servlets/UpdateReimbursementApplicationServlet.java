package com.revature.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.ReimbursementApplication;
import com.revature.daos.ReimbursementApplicationDAO;

/**
 * Servlet implementation class UpdateReimbursementApplicationsServlet
 */
public class UpdateReimbursementApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReimbursementApplication reimbursementapplication;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reimbursementapplication = new ReimbursementApplication();
		reimbursementapplication.setApplicationid(Integer.parseInt(request.getParameter("applicationid")));
		reimbursementapplication.setEmployeeid(Integer.parseInt(request.getParameter("employeeid")));
		reimbursementapplication.setStatusid(Integer.parseInt(request.getParameter("statusid")));
		reimbursementapplication.setEventid(Integer.parseInt(request.getParameter("eventid")));
		reimbursementapplication.setAmount(Float.parseFloat((request.getParameter("amount"))));
		if(Integer.parseInt(request.getParameter("isapproved")) == 1) {
			reimbursementapplication.setApproved(true);
		} else {
			reimbursementapplication.setApproved(false);
		}
		try {
			if(ReimbursementApplicationDAO.updateReimbursementApplication(reimbursementapplication)) {
				response.setStatus(200);
			} else {
				System.err.println("Update reimbursement application servlet could not process update");
				response.setStatus(500);
			}
		} catch (SQLException e) {
			System.err.println("Update reimbursement application servlet encountered SQLException");
			response.setStatus(500);
		}
	}
}
