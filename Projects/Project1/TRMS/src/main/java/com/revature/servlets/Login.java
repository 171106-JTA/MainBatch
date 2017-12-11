package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.TRMSDao;
import com.revature.services.Service;

/**
 * Servlet implementation class Login
 * If username and password match a record in the database, redirect user to appropiate account page based on status and save id to session
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String uname=(String)request.getParameter("username");
		String pword=(String)request.getParameter("password");
			
		boolean validLogin= Service.checkLogin(uname,pword);
		if(validLogin)//if information matchess a record
		{
			TRMSDao dao= new TRMSDao();
			int id= dao.getId(uname);
			
			HttpSession session = request.getSession();
			session.invalidate();//invalidate existing session on new login
			session=request.getSession();
			
			session.setAttribute("id", id);//save id to session

			if(Service.canApprove(id))//if benco, chair, or supervisor
			{
				RequestDispatcher rd = request.getRequestDispatcher("accountSup.html"); 
				rd.include(request, response);
			}
			else//base employee
			{
				RequestDispatcher rd = request.getRequestDispatcher("account.html");
				rd.include(request, response);
			}
			
		}
		else//input not matching any records
		{
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
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
