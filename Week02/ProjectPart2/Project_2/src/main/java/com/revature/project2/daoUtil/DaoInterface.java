package com.revature.project2.daoUtil;

import java.util.List;

public interface DaoInterface {
	
	public boolean verify(String username, String password);
	public boolean verifyAdmin(String username, String password);
	public double getDeposit(String username);
	public double getDebt(String username);
	public double getLoan(String username);
	
	public List<String> getLoanPending();//gets users pending for loan
	public List<String> getNormalUsers();//get users who are approved
	public List<String> getPendingUsers();//get users who are not approved
	public List<String> getBlockedUsers();//get blocked users
	public List<String> getUnblockedUsers();//get unblocked users
	
	public void makePayment(String username, double money);
	public void makeDeposit(String username, double money);
	public void grantLoan(String username);
	public void denyLoan(String username);
	
	public void blockUser(String username);
	public void unblockUser(String username);
	
	public void createUser(String username, String password);
	
	public void approveAccount(String username);
	public void makeAdmin(String username);
	public void makeWithdrawal(String username, double money);

}
