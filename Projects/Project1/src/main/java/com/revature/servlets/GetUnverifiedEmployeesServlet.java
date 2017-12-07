package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.beans.Request;
import com.revature.dao.TRMSDao;

/**
 * Servlet implementation class GetUnverifiedUsers
 */
public class GetUnverifiedEmployeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GetUnverifiedEmployees");
		TRMSDao dao = TRMSDao.getDao();
		List<Employee> unvEmps = dao.getUnverifiedEmployees();
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();

		if (!unvEmps.isEmpty()) {
			String myXml = "<root>";

			for (Employee e : unvEmps) {
				myXml += "<employee><username>" + e.getUsername() + "</username>" + "<FName>" + e.getFname() + "</FName>"
						+ "<LName>" + e.getLname() + "</LName></employee>";
			}
			myXml += "</root>";

			out.println(myXml);
		}
		else{
			out.println("<root><employee><username>None</username><FName>None</FName>"
					+ "<LName>None</LName></employee></root>");
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
