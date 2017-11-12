package com.revature.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Admin;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
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
	
	// Flag used to exit server thread
	private boolean runThread = true;
	
	public Server() {
		super();
		initBoss();
	}

	public Server(Runnable arg0, String arg1) {
		super(arg0, arg1);
		initBoss();
	}

	public Server(Runnable arg0) {
		super(arg0);
		initBoss();
	}

	public Server(String arg0) {
		super(arg0);
		initBoss();
	}
	
	@Override
	public void run() {
		while (runThread) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
	}

	
	public void kill() {
		runThread = false;
	}
	
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
	 * @param username
	 * @param password
	 * @return session id
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
		
		// Make request
		resultset = router.handleRequest(new Request(0, 0, "USER", "LOGIN", query, null));
		
		// If user not found
		if (resultset.size() == 0)
			throw new Exception("Username or password invalid!");
			
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
	
	@Override
	public void finalize() {
		for (Session session : sessions.values()) {
			session.kill();
		}
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	private void initBoss() {
		FieldParams query = new FieldParams();
		List<BusinessObject> bigboss = null;
		Request request;

		// Attempt to load existing data 
		database.setup(null);
		
		// Set query params
		query.put(User.ID, "0");
		query.put(User.USERNAME, "big.boss");
		
		// Build request to attempt to find Boss
		request = new Request(0, 0, "USER", "GETUSER", query, null);
		request.setCheckpoint(Checkpoint.ADMIN);
		
		try {
			// If account not found then create
			if (router.handleRequest(request).size() == 0) {
				bigboss = new ArrayList<>();
				bigboss.add(new Admin(0, "big.boss", "master"));
				bigboss.add(new UserInfo(0, "big.boss@mybank.com", "34123 Mybank St., 14356 Pluto", "1234567890"));
				bigboss.add(new User(1, "guest", "guest", Checkpoint.NONE));
			}
		} catch (RequestException e) {
			// TODO log
		}
		
		// Perform database initialization
		database.setup(bigboss);
	}
}
