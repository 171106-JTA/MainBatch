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
		if(validLogin)
		{
			TRMSDao dao= new TRMSDao();
			int id= dao.getId(uname);
			System.out.println(id);
			
			HttpSession session = request.getSession();
			session.invalidate();//invalidate existing session on new login
			session=request.getSession();
			
			session.setAttribute("id", id);

			if(Service.canApprove(id))
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
		else
		{
			System.out.println("not working");
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
