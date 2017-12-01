package com.revature.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Trainer;
import com.revature.dao.TrainerDao;

public class GetLikeUsers {
	public static void getUsers(HttpServletResponse response, String username) throws IOException {
		TrainerDao td = new TrainerDao();

		List<Trainer> trainers = td.selectTrainerByLikeUsername(username);
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		if (trainers != null) {
			String myXml = "<root>";

			for (Trainer t : trainers) {
				myXml += "<trainer><username>" + t.getUsername() + "</username>" + "<fname>" + t.getFname() + "</fname>"
						+ "<mname>" + t.getMname() + "</mname>" + "<lname>" + t.getLname() + "</lname>" + "</trainer>";
			}
			myXml += "</root>";
			
			for(Trainer t : trainers){
				ObjectMapper mapper = new ObjectMapper();
				System.out.println(mapper.writeValueAsString(t));
			}
			
			out.println(myXml);
		}
		else{
			out.println("<root></root>");
		}
		
	}
}
