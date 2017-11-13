package com.revature.model.account;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.revature.model.Loan;
import com.revature.model.Transaction;
import com.revature.model.request.AccountRequest;
import com.revature.model.request.CancelRequest;
import com.revature.model.request.LoanRequest;

public abstract class AdultUserAccount extends UserAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3486958955145209273L;

	public AdultUserAccount(int limit, double interest, int penalty) {
		super(limit, interest, penalty);
	}

	public AdultUserAccount(String user, String pass, int age, int ssn, int uid, int limit, int penalty, int status,
			double interest, double balance, Date start, long successiveMillis, int successiveDays,
			boolean balanceNonNeg, boolean stateChange, int days, int years, Map<Integer, Transaction> transactions,
			Map<Integer, Loan> loans, Set<LoanRequest> approvedLoanRequests, Set<LoanRequest> rejectedLoanRequests,
			Set<AccountRequest> approvedAccountRequests, Set<AccountRequest> rejectedAccountRequests,
			Set<CancelRequest> approvedRollbackRequests, Set<CancelRequest> rejectedRollbackRequests) {
		
		super(user, pass, age, ssn, limit, penalty, status, interest, balance, start, successiveMillis, successiveDays, balanceNonNeg,
				stateChange, days, years, transactions, loans, approvedLoanRequests, rejectedLoanRequests,
				approvedAccountRequests, rejectedAccountRequests, approvedRollbackRequests, rejectedRollbackRequests);
	}
}
