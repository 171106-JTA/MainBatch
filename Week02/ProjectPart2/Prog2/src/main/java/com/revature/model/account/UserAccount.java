package com.revature.model.account;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.model.Properties;
import com.revature.model.*;
import com.revature.model.Transaction.Type;
import com.revature.model.request.*;
import com.revature.throwable.*;
import com.revature.view.Console;

public abstract class UserAccount extends Account {
	/**
	 * UserAccount: The top-level class of all user account types
	 * Handles all user functionality
	 */
	private static final long serialVersionUID = -4924824758721672232L;
	private static Logger log = Logger.getLogger(UserAccount.class);
	protected int limit, penalty, status;
	protected double interest;
	private double balance;
	private Date start = new Date();
	private Date timeStop = new Date();
	private Date threadStop = new Date();
	private long successiveMillis = 0;
	private int successiveDays = 0;
	private boolean balanceNonNeg = true;
	private boolean stateChange = true;
	private int days = 0;
	private int years = 0;
	private boolean run = true;
	private int ssn = 0;
	private transient Timer acctTimer;
	private transient Thread acctMonitor;
	private Map<Integer, Transaction> transactions = new HashMap<Integer, Transaction>();
	private Map<Integer, Loan> loans = new HashMap<Integer, Loan>();
	private Set<LoanRequest> approvedLoanRequests = new HashSet<LoanRequest>();
	private Set<LoanRequest> rejectedLoanRequests = new HashSet<LoanRequest>();
	private Set<AccountRequest> approvedAccountRequests = new HashSet<AccountRequest>();
	private Set<AccountRequest> rejectedAccountRequests = new HashSet<AccountRequest>();
	private Set<CancelRequest> approvedRollbackRequests = new HashSet<CancelRequest>();
	private Set<CancelRequest> rejectedRollbackRequests = new HashSet<CancelRequest>();

	public UserAccount(int limit, double interest, int penalty) {
		super();
//		start = new Date();
//		status = com.revature.model.Properties.ACCT_STAT_PENDING;
//		this.interest = interest;
//		this.limit = limit;
//		this.penalty = penalty;
		acctTimer = new Timer();
		acctTimer.schedule(new AcctTimeTask(), TimeUnit.DAYS.toMillis(1));
		acctMonitor = new AcctThread();
		acctMonitor.start();
	}

	public UserAccount(String user, String pass, int age, int ssn, int limit, int penalty, int status, double interest,
			double balance, Date start, long successiveMillis, int successiveDays, boolean balanceNonNeg,
			boolean stateChange, int days, int years, Map<Integer, Transaction> transactions, Map<Integer, Loan> loans,
			Set<LoanRequest> approvedLoanRequests, Set<LoanRequest> rejectedLoanRequests,
			Set<AccountRequest> approvedAccountRequests, Set<AccountRequest> rejectedAccountRequests,
			Set<CancelRequest> approvedRollbackRequests, Set<CancelRequest> rejectedRollbackRequests) {
		super(user, pass, age, ssn);
		this.limit = limit;
		this.penalty = penalty;
		this.status = status;
		this.interest = interest;
		this.balance = balance;
		this.start = start;
		this.successiveMillis = successiveMillis;
		this.successiveDays = successiveDays;
		this.balanceNonNeg = balanceNonNeg;
		this.stateChange = stateChange;
		this.days = days;
		this.years = years;
		this.ssn = ssn;
		this.transactions = transactions;
		this.loans = loans;
		this.approvedLoanRequests = approvedLoanRequests;
		this.rejectedLoanRequests = rejectedLoanRequests;
		this.approvedAccountRequests = approvedAccountRequests;
		this.rejectedAccountRequests = rejectedAccountRequests;
		this.approvedRollbackRequests = approvedRollbackRequests;
		this.rejectedRollbackRequests = rejectedRollbackRequests;
		setTimer();
		startThread();
	}

	// getters and setters
	public long getSuccessiveMillis() {
		return successiveMillis;
	}

	public void setSuccessiveMillis(long successiveMillis) {
		this.successiveMillis = successiveMillis;
	}

	public int getSuccessiveDays() {
		return successiveDays;
	}

	public void setSuccessiveDays(int successiveDays) {
		this.successiveDays = successiveDays;
	}

	public boolean isBalanceNonNeg() {
		return balanceNonNeg;
	}

	public void setBalanceNonNeg(boolean balanceNonNeg) {
		this.balanceNonNeg = balanceNonNeg;
	}

	public boolean isStateChange() {
		return stateChange;
	}

