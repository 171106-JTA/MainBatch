package com.revature.model.account;

import com.revature.model.Properties;

public final class RootUserAccount extends UserAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5010253964414527401L;
	private static double interest = Properties.ROOT_USER_INT;
	private static int limit = Properties.ROOT_USER_CRED_LIMIT;
	private static int penalty = Properties.ROOT_USER_PENALTY;
	
	public RootUserAccount() {
		super(limit, interest, penalty);
	}

	@Override
	public String toString() {
		return "RootUserAccount [limit=" + limit + ", penalty=" + penalty + ", status=" + status + ", interest="
				+ interest + ", permissions=" + permissions + "]";
	}

}