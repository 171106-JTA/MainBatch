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
 * Servlet implementation class Assess
 */
public class Assess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Assess() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String req_id = request.getParameter("req_id");
		DaoImpl dao = new DaoImpl();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ArrayList<Request> reqs = dao.getRequests();
		String username = null;
		for(Request req : reqs){
			if(req.getReq_id() == Integer.parseInt(req_id))
				username = req.getEmp_id();
		}
		
		if(dao.get_score(Integer.parseInt(req_id)) < 60){
			dao.deny(Integer.parseInt(req_id));
			dao.sendMessage((String)session.getAttribute("username"), username, "Your request #" + req_id + " was denied for failing evaluation");
		}
			
		else{
			dao.approve(Integer.parseInt(req_id));
			dao.sendMessage((String)session.getAttribute("username"), username, "Your request #" + req_id + " was approved for passing evaluation");
		}
			
		out.println("<link rel='stylesheet' type='text/css' href='resources/CSS/main.css'>");
		//out.println("<div align='center'><img src='resources/Images/icarus.gif' height='200px' width='200px'>");
		out.println("<h1>Assessment Complete</h1><a href='BCViewRequests'>Back</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
