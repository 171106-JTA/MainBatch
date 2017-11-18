package com.revature.businessobject.info.account;

import java.util.Date;

import com.revature.businessobject.BusinessObject;

public class Receipt extends BusinessObject {
	private long id;
	private long invoiceId;
	private float amountPaid;
	private Date datePaid;
	
	public Receipt() {
		super();
	}
	
	public Receipt(long id, long invoiceId, float amountPaid, Date datePaid) {
		super();
		this.id = id;
		this.invoiceId = invoiceId;
		this.amountPaid = amountPaid;
		this.datePaid = datePaid;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public float getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(float amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Date getDatePaid() {
		return datePaid;
	}
	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}
}
