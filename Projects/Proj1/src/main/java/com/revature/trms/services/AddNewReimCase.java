package com.revature.trms.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.trms.model.ReimbursementCase;

public class AddNewReimCase {
	public static boolean addReimCase(HttpServletRequest request) {
		String eventDatestr = request.getParameter("eventDate");
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
		Date eventDate = null;
		try {
			eventDate = sdf.parse(eventDatestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpSession session = request.getSession(true);
		String employeeId = (String)session.getAttribute("employeeId");
		ReimbursementCase reimCase = new ReimbursementCase(
				employeeId,
				eventDate,
				Integer.parseInt(request.getParameter("eventDuration")),
				request.getParameter("eventLocation"),
				request.getParameter("eventType"),
				request.getParameter("gradingFormat"),
				Double.parseDouble("cost"),
				request.getParameter("eventDescription")
				);
		return true;
	}
}
