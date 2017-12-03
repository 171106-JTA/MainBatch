package com.revature.servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Request;
import com.revature.dao.TRMSDao;

/**
 * Servlet implementation class SubmitRequest
 */
public class SubmitRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("we are inserting a request");
		
		String eventName = request.getParameter("event");
		String gradingFormat = request.getParameter("gradingFormat");
		String description = request.getParameter("description");
		String location = request.getParameter("location");
		Integer cost = Integer.valueOf(request.getParameter("eventCost"));
		File file = new File(request.getParameter("fileupload"));
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		Request req = new Request(username, eventName, location, description, cost, gradingFormat, file);
		
		TRMSDao dao = TRMSDao.getDao(); //grab our dao
		dao.insertRequest(req);
	}

}
