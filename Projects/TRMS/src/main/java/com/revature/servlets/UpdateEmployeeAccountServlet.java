package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.EmployeeAccount;
import com.revature.daos.EmployeeAccountDAO;

public class UpdateEmployeeAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeAccount employeeaccount;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			employeeaccount = new EmployeeAccount();
			employeeaccount.setUsername(info.get("username"));
			employeeaccount.setPassword(info.get("password"));
			employeeaccount.setFirstname(info.get("firstname"));
			employeeaccount.setMiddlename(info.get("middlename"));
			employeeaccount.setLastname(info.get("lastname"));
			employeeaccount.setEmail(info.get("email"));
			employeeaccount.setAddress(info.get("address"));
			employeeaccount.setCity(info.get("city"));
			employeeaccount.setState(info.get("state"));
			employeeaccount.setLocked(false);
			if(Integer.parseInt(info.get("isadmin")) == 1) {
				employeeaccount.setAdmin(true);
			} else {
				employeeaccount.setAdmin(false);
			}
			employeeaccount.setEmployeeid(Integer.parseInt(info.get("employeeid")));
			EmployeeAccountDAO.updateEmployeeAccount(employeeaccount);
			response.setStatus(200);
		} catch (SQLException e) {
			System.err.println("Create employee account servlet encountered SQLException");
			response.setStatus(500);
		}
	}
}
