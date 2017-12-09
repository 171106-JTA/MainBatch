package com.revature.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;

import com.revature.dao.TRMSDao;

/**
 * Servlet implementation class AssignEmployeeToDirectSupervisor
 */
@MultipartConfig
public class AssignEmployeeToDirectSupervisor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AssignEmployeeToDirectSupervisorServlet");
		
		RequestDispatcher rd = null;
		String dirSupUsername = (String) request.getParameter("pickDirSup");
		String empUsername = (String) request.getParameter("pickEmployee");
		
		TRMSDao dao = TRMSDao.getDao();
		dao.assignDirectSupervisor(empUsername, dirSupUsername);
		rd = request.getRequestDispatcher("DepHead.html");
		rd.include(request, response);
	}
}