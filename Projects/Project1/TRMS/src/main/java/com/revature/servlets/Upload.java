package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Employee;
import com.revature.beans.Request;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.dao.RequestDaoImpl;

/**
 * Servlet implementation class Upload
 */
public class Upload extends HttpServlet {
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
		int empId = Integer.parseInt(request.getParameter("empId"));
		String password = request.getParameter("password");
		System.out.println(email);
		System.out.println(empId);
		System.out.println(password);
		
		int reqId = Integer.parseInt(request.getParameter("reqId"));
		System.out.println(reqId);
		
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
		
		pw.write("Please Select file to upload");
		pw.write("<form id=\"fileUpload\" action=\"UploadFile\" method=\"post\" enctype=\"multipart/form-data>\r\n" + 
				"    <input type=\"hidden\" name=\"test\" id=\"test\" value=\"success\"/>\r\n" + 
				"    File: <input type=\"file\" name=\"file\" id=\"file\"/>\r\n" + 
				"	 <input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">" +
				"	 <input type=\"hidden\" name=\"password\" value=\"" + password + "\">" +
				" 	 <input type=\"hidden\" name=\"email\" value=\"" + email + "\">" +
				"    <input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">" +
				"    <input type=\"submit\" id=\"submit\" value=\"Upload\"/>\r\n" + 
				"  </form>");
		
		pw.write(" <script type=\"text/javascript\" src=\"JavaScript/upload.js\"></script>");
		
	}

}
