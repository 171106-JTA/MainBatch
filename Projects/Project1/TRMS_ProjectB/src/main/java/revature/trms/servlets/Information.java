package revature.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import revature.trms.bean.User;
import revature.trms.dao.DaoImpl;

/**
 * Servlet implementation class Information
 */
public class Information extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Information() {
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
		DaoImpl dao = new DaoImpl();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		User user = dao.getUser(username);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<link rel='stylesheet' type='text/css' href='resources/CSS/main.css'>");
		
		out.println("<p>User Name: " + user.getEmp_id() + "</p>");
		out.println("<p>First Name: " + user.getFname() + "</p>");
		out.println("<p>Last Name: " + user.getLname() + "</p>");
		out.println("<p>E-mail: " + user.getEmail() + "</p>");
		out.println("<p>Phone: " + user.getPhone() + "</p>");
		out.println("<p>Address: " + user.getAddress() + "</p>");
		
		out.println("<a href='Profile.html'>Back</a>");
	}

}
