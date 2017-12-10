package com.revature.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.revature.beans.Request;
import com.revature.dao.EventTypeDaoImpl;
import com.revature.dao.RequestDaoImpl;

public class SubmitRequest {
	
	private static Map<Integer, Double> reimburseMap = null;
	
	/**
	 * If the user submitting the request has fewer than 1000 in
	 * reimbursement requests for the year, add the reimbursement
	 * request to the db.
	 * @param req the request to add
	 * @return true if the request was submitted to the database
	 */
	public static boolean submit(Request req) {
		
		initMap();
		
		RequestDaoImpl dao = new RequestDaoImpl();
		
		try {
			List<Request> requests = dao.getRequests(req.getEmployeeId());
			double reimbursementAmount = 0;
			
			//int year = LocalDateTime.now().getYear();
			for(Request r : requests) {
				
				//TODO: get date of request and compare to year.
				//if(r.getTime().getYear == year) {
					int eventType = r.getEventType();
					reimbursementAmount += reimburseMap.get(eventType) * r.getCost();
				//}
			}
			
			//check amount of the reimbursement requests
			if(reimbursementAmount >= 1000) {
				return false;
			}

			//add request to the db
			dao.addRequest(req);
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Populate the map from event type to reimbursement percentage
	 */
	private static void initMap() {
		if(reimburseMap == null) {
			try {
				reimburseMap = new EventTypeDaoImpl().getReimbursementAmounts();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
