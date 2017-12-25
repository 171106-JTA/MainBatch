package com.revature.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.TRMSDao;
import com.revature.services.Service;

/**
 * Servlet implementation class ApplicationSubmit
 * Submit application to database based on page information
 */
public class ApplicationSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplicationSubmit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TRMSDao dao= new TRMSDao();
		HttpSession session= request.getSession();
		int gradeType=Integer.parseInt(request.getParameter("gradeType"));
		if(gradeType==0)//if custom grade type
		{
			boolean presentReq=false;
			String present = request.getParameter("present");
			if(present.equals("present"))//determine if presentation is needed from checkbox
			{
				presentReq=true;
			}
			String passGrade= request.getParameter("customPass");
			//add application with custom grade to database
			dao.addApplication(request, (int)session.getAttribute("id"),request.getParameter("eventDate"), passGrade, presentReq);
		}
		else {
			//add application to database
			dao.addApplication(request, (int)session.getAttribute("id"),request.getParameter("eventDate"));
		}
		//redirect based on user status
		if(Service.canApprove((int)session.getAttribute("id")))
		{
		
			RequestDispatcher rd = request.getRequestDispatcher("accountSup.html"); 
			rd.include(request, response);
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("account.html");
			rd.include(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
