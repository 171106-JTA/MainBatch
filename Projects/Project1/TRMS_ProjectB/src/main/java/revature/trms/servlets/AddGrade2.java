package revature.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import revature.trms.dao.DaoImpl;

/**
 * Servlet implementation class AddGrade2
 */
public class AddGrade2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGrade2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		int req_id = Integer.parseInt(request.getParameter("req_id"));
		double grade = Double.parseDouble(request.getParameter("grade"));
		String message = request.getParameter("message");
		String emp_id = request.getParameter("emp_id");
		DaoImpl dao = new DaoImpl();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		dao.gradeDH(req_id, grade);
		dao.sendMessage(username, emp_id, message);
		out.println("<link rel='stylesheet' type='text/css' href='resources/CSS/main.css'>");
		//out.println("<div align='center'><img src='resources/Images/icarus.gif' height='200px' width='200px'>");
		out.println("<h2>Complete</h2><br> <a href='DHProfile.html'>Back</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
