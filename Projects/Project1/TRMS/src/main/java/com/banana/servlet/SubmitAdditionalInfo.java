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
		
		try {
			Part file = request.getPart("addedfile");
			Part addedInfo = request.getPart("addedinfo");
			Scanner scanner = new Scanner(addedInfo.getInputStream());
			String info = scanner.nextLine();
			Part id = request.getPart("infoid");
			scanner = new Scanner(id.getInputStream());
			int irId = scanner.nextInt();
			
			InfoRequestManipulation.update(irId, file, info);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
		}

	}

}