	public void setStateChange(boolean stateChange) {
		this.stateChange = stateChange;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public Set<LoanRequest> getApprovedLoanRequests() {
		return approvedLoanRequests;
	}

	public void setApprovedLoanRequests(Set<LoanRequest> approvedLoanRequests) {
		this.approvedLoanRequests = approvedLoanRequests;
	}

	public Set<LoanRequest> getRejectedLoanRequests() {
		return rejectedLoanRequests;
	}

	public void setRejectedLoanRequests(Set<LoanRequest> rejectedLoanRequests) {
		this.rejectedLoanRequests = rejectedLoanRequests;
	}

	public Set<AccountRequest> getApprovedAccountRequests() {
		return approvedAccountRequests;
	}

	public void setApprovedAccountRequests(Set<AccountRequest> approvedAccountRequests) {
		this.approvedAccountRequests = approvedAccountRequests;
	}

	public Set<AccountRequest> getRejectedAccountRequests() {
		return rejectedAccountRequests;
	}

	public void setRejectedAccountRequests(Set<AccountRequest> rejectedAccountRequests) {
		this.rejectedAccountRequests = rejectedAccountRequests;
	}

	public Set<CancelRequest> getApprovedRollbackRequests() {
		return approvedRollbackRequests;
	}

	public void setApprovedRollbackRequests(Set<CancelRequest> approvedRollbackRequests) {
		this.approvedRollbackRequests = approvedRollbackRequests;
	}

	public Set<CancelRequest> getRejectedRollbackRequests() {
		return rejectedRollbackRequests;
	}

	public void setRejectedRollbackRequests(Set<CancelRequest> rejectedRollbackRequests) {
		this.rejectedRollbackRequests = rejectedRollbackRequests;
	}

	public void setLoans(Map<Integer, Loan> loans) {
		this.loans = loans;
	}

	public int getSSN() {
		return ssn;
	}

	public void setSSN(int ssn) {
		this.ssn = ssn;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setTransactions(Map<Integer, Transaction> transactions) {
		this.transactions = transactions;
	}

	/**
	 * Set timer for UserAccount for firing off time related events 
	 */
	public void setTimer() {
		days += TimeUnit.MILLISECONDS.toDays(new Date().getTime()) - TimeUnit.MILLISECONDS.toDays(timeStop.getTime());
		acctTimer = new Timer();
		acctTimer.schedule(new AcctTimeTask(), TimeUnit.DAYS.toMillis(1));
	}

	/**
	 * Stops the time and saves its stop time
	 */
	public void stopTimer() {
		timeStop = new Date();
		acctTimer.cancel();
	}

	/**
	 * Start thread for user accounts. Thread monitors sensitive activity for 
	 * bonuses and penalties to credit limits and interest rates
	 */
	public void startThread() {
		successiveDays += TimeUnit.MILLISECONDS.toDays(new Date().getTime())
				- TimeUnit.MILLISECONDS.toDays(threadStop.getTime());
		successiveMillis += (new Date().getTime() - threadStop.getTime()) % TimeUnit.DAYS.toMillis(1);
		acctMonitor = new AcctThread();
		acctMonitor.start();
	}

	/**
	 * Flag the thread to exit loop and stop
	 * 
	 */
	public void tryStopThread() {
		threadStop = new Date();
		run = false;
		if (acctMonitor.isAlive())
			acctMonitor.interrupt();
	}

	protected void setPermissions() {
		super.permissions = com.revature.model.Properties.READ;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
		if (this.limit < 0)
			this.limit = 0;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getBalance() {
		return balance;
	}

	/**
	 * Sets the account balance. The monitor thread will record the length 
	 * the balance stays positive or negative to issue bonuses or penalties 
	 * 
	 * @param balance: the new balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
		if (this.balance >= 0) {
			if (!balanceNonNeg)
				stateChange = true;
			balanceNonNeg = true;
		} else {
			if (balanceNonNeg) {
				stateChange = true;
				Console.print(Properties.DEBT_WARN, Level.WARN_INT);
			}
			balanceNonNeg = false;
		}
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public Date getStart() {
		return start;
	}

	/**
	 * Processes the transaction for this user. throws an overdraft exception if balance would go negative
	 * 
	 * @param amount: the amount of the transaction
	 * @param msg: a description of the transaction
	 * @param override: an override flag for issuing overdraft penalties
	 */
	public synchronized Transaction processTransaction(double amount, String message, boolean override)
			throws AccountOverdraftException {
		Transaction.Type type = null;
		Transaction t = null;
		if (balance + amount < 0)
			if (!override) {
				throw new AccountOverdraftException();
			}
		type = amount >= 0 ? Type.DEPOSIT : Type.WITHDRAW;
		t = new Transaction(amount, message, type, this);
		synchronized (transactions) {
			transactions.put(t.getUid(), t);
		}
		balance += amount;
		setBalance(balance);
		return t;
	}

	/**
	 * Processes the a special transaction of loan for this user. 
	 * throws an overdraft exception if balance would go negative
	 * 
	 * @params id: the loan id
	 * @param amount: the amount of the transaction
	 * @param msg: a description of the transaction
	 * @param override: an override flag for issuing overdraft penalties
	 */
	public synchronized Transaction processTransaction(int id, double amount, String message, boolean override) 
			throws AccountOverdraftException {
		Transaction t;
		Transaction.Type type;
		if (balance - amount < 0)
			if (!override) {
				throw new AccountOverdraftException();
			}
		if (amount > 0) {
			if (loans.get(id).payDownLoan(amount)) {
				loans.remove(id);
			}
		}

		type = amount < 0 ? Type.LOAN_DISPERSAL : Type.LOAN_PAYMENT;
		t = new Transaction(-amount, message, type, loans.get(id));
		synchronized (transactions) {
			transactions.put(t.getUid(), t);
		}

		balance -= amount;
		setBalance(balance);
		return t;
	}

	/**
	 * Cancels any active transaction and rolls back to before the transaction
	 * 
	 * @param id: the transaction id
	 */
	public synchronized void cancelTransaction(int id) {
		balance -= transactions.get(id).getAmount();
		transactions.remove(id);
		setBalance(balance);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 *  Adds approved or rejected requests to the proper set
	 * 
	 * @param prot: the procedural protocol
	 * @param r: the request to add
	 */
	public boolean addToSet(int prot, Request r) {
		if (r instanceof LoanRequest) {
			if (prot == com.revature.model.Properties.PROT_APPROVE)
				return approvedLoanRequests.add((LoanRequest) r);
			return rejectedLoanRequests.add((LoanRequest) r);
		}
		if (r instanceof AccountRequest) {
			if (prot == com.revature.model.Properties.PROT_APPROVE)
				return approvedAccountRequests.add((AccountRequest) r);
			return rejectedAccountRequests.add((AccountRequest) r);
		}
		if (r instanceof CancelRequest) {
			if (prot == com.revature.model.Properties.PROT_APPROVE)
				return approvedRollbackRequests.add((CancelRequest) r);
			return rejectedRollbackRequests.add((CancelRequest) r);
		}

		return false;
	}

	public abstract String toString();

	public Map<Integer, Transaction> getTransactions() {
		return transactions;
	}

	/**
	 *  Appends a loan to this account
	 * 
	 * @param amount: the amount of the loan
	 * @param string: the description of the loan
	 */
	public void processLoan(double amount, String string) {
		amount  = amount < 0 ? amount = -amount : amount;
		Loan l = new Loan(amount);
		loans.put(l.getUid(), l);
		processTransaction(l.getUid(), -amount, string, false);
	}

	public Map<Integer, Loan> getLoans() {
		return loans;
	}
	
	/**
	 * AcctTimeTask: the account time task is the time taks associated to the account timer.
	 * I fires events for fixed timestamps such as membership benefits and birthdays 
	 * The time task is charged with handling all such timed related events and may result
	 * in age-based account conversions via an Observer or reduced interest rates with 
	 * loyalty/memebership rewards
	 * 
	 * @param amount: the amount of the transaction
	 * @param msg: a description of the transaction
	 * @param override: an override flag for issuing overdraft penalties
	 */

	private class AcctTimeTask extends TimerTask {
		public static final int FIRST_RATE = 10;
		public static final int SECOND_RATE = 20;

		@Override
		public void run() {
			days++;
			for (int i = 0; i < days / 365; i++) {
				years++;
				setAge(getAge() + 1);
				days = 0;
				if (UserAccount.this instanceof BasicUserAccount) {
					if (years > FIRST_RATE)
						setInterest(getInterest() - com.revature.model.Properties.BASIC_INT_RATE_BELOW20);
					if (years > SECOND_RATE)
						setInterest(getInterest() - com.revature.model.Properties.BASIC_INT_RATE_BELOW30);
				}
			}
		}
	}

	/**
	 * The account thread monitors all realtime account activity to issue bonuses or penalties 
	 * depending on the state of user accounts. If user balances remain in a set state for a 
	 * lont enough period of time, they will be rewarded or penalized depending on what state
	 * they are in
	 */
	private class AcctThread extends Thread {

		public AcctThread() {
			run = true;
			setDaemon(true);
		}

		@Override
		public void run() {
			while (run) {
				long start = 0;
				start = System.currentTimeMillis();
				if (stateChange) {
					successiveDays = 0;
					successiveMillis = 0;
					stateChange = false;
				}
				successiveMillis += (System.currentTimeMillis() - start);
				successiveDays = (int) TimeUnit.MILLISECONDS.toDays(successiveMillis);
				successiveMillis -= TimeUnit.DAYS.toMillis(successiveDays);
				log.trace("time: " + successiveDays + " days " + successiveMillis + "ms");
				Console.logFinances(balance, interest, penalty, limit, Level.TRACE_INT);

				for (int i = com.revature.model.Properties.SUCESSIVE_DAYS; i < successiveDays; successiveDays -= i) {
					if (balanceNonNeg) {
						setLimit(getLimit() + com.revature.model.Properties.CRED_LIMIT_RATE);
					} else {
						setInterest(getInterest() + com.revature.model.Properties.INT_RATE_PER_MO);
						setLimit(getLimit() - com.revature.model.Properties.CRED_LIMIT_RATE);
					}
				}
			}
		}
	}
}
