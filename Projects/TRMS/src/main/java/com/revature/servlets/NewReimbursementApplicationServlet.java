package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
		BufferedReader content = request.getReader();
		random = new Random();
		String[] contents = content.readLine().split("&");
		String[] keyvalue = new String[2];
		Map<String, String> info = new HashMap<String, String>();
		for(int i = 0; i < contents.length; i++) {
			keyvalue = contents[i].split(":");
			info.put(keyvalue[0], keyvalue[1]);
			System.out.println(keyvalue[0] + " : " + keyvalue[1]);
		}
		reimbursementapplication = new ReimbursementApplication();
		reimbursementapplication.setApplicationid(random.nextInt(99999999) + 1);
		reimbursementapplication.setEmployeeid(Integer.parseInt(info.get("employeeid")));
		reimbursementapplication.setStatusid(0);
		reimbursementapplication.setEventid(Integer.parseInt(info.get("eventid")));
		reimbursementapplication.setAmount(Float.parseFloat(info.get("amount")));
		reimbursementapplication.setApproved(0);
		System.out.println(reimbursementapplication.toJSON());
		try {
			if(ReimbursementApplicationDAO.insertReimbursementApplication(reimbursementapplication)) {
				response.setStatus(200);
			} else {
				response.setStatus(418);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("New reimbursement application servlet encountered SQLException");
			response.setStatus(500);
		}
	}
}
