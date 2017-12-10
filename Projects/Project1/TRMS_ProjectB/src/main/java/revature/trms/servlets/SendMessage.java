package revature.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import revature.trms.dao.DaoImpl;

/**
 * Servlet implementation class SendMessage
 */
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String receiver = request.getParameter("receiver");
		String message = request.getParameter("message");
		DaoImpl dao = new DaoImpl();
		RequestDispatcher rd = request.getRequestDispatcher("Messages");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<link rel='stylesheet' type='text/css' href='resources/CSS/main.css'>");
		
		//out.println("hello" + username);
		if(dao.getUser(receiver) != null && !receiver.equals(username)){
			
			dao.sendMessage(username, receiver, message);
			if(dao.login_emp((String)session.getAttribute("username"), (String)session.getAttribute("password"))){
				out.println("<h1>Message Sent!</h1><a href='Profile.html'>Back</a>");//Code for type two
			}else if(dao.login_sv((String)session.getAttribute("username"), (String)session.getAttribute("password"))){
				out.println("<h1>Message Sent!</h1><a href='SVProfile.html'>Back</a>");//Code for type two
			}else if(dao.login_dh((String)session.getAttribute("username"), (String)session.getAttribute("password"))){
				out.println("<h1>Message Sent!</h1><a href='DHProfile.html'>Back</a>");//Code for type two
			}else if(dao.login_bc((String)session.getAttribute("username"), (String)session.getAttribute("password"))){
				out.println("<h1>Message Sent!</h1><a href='BCProfile.html'>Back</a>");//Code for type two
			}
			
			
			/*session.setAttribute("username", username);//test to avoid wrong input crash
			out.println("<h1>Message Sent!</h1>");
			rd.include(request, response);*/
		}else {
			out.println("<div align='center'><img src='resources/Images/icarus.gif' height='200px' width='200px'></div>");
			out.println("<h1>Detected Error!</h1><a href='ViewMessages'>Back</a>");//code for type two
			
			/*out.println("<h1>Error Detected!</h1>");
			rd.forward(request, response);*/
			//session.setAttribute("username", username);//test to avoid wrong input crash
			//rd.include(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
