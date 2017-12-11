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
 * Servlet implementation class Approve
 */
public class Approve extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Employee currEmp = (Employee) session.getAttribute("currEmp");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		QuickFunction.insertBoostrap(response);
		QuickFunction.insertNavBar(response);
		
		Form currForm = (Form) session.getAttribute("currForm");
		currForm.setCurrViewer(currEmp.getEmpPos() + 1);
		if(currEmp.getEmpPos() == 1) {
			System.out.println("Updated 1");
			currForm.setStatus("Recently Approved by Supervisor. Pending for approval by Department Head.");
		}
		else if (currEmp.getEmpPos() == 2) {
			System.out.println("Updated 2");
			currForm.setStatus("Recently Approved by Department Head. Pending for approval by the Ben Co.");
		}
		FormDao fdao = new FormDao();
		fdao.updateForm(currForm);
		
		out.println("<h3> Form approved! </h3>"); //Not Fully Implemented yet.
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
