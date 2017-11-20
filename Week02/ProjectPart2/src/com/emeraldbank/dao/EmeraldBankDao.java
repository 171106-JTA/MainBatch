package com.emeraldbank.dao;

import java.util.List;

import com.emeraldbank.accounts.Acct;
import com.emeraldbank.users.User;


public interface EmeraldBankDao {
	
	public User getUserByLoginId(String userLoginId);
	public User getUserByLoginId(String userLoginId, boolean redacted);
	public Acct getAcctByAcctNum(String acctNum); 
	public List<Acct> getPortfolioByLoginId(String userLoginId); 
	public int getUserIsAdminByLoginId(String userLoginId); 
	
	public int createNewUser(User u); 
	public int createNewBankAcct(String acctNum, String ownerLoginId);
	public int deleteUser(String userLoginId); 
	public int closeBankAcct(String acctNum);
	
	public double getAcctBalance(String acctNum); 
	public double depositIntoAcct(String acctNum, double amt); 
	public double withdrawFromAcct(String acctNum, double amt);
	
	public int getUserStatus(String userLoginId); 
	public String getUserStatusString(String userLoginId);
	public int getBankAcctStatus(String acctNum);
	public String getBankAcctStatusString(String acctNum); 
	
	public int activateUser(String userLoginId); 
	public int activateBankAcct(String acctNum); 
	public int lockUser(String userLoginId); 
	public int lockAcct(String acctNum); 
	public int promoteToAdmin(String userLoginID);
	
}
