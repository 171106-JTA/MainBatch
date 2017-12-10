package revature.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import revature.trms.bean.Message;
import revature.trms.bean.Request;
import revature.trms.dao.DaoImpl;

/**
 * Servlet implementation class ViewMessages
 */
public class ViewMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewMessages() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		DaoImpl dao = new DaoImpl();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		ArrayList<Message> messages = dao.getMessages();
		
		out.println("<link rel='stylesheet' type='text/css' href='resources/CSS/main.css'>");
		
		out.println("<h3>Messages Received</h3>");
		out.println("<table border='3px'><tr><th>DATE</th><th>SENDER</th><th>MESSAGE</th></tr>");
		
		for(Message message : messages){
			if(message.getReceiver().equals(username))
				out.println("<tr><td>" + message.getDate() + "</td> <td>" + message.getSender() + "</td> <td>" + message.getMessage() + "</td></tr>");
		}
		
		out.println("</table>");
		
		out.println("<h3>Messages Sent</h3>");
		out.println("<table border='3px'><tr><th>DATE</th><th>RECEIVER</th><th>MESSAGE</th></tr>");
		
		for(Message message : messages){
			if(message.getSender().equals(username))
				out.println("<tr><td>" + message.getDate() + "</td> <td>" + message.getReceiver() + "</td> <td>" + message.getMessage() + "</td></tr>");
		}
		
		out.println("</table>");
		
		out.println("<form action='SendMessage'><p>Receiver</p><input type='text' name='receiver'>"
				+ "<p>Message</p><input type='text' name='message'>"
				+ "<input type='submit' value='Send Message'></form>");
		
		if(dao.login_emp((String)session.getAttribute("username"), (String)session.getAttribute("password"))){
			out.println("<a href='Profile.html'>Back</a>");//Code for type two
		}else if(dao.login_sv((String)session.getAttribute("username"), (String)session.getAttribute("password"))){
			out.println("<a href='SVProfile.html'>Back</a>");//Code for type two
		}else if(dao.login_dh((String)session.getAttribute("username"), (String)session.getAttribute("password"))){
			out.println("<a href='DHProfile.html'>Back</a>");//Code for type two
		}else if(dao.login_bc((String)session.getAttribute("username"), (String)session.getAttribute("password"))){
			out.println("<a href='BCProfile.html'>Back</a>");//Code for type two
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
