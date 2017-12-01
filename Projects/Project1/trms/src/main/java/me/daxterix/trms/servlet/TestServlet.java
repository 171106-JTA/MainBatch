package me.daxterix.trms.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/test")
public class TestServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

                // Set response content type
                response.setContentType("text/html");

                // Actual logic goes here.
                PrintWriter out = response.getWriter();
                out.println("<h1>" + "testing testing 1, 2, 3" + "</h1>");

	    }

	    /*
	     * this is the method that actually performs the actions with the data. it takes a request and response object
	     * (which were wrapped by the web container)
	     *
	     *
	     */


}
