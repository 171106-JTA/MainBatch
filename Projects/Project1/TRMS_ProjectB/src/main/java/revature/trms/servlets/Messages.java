package revature.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import revature.trms.bean.Message;
import revature.trms.dao.DaoImpl;

/**
 * Servlet implementation class Messages
 */
public class Messages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Messages() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		DaoImpl dao = new DaoImpl();
		ArrayList<Message> messages = dao.getMessages();
		String username = (String) session.getAttribute("username");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//out.println("<h1>Hello" + username + "</h1>");
		out.println("<h1>Messages Received</h1>");
		out.println("<table border='5px' width='500px'><tr><th>DATE</th><th>SENDER</th><th>MESSAGE</th></tr>");
		for(Message m : messages){
			if(username.equals(m.getReceiver())){
				out.println("<tr><td>" + m.getDate() + "</td><td>" + m.getSender() + "</td><td>" + m.getMessage() + "</td></tr>");
			}
		}
		out.println("</table>");
		
		out.println("<h1>Messages Sent</h1>");
		out.println("<table border='5px' width='500px'><tr><th>DATE</th><th>SENDER</th><th>MESSAGE</th></tr>");
		for(Message m : messages){
			if(username.equals(m.getSender())){
				out.println("<tr><td>" + m.getDate() + "</td><td>" + m.getSender() + "</td><td>" + m.getMessage() + "</td></tr>");
			}
		}
		out.println("</table>");
		
		RequestDispatcher rd = request.getRequestDispatcher("Messages.html");
		rd.include(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
