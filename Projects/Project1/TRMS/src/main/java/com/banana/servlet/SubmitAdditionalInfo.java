package com.banana.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.banana.service.InfoRequestManipulation;


/**
 * Servlet implementation class SubmitAdditionalInfo
 */
@MultipartConfig
public class SubmitAdditionalInfo extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//TODO get info
	} 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try {
			Part file = request.getPart("addedfile");
			Part addedInfo = request.getPart("addedinfo");
			Scanner scanner = new Scanner(addedInfo.getInputStream());
			String info = scanner.nextLine();
			Part id = request.getPart("infoid");
			scanner = new Scanner(id.getInputStream());
			int irId = scanner.nextInt();
			
			InfoRequestManipulation.update(irId, file, info);
			out.println("<h3>Success!</h3>");
		}catch(Exception e) {
			out.println(e.getMessage() + " Exception");
		}finally {
			
		}
		
		/*
		try {
			Part p1 = request.getPart("addedfile");
			InputStream is = p1.getInputStream();
			System.out.println("here in hell");
			Part p2 = request.getPart("addedinfo");
			Scanner s = new Scanner(p2.getInputStream());
			String name = s.nextLine();
			System.out.println(name);
			String outputfile = p1.getName();
			FileOutputStream os = new FileOutputStream(outputfile);
			
			int ch = is.read();
			while(ch != -1) {
				os.write(ch);
				ch = is.read();
			}
			os.close();
			out.println("<h3>Success!</h3>");
		}catch(Exception e) {
			out.println(e.getMessage() + "Exception");
		}finally {
			out.close();
		} */
	}

}
