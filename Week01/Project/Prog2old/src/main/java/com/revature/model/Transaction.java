package com.revature.model;

import java.io.Serializable;

public class Transaction implements Serializable {
	/**
	 * The transaction of a user
	 * Transactions specify the amount, a description, an enumerator type, 
	 * and optionally a destination of the transaction
	 */
	private static final long serialVersionUID = -4492337687629895886L;
	private double amount;
	private static int id = 0;
	private String message;
	private int uid;
	private Type type;
	private Object destination;

	public Transaction(double amount, String msg, Type type, Object dest) {
		this.amount = amount;
		this.message = msg;
		this.type = type;
		this.destination = dest;
		synchronized (Transaction.class) {
			uid = id;
			id++;
		}
	}

	public int getUid() {
			return uid;
	}

	public double getAmount() {
		return amount;
	}

	public String getMessage() {
		return message;
	}
	
	public Type getType() {
		return type;
	}
	
	public Object getDestination() {
		return destination;
	}

	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", + type=" + type + ", + "
				+ "message=" + message + ", destination=" + destination + "]";
	}
	
	public enum Type {
		WITHDRAW, DEPOSIT, LOAN_PAYMENT, LOAN_DISPERSAL;
	}
	
	
}