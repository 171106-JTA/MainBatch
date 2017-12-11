package com.project1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class QuickFunction {
	
	static void insertBoostrap(HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<link rel=\"stylesheet\"\r\n" + 
				"	href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\r\n" + 
				"\r\n" + 
				"<!-- jQuery library -->\r\n" + 
				"<script\r\n" + 
				"	src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\r\n" + 
				"\r\n" + 
				"<!-- Latest compiled JavaScript -->\r\n" + 
				"<script\r\n" + 
				"	src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>"
				+ "<link rel=\"stylesheet\" type=\"text/css\"\r\n" + 
				"	href=\"resources/css/Project1.css\">");
		
	}
	
	static void insertNavBar(HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		
		out.println("<nav class=\"navbar navbar-inverse\">\r\n" + 
				"  <div class=\"container-fluid\">\r\n" + 
				"    <div class=\"navbar-header\">\r\n" + 
				"      <a class=\"navbar-brand\" href=\"#\">TSR</a>\r\n" + 
				"    </div>\r\n" + 
				"    <ul class=\"nav navbar-nav\">\r\n" + 
				"      <li class=\"active\"><a href=\"#\">Home</a></li>\r\n" + 
				"    </ul>\r\n" + 
				"    <ul class=\"nav navbar-nav navbar-right\">\r\n" +  
				"      <li><a href='Logout'><span class=\"glyphicon glyphicon-log-in\"></span> Logout</a></li>\r\n" + 
				"    </ul>\r\n" + 
				"  </div>\r\n" + 
				"</nav>");
	}
}
