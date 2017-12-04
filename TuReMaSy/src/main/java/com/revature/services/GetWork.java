package com.revature.services;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.revature.beans.Employee;
import com.revature.dao.MyDao;

public class GetWork {
		public static void getEmps(HttpServletResponse response) {
			MyDao empData = new MyDao();
			java.util.List<Employee> employees = empData.getAllEmployees();
			response.setContentType("json/application");
			
			ObjectMapper mapper = new ObjectMapper();
		      String jsonString; //= "{\"name\":\"Mahesh\", \"age\":21}";
		      
		      //map json to student
				
		      try {
		    	  mapper.writeValue(System.out, employees);
		         
		         System.out.println(employees);
		         
		         mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
		         jsonString = mapper.writeValueAsString(employees);
		         
		         System.out.println(jsonString);
		      } catch (JsonParseException e) { 
		         e.printStackTrace();
		      } catch (JsonMappingException e) { 
		         e.printStackTrace(); 
		      } catch (IOException e) { 
		         e.printStackTrace(); 
		      }
		   }

		public Employee find(String username, String password) {
			// TODO Auto-generated method stub
			return null;
		}


		}