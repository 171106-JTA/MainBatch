package com.revature.businessobject.info.account;

import java.util.Date;

import com.revature.businessobject.BusinessObject;

public class Invoice extends BusinessObject {
	public static final String ID = "id";
	public static final String ACCOUNTNUMBER = "account_number";
	public static final String DATEISSUED = "date_issued";
	public static final String PAYMENTDATE = "payment_date";
	public static final String AMOUNTDUE = "amount_due";
	
	private long id;
	private String accountNumber;
	private Date dateIssued;
	private Date paymentDate;
	private float amountDue;
	
	public Invoice() {
		super();
	}
	
	public Invoice(long id, String accountNumber, Date dateIssued,
			Date paymentDate, float amountDue) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.dateIssued = dateIssued;
		this.paymentDate = paymentDate;
		this.amountDue = amountDue;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Date getDateIssued() {
		return dateIssued;
	}
	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public float getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(float amountDue) {
		this.amountDue = amountDue;
	}
}
