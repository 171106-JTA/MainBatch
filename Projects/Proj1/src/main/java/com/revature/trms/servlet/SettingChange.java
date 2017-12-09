package com.revature.trms.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.trms.dao.EmployeeDAO;
import com.revature.trms.dao.ReimCaseDAO;

/**
 * Servlet implementation class SettingChange
 */
public class SettingChange extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		EmployeeDAO ed = new EmployeeDAO();
		String changeCode = request.getParameter("changeCode");
		String userType = request.getParameter("userType");
		int supervisorId =0; 
		if (request.getParameter("supervisorId") != "") {
			supervisorId = Integer.getInteger(request.getParameter("supervisorId"));
		}
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		if (changeCode.equals("change")) {
			ed.updateUserType((String) session.getAttribute("username"), userType);
			out.println(userType);
			out.println("usertype updated");
		}
		System.out.println(supervisorId);
		out.println("<hr>" + "<a href='make_approval.html'>BACK</a>");
	}

}
