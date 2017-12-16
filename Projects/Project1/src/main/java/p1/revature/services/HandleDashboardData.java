package p1.revature.services;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import p1.revature.beans.Employee;
import p1.revature.beans.EventGradeCriteria;
import p1.revature.beans.Location;
import p1.revature.beans.ReimbursableEvent;
import p1.revature.beans.ReimbursementRequest;
import p1.revature.beans.State;
import p1.revature.dao.EmployeeDao;
import p1.revature.dao.EventGradeCriteriaDao;
import p1.revature.dao.LocationDao;
import p1.revature.dao.ReimbursementRequestDao;

public class HandleDashboardData extends ObjectJSONService {
	
	public static boolean validateUserForm(HttpServletRequest req)
	{
		// get all the parameters here
		try {
			// parse that json object
			Type gradeRubricType = new TypeToken<List<EventGradeCriteria>>() {}.getType();
			JsonObject json = gson.fromJson(req.getReader(), JsonObject.class);
			ReimbursementRequest rr = gson.fromJson(json.get("reimbursementRequest"), ReimbursementRequest.class);
			List<EventGradeCriteria> ec = gson.fromJson(json.get("gradeScale"), gradeRubricType);
			//do database lookups here
			// if a ReimbursementRequest already exists in the data store with an id, return false immediately
			ReimbursementRequestDao rrDAO = new ReimbursementRequestDao();
			if (rrDAO.getRequestByEventID(rr.getReimbursableEvent().getEventID()) != null) return false;
			// next, we validate the ReimbursableEvent...
			ReimbursableEvent re = rr.getReimbursableEvent();
			// ...oh wait, that's done for us via the JSON-to-Java-Object process, via exceptions!
			// let's validate that event's Location...
			LocationDao locDao= new LocationDao();
			Location location = re.getLocation();
			// if location already exists in the database, we get its id
			Location fetchedLocation = locDao.getLocationByAddressCityState(location);
			if (fetchedLocation != null) location.setLocationID(fetchedLocation.getLocationID());
			// zipCode better be 5 digits long
			int zip = location.getZipCode();
			if ((zip < 10000) || (zip > 99999)) return false;
			// suiteNumber should be positive
			if (location.getSuiteNumber() < 0) return false;
			// validate the state the location has
			if (new LocationDao().getState(new State(location.getStateAbbreviation())) == null) return false;
			// all EventGradeCriteria better have positive cutoff percentages!
			for (EventGradeCriteria criteria : ec)
			{
				if (criteria.getCutoffPercentage() < 0) return false;
			}
			// temporarily add ReimbursementRequest,List<EventGradeCriteria> to the session
			req.getSession().setAttribute("rr", rr);
			req.getSession().setAttribute("gradeRubric", ec);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		} catch (JsonIOException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		catch (NumberFormatException ex)
		{
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static int submitReimbursementRequest(HttpServletRequest req)
	{
		// first, we perform validation
		if (validateUserForm(req))
		{
			// next, we fetch the data from the session, and clean up after ourselves there.
			HttpSession  session    = req.getSession();
			ReimbursementRequest rr = (ReimbursementRequest)session.getAttribute("rr");
			List<EventGradeCriteria> rubric = (List<EventGradeCriteria>)session.getAttribute("gradeRubric");
			Employee user = (Employee)session.getAttribute("user");
			session.removeAttribute("rr");
			session.removeAttribute("gradeRubric");
			// give rubric some default data if it is either null or empty
			if ((rubric == null) || (rubric.isEmpty())) rubric = EventGradeCriteria.getDefaultRubric();
			// fetch necessary fields 
			rr.setRequesterID(new EmployeeDao().getEmployeeID(user));
			// next, we write the data to the data store
			int rrPK = new ReimbursementRequestDao().insertReimbursementRequest(rr, true);
			for (EventGradeCriteria c : rubric) { c.setEventID(rr.getReimbursableEventID()); }
			int criteriaRowsInserted = new EventGradeCriteriaDao().insertEventGradeCriteria(rubric);
			// put rr back into session
			req.getSession().setAttribute("rr", rr);
			return criteriaRowsInserted + 1;
		}
		else
		{
			System.out.println("invalid form data");
		}
		return 0; 
	}
}
