package com.revature.model.account;

import com.revature.model.Properties;

public final class SeniorUserAccount extends AdultUserAccount {
	private static double interest = Properties.SENIOR_INT_RATE;
	private static int limit = Properties.SENIOR_CRED_LIMIT;
	private static int penalty = Properties.SENIOR_USER_PENALTY;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8157795139108118928L;

	public SeniorUserAccount() {
		super(limit, interest, penalty);
	}
	
	public SeniorUserAccount(UserAccount from) {

		super(from.getUser(), from.getPass(), from.getAge(), from.getSSN(), from.getUid(), from.getLimit(), from.getPenalty(), from.getStatus(), from.getInterest(), from.getBalance(),
				from.getStart(), from.getSuccessiveMillis(), from.getSuccessiveDays(), from.isBalanceNonNeg(),
				from.isStateChange(), from.getDays(), from.getYears(), from.getTransactions(),
				from.getLoans(), from.getApprovedLoanRequests(), from.getRejectedLoanRequests(),
				from.getApprovedAccountRequests(), from.getRejectedAccountRequests(),
				from.getApprovedRollbackRequests(), from.getRejectedRollbackRequests());
	}
	@Override
	public String toString() {
		return "SeniorUserAccount [limit=" + limit + ", penalty=" + penalty + ", status=" + status + ", interest="
				+ interest + ", permissions=" + permissions + "]";
	}
	
	
}