package com.revature.server;

import java.util.HashMap;
import java.util.Map;

import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.core.factory.FieldParamsFactory;
import com.revature.persistence.Persistenceable;
import com.revature.persistence.file.FileDataManager;
import com.revature.route.RequestRouter;
import com.revature.route.Routeable;
import com.revature.server.session.Session;

public class Server extends Thread {
	public static Routeable router = new RequestRouter();
	public static Persistenceable database = FileDataManager.getManager();
	
	// Active sessions for Server
	public Map<Integer, Session> sessions = new HashMap<>();
	
	// For Sessions waiting for response
	private Map<Integer, Session> requests = new HashMap<>();
	
	/**
	 * Attempt to create session
	 * @param username
	 * @param password
	 * @return session id
	 * @throws Exception
	 */
	public FieldParams login(String username, String password) throws Exception {
		FieldParamsFactory factory = FieldParamsFactory.getFactory();
		FieldParams request = new FieldParams();
		Resultset resultset;
		Session session;
		FieldParams response;
		
		// set request
		request.put("route", "USER");
		request.put("transtype", "LOGIN");
		request.put("username", username);
		request.put("password", password);
		
		// Make request
		resultset = router.handleRequest(request);
		
		// If user not found
		if (resultset.size() == 0)
			throw new Exception("Username or password invalid!");
			
		// Create Session
		session = new Session((User)resultset.get(0));
		sessions.put(session.hashCode(), session);
		
		// Prepare response
		response = factory.getFieldParams((User)resultset.get(0));
		response.put("sessionid", Integer.toString(session.hashCode()));
		
		// start session
		session.start();
		return response;
	}
	
	public void pushRequest(FieldParams requestParams) throws RequestException {
		Integer sessionid;
		Session session;
		
		if (requestParams.get("sessionid") == null)
			throw new RequestException(requestParams, 0, "SessionID required!");
		
		sessionid = Integer.parseInt(requestParams.get("sessionid"));
		
		if (!sessions.containsKey(sessionid))
			throw new RequestException(requestParams, 0, "Invalid SessionID!");
		
		session = sessions.get(sessionid);
		requests.put(sessionid, session);
		session.setRequestParams(requestParams);
	}

	public Resultset getResponse(FieldParams requestParams) throws RequestException {
		Resultset response = null;
		Integer sessionid;
		Session session;
		
		if (requestParams.get("sessionid") == null)
			throw new RequestException(requestParams, 0, "SessionID required!");
		
		sessionid = Integer.parseInt(requestParams.get("sessionid"));
		
		if (!requests.containsKey(sessionid))
			throw new RequestException(requestParams, 0, "Invalid SessionID!");
		
		// Check request on session
		session = requests.get(sessionid);
		
		// IFF response is ready
		if (session.isResponseReady()) {
			response = session.getResponse();
			
			// remove from cached requests
			requests.remove(sessionid);
		} 
		
		return response;
	}
}
