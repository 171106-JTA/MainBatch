package com.revature.servlets;

import java.io.IOException;
import java.sql.SQLException;

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
		try {
			employee.setEmployeeid(Integer.parseInt(request.getParameter("employeeid")));
			employee.setPositionid(Integer.parseInt(request.getParameter("position")));
			employee.setReportsto(Integer.parseInt(request.getParameter("reportsto")));
			employee.setFacilityid(Integer.parseInt(request.getParameter("facilityid")));
			if(EmployeeDAO.isEmployee(employee)) {
				employeeaccount = new EmployeeAccount();
				employeeaccount.setUsername(request.getParameter("username"));
				employeeaccount.setPassword(request.getParameter("password"));
				employeeaccount.setFirstname(request.getParameter("firstname"));
				employeeaccount.setMiddlename(request.getParameter("middlename"));
				employeeaccount.setLastname(request.getParameter("lastname"));
				employeeaccount.setEmail(request.getParameter("email"));
				employeeaccount.setAddress(request.getParameter("address"));
				employeeaccount.setCity(request.getParameter("city"));
				employeeaccount.setState(request.getParameter("state"));
				employeeaccount.setLocked(false);
				if(employee.getPositionid() > 2) {
					employeeaccount.setAdmin(true);
				} else {
					employeeaccount.setAdmin(false);
				}
				employeeaccount.setEmployeeid(Integer.parseInt(request.getParameter("employeeid")));
				EmployeeAccountDAO.insertEmployeeAccount(employeeaccount);
				response.setStatus(200);
			} else {
				response.setStatus(403);
			}
		} catch (SQLException e) {
			System.err.println("Create employee account servlet encountered SQLException");
			response.setStatus(500);
		}
	}
}