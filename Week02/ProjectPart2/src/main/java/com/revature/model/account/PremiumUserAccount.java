package com.revature.model.account;

import com.revature.model.Properties;

public final class PremiumUserAccount extends AdultUserAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7424634860222954416L;
	private static double interest = Properties.PREMIUM_INT_RATE;
	private static int limit = Properties.PREMIUM_CRED_LIMIT;
	private static int penalty = Properties.PREMIUM_USER_PENALTY;

	public PremiumUserAccount() {
		super(limit, interest, penalty);
	}

	public PremiumUserAccount(UserAccount from) {
		super(from.getUser(), from.getPass(), from.getAge(), from.getSSN(), from.getUid(), from.getLimit(), from.getPenalty(), from.getStatus(), from.getInterest(), from.getBalance(),
				from.getStart(), from.getSuccessiveMillis(), from.getSuccessiveDays(), from.isBalanceNonNeg(),
				from.isStateChange(), from.getDays(), from.getYears(), from.getTransactions(),
				from.getLoans(), from.getApprovedLoanRequests(), from.getRejectedLoanRequests(),
				from.getApprovedAccountRequests(), from.getRejectedAccountRequests(),
				from.getApprovedRollbackRequests(), from.getRejectedRollbackRequests());

		if (from.getInterest() > Properties.PREMIUM_INT_RATE)
			setInterest(Properties.PREMIUM_INT_RATE);
		if (from.getLimit() < Properties.PREMIUM_CRED_LIMIT)
			setLimit(Properties.PREMIUM_CRED_LIMIT);
		if (from.getPenalty() > Properties.PREMIUM_USER_PENALTY)
			setPenalty(Properties.PREMIUM_USER_PENALTY);
	}

	@Override
	public String toString() {
		return "PremiumUserAccount [limit=" + limit + ", penalty=" + penalty + ", status=" + status + ", interest="
				+ interest + ", permissions=" + permissions + "]";
	}

}