package com.project1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project1.classes.Employee;

/**
 * Servlet implementation class CreateForm
 */
public class CreateForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateForm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		QuickFunction.insertBoostrap(response);
		QuickFunction.insertNavBar(response);
		out.println("<title>Creating A Form</title>");
		
		if(session.isNew()) {
			out.println("<h3>You're not logged in.</h3>");
			session.invalidate();
		}else {
			Employee currEmp = (Employee)session.getAttribute("currEmp");
			//out.println(currEmp);
			out.println("<form action=\"SubmitFormDoc\" method=\"post\" name=\"formRequest\">\r\n" + 
					"			<!-- TO BE CHANGED -->\r\n" + 
					"			<h4>Fill our your form down below.</h4>\r\n" + 
					"			<b><p id=\"error\" style=\"color:red\"></p></b>\r\n" + 
					"			<ul style=\"list-style-type: none;\">\r\n" + 
					"        <li>What kind of reimbursement are you getting?</li>\r\n" + 
					"        <select name='RemType'>\r\n" + 
					"          <option value=\"Courses\">Courses</option>\r\n" + 
					"          <option value=\"Seminars\">Seminars</option>\r\n" + 
					"          <option value=\"CertPrepClass\">Certification Preparation Classes</option>\r\n" + 
					"          <option value=\"Certification\">Certification</option>\r\n" + 
					"          <option value=\"TechTraining\">Tech Training</option>\r\n" + 
					"          <option value=\"Other\">Other</option>\r\n" + 
					"        </select>\r\n" + 
					"				<li>Amount</li>\r\n" + 
					"				<li><input type=\"text\" name=\"amount\" placeholder=\"Enter Amount\"\r\n" + 
					"					required></li>\r\n" + 
					"				<li><input type=\"reset\" value=\"reset\"></li>\r\n" + 
					"				<li><input type=\"submit\" value=\"Submit Form\"></li>\r\n" + 
					"			</ul>\r\n" + 
					"		</form>"
					
					);
		}
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
