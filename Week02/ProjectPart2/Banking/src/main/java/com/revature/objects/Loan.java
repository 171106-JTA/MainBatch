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

public class Loan {
	/*
	 *Loan is considered as a special type of account 
	 *the balance <0 till all payments are made.
	 **/
	
	/**
	 * 
	 */

	//Properties
	private int loanNumber;
	private double principal;
	private int term = 60;				//Period of the loan in months
	private double interestRate;
	private int approved = 0;	//Waiting for an Admin to approve
	private int isPastDue = 0;
	private String accountNumber;
	
	//Constructor
	public Loan(int loanNumber, double principal, int term, double interestRate, int approved, int isPastDue, String accountNumber) {
		this.loanNumber = loanNumber;
		this.principal = principal;
		this.term = term;
		this.interestRate = interestRate;
		this.approved = approved;
		this.isPastDue = isPastDue;
		this.accountNumber = accountNumber;
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
			
	public int isApproved() {
		return approved;
	}
	public void Approve() {
		this.approved = 1;
	}
	public void desapprove() {
		this.approved = 0;
	}

	public int isPastDue() {
		return isPastDue;
	}
	public void setPastDue(int isPastDue) {
		this.isPastDue = isPastDue;
	}
	
	public int getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(int loanNumber) {
		this.loanNumber = loanNumber;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	@Override
	public String toString() {
		return super.toString() + 
				"\nLoan details\n-------------\nPrincipal:\t" + principal + "\nTerm=" + term + 
				"\nInterest Rate:\t" + interestRate + "\nApproved:\t" + approved + 
				"\nisPastDue:\t" + isPastDue + "\n-------------";
	}

}
