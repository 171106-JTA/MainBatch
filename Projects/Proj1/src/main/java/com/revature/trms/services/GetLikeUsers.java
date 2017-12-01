package com.revature.trms.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.revature.trms.dao.EmployeeDAO;
import com.revature.trms.model.Employee;

public class GetLikeUsers {
	public static void getUsers(HttpServletResponse response, String username) throws IOException {
		EmployeeDAO ed = new EmployeeDAO();
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		List<Employee> employees = ed.selectEmployeeByLikeUsername(username);

		if (employees != null) {
			String myXml = "<root>";

			for (Employee emp : employees) {
				myXml += "<trainer>" + "<username>" + emp.getUsername() + "</username>" + "<fname>" + emp.getFname()
						+ "</fname>" + "<lname>" + emp.getLname() + "</lname></trainer>";
			}
			myXml += "</root>";
			out.println(myXml);
		}
		else {
			out.println("<root></root>");
		}
	
	}
}
