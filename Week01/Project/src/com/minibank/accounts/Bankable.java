package com.minibank.accounts;

/**
 * Interface for core bank methods for banking transactions
 * @author sjgillet
 *
 */
public interface Bankable {
	
	public boolean acctExists(String acctNum); 
	public double balanceInquiry(String acctNum); 
	public int deposit(String acctNum, double amt); 
	public int withdraw(String acctNum, double amt); 
	public int setAcctStatus(String acctNum, int status); 
	public String getAcctOwnerID(String acctNum);
	public String openAcct(String ownerID);
	public int closeAcct(String acctNum);
	
	
	
}
