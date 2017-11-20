package com.revature.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.core.factory.FieldParamsFactory;
import com.revature.persistence.Persistenceable;
import com.revature.persistence.database.DatabaseManager;
import com.revature.route.RequestRouter;
import com.revature.route.Routeable;
import com.revature.server.session.Session;

/**
 * Used to listen and handle client requests 
 * @author Antony Lulciuc
 */
public class Server implements Runnable {
	public static Routeable router = new RequestRouter();
	public static Persistenceable database = DatabaseManager.getManager();
	public static Logger logger = Logger.getLogger(Server.class);
	
	// Active sessions for Server
	public Map<Integer, Session> sessions = new HashMap<>();
	
	// For Sessions waiting for response
	private Map<Integer, Session> requests = new HashMap<>();
	
	// Flag used to exit server thread
	private boolean runThread = true;
	
	/**
	 * No args constructor
	 */
	public Server() {
	}
	
	/**
	 * TODO listen for clients over the network
	 */
	@Override
	public void run() {
		while (runThread) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Used to exit thread
	 */
	public void kill() {
		runThread = false;
	}
	
	/**
	 * Used to stop session
	 * @param sessionId - id of session to kill
	 */
	public void kill(int sessionId) {
		Session session = sessions.get(sessionId);
		
		if (session != null) {
			session.kill();
			sessions.remove(sessionId);
			requests.remove(sessionId);
		}
	}
	
	/**
	 * Attempt to create session
	 * @param username - account name
	 * @param password - password associated with account
	 * @return session id if account found 
	 * @throws Exception
	 */
	public FieldParams login(String username, String password) throws Exception {
		FieldParamsFactory factory = FieldParamsFactory.getFactory();
		FieldParams query = new FieldParams();
		Resultset resultset;
		Session session;
		FieldParams response;
		
		// set request
		query.put(User.USERNAME, username);
		query.put(User.PASSWORD, password);
		
		// Log attempt
		logger.debug("Attempting to login with " + query);
		
		// Make request
		resultset = router.handleRequest(new Request(0, 0, "USER", "LOGIN", query, null));
		
		// If user not found
		if (resultset.size() == 0) {
			logger.debug("Failed to login with cedentials " + query);
			throw new Exception("Username or password invalid!");
		} else {
			logger.debug("Successful attempt to login with cedentials " + query);
		}
			
		// Create Session
		session = new Session((User)resultset.get(0));
		sessions.put(session.hashCode(), session);
		
		// Prepare response
		response = factory.getFieldParams((User)resultset.get(0));
		response.put(User.SESSIONID, Integer.toString(session.hashCode()));
		
		// start session
		session.start();
		return response;
	}
	
	/**
	 * Notifies session request as been made 
	 * @param request - what to process from client
	 * @throws RequestException
	 */
	public void pushRequest(Request request) throws RequestException {
		Integer sessionid;
		Session session;
		
		if (request.getSessionId() < 1)
			throw new RequestException(request, "SessionID required!");
		
		if (!sessions.containsKey(sessionid = request.getSessionId()))
			throw new RequestException(request, "Invalid SessionID!");
		
		// Get session
		session = sessions.get(sessionid);
		requests.put(sessionid, session);
		
		// Append session checkpoint data to request (determines what retype of requests we can make
		request.setCheckpoint(session.getCheckpoint());
		
		// Set session request
		session.setRequestParams(request);
	}

	/**
	 * Checks to see if request is complete
	 * @param request - contains user/session information 
	 * @return result of request if ready else null
	 * @throws RequestException
	 */
	public Resultset getResponse(Request request) throws RequestException {
		Resultset response = null;
		Integer sessionid;
		Session session;
		
		if (request.getSessionId() < 1)
			throw new RequestException(request, "SessionID required!");
		
		if (!requests.containsKey(sessionid = request.getSessionId()))
			throw new RequestException(request, "Invalid SessionID!");
		
		// Check request on session
		session = requests.get(sessionid);
		
		// IFF response is ready
		if (session.isResponseReady()) {
			response = session.getResponse();
			
			// remove from cached requests
			requests.remove(sessionid);
			
			if (session.hasError())
				throw session.getException();
		} 
		
		return response;
	}
	
	/**
	 * Stop all sessions if server is shutting down
	 */
	@Override
	public void finalize() {
		for (Session session : sessions.values()) {
			session.kill();
		}
	}
	
	
	///
	//	PRIVATE METHODS 
	///
}
