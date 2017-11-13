package com.minibank.accounts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.minibank.controller.Consts;


/**
 * Bank class, containing the collection for bank accounts, and the 
 * methods for manipulating them via their respectiv Acct object methods. 
 * @author sjgillet
 *
 */
public class Bank implements Bankable, Serializable{

	private static final long serialVersionUID = 4477316476373454257L;

	private static Bank bank; 
	private static HashMap<String, Acct> accounts;

	private static ObjectOutputStream oos;
	private static ObjectInputStream ois; 
	
	static Logger logger = Logger.getLogger(Bank.class); 

	private Bank() {

	}

	/**
	 * Initializes the Bank class's references to itself and to its bank
	 * account map collection object. Also attempts to read in a save file of
	 * serialized account information. 
	 */
	public static void initialize() {
		if(bank == null)
			bank = new Bank();
		if(accounts == null)
			accounts = new HashMap<String, Acct>();

		try {
			ois = new ObjectInputStream(new FileInputStream(Consts.ACCTS_SER_PATH ));
			accounts = (HashMap<String, Acct>) ois.readObject();
		} catch (FileNotFoundException | ClassNotFoundException e) {
			logger.warn("No accounts save file found. Will be generated at next serialization attempt");
		} catch (IOException e) {
			logger.warn("IOException caught at Bank.Initialize(), opening OIS"); 
		}

	}
	
	/**
	 * Bank constructor, calls the Bank to initialize and returns a reference to this class
	 * @return
	 */
	public static Bank getBank() {
		initialize(); 
		return bank; 
	}

	//==================================
	
	/**
	 * Checks if the given bank account exists in the bank's accounts collection
	 * @param acctNum - the account number to search for
	 * @return - true if the account is found in the Bank's accounts collection. 
	 * False otherwise. 
	 */
	@Override
	public boolean acctExists(String acctNum) {
		return accounts.containsKey(acctNum);
	}

	/**
	 * Gets the current balance of the given bank account
	 * @param acctNum - the account number for the account to inquiry
	 * @return - The balance of the given bank account. -1 if the account
	 * is not found. 
	 */
	@Override
	public double balanceInquiry(String acctNum) {
		if(acctExists(acctNum)) {
			return accounts.get(acctNum).getBalance();
		}
		else return -1; 
	}

	/**
	 * Deposits the given amount of money into the given bank account
	 * @param acctNum - The account number for the bank account to deposit to
	 * @param amt - The amount to deposit
	 * @return - 1 if the deposit was susccessful. 0  if the account was not 
	 * found. -1 if the account was not yet activated. 
	 * -2 if the account is locked. 
	 */
	@Override
	public int deposit(String acctNum, double amt) {
		if(acctExists(acctNum)) {
			Acct a = accounts.get(acctNum); 
			if(a.getStatus() == 1) {
				a.depositFunds(amt);
				//error checking
				a = new Acct(); //safety clearing.
				serialize();
				return 1; //deposited successfully
			}
			else {
				int status = a.getStatus();
				a = new Acct(); 
				return status - 1; //account inactive or locked
			}				
		}
		return 0; //did not match account
	}

	/**
	 * Withdraws funds from the given account.
	 * @param acctNum - The account number of the bank account to withdraw from
	 * @param amt - The amount to withdraw from the account
	 * @return - 1 if the withdrawal was successful. 0 if the account was
	 * not found. -1 if the account is not yet activated. -2 if the account
	 * is locked. 
	 */
	@Override
	public int withdraw(String acctNum, double amt) {
		if(acctExists(acctNum)) {
			Acct a = accounts.get(acctNum); 
			if(a.getStatus() == 1) {

				if(a.getBalance() < amt || a.getBalance() <= 0) {
					a = new Acct(); //safety clearing
					return -3; //insufficient funds
				}

				else {	
					a.withdrawFunds(amt);
					//error checking
					a = new Acct(); //safety clearing
					serialize();
					return 1; //withdrawn successfully
				}
			}
			else {
				int status = a.getStatus();
				a = new Acct(); 
				return status - 1; //account inactive or locked
			}				
		}
		return 0; //did not match account
	}

