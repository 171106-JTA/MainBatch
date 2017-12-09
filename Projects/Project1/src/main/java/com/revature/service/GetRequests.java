package com.revature.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Employee;
import com.revature.beans.Request;
import com.revature.dao.TRMSDao;

public class GetRequests {
	public static void getRequests(HttpServletResponse response, String username) throws IOException {
		TRMSDao dao = TRMSDao.getDao();

		List<Request> requests = dao.getRequestsAsDirectSupervisor(username);
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();

		if (requests != null && !requests.isEmpty()) {
			String myXml = "<root>";

			for (Request r : requests) {
				Employee emp = r.getEmp();
				System.out.println("submissionDate: " +  r.getSubmissionDate().toString());
				System.out.println("dateOfEvent: " +  r.getDateOfEvent().toString());

				myXml += "<request><username>" + emp.getUsername() + "</username><fname>" + emp.getFname() + "</fname>" +
						"<lname>" + emp.getLname() + "</lname><Event>" + r.getEvent() + "</Event>" +
						"<location>" + r.getLocation() + "</location>" +
						"<cost>" + r.getCost() + "</cost><description>" + r.getDescription() + "</description>" +
						"<gradingFormat>" + r.getGradingFormat() + "</gradingFormat>" +
						"<submissionDate>" + r.getSubmissionDate().toString() + "</submissionDate>" +
						"<dateOfEvent>" + r.getDateOfEvent().toString() + "</dateOfEvent></request>";
			}
			myXml += "</root>";
			out.println(myXml);
		} else {
			out.println("<root><request><username>none</username><Event>none</Event>"
					+ "<cost>none</cost><submissionDate>none</submissionDate>" 
					+ "<dateOfEvent>none</dateOfEvent></request></root>");
		}
	}
}