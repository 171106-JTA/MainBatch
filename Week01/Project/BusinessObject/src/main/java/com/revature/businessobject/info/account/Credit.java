package com.revature.businessobject.info.account;

import java.util.Date;

/**
 * 
 * @author Antony Lulciuc
 *
 */
public class Credit extends Account {
	public static final String BALANCE = "balance";
	public static final String MINIMALPAYMENTDUE = "minimal_payment_due";
	public static final String CREDITLIMIT = "credit_limit";
	public static final String RATEID = "rate_id";
	
	private float balance;
	private float minimalPaymentDue;
	private float creditLimit;
	private long rateId;
	
	public Credit(long userId, String number, long typeId, long statusId,
			String created, float balance, float minimalPaymentDue,
			float creditLimit, long rateId) {
		super(userId, number, typeId, statusId, created, balance,  Type.CREDIT);
		this.balance = balance;
		this.minimalPaymentDue = minimalPaymentDue;
		this.creditLimit = creditLimit;
		this.rateId = rateId;
	}
	
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public float getMinimalPaymentDue() {
		return minimalPaymentDue;
	}
	public void setMinimalPaymentDue(float minimalPaymentDue) {
		this.minimalPaymentDue = minimalPaymentDue;
	}
	public float getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(float creditLimit) {
		this.creditLimit = creditLimit;
	}
	public long getRateId() {
		return rateId;
	}
	public void setRateId(long rateId) {
		this.rateId = rateId;
	}
}
