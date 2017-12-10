package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Employee;
import com.revature.beans.Request;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.dao.RequestDaoImpl;

/**
 * Servlet implementation class NewRequest
 */
public class NewRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		int empId = Integer.parseInt(request.getParameter("empId"));
		String password = request.getParameter("password");
		System.out.println(email);
		System.out.println(empId);
		System.out.println(password);
		
		int eventType = 0;
		switch (request.getParameter("event")) {
		case("university"):
			eventType = 0;
			break;
		case("seminar"):
			eventType = 1;
			break;
		case("certificationPrep"):
			eventType = 2;
			break;
		case("certification"):
			eventType = 3;
			break;
		case("technical"):
			eventType = 4;
			break;
		case("other"):
			eventType = 5;
			break;				
		}
			
		PrintWriter pw = response.getWriter();
			
		RequestDaoImpl reqDao = new RequestDaoImpl();
		Request req = new Request();
		EmployeeDaoImpl empDao = new EmployeeDaoImpl();
		Employee emp = new Employee();
		
		double reimburseAmt = CalcReimburseAmt(Double.parseDouble(request.getParameter("cost")), 
												eventType);
		
		req.setEmployeeId(empId);
		req.setCost(reimburseAmt);
		req.setDate(request.getParameter("date"));
		req.setStreetAddress(request.getParameter("addr"));
		req.setCity(request.getParameter("city"));
		req.setState(request.getParameter("state"));
		req.setZip(request.getParameter("zip"));
		req.setDescription(request.getParameter("description"));
		req.setStatus(Integer.parseInt(request.getParameter("status")));
		
		int grading = 0;
		switch (request.getParameter("grading")) {
			case("Percentage"):
				grading = 0;
				break;
			case("Pass Fail"):
				grading = 1;
		}
		req.setGradingFormat(grading);
		req.setDaysMissed(Integer.parseInt(request.getParameter("days")));
		req.setJustification(request.getParameter("justification"));
			
		System.out.println(req.toString());
		
		pw.write("<header>test</header>");
		pw.write(" <script type=\"text/javascript\" src=\"JavaScript/header.js\"></script>");

		pw.write("<form action=\"Login\" method=\"POST\">\r\n" +
				"<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">" +
				"<input type=\"hidden\" name=\"password\" value=\"" + password + "\">" +
				"<input type=\"hidden\" name=\"email\" value=\"" + email + "\">" +
				"<input type=\"submit\" value=\"Return to Home\"> " +
			"</form>");
		pw.write("<hr>");
		
		try {
			emp = empDao.getEmployee(empId);
			emp.setPassword(password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if ((emp.getFunds() - req.getCost())<0) {
			pw.write("Insufficient Funds.");
			pw.write("<h3>Remaining Funds: " + emp.getFunds() + "</h3>");
		} else {			
			try {
				reqDao.addRequest(req);
				emp.setFunds(emp.getFunds() - reimburseAmt);
				empDao.modifyEmployee(empId, emp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.write("Request Added");
			pw.write("<h3>Remaining Funds: " + emp.getFunds() + "</h3>");
		}
	}
	
	private double CalcReimburseAmt(double cost, int eventType){
		double result = 0.0;
		switch(eventType) {
		case(0):// Univ Course 80%
			result = Math.round(cost* .8 * 100.0) / 100.0;
			break;
		case(1):// Seminar 60%
			result = Math.round(cost* .6 * 100.0) / 100.0;
			break;
		case(2):// Cert Prep 75%
			result = Math.round(cost* .75 * 100.0) / 100.0;
			break;
		case(3):// Cert 100%
			result = Math.round(cost* 1.0 * 100.0) / 100.0;
			break;
		case(4):// Tech Training 90%
			result = Math.round(cost* .9 * 100.0) / 100.0;
			break;
		case(5):// Other 30%
			result = Math.round(cost* .3 * 100.0) / 100.0;
			break;
		}
		return result;
	}

}
