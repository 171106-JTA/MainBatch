package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.beans.Request;
import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.util.GetRequests;
import com.revature.util.SubmitRequest;

/**
 * Servlet implementation class ViewYourRequests
 */
public class ViewYourRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		int empId = Integer.parseInt(request.getParameter("empId"));
		String password = request.getParameter("password");
		System.out.println(email);
		System.out.println(empId);
		System.out.println(password);
		
		PrintWriter pw = response.getWriter();
		
		EmployeeDao dao = new EmployeeDaoImpl(); 
		
		List<List<Request>> requests = null;
		Employee emp = null;
		
		try {
			emp = dao.getEmployee(empId);
			
			if(emp.getDepartment().equals("HUMAN RESOURCES")) {
				requests = GetRequests.getRequestsBenCo(empId);
			} else if(emp.getTitle().equalsIgnoreCase("department head")) {
				requests = GetRequests.getRequestsDepartmentHead
						(empId, emp.getDept_id());
			} else {
				requests = GetRequests.getRequests(empId);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		pw.write("<header>test</header>");
		pw.write(" <script type=\"text/javascript\" src=\"JavaScript/header.js\"></script>");
		pw.write("<h2>Requests</h2>");
		
		pw.write("<form action=\"Login\" method=\"POST\">\r\n" +
					"<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">" +
					"<input type=\"hidden\" name=\"password\" value=\"" + password + "\">" +
					"<input type=\"hidden\" name=\"email\" value=\"" + email + "\">" +
					"<input type=\"submit\" name=\"selection\" value=\"Return to Home\"> " +
				"</form>");
		pw.write("<hr>");
		
		
		if (emp.getTitle().equals("BENEFIT COORDINATOR")){
			pw.write("<table border=\"1\">"
					   + "<tr>"
					   +	 "<th>Approve</th>"
					   +	 "<th>Deny</th>"
					   +	 "<th>Inquiry</th>"
					   +     "<th>RequestID</th>"
					   +     "<th>EmployeeID</th>"
					   +     "<th>Cost</th>"
					   +     "<th>Status</th>"
//					   +     "<th>Address</th>"
//					   +     "<th>City</th>"
//					   +     "<th>State</th>"
//					   +     "<th>Zip</th>"
					   +     "<th>Description</th>"
					   +     "<th>EventType</th>"
					   +     "<th>DaysMissed</th>"
					   +     "<th>Justification</th>"
					   + "</tr>");
			for (Request r : requests.get(0)) {
				String buttons = getButtonsBenCo(r.getId(), email, empId, password, r.getStatus());
				pw.write("<tr>"
						+ buttons
						+ "   <td>" + r.getId() + "</td>"
						+ "   <td>" + r.getEmployeeId() + "</td>"
						+ "   <td>$" + r.getCost() + "</td>"
						+ "   <td>" + statusString(r.getStatus()) + "</td>"
//						+ "   <td>" + r.getStreetAddress() + "</td>"
//						+ "   <td>" + r.getCity() + "</td>"
//						+ "   <td>" + r.getState() + "</td>"
//						+ "   <td>" + r.getZip() + "</td>"
						+ "   <td>" + r.getDescription() + "</td>"
						+ "   <td>" + r.getEventType() + "</td>"
						+ "   <td>" + r.getDaysMissed() + "</td>"
						+ "   <td>" + r.getJustification() + "</td>"
						+ "</tr>");
			}
			pw.write("</table>");
			pw.write("<hr>");
		} else if (emp.getTitle().equals("NONE")) {
			pw.write("<table border=\"1\">"
					   + "<tr>"
					   +	 "<th>Inquiry</th>"
					   +	 "<th>Upload</th>"
					   +     "<th>RequestID</th>"
					   +     "<th>EmployeeID</th>"
					   +     "<th>Cost</th>"
					   +     "<th>Status</th>"
//					   +     "<th>Address</th>"
//					   +     "<th>City</th>"
//					   +     "<th>State</th>"
//					   +     "<th>Zip</th>"
					   +     "<th>Description</th>"
					   +     "<th>EventType</th>"
					   +     "<th>DaysMissed</th>"
					   +     "<th>Justification</th>"
					   + "</tr>");
			for (Request r : requests.get(0)) {
				pw.write("<tr>"
						+ "   <td><form action=\"EditYourRequests\" method=\"post\">\r\n"
						+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + r.getId() + "\">"
						+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
						+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
						+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
						+ "	   		<input type=\"submit\" name=\"selection\" value=\"Inquiry\">"
						+ "   </form></td>"
						+ "   <td><form action=\"Upload\" method=\"post\">\r\n"
						+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + r.getId() + "\">"
						+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
						+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
						+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
						+ "	   		<input type=\"submit\" name=\"selection\" value=\"Upload\">"
						+ "   </form></td>"
						+ "   <td>" + r.getId() + "</td>"
						+ "   <td>" + r.getEmployeeId() + "</td>"
						+ "   <td>$" + r.getCost() + "</td>"
						+ "   <td>" + statusString(r.getStatus()) + "</td>"
//						+ "   <td>" + r.getStreetAddress() + "</td>"
//						+ "   <td>" + r.getCity() + "</td>"
//						+ "   <td>" + r.getState() + "</td>"
//						+ "   <td>" + r.getZip() + "</td>"
						+ "   <td>" + r.getDescription() + "</td>"
						+ "   <td>" + r.getEventType() + "</td>"
						+ "   <td>" + r.getDaysMissed() + "</td>"
						+ "   <td>" + r.getJustification() + "</td>"
						+ "</tr>");
			}
			pw.write("</table>");
			pw.write("<hr>");
		} else {
			String buttonHeaders = "";
			for (int i = 0; i < requests.size(); i++) {
				switch (i) {
				case 0:
					pw.write("<p>Personal Tuition Reimbursement Request(s):</p>");
					buttonHeaders = "<th>Inquiry</th>";
					break;
				case 1:
					pw.write("<p>Subordinate(s) Tuition Reimbursement Request(s):</p>");
					buttonHeaders =  "<th>Approve</th>"
							  		+"<th>Deny</th>"
							  		+"<th>Inquiry</th>";
					break;
				case 2:
					pw.write("<p>Department Tuition Reimbursement Request(s):</p>");
					buttonHeaders =	 "<th>Approve</th>"
									+"<th>Deny</th>"
									+"<th>Inquiry</th>";
					break;
				default:
					break;
				}
				pw.write("<table border=\"1\">"
					   + "<tr>"
					   +	 buttonHeaders 
					   +     "<th>RequestID</th>"
					   +     "<th>EmployeeID</th>"
					   +     "<th>Cost</th>"
					   +     "<th>Status</th>"
//					   +     "<th>Address</th>"
//					   +     "<th>City</th>"
//					   +     "<th>State</th>"
//					   +     "<th>Zip</th>"
					   +     "<th>Description</th>"
					   +     "<th>EventType</th>"
					   +     "<th>DaysMissed</th>"
					   +     "<th>Justification</th>"
					   + "</tr>");
				for (Request r : requests.get(i)) {
					String buttons = getButtonsSuper(r.getId(), email, empId, password, r.getStatus(), i);
					pw.write("<tr>"
							+     buttons
							+ "   <td>" + r.getId() + "</td>"
							+ "   <td>" + r.getEmployeeId() + "</td>"
							+ "   <td>$" + r.getCost() + "</td>"
							+ "   <td>" + statusString(r.getStatus()) + "</td>"
//							+ "   <td>" + r.getStreetAddress() + "</td>"
//							+ "   <td>" + r.getCity() + "</td>"
//							+ "   <td>" + r.getState() + "</td>"
//							+ "   <td>" + r.getZip() + "</td>"
							+ "   <td>" + r.getDescription() + "</td>"
							+ "   <td>" + r.getEventType() + "</td>"
							+ "   <td>" + r.getDaysMissed() + "</td>"
							+ "   <td>" + r.getJustification() + "</td>"
							+ "</tr>");
				}
				pw.write("</table>");
				pw.write("<hr>");
			}
		}
	}
	
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int empId = 0;
		
		try {
			empId = (Integer)request.getSession().getAttribute("employeeId");
		} catch (Exception e) {
			System.out.println("exception in post request");
			e.printStackTrace();
		}
		
		String requestData = request.getParameter("request");
		System.out.println("Request data: " + requestData);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Request req = mapper.readValue(requestData, Request.class);
		
		req.setEmployeeId(empId);
		
		
		if(SubmitRequest.submit(req)) {
			System.out.println("successfully added reimbursement request");
		} else {
			System.out.println("failed to add reimbursement request");
		}
	}
	
	public String getButtonsBenCo(int reqId, String email, int empId, String password, int status){
		String result = "";
		if (status == 4) {
			result = 
				"<td><form action=\"EditYourRequests\" method=\"post\">\r\n"
				+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
				+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
				+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
				+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
				+ "	   		<input type=\"submit\" name=\"selection\" value=\"Approve\">"
				+ "   </form></td>"
				+ "   <td><form action=\"EditYourRequests\" method=\"post\">\r\n"
				+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
				+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
				+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
				+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
				+ "	   		<input type=\"submit\" name=\"selection\" value=\"Deny\">"
				+ "   </form></td>"
				+ "   <td><form action=\"EditYourRequests\" method=\"post\">\r\n"
				+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
				+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
				+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
				+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
				+ "	   		<input type=\"submit\" name=\"selection\" value=\"Inquiry\">"
				+ "   </form></td>";
		} else if (status == 5){
			result = "<td></td>"
					+ "   <td></td>"
					+ "   <td><form action=\"EditYourRequests\" method=\"post\">\r\n"
					+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
					+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
					+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
					+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
					+ "	   		<input type=\"submit\" name=\"selection\" value=\"Reply\">"
					+ "   </form></td>";
		}else {
		
			result = 
					"<td></td>"
					+ "   <td></td>"
					+ "   <td></td>";
		}
		return result;
	}
	
	
	public String getButtonsSuper(int reqId, String email, int empId, String password, int status, int reqLevel){
		String result = "";
		switch (reqLevel) {
			case 0: //personal requests
				if (status == 8) { //question to employee
					result = "<td><form action=\"EditYourRequests\" method=\"post\">\r\n"
							+ 		"<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
							+ 		"<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
							+ 		"<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
							+ 		"<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
							+ 		"<input type=\"submit\" name=\"selection\" value=\"Reply\">"
							+ "</form></td>";
				} else { //option to inquiry
				result = "<td><form action=\"EditYourRequests\" method=\"post\">\r\n"
						+ 		"<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
						+ 		"<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
						+ 		"<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
						+ 		"<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
						+ 		"<input type=\"submit\" name=\"selection\" value=\"Inquiry\">"
						+ "</form></td>";
				}
				break;
			case 1: //subordinate requests
				if (status == 0) { //submitted to super
					result = 
						"<td><form action=\"EditYourRequests\" method=\"post\">\r\n"
						+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
						+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
						+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
						+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
						+ "	   		<input type=\"submit\" name=\"selection\" value=\"Approve\">"
						+ "   </form></td>"
						+ "   <td><form action=\"EditYourRequests\" method=\"post\">\r\n"
						+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
						+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
						+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
						+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
						+ "	   		<input type=\"submit\" name=\"selection\" value=\"Deny\">"
						+ "   </form></td>"
						+ "   <td><form action=\"EditYourRequests\" method=\"post\">\r\n"
						+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
						+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
						+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
						+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
						+ "	   		<input type=\"submit\" name=\"selection\" value=\"Inquiry\">"
						+ "   </form></td>";
				} else if (status == 1) { //question to depart head
					result = 
							"<td></td>"
							+ "   <td></td>"
							+ "   <td><form action=\"EditYourRequests\" method=\"post\">\r\n"
							+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
							+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
							+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
							+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
							+ "	   		<input type=\"submit\" name=\"selection\" value=\"Reply\">"
							+ "   </form></td>";
				}else {
					result = 
							"<td></td>"
							+ "   <td></td>"
							+ "   <td></td>";
				}
				break;
			case 2: //dept requests
				if (status == 3) { //question to dept head
					result = 
							"<td></td>"
							+ "   <td></td>"
							+ "   <td><form action=\"EditYourRequests\" method=\"post\">\r\n"
							+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
							+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
							+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
							+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
							+ "	   		<input type=\"submit\" name=\"selection\" value=\"Reply\">"
							+ "   </form></td>";
				} else if (status == 2) { //submitted to dept head
					result = 
						"<td><form action=\"EditYourRequests\" method=\"post\">\r\n"
						+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
						+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
						+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
						+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
						+ "	   		<input type=\"submit\" name=\"selection\" value=\"Approve\">"
						+ "   </form></td>"
						+ "   <td><form action=\"EditYourRequests\" method=\"post\">\r\n"
						+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
						+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
						+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
						+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
						+ "	   		<input type=\"submit\" name=\"selection\" value=\"Deny\">"
						+ "   </form></td>"
						+ "   <td><form action=\"EditYourRequests\" method=\"post\">\r\n"
						+ "    		<input type=\"hidden\" name=\"reqId\" value=\"" + reqId + "\">"
						+ "    		<input type=\"hidden\" name=\"email\" value=\"" + email + "\">"
						+ "    		<input type=\"hidden\" name=\"password\" value=\"" + password + "\">"
						+ "    		<input type=\"hidden\" name=\"empId\" value=\"" + empId + "\">"
						+ "	   		<input type=\"submit\" name=\"selection\" value=\"Inquiry\">"
						+ "   </form></td>";
				} else {
					result = 
							"<td></td>"
							+ "   <td></td>"
							+ "   <td></td>";
				}
				break;
		}
				
		return result;
	}
	
	public String statusString (int statusInt) {
		String result = "";
		
		switch(statusInt) {
		case(0):
			result = "Submitted to Supervisor";
			break;
		case(1):
			result = "Question to Supervisor";			
			break;
		case(2):
			result = "Submitted to Department Head";
			break;
		case(3):
			result = "Question to Department Head";
			break;
		case(4):
			result = "Submitted to Benefits Coordinator";
			break;
		case(5):
			result = "Question to Benefits Coordinator";
			break;
		case(6):
			result = "Approved-Pending";
			break;
		case(7):
			result = "Denied";
			break;
		case(8):
			result = "Question to Employee";
			break;
		case(9):
			result = "Approved-Verified";
			break;	
		}
		return result;
	}

}
