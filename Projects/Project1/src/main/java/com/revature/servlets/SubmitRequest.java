package com.revature.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Request;
import com.revature.dao.TRMSDao;

/**
 * Servlet implementation class SubmitRequest
 */
@MultipartConfig
public class SubmitRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
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
		File file = null;
//		File file = new File(request.getParameter("fileupload"));
		
		// expected input format in YYYY-MM-DD???
		SimpleDateFormat in = new SimpleDateFormat("dd-MM-yy");
		String eventDate = request.getParameter("dateOfEvent");
//		java.util.Date date = in.parse(eventDate);

//		// output format
//		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//		out.println(fmt.format(date));
////		Date date = new Date();
		
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		System.out.println("user submitting the request: " + username);
		
		System.out.println("eventName: " + eventName + "cost: " + cost);
		
//		Request req = new Request(username, eventName, location, description, cost, gradingFormat, date, file);
		
		TRMSDao dao = TRMSDao.getDao(); //grab our dao
//		dao.insertRequest(req);
	}
}