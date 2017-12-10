package com.revature.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.RequestDao;
import com.revature.dao.RequestDaoImpl;
import com.revature.beans.Request;

public class GetRequests {

	public static List<List<Request>> getRequests(int id) {
		
		List<List<Request>> requests = new ArrayList<List<Request>>();
		
		RequestDaoImpl dao = new RequestDaoImpl();
		
		try {
			List<Request> empRequests = dao.getRequests(id);
			requests.add(0,empRequests);
			
			List<Request> subRequests = dao.getSubordinateRequests(id);
			requests.add(1, subRequests);
		} catch(SQLException e) {
			e.printStackTrace();
			return requests;
		}
		return requests;
	}
	
	/**
	 * Get the requests for the department head and their subordinates
	 * @param id the dept head's id
	 * @param deptId the dept id
	 * @return a list containing the dept head's requests at index 1 and
	 * their subordinates requests at index 2.
	 */
	public static List<List<Request>> getRequestsDepartmentHead(int id, int deptId) {
		
		List<List<Request>> requests = new ArrayList<List<Request>>();
		
		RequestDaoImpl dao = new RequestDaoImpl();
		
		try {
			List<Request> empRequests = dao.getRequests(id);
			requests.add(0,empRequests);
			
			List<Request> subRequests = dao.getSubordinateRequests(id);
			requests.add(1, subRequests);
			
			List<Request> deptRequests = dao.getDepartmentRequests(deptId);
			requests.add(2, deptRequests);
		} catch(SQLException e) {
			e.printStackTrace();
			return requests;
		}
		return requests;
	}
	
	/**
	 * Get the requests for the department head and their subordinates
	 * @param id the BenCo's id
	 * @return a list containing the BenCo's requests at index 1 and
	 * all other requests at index 2.
	 */
	public static List<List<Request>> getRequestsBenCo(int id) {

		List<List<Request>> requests = new ArrayList<List<Request>>();
		
		RequestDaoImpl dao = new RequestDaoImpl();
		
		try {
//			List<Request> empRequests = dao.getRequests(id);
//			requests.add(0,empRequests);
			
			List<Request> all = dao.getRequests();
			
//			//remove this employee's requests from the list of all requests
//			for(int i = 0; i < all.size(); i++) {
//				if(all.get(i).getEmployeeId() == id) {
//					all.remove(i);
//				}
//			}
			
			requests.add(0, all); //requests.add(1, all);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return requests;
	}
}

