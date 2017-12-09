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

import com.revature.beans.Employee;
import com.revature.beans.EmployeeAccount;
import com.revature.daos.EmployeeAccountDAO;
import com.revature.daos.EmployeeDAO;

public class CreateEmployeeAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeAccount employeeaccount;
	private Employee employee;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader content = request.getReader();
		String[] contents = content.readLine().split("&");
		String[] keyvalue = new String[2];
		Map<String, String> info = new HashMap<String, String>();
		for(int i = 0; i < contents.length; i++) {
			keyvalue = contents[i].split(":");
			info.put(keyvalue[0], keyvalue[1]);
			System.out.println(keyvalue[0] + " : " + keyvalue[1]);
		}
		try {
			employee = new Employee();
			employee.setEmployeeid(Integer.parseInt(info.get("employeeid")));
			employee.setPositionid(Integer.parseInt(info.get("positionid")));
			employee.setReportsto(Integer.parseInt(info.get("reportsto")));
			employee.setFacilityid(Integer.parseInt(info.get("facilityid")));
			if(EmployeeDAO.isEmployee(employee)) {
				System.out.println("IS AN EMPLOYEE");
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
				if(employee.getPositionid() > 2) {
					employeeaccount.setAdmin(true);
				} else {
					employeeaccount.setAdmin(false);
				}
				employeeaccount.setEmployeeid(Integer.parseInt(info.get("employeeid")));
				EmployeeAccountDAO.insertEmployeeAccount(employeeaccount);
				System.out.println("Done");
				response.setStatus(200);
			} else {
				response.setStatus(403);
			}
		} catch (SQLException e) {
			System.err.println("Create employee account servlet encountered SQLException");
			e.printStackTrace();
			response.setStatus(500);
		}
	}
}