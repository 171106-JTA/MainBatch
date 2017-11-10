package com.revature.server.session;

import com.revature.businessobject.user.User;
import com.revature.businessobject.user.UserRole;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.server.Server;

public final class Session extends Thread {
	private User user;
	private FieldParams requestParams;
	private Resultset response;
	private RequestException exception;
	private boolean ready;
	private boolean error;
	
	public Session(User user) {
		super();
		this.user = user;
		this.ready = true;
	}
	
	public boolean hasCheckpoint(UserRole checkpoint) {
		return user == null ? false : user.getRole() == checkpoint;
	}
	
	@Override
	public void run() {
		while (true) {
			if (!this.ready && this.requestParams != null) {
				try {
					// Set what user has access to
					requestParams.put("checkpoint", Integer.toString(user.getRole().ordinal()));
					
					// Make request 
					response = Server.router.handleRequest(requestParams);
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
	
	public boolean isResponseReady() {
		return ready;
	}
	
	public boolean hasError() {
		return error;
	}
	
	public RequestException getException() {
		return exception;
	}
	
	public FieldParams getRequestParams() {
		return requestParams;
	}
	
	public Resultset getResponse() {
		return response;
	}
	
	public synchronized void setRequestParams(FieldParams requestParams) {
		if (this.ready) {
			this.requestParams = requestParams;
			this.response = null;
			this.exception = null;
			this.error = false;
			this.ready = false;
		}
	}
}
