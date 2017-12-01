package com.revature.model.request;

public class AccountRequest extends Request {

	public AccountRequest(String name) {
		super(name);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3546105513954424163L;

	@Override
	public String toString() {
		return "AccountRequest [uid=" + getUid() + ", userName=" + getUser() + "]";
	}

	
}
