package p1.revature.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import p1.revature.beans.Employee;
import p1.revature.dao.LocationDao;
import p1.revature.dao.ReimbursableExpenseTypeDao;
import p1.revature.dao.ReimbursementRequestDao;

public class GetTRMSFormData extends ObjectJSONService {
	
	// method to send json
	/**
	 * Gets the states (as in the "states of this country") pre-written to the database
	 * @return String representation of JSON representation of that
	 */
	public static String getLocationStates()
	{
		return String.format("{\"states\" : %s}", 
				toJSON(new LocationDao().getAllStates()));
	}
	
	/**
	 * Gets the types of reimbursable expenses that have been pre-written to the database
	 * @return String representation of JSON representation of that
	 */
	public static String getExpenseTypes()
	{
		return String.format("{\"expenseTypes\" : %s}", 
				toJSON(new ReimbursableExpenseTypeDao().getAllExpenseTypes()));
	}
	
	public static String getReimbursementRequests(HttpServletRequest req)
	{
		// get the logged in user from the session 
		HttpSession session = req.getSession();
		if (session.isNew()) return null;
		
		return String.format("{\"reimbursementRequests\" : %s}", 
				toJSON(new ReimbursementRequestDao().getEventsByRequester((Employee)session.getAttribute("user"))));
		
	}
}
