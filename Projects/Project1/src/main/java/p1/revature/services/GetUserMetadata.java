package p1.revature.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetUserMetadata extends ObjectJSONService {
	public static String initJSONFromSession(HttpServletRequest req)
	{
		// let's load everything from the session. 
		HttpSession session = req.getSession();
		if (session.isNew())	return "";
		String json = String.format("{\"user\":%s,\"userIsManger\":%s}", 
				toJSON(session.getAttribute("user")),
				session.getAttribute("userIsManager"));
		
		return json;
	}
}
