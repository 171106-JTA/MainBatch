package com.revature.trms.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.trms.dao.ReimCaseDAO;
import com.revature.trms.model.ReimbursementCase;

public class AddNewReimCase {
	public static void addReimCase(HttpServletRequest request, HttpServletResponse response) {
		String eventDatestr = request.getParameter("eventDate");
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		
		Date eventDate = null;
		try {
			eventDate = sdf.parse(eventDatestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpSession session = request.getSession(true);
		String employeeId = String.valueOf((session.getAttribute("employeeid")));
		
		ReimCaseDAO reimdb = new ReimCaseDAO();
		ReimbursementCase reimCase = new ReimbursementCase(
				employeeId,
				eventDate,
				Integer.parseInt(request.getParameter("eventDuration")),
				request.getParameter("eventLocation"),
				request.getParameter("eventType"),
				request.getParameter("gradingFormat"),
				Double.parseDouble(request.getParameter("cost")),
				request.getParameter("eventDescription")
				);
		System.out.println(reimCase);
		System.out.println(session.getAttribute("username"));
		System.out.println(session.getAttribute("employeeid"));
		try {
			PrintWriter out = response.getWriter();
			if(reimdb.insertNewReimCase(reimCase)) {
				out.println("<h1>You've successfully added a reimbursement request.");
			}
			else {
				out.println("<h1>Whoops, something went wrong</h1>");
			}
			out.println("<hr>" + "<a href='make_request.html'>BACK</a>");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
