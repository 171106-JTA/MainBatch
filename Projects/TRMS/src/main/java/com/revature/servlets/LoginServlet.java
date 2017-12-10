package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.EmployeeAccount;
import com.revature.daos.EmployeeAccountDAO;
import com.revature.utilities.CloserUtility;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeAccount employeeaccount;
	private PrintWriter write;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		employeeaccount = null;
		try {
			BufferedReader content = request.getReader();
			String[] contents = content.readLine().split("&");
			String[] keyvalue = new String[2];
			Map<String, String> info = new HashMap<String, String>();
			for(int i = 0; i < contents.length; i++) {
				keyvalue = contents[i].split(":");
				info.put(keyvalue[0], keyvalue[1]);
				System.out.println(keyvalue[0] + " : " + keyvalue[1]);
			}
			System.out.println(info.get("username") + info.get("password"));
			employeeaccount = EmployeeAccountDAO.getEmployeeAccount(info.get("username"), info.get("password"));
			if(employeeaccount == null) {
				response.setStatus(204);
			} else {
				response.setContentType("application/json");
				write = response.getWriter();
				write.write(employeeaccount.toJSON());
				response.setStatus(200);
			}
		} catch(IOException e) {
			System.err.println("Login servlet encountered IOException.");
			response.setStatus(500);
		} catch(SQLException e) {
			System.err.println("Login servlet encountered SQLException.");
			response.setStatus(500);
		} finally {
			System.out.println("LoginServlet end");
			CloserUtility.close(write);
		}
	}
}
