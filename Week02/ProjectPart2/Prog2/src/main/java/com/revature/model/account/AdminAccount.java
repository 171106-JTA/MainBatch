package com.revature.model.account;

public abstract class AdminAccount extends Account {
	private UserAccount associatedAccount;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3190352456597053001L;

	
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
