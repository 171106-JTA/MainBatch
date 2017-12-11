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
 * Servlet implementation class AlterFinish
 */
public class AlterFinish extends HttpServlet {
	

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
		double alteredAmount = Double.parseDouble(request.getParameter("alterAmount"));
		System.out.println("Altered Amount 1: " + alteredAmount);
		Form currForm = (Form) session.getAttribute("currForm");
		currForm.setAlterApproved(1);
		currForm.setAlteredAmount(alteredAmount);
		System.out.println("Altered Amount 2: " + currForm.getAlteredAmount());
		currForm.setStatus("Amount altered. Awaiting alteration approval from employee.");
		FormDao fdao = new FormDao();
		fdao.alterForm(currForm);
		
		out.println("<h3>Alteration confirmed. Approval from Employee is now pending.");
		out.println("<br> <a href='BenReview'>View More Form</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
