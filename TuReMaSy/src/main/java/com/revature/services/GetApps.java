package com.revature.services;

import java.awt.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Application;
import com.revature.dao.MyDao;

public class GetApps {

	public static void getApps(HttpServletResponse response) {
		MyDao appData = new MyDao();
		java.util.List<Application> applications = appData.getAllApplications();
		response.setContentType("json/application");
		
		ObjectMapper mapper = new ObjectMapper();
	      String jsonString;// = "{\"name\":\"Mahesh\", \"age\":21}";
	      
	      //map json to student
			
	      try {
	    	  mapper.writeValue(System.out, applications);
	         
	         System.out.println(applications);
	         
	         mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
	         jsonString = mapper.writeValueAsString(applications);
	         
	         System.out.println(jsonString);
	      } catch (JsonParseException e) { 
	         e.printStackTrace();
	      } catch (JsonMappingException e) { 
	         e.printStackTrace(); 
	      } catch (IOException e) { 
	         e.printStackTrace(); 
	      }
	   }
	
	}
