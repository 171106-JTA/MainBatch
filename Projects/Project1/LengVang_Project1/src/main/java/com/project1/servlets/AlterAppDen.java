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
 * Servlet implementation class AlterAppDen
 */
public class AlterAppDen extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Employee currEmp = (Employee) session.getAttribute("currEmp");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		QuickFunction.insertBoostrap(response);
		QuickFunction.insertNavBar(response);
		
		FormDao fdao = new FormDao();
		Form currForm = fdao.getFormById(Integer.parseInt(request.getParameter("ReqIdSelected")), 3);
		
		if(currForm == null) {
			out.println("<h3>Form not found. Please go back and look at the table for an appropriate request ID</h3>");
		}else {
			String appDen = (request.getParameter("AppDen"));
			System.out.println(appDen);
			if(appDen.equals("Approve")) {
				currForm.setAlterApproved(0);
				currForm.setAmount(currForm.getAlteredAmount());
				currForm.setStatus("Alteration approved.");
				fdao.alterForm(currForm);
				out.println("<h3>Alteration Approved.</h3>");
			}
			else {
				currForm.setAlterApproved(0);
				currForm.setCurrViewer(0);
				fdao.alterForm(currForm);
				fdao.updateForm(currForm);
				currForm.setStatus("Alteration denied by employee. Employee canceled reimbursement.");
				out.println("<h3>Alteration Denied</h3>");
			}
		}
		out.println("<a href='ViewAlteredRem'>View More</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
