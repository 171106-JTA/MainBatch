package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.revature.beans.Request;
import com.revature.dao.RequestDaoImpl;
import com.revature.util.FileUploader;

/**
 * Servlet implementation class UploadFile
 */
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		System.out.println(email);
		int empId = Integer.parseInt(request.getParameter("empId"));
		System.out.println(empId);
		String password = request.getParameter("password");
		System.out.println(password);
		int reqId = Integer.parseInt(request.getParameter("reqId"));
		System.out.println(reqId);
		
		String file = request.getParameter("file");
		System.out.println(file);
		
		PrintWriter pw = response.getWriter();
		
		RequestDaoImpl reqDao = new RequestDaoImpl(); 
		Request req = null;
		
		try {
			req = reqDao.getRequest(reqId);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		pw.write("<header>test</header>");
		pw.write(" <script type=\"text/javascript\" src=\"JavaScript/header.js\"></script>");
		pw.write("<form action=\"Login\" method=\"POST\">\r\n" +
				"<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">" +
				"<input type=\"hidden\" name=\"password\" value=\"" + password + "\">" +
				"<input type=\"hidden\" name=\"email\" value=\"" + email + "\">" +
				"<input type=\"submit\" value=\"Return to Home\"> " +
			"</form>");
	    pw.write("<hr>");
	
		pw.write("<h3>Request ID: " + req.getId() + "</h3>");
		pw.write("<hr>");


//		//Upload file
//		Part filePart = request.getPart("file");
//		if(FileUploader.upload(filePart, reqId)) {
//			pw.write("{\"alert\" : \"Successfully uploaded attachment.\"}");
//		} else {
//			pw.write("{\"alert\" : \"Failed to upload attachment.\"}");
//		}
//		request.get
	}

}
