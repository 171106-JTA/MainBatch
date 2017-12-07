package com.revature.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.ReimbursementApplication;
import com.revature.daos.ReimbursementApplicationDAO;

/**
 * Servlet implementation class ReimbursementApplicationServlet
 */
public class NewReimbursementApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReimbursementApplication reimbursementapplication;
	private Random random;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reimbursementapplication = new ReimbursementApplication();
		reimbursementapplication.setApplicationid(random.nextInt(99999999));
		reimbursementapplication.setEmployeeid(Integer.parseInt(request.getParameter("employeeid")));
		reimbursementapplication.setStatusid(0);
		reimbursementapplication.setEventid(Integer.parseInt(request.getParameter("eventid")));
		reimbursementapplication.setAmount(Float.parseFloat((request.getParameter("amount"))));
		reimbursementapplication.setApproved(false);
		try {
			if(ReimbursementApplicationDAO.insertReimbursementApplication(reimbursementapplication)) {
				response.setStatus(200);
			} else {
				response.setStatus(418);
			}
		} catch (SQLException e) {
			System.err.println("New reimbursement application servlet encountered SQLException");
			response.setStatus(500);
		}
	}
}
