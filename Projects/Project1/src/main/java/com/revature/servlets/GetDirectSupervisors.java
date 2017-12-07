package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Employee;
import com.revature.dao.TRMSDao;

/**
 * Servlet implementation class GetDirectSupervisors
 */
public class GetDirectSupervisors extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GetDirectSupervisors");
		TRMSDao dao = TRMSDao.getDao();
		List<Employee> dirSups = dao.getDirectSupervisors();
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		if (!dirSups.isEmpty()) {
			String myXml = "<root>";

			for (Employee e : dirSups) {
				myXml += "<employee><username>" + e.getUsername() + "</username>" + "<FName>" + e.getFname() + "</FName>"
						+ "<LName>" + e.getLname() + "</LName></employee>";
			}
			myXml += "</root>";
			
			out.println(myXml);
		}
		else{
			out.println("<root></root>");
		}
	
	
	}
}