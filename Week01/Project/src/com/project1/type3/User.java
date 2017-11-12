package com.project1.type3;

import java.io.Serializable;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class User implements Serializable{
	
	final static Logger logger = Logger.getLogger("User.class");
	
	private String username, password;
	private boolean admin, locked, loanPending, approved;
	private float deposit, loan, debt;
	
	/**
	 * Constructor for user account. Only takes username and password string arguments
	 * to initialize User object. Otherwise all fields by defaults are false or 0
	 * since new user has not made any type of transactions before approval by administrator and
	 * their first login.
	 * @param username
	 * @param password
	 */
	public User(String username, String password){
		this.username = username;
		this.password = password;
		this.admin = false;
		this.deposit = 0.0f;
		this.loan = 0.0f;
		this.debt = 0.0f;
		this.locked = false;
		this.loanPending = false;
		this.approved = false;
	}

	public String getUsername() {
		return username;
	}
	
	/*
	 * Getters for class
	 */
	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean isLoanPending() {
		return loanPending;
	}

	public float getDeposit() {
		return deposit;
	}

	public float getLoan() {
		return loan;
	}

	public float getDebt() {
		return debt;
	}
	
	public boolean isApproved(){
		return approved;
	}
	
	public void makeApproved(){
		approved = true;
	}
	
	/**
	 * user is prompted for an amount of money to deposit. If the user puts a negative value
	 * the method will not let them progress until they enter either a zero or positive value.
	 */
	public void makeDeposit(){
		Scanner scan = new Scanner(System.in);
		float money;
		System.out.print("What amount of money would you like to deposit?\n$");
		money = scan.nextFloat();
		if(money < 0)
			while(money < 0){
				System.out.print("Please enter a valid value\n$");
				money = scan.nextFloat();
			}
		logger.trace("USER: " + username + " DEPOSITED $" + money);
		deposit+=money;
	}
	
	/**
	 * user is prompted to make a withdrawal from their account. They method will not 
	 * allow the user to exceed their deposit or request a negative amount of money
	 */
	public void makeWithdrawal(){
		Scanner scan = new Scanner(System.in);
		float money;
		System.out.print("What amount of money would you like to deposit?\n$");
		money = scan.nextFloat();
		if(money < 0)
			while(money < 0){
				System.out.print("Please enter a valid value\n$");
				money = scan.nextFloat();
			}
		if(money > deposit)
			while(money > deposit){
				System.out.print("You are above your balance by $" + (money - deposit) + "!\n$");
				money = scan.nextFloat();
			}
		
		if(money > 0)logger.trace("USER: " + username + " WITHDREW $" + money);
		deposit-=money;
	}
	
	/**
	 * User is prompted to enter a positive value to take a loan. Their loan request will be stored in
	 * the user's loan variable and will make the boolean loanPending set to true. They will also be notified in their
	 * account menu that their request is pending until an admin either approves or denies their request.
	 */
	public void takeLoan(){
		Scanner scan = new Scanner(System.in);
		System.out.print("How much would you like to borrow?\n$");
		loan = scan.nextFloat();
		if(loan < 0)
			while(loan < 0){
				System.out.print("Please enter a valid amount.\n$");
				loan = scan.nextFloat();
			}
		if(loan > 0){
			loanPending = true;
			logger.warn("USER: " + username + " APPLIED FOR $" + loan + " LOAN");
		}
	}
	
	/**
	 * User is prompted to make a payment on their loans. The method will not accept negative values.
	 * If the user pays over the amount due the program deposit the extra amount into their
	 * account.
	 */
	public void makePayment(){
		Scanner scan = new Scanner(System.in);
		float money;
		float extra = 0.0f;
		
		if(debt > 0.0f){
			System.out.print("How much would you like to pay back?\n$");
			money = scan.nextFloat();
			
			if(money < 0){
				while(money < 0){
					System.out.print("Please enter a valid amount.\n$");
					money = scan.nextFloat();
				}
			}
			
			if(money > debt){
				extra = money - debt;
				money = debt;
				System.out.println("You have payed over the amount due, the remaining $" + extra + " will deposited in your account.");
				//debt = 0;
				//deposit+=extra;
			}
			
			
			if(money > 0 && extra == 0.0f)logger.debug("USER: " + username + " MADE LOAN PAYMENT OF $" + money);
			else if(money > 0){
				logger.debug("USER: " + username + " MADE A LOAN PAYMENT OF $" + (money+extra));
				logger.trace("USER: " + username + " DEPOSITED AMOUNT OF $" + extra);
			}
			debt-=money;
			deposit+=extra;
			
			if(debt == 0.0f)logger.debug("USER: " + username + " HAS COMPLETED LOAN PAYMENT");
		}
		else
			System.out.println("You do not have any payments to make.");
		
	}
	
	/**
	 * Account's boolean for admin is set to true, they will now be able to login as administrator.
	 */
	public void makeAdmin(){
		admin = true;
		logger.info("USER: " + username + " HAS BEEN APPROVED FOR ADMIN ACCESS");
	}
	
	/**
	 * Account's boolean is set to true, they will not be able to login at all.
	 */
	public void lockAccount(){
		if(!locked)locked = true;
		logger.warn("USER: " + username + " HAS BEEN LOCKED");
	}
	
	/**
	 * Account's boolean for locked is set to false, they will be able to login
	 */
	public void unlockAccount(){
		if(locked)locked = false;
		logger.warn("USER: " + username + " HAS BEEN UNLOCKED");
	}
	
	/**
	 * Account's approval for loan has their debt and deposit field be incremented by loan amount and 
	 * has the boolean for loanPending to be set false (user will no longer get a notification for loan pending). 
	 * loan is set back to 0 since request is approved
	 */
	public void loanApproved(){
		if(loanPending){
			logger.warn("USER: " + username + " HAS BEEN APPROVED FOR LOAN OF $" + loan);
			loanPending = false;
			debt+=loan;
			deposit+=loan;
			loan = 0.0f;
			
		}
	}
	
	/**
	 * Account's request for loan is denied. Boolean is set to false since admin has viewed the request and
	 * made decision on it. Also loan is set back to 0. Note that debt and deposit have been left unaltered.
	 */
	public void loanDenied(){
		if(loanPending){
			logger.debug("USER: " + username + " HAS BEEN DENIED FOR LOAN OF $" + loan);
			loanPending = false;
			loan = 0.0f;
			
		}
	}

}
