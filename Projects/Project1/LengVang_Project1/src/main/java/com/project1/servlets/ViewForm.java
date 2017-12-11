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
import com.project1.dao.EmployeeDao;
import com.project1.dao.FormDao;

/**
 * Servlet implementation class ViewForm
 */
public class ViewForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Employee currEmp = (Employee) session.getAttribute("currEmp");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		QuickFunction.insertBoostrap(response);
		QuickFunction.insertNavBar(response);
		
		FormDao fdao = new FormDao();
		Form currForm = fdao.getFormById(Integer.parseInt(request.getParameter("ReqIdSelected")), currEmp.getEmpPos());
		
		if(currForm == null) {
			out.println("<h3>Form not found. Please go back and look at the table for an appropriate request ID</h3>");
		}else {
			EmployeeDao edao = new EmployeeDao();
			Employee formEmp = edao.getEmp(currForm.getEmployeeID());
			out.println("<h3>Currently Viewing Request ID:" + currForm.getRequestID() + "</h3>");
			out.println("<div class = 'well input'> <p> ");
			out.println("Name: " + formEmp.getFname() + " ");
			if(formEmp.getMname() != null) {
				out.println(formEmp.getMname() + " ");
			}
			out.println(formEmp.getLname() + " ");
			out.println("Employee ID: " + formEmp.getEmployeeID() + " ");
			out.println("Email: " + formEmp.getEmail() + " ");
			out.println("Reimbursement Amount: $" + currForm.getAmount() + "</p></div><br>");
			
			session.setAttribute("currForm", currForm);
			out.println("<br> <a href='Approve'>Approve Reimbursement</a>");
			out.println("<br> <a href='Deny'>Deny Reimbursment</a>");
			
			out.println("<div align='center'><object width=50% height=50% data='" + currForm.getFileName() + "'></object></div>");
			//out.println("</div>");
		}
		
		out.println("<br> <a href='DSReview'>Back</a>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
