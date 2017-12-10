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
 * Servlet implementation class BCViewRequests
 */
public class BCViewRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BCViewRequests() {
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
		out.println("<h2>Request awaiting action</h2>"
				+ "<table border='3px'><tr><th>EMPLOYEE ID</th> <th>REQUEST ID</th> <th>SUPER VISOR GRADE</th> <th>DEPT HEAD GRADE</th> <th>AVERAGE GRADE</th></tr>");
		
		for(Request req : reqs){
			if(req.getGrade1() != 0 && req.getGrade2() != 0 && req.getPending() == 1){
				out.println("<tr> <th>" + req.getEmp_id() + "</th> <th>" + req.getReq_id() + "</th> <th>" + req.getGrade1() + "%</th> <th>" + req.getGrade2() + "%</th> <th>" + dao.get_score(req.getReq_id()) + "%</th> </tr>");
			}//end if
		}//end for
		out.println("</table>");
		
		/*
		 * add code to create the ability to deny/approve the codes
		 * dao may need a new function to create that - DONE
		 */
		
		out.println("<form action='Assess'><p>REQUEST ID</p><input type='text' name='req_id'><input type='submit' name='Process'></form>");
		
		out.println("<a href='BCProfile.html'>Back</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
