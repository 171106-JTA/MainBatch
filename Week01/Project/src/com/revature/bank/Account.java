package com.revature.bank;

import java.io.Serializable;
import java.util.LinkedList;

public class Account implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private int _number;
	private double _balance;
	private double _interestRate; // This is not used now, but likely will be. 
	public static transient LinkedList<Integer> acctNumbers; // probably not the best thing to use here...
	// a *very* high threshold for any Account balance (1 billion)
	public static final double MAX_BALANCE = 1000000000.00; 
	
	public Account() { 
		this(0.0);
	}
	
	public Account(double balance)
	{
		this(randomAcctNumber(), balance);
	}
	
	public Account(int acctNumber, double balance)
	{
		// initialize acctNumbers and add acctNumber to it if not already done
		if (acctNumbers == null) {
			acctNumbers = new LinkedList<Integer>();
			acctNumbers.add(acctNumber);
		}
		// if acctNumber not found in acctNumbers, add it
		else 
		{
			// search for acctNumber in acctNumbers
			boolean found = acctNumbers.indexOf(acctNumber) != -1;
			// if not found
			if (!found) {
				// append acctNumber to the list of account numbers
				acctNumbers.push(acctNumber);
			}
			// otherwise
			else
			{
				// while acctNumber is in acctNumbers
				while (acctNumbers.contains(acctNumber))
				{
					// set acctNumber to a new random int
					acctNumber = randomAcctNumber();
				}				
			}
		}
		_number = acctNumber;
		_balance = balance;
		// log account creation
		Application.logger.info(String.format("Account instantiated\nAcct #: %d\nAcct. Bal: $%.2f", 
			_number,
			_balance
		));
	}
	
	/**
	 * Returns the balance for this Account.
	 * @return account balance  
	 */
	public double getBalance() {
		return _balance;
	}
	/**
	 * Sets the balance for this Account.
	 * @param _balance
	 */
	public void setBalance(double balance) {
		_balance = balance;
	}
	
	/**
	 * 
	 * @return the numeric ID of this Account
	 */
	public int getNumber() {
		return _number;
	}
	/**
	 * add money to this Account
	 * @param money
	 */
	public void deposit(double money) throws IllegalArgumentException
	{
/*		if ((_balance + money == Math.max(_balance, money)) && 
				(_balance > 0) && (money > 0))*/
		if ((_balance + money > MAX_BALANCE))
		{
			String message = String.format("Account balance overflow.\n\nAvailable balance: $%.2f\nDeposit amount: $%.2f",
				_balance, 
				money);
			// log attempt at exceeding the limits
			Application.logger.error(message);
			// we're done here
			throw new IllegalArgumentException(message);
		}
		_balance += money;
	}
	
	/**
	 * Withdraws money from this Account
	 * @param money : double
	 * @throws Exception if money exceeds the balance of this Account
	 */
	// NOTE: numerical stability is necessarily threatened iff money is close to _balance
	public void withdraw(double money) throws IllegalArgumentException
	{
		if (money > _balance) {
			String message = String.format("Insufficient funds.\n\nAmount requested: %.2f\nAvailable balance:", 
					money,
					_balance);
			// log overdraw attempt
			Application.logger.error(
				String.format(
					"User tried to withdraw more money than they had.\nAcct. balance : $%.2f\nAmount requested: $%.2f\n%s",
					_balance,
					money,
					message)
			);
			// we're done here
			throw new IllegalArgumentException(message);
		}
		// log
		Application.logger.info(
			String.format("Withdrawal happening.\nOld balance: $%.2f\nWithdrawal amount: $%.2f",
				_balance,
				money));
		_balance -= money;
		Application.logger.info(String.format("New balance: $%.2f", _balance));
	}
			
	private static int randomAcctNumber()
	{
		return (int)(Math.random() * 10000000) + 1;
	}
	
}
