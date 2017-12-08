package com.revature.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.revature.beans.Employee;
import com.revature.beans.Request;
import com.revature.dao.TRMSDao;

/**
 * Servlet implementation class SubmitRequest
 */
@MultipartConfig
public class SubmitRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("We are inserting a request new print");
		RequestDispatcher rd = null;
		
		final TRMSDao dao = TRMSDao.getDao(); //grab our dao
		final HttpSession session = request.getSession();
		final String username = (String) session.getAttribute("username");
		
		//grab data from input fields
		final String eventName = request.getParameter("event");
		final String gradingFormat = request.getParameter("gradingFormat");
		final String description = request.getParameter("description");
		final String location = request.getParameter("location");
		final double cost = Double.valueOf(request.getParameter("eventCost"));

		//get the file attachment
		InputStream inputStream = null;
		final Part part = request.getPart("fileUpload");
		final String filename = extractFileName(part);
		if (filename != null && filename.length() > 0) {
			inputStream = part.getInputStream();
		}

		//get the date of event
		final String eventDate = request.getParameter("dateOfEvent");
		final String[] datesplit = eventDate.split("-");
		final int[] dateints = new int[3];
		for (int i = 0; i < datesplit.length; i++) {
			dateints[i] = Integer.parseInt(datesplit[i]);
		}
		final Date date = new Date(dateints[0], dateints[1], dateints[2]);

		final Request req = new Request(username, eventName, location, description, cost, gradingFormat, date, inputStream);
		boolean success = dao.insertRequest(req);
		
		if (success) {
			System.out.println("going to request submitted success page");
			response.sendRedirect("RequestSuccessfullySubmitted.html");
		} else {
			System.out.println("going to request submitted unsuccess page");
			response.sendRedirect("RequestUnsuccessfullySubmitted.html");
		}
	}

	/**
	 * This code is used to grab the file name of the file.
	 * Source for this code belongs to: http://o7planning.org/en/10839/uploading-and-downloading-files-from-database-using-java-servlet#a3594170
	 * That reference provided useful information to store files to my database.
	 * @param part
	 * @return the filename
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {

				String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
				clientFileName = clientFileName.replace("\\", "/");
				int i = clientFileName.lastIndexOf('/');

				return clientFileName.substring(i + 1);
			}
		}
		return null;
	}
}