package com.revature.trms.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.trms.service.Login;

/**
 * Servlet implementation class FrontController
 * 
 * Should only redirect and not handle any business logics
 * 
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = request.getRequestURI();
		System.out.println(url);
		
		String[] split = url.split("/");
		String action = split[split.length-1].split(".")[0];
		
		System.out.println(action);
		
		RequestDispatcher rd = null;
		HttpSession session = null;
		
		switch(action) {
		case "hello":
			rd = request.getRequestDispatcher("HeyBobbert");
			rd.forward(request, response);
			break;
		case "login":
			request.setAttribute("issue", null); // Stores any error messages that occurs
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Login login = new Login();
			if(login.validateLogin(username, password)) {
				session = request.getSession();
				session.setAttribute("user", request.getAttribute("username"));
			}else {
				request.setAttribute("issue", "INVALID CREADENTIALS");
			}
			rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			if(!(request.getAttribute("issue") == null)) {
				out.println("<h3 style='color:red'>" + request.getAttribute("issue") + "</h3>");
			}
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