	/**
	 * Gets the activity status of the given bank account.
	 * @param acctNum - The account number of the bank account to inquire
	 * @return - the status of the account. 1 if active and able to take 
	 * transactions. 0 if it is not yet activated. -1 if it is locked.
	 * -2 if the account was not found. 
	 */
	public int getStatus(String acctNum) {
		if(acctExists(acctNum)) {
			return accounts.get(acctNum).getStatus();
		}
		else return -2; //could not find account with acctNum 
	}

	/**
	 * Sets the activity status of the given bank account
	 * @param acctNum - the account number of the account to set status
	 * @param status - the activity status to set. 1 for active and able 
	 * to take transactions. 0 to set to not activated. -1 to lock and
	 * bar from taking transactions.
	 * @return - 1 if the status was set successfully. 0 if the account
	 * was not found. 
	 */
	@Override
	public int setAcctStatus(String acctNum, int status) {
		if(acctExists(acctNum)) {
			accounts.get(acctNum).setStatus(status);
			serialize();
			return 1; 
		}
		else return 0; 
	}

	/**
	 * Activates a given bank account for use.
	 * @param acctNum - The account number for the account to activate
	 * @return - 1 if the account was activated successfully. 0 if it
	 * was not found. 
	 */
	public int activateAcct(String acctNum) {
		int rslt = this.setAcctStatus(acctNum, 1);
		serialize();
		return rslt; 
	}
	
	/**
	 * Gets the userID of the owner of the given account
	 * @param acctNum - the account number for the account to inquire
	 * @return - String userID of the account's owner. Null if the 
	 * account is not found successfully. 
	 */
	@Override
	public String getAcctOwnerID(String acctNum) {
		if(acctExists(acctNum)) {
			return accounts.get(acctNum).getOwnerID();
		}
		return null;
	}

	/**
	 * Locks the given account and bars it from taking transactions
	 * @param acctNum - the account number for the bank account to lock
	 * @return - 1 if the account was locked successfully. -1 if the account
	 * was inactive. -2 if the account was already locked. 0 if the account was
	 * not found
	 */
	public int lockAcct(String acctNum) {
		if(acctExists(acctNum)) {
			int s = accounts.get(acctNum).getStatus();
			accounts.get(acctNum).lock();
			if(s < 1)
				return s - 1; //return a flag for the status of the non-active account;
			else {
				serialize();
				return 1; //successfully locked an active acct
			}
		}
		else return 0; //did not find account by acctNum
	}

	/**
	 * Opens a bank account with the given id as the account's owner and adds it to the 
	 * Bank's accounts collection. 
	 * @param ownerID - The login ID of the user that owns the new account
	 * return - the generated account number for the new bank account
	 */
	@Override
	public String openAcct(String ownerID) {
		Acct acct = new Acct(ownerID);
		String acctNum = acct.getAcctNum();
		accounts.put(acctNum,acct);
		acct = new Acct(); //safety clearing
		serialize();
		return acctNum; 
	}

	/**
	 * Closes the given bank account and remves it from the Bank's accounts collection.
	 * @param acctNum - the account number for the bank account to close.
	 * @return - 1 if the account was closed successfully. 0 if the account was not 
	 * found. 
	 */
	@Override
	public int closeAcct(String acctNum) {
		if(acctExists(acctNum)) {
			accounts.remove(acctNum);
			serialize();
			return 1; 
		}
		else
			return 0;
	}

	/**
	 * Serializes the Bank's accounts collction and saves it to a file given by the path set
	 * in the Constants class. 
	 */
	private void serialize() {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(Consts.ACCTS_SER_PATH));
			oos.writeObject(accounts);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} 

	}



}
