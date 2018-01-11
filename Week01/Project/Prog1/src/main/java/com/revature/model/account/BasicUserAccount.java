package com.revature.model.account;

import com.revature.model.Properties;


public final class BasicUserAccount extends AdultUserAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2386161524991619034L;
	
	private static double interest = Properties.BASIC_INT_RATE_BELOW10;
	private static int limit = Properties.BASIC_CRED_LIMIT;
	private static int penalty = Properties.BASIC_USER_PENALTY;
	
	public BasicUserAccount() {
		super(limit, interest, penalty);
	}
	
	public BasicUserAccount(UserAccount from) {

		super(from.getUser(), from.getPass(), from.getAge(), from.getSSN(), from.getUid(), from.getLimit(), from.getPenalty(), from.getStatus(), from.getInterest(), from.getBalance(),
				from.getStart(), from.getSuccessiveMillis(), from.getSuccessiveDays(), from.isBalanceNonNeg(),
				from.isStateChange(), from.getDays(), from.getYears(), from.getTransactions(),
				from.getLoans(), from.getApprovedLoanRequests(), from.getRejectedLoanRequests(),
				from.getApprovedAccountRequests(), from.getRejectedAccountRequests(),
				from.getApprovedRollbackRequests(), from.getRejectedRollbackRequests());
	}

	public void setInterest(double interest) {
		super.setInterest(interest);
	}

	@Override
	public String toString() {
		return "BasicUserAccount [limit=" + limit + ", penalty=" + penalty + ", status=" + status + ", interest="
				+ interest + ", permissions=" + permissions + "]";
	}

	
}