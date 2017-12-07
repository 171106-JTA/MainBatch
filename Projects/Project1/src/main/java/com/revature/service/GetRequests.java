package com.revature.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.revature.beans.Request;
import com.revature.dao.TRMSDao;

public class GetRequests {
	public static void getRequests(HttpServletResponse response, String username) throws IOException {
		TRMSDao dao = TRMSDao.getDao();

		List<Request> requests = dao.getRequests(username);
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();

		if (requests != null) {
			String myXml = "<root>";

			for (Request r : requests) {
				myXml += "<request><username>" + r.getUsername() + "</username>" + "<Event>" + r.getEvent() + "</Event>"
						+ "<cost>" + r.getCost() + "</cost>" + "<submissionDate>" + r.getSubmissionDate().toString() + "</submissionDate>" 
						+ "<dateOfEvent>" + r.getDateOfEvent().toString() + "</dateOfEvent>" + "</request>";
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