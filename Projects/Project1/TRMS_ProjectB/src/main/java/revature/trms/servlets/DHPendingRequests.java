package revature.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import revature.trms.bean.Request;
import revature.trms.dao.DaoImpl;

/**
 * Servlet implementation class DHPendingRequests
 */
public class DHPendingRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DHPendingRequests() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DaoImpl dao = new DaoImpl();
		ArrayList<Request> reqs = dao.getRequests();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<link rel='stylesheet' type='text/css' href='resources/CSS/main.css'>");
		out.println("<table border='3px'><tr><th>EMPLOYEE ID</th> <th>REQUEST ID</th> <th>AMOUNT</th></tr>");
		for(Request req : reqs){
			if(req.getGrade2() == 0 ){
				out.println("<tr><td>" + req.getEmp_id() + "</td> <td>" + req.getReq_id() + "</td> <td>" + req.getMoney() + "</td></tr>");
			}
		}
		out.println("</table>");
		
		out.println("<form action='AddGrade2'>"
				+ "<p>Employee ID</p><input type='text' name='emp_id' required>"
				+ "<p>Request ID</p><input type='text' name='req_id' required>"
				+ "<p>Grade</p><input type='text' name='grade'  required>"
				+ "<p>Message</p><input type='text' name='message'  required>"
				+ "<input type='submit' value='Send Grade'></form>");
		
		out.println("<a href='DHProfile.html'>Back</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
