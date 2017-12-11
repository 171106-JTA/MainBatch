package com.project1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project1.classes.Employee;
import com.project1.services.createForm;

/**
 * Servlet implementation class FormRequest
 */
public class FormRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		out.println("<title>Form Submitted</title>");
		
		createForm.submitForm(request);
		
		out.println("<h3>Form submitted!</h3>");
		
		
		out.println("<br><div class='well input'> <form action='Login' method='post'>\r\n" + 
				"			<input type=\"submit\" value=\"Back\">\r\n" + 
				"		</form></div>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
