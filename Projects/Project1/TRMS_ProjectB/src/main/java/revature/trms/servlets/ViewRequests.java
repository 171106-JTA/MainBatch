package revature.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import revature.trms.bean.Request;
import revature.trms.dao.DaoImpl;

/**
 * Servlet implementation class ViewRequests
 */
public class ViewRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewRequests() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		DaoImpl dao = new DaoImpl();
		ArrayList<Request> requests = dao.getRequests();
		
		//out.println("<meta charset='UTF-8'>");
		out.println("<link rel='stylesheet' type='text/css' href='resources/CSS/main.css'>");
		
		out.println("<p>Reimbursement Available: $" + dao.getAvail(username) + "</p>");
		out.println("<p>Reimbursement Granted: $" + (1000 - dao.getAvail(username)) + "</p>");
		
		out.println("<table border='3px'><tr><th>REQUEST ID</th><th>REQUEST AMOUNT</th><th>REQUEST STATUS</th></tr>");
		
		for(Request r : requests){
			if(r.getEmp_id().equals(username)){
				out.println("<tr><td>" + r.getReq_id() + "</td> <td> $" + r.getMoney() + "</td>");
				
				if(r.getPending() == 0)
					out.println("<td> DENIED </td></tr>");
				else if(r.getPending() == 1)
					out.println("<td> PENDING </td></tr>");
				else
					out.println("<td> APPROVED </td></tr>");
			}
		}
		
		out.println("</table>");
		out.println("<a href='Profile.html'>Back</a><br>");
		out.println("<a href='Reimb.html'>Apply for reimbursement</a>");
	}

}
