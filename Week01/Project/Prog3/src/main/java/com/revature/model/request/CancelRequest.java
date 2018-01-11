package com.revature.model.request;

public class CancelRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3467060181142274105L;
	private int transId;

	public CancelRequest(String user, int id) {
		super(user);
		transId = id;
	}

	public int getTransId() {
		return transId;
	}

	@Override
	public String toString() {
		return "CancelRequest [transId=" + transId + ", uid=" + getUid() + ", user=" + getUser() + "]";
	}
	
}
