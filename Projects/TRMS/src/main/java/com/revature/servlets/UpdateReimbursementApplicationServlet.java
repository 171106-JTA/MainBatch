package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
		System.out.println(("HELLO FROM REIMBURSEMENT APPLICATION"));
		BufferedReader content = request.getReader();
		String[] contents = content.readLine().split("&");
		String[] keyvalue = new String[2];
		Map<String, String> info = new HashMap<String, String>();
		for(int i = 0; i < contents.length; i++) {
			keyvalue = contents[i].split(":");
			info.put(keyvalue[0], keyvalue[1]);
			System.out.println(keyvalue[0] + " : " + keyvalue[1]);
		}
		reimbursementapplication = new ReimbursementApplication();
		reimbursementapplication.setApplicationid(Integer.parseInt(info.get("applicationid")));
		reimbursementapplication.setEmployeeid(Integer.parseInt(info.get("employeeid")));
		reimbursementapplication.setStatusid(Integer.parseInt(info.get("statusid")));
		reimbursementapplication.setEventid(Integer.parseInt(info.get("eventid")));
		reimbursementapplication.setAmount(Float.parseFloat(info.get("amount")));
		reimbursementapplication.setPassed(Integer.parseInt(info.get("ispassed")));
		reimbursementapplication.setApproved(Integer.parseInt(info.get("isapproved")));
		try {
			if(ReimbursementApplicationDAO.updateReimbursementApplication(reimbursementapplication)) {
				System.out.println(("------------------------SUCCESS"));
				response.setStatus(200);
			} else {
				System.err.println("Update reimbursement application servlet could not process update");
				response.setStatus(500);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Update reimbursement application servlet encountered SQLException");
			response.setStatus(500);
		}
	}
}
