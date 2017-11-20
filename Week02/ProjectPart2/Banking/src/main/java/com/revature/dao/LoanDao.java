package com.revature.dao;

import java.util.ArrayList;
import com.revature.objects.Loan;;

public interface LoanDao {

	public void newLoan(Loan loan);
	public Loan findById(int loanNumber);
	public Loan findByCustomer(int customerId);
	public void approve(int loanNumber);
	public ArrayList<Loan> listOfPendingLoans();
	public ArrayList<Loan> listOfApprovedLoans();
	public void deny(int loanNumber);
}
