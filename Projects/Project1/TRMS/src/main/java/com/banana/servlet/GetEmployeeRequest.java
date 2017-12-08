package com.banana.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banana.service.GetAllData;
import com.banana.service.GetEmployeeData;

/**
 * Servlet implementation class GetEmployeeRequest
 */
public class GetEmployeeRequest extends HttpServlet {
	
	//TODO session retention issue; when you back up to the login, the session is saved as intended but this screws up the new logined in user
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println("get");
		if(!session.isNew()) {
			GetAllData.getAllData(response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer empId = (Integer)session.getAttribute("empId");
		System.out.println("post");
		if(!session.isNew() && empId != null) {
			System.out.println("post here");
			GetEmployeeData.getEmployeeData(response, empId);
		}
	}

}
