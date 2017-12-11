package com.project1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project1.classes.Employee;
import com.project1.classes.Form;
import com.project1.dao.FormDao;

/**
 * Servlet implementation class DenyFinish
 */
public class DenyFinish extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Employee currEmp = (Employee) session.getAttribute("currEmp");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		QuickFunction.insertBoostrap(response);
		QuickFunction.insertNavBar(response);
		
		Form currForm = (Form) session.getAttribute("currForm");
		String reason = request.getParameter("reasonDeny");
		reason = "Reimbursement denied. Reason: " + reason;
		System.out.println(reason);
		currForm.setStatus(reason);
		currForm.setCurrViewer(0);
		FormDao fdao = new FormDao();
		fdao.updateForm(currForm);
		
		out.println("<h3>Form successfully denied.</h3>");
		out.println("<br> <a href='DSReview'>View More Forms</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
