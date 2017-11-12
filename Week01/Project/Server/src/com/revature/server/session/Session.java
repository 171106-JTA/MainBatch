package com.revature.server.session;

import com.revature.businessobject.user.User;
import com.revature.businessobject.user.Checkpoint;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.server.Server;

public final class Session extends Thread {
	private User user;
	private Request request;
	private Resultset response;
	private RequestException exception;
	private boolean ready;
	private boolean error;
	private boolean runThread;
	
	public Session(User user) {
		super();
		this.user = user;
		this.ready = true;
		this.runThread = true;
	}
	
	public boolean hasCheckpoint(String checkpoint) {
		return user == null ? false : user.getCheckpoint() == checkpoint;
	}
	
	@Override
	public void run() {
		while (runThread) {
			if (!this.ready && this.request != null) {
				try {
					// Set what user has access to
					request.setCheckpoint(user.getCheckpoint());
					
					// Make request 
					response = Server.router.handleRequest(request);
				} catch (RequestException e) {
					// Cache exception and set error flag to true
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
	
	public void kill() {
		runThread = false;
	}
	
	public String getCheckpoint() {
		return user.getCheckpoint();
	}
	
	public boolean isResponseReady() {
		return ready;
	}
	
	public boolean hasError() {
		return error;
	}
	
	public RequestException getException() {
		return exception;
	}
	
	public Request getRequestParams() {
		return request;
	}
	
	public Resultset getResponse() {
		return response;
	}
	
	public synchronized void setRequestParams(Request request) {
		if (this.ready) {
			this.request = request;
			this.response = null;
			this.exception = null;
			this.error = false;
			this.ready = false;
		}
	}
}
