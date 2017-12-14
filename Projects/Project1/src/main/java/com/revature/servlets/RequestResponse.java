package com.revature.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.TRMSDao;

/**
 * Servlet implementation class RequestResponse
 */
public class RequestResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RequestResponse");
		String reqResponse = (String) request.getParameter("approveDenyRequest");
		int reqIDInput = Integer.parseInt(request.getParameter("reqIDInput"));
		
		if (reqResponse.equals("Approve")) {
			
			TRMSDao dao = TRMSDao.getDao();
			dao.approveDenyRequest(reqIDInput, "APPROVED");
		} else {
			TRMSDao dao = TRMSDao.getDao();
			dao.approveDenyRequest(reqIDInput, "DENIED");
		}
		RequestDispatcher rd = request.getRequestDispatcher("DirSup.html");
		rd.include(request, response);
	}
}