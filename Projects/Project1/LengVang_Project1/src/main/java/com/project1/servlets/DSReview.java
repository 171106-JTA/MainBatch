package com.project1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project1.classes.Employee;
import com.project1.classes.Form;
import com.project1.dao.FormDao;

/**
 * Servlet implementation class DSReview
 */
public class DSReview extends HttpServlet {
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
		out.println("<title>Reviewing Forms</title>");
		
		FormDao fdao = new FormDao();
		List<Form> forms = new ArrayList<Form>();
		forms = fdao.getFormsMatching(currEmp.getEmpPos());
		
		out.println("<div class='well input'>");
		out.println("<h3>Forms currently awaiting approval.</h3>");
		
		out.println("<table border='2px'>\r\n" + 
				"			<tr><th>Request ID</th><th>Employee ID</th><th>Reimbursement Type</th><th>Reimbursement Amount</th><th>Date Submitted</th><th>Message</th></tr>");
		for(Form f: forms) {
			out.println("<tr><td>" + f.getRequestID() + "<td>" + f.getEmployeeID() + "</td><td>" + f.getTypeRem() + "</td><td>" + f.getAmount() + "</td><td>" + f.getDateSub() + "</td><td>" + f.getStatus() + "</td></tr>");
		}
		
		out.println("</table>");
		
		out.println("<br><form action='ViewForm'>"
				+ "			<input type='number' name='ReqIdSelected' required>"
				+ "			<input type='submit' value='View Request Number'>"
				+ "		</form>");
		
		out.println("</div>");
		
		out.println("<br> <form action='Login' method='post'>\r\n" + 
				"			<input type=\"submit\" value=\"Back\">\r\n" + 
				"		</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
