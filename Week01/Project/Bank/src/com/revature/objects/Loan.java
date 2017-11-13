/**
 * Class Loans
 * 
 * A loan should be considered as an account but
 * because of some specificities like the goal of
 * paying off the account and payments being removed
 * from the balance, we had it split from the account class
 * but it still extends it.
 * 
 * @Instructor Ryan Lessley
 * @author Mahamadou
 * @Version 1.0
 * @Date 11/13/2017
 * 
 */
package com.revature.objects;

import java.util.Date;

public class Loan extends Account {
	/*
	 *Loan is considered as a special type of account 
	 *the balance <0 till all payments are made.
	 **/
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = -8881560815836513074L;
	private static final long serialVersionUID = 5857116751215858827L;

	//Properties
	private double principal;
	private int term = 60;				//Period of the loan in months
	private double interestRate;
	private boolean approved = false;	//Waiting for an Admin to approve
	private boolean isPastDue = false;
	
	//Constructor
	public Loan(String accountNber, Date creationDate, int accTypeID, boolean isActive, int customerID,
			double principal, int term, double interestRate, boolean approved, boolean isPastDue) {
		super(accountNber, creationDate, accTypeID, isActive, customerID);
		this.principal = principal;
		this.term = term;
		this.interestRate = interestRate;
		this.approved = approved;
		this.isPastDue = isPastDue;
	}

	//Getters and Setters
	public double getPrincipal() {
		return principal;
	}
	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}

	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
			
	public boolean isApproved() {
		return approved;
	}
	public void Approve() {
		this.approved = true;
	}
	public void desapprove() {
		this.approved = false;
	}

	public boolean isPastDue() {
		return isPastDue;
	}
	public void setPastDue(boolean isPastDue) {
		this.isPastDue = isPastDue;
	}
	
	@Override
	public String toString() {
		return super.toString() + 
				"\nLoan details\n-------------\nPrincipal:\t" + principal + "\nTerm=" + term + 
				"\nInterest Rate:\t" + interestRate + "\nApproved:\t" + approved + 
				"\nisPastDue:\t" + isPastDue + "\n-------------";
	}

}
