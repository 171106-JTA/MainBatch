package com.revature.server.session;

import com.revature.businessobject.user.User;

import org.apache.log4j.Logger;

import com.revature.businessobject.user.Checkpoint;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.server.Server;

/**
 * Defines user Sessions (handles client request)
 * @author Antony Lulciuc
 */
public final class Session extends Thread {
	private static Logger logger = Logger.getLogger(Session.class);
	private User user;
	private Request request;
	private Resultset response;
	private RequestException exception;
	private boolean ready;
	private boolean error;
	private boolean runThread;
	
	/**
	 * Initialize Session with user data from database
	 * @param user - basic account info
	 */
	public Session(User user) {
		super();
		this.user = user;
		this.ready = true;
		this.runThread = true;
	}
	
	/**
	 * Determines if account has specified checkpoint
	 * @param checkpoint - what is in question to check user against
	 * @return true if has checkpoint else false.
	 */
	public boolean hasCheckpoint(String checkpoint) {
		return user == null ? false : user.getCheckpoint() == checkpoint;
	}
	
	/**
	 * Performs work for session
	 */
	@Override
	public void run() {
		while (runThread) {
			if (!this.ready && this.request != null) {
				try {
					// login request initiation 
					logger.debug("processing " + request.toString() + " for " + this.toString());
					
					// Set what user has access to
					request.setCheckpoint(user.getCheckpoint());
					
					// Make request 
					response = Server.router.handleRequest(request);
					logger.debug(this.toString() + " completed request");
				} catch (RequestException e) {
					// Cache exception and set error flag to true
					logger.warn(this.toString() + " failed, message:>" + e.toString());
					exception = e;
					error = true;
				} finally {
					// Set response ready flag to true
					ready = true;
				}
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Notifies thread to stop processing requests
	 */
	public void kill() {
		runThread = false;
	}
	
	/**
	 * @return user checkpoint 
	 */
	public String getCheckpoint() {
		return user.getCheckpoint();
	}
	
	/**
	 * @return true if rrequest has been processed else false
	 */
	public boolean isResponseReady() {
		return ready;
	}
	
	/**
	 * @return true if error occurred else false
	 */
	public boolean hasError() {
		return error;
	}
	
	/**
	 * @return handle to exception if error occurred else null
	 */
	public RequestException getException() {
		return exception;
	}
	
	/**
	 * @return handle to last known request
	 */
	public Request getRequestParams() {
		return request;
	}
	
	/**
	 * @return handle to response pertaining to current request
	 */
	public Resultset getResponse() {
		return response;
	}
	
	/**
	 * Initialize data to perform work for client
	 * @param request - what to process for client
	 */
	public synchronized void setRequestParams(Request request) {
		if (this.ready) {
			this.request = request;
			this.response = null;
			this.exception = null;
			this.error = false;
			this.ready = false;
		}
	}

	@Override
	public String toString() {
		return "Session [sessionid=" + this.hashCode() + ", user=" + user + "]";
	}
	
	
}
