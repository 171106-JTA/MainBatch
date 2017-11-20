package com.revature.model.account;

import com.revature.model.Properties;

public class SiteAdminAccount extends AdminAccount {
	private UserAccount associatedAccount;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3809627223907285013L;
	
	protected void setPermissions() {
		super.permissions = Properties.WRITE;
	}	
	
}