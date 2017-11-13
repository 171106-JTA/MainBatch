package com.revature.model.account;

import com.revature.model.Properties;

public final class AdminAccount extends Account {
	private UserAccount associatedAccount;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3809627223907285013L;
	
	protected void setPermissions() {
		super.permissions = Properties.WRITE;
	}
	
	public UserAccount getAssociatedAccount() {
		return associatedAccount;
	}
	
	public void setAssociatedAccount(UserAccount associatedAccount) {
		this.associatedAccount = associatedAccount;
	}

	@Override
	public String toString() {
		return "AdminAccount [associatedAccount=" + associatedAccount + "]";
	}
	
	
}