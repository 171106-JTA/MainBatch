package com.revature.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Employee;
import com.revature.dao.EmployeeDao;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmployeeDao ed = new EmployeeDao();
		Employee em = ed.getEmployeeByUsername(request.getParameter("usrnm"));
		if((em!=null)&&(request.getParameter("pswrd").equals(em.getPswrd()))){
			request.setAttribute("currentEm", em);
			RequestDispatcher view = request.getRequestDispatcher("account.jsp");
			view.forward(request, response);
		}
	}

}
