package com.revature.processor.handler;

import java.util.ArrayList;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.CodeList;
import com.revature.businessobject.info.Info;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Status;
import com.revature.businessobject.info.account.Transaction;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.StringFormater;
import com.revature.core.exception.RequestException;
import com.revature.server.Server;

/**
 * Defines banking process operations 
 * @author Antony Lulciuc
 */
public final class BankingRequestHandler {
	List<CodeList> accountStatus;
	
	
	/**
	 * Attempts to add funds to account
	 * @param request - must define query and transaction
	 * @return resultset with modified record count of 1 on success
	 * @throws RequestException
	 */
	public Resultset checkingAddFunds(Request request) throws RequestException {
		FieldParams transact = request.getTransaction();
		FieldParams query = request.getQuery();
		Account account;
		Resultset data;
		float amount;
		
		if (accountStatus == null) {
			init();
		}
		
		try {
			// Ensure we are adding a numerical value
			amount = Float.parseFloat(transact.get(Transaction.AMOUNT));
		} catch (NumberFormatException e) {
			throw new RequestException(request, e.getMessage());
		}
		
		// Ensure we are added a Positive amount to account 
		if (amount < 0.0f) 
			throw new RequestException(request, "You cannot add negative funds to your account!");
		else {
			// Set session user id as id
			query.put(Info.USERID, Long.toString(request.getUserId()));
			query.remove(Account.BALANCE);
			
			// Get current account 
			if ((data = Server.database.select(BusinessClass.ACCOUNT, query)).size() == 0)
					throw new RequestException(request, "Account not found!");
			
			// Ensure account is active
			if (!getCodeListById(accountStatus, ((account = (Account)data.get(0)).getStatusId())).getValue().toLowerCase().equals(Status.ACTIVE))
				throw new RequestException(request, "Account must be activated before use!");		
			
			// Perform calculation
			amount += account.getBalance();
			
			transact.clear();
			
			// Update account total
			transact.put(Account.BALANCE, StringFormater.currency(amount));
			
			// Update account
			return new Resultset(Server.database.update(BusinessClass.ACCOUNT, request.getQuery(), transact));
		}
	}

	/**
	 * Attempts to remove funds to account
	 * @param request - must define query and transaction
	 * @return resultset with modified record count of 1 on success
	 * @throws RequestException
	 */
	public Resultset checkingRemoveFunds(Request request) throws RequestException {
		FieldParams transact = request.getTransaction();
		FieldParams query = request.getQuery();
		Account account;
		Resultset data;
		float amount;
		
		if (accountStatus == null) {
			init();
		}
		
		try {
			// Ensure we are adding a numerical value
			amount = Float.parseFloat(transact.get(Transaction.AMOUNT));
		} catch (NumberFormatException e) {
			throw new RequestException(request, e.getMessage());
		}
		
		// Ensure we are added a Positive amount to account 
		if (amount < 0.0f) 
			throw new RequestException(request, "You cannot remove negative funds from your account!");
		else {
			// Set session user id as id
			query.put(Info.USERID, Long.toString(request.getUserId()));
			query.remove(Account.BALANCE);
			
			// Get current account 
			if ((data = Server.database.select(BusinessClass.ACCOUNT, query)).size() == 0)
					throw new RequestException(request, "Account not found!");
			
			// Ensure account is active
			if (!getCodeListById(accountStatus, ((account = (Account)data.get(0)).getStatusId())).getValue().toLowerCase().equals(Status.ACTIVE))
				throw new RequestException(request, "Account must be activated before use!");
			
			// Perform calculation
			amount = account.getBalance() - amount;
			
			// Prevent account overdraw
			if (amount < 0.0f)
				throw new RequestException(request, "You do not have enough funds to preform transaction!");
			
			transact.clear();
			
			// Update account total
			transact.put(Checking.BALANCE, StringFormater.currency(amount));
			
			// Update account
			return new Resultset(Server.database.update(BusinessClass.ACCOUNT, request.getQuery(), transact));
		}
	}
	
	
	private void init() {
		FieldParams params = new FieldParams();
		Resultset res;
		
		// init status data
		accountStatus = new ArrayList<>();
		params.put(CodeList.CODE, "ACCOUNT-STATUS");
		
		// get data
		res = Server.database.select(BusinessClass.CODELIST, params);
				
		for (BusinessObject object : res) 
			accountStatus.add((CodeList)object);
	}
	
	
	private CodeList getCodeListById(List<CodeList> list, long id) {
		CodeList code = null;
		
		for (CodeList item : list) {
			if (item.getId() == id) {
				code = item;
				break;
			}
		}
		
		return code;
	}
	
	private CodeList getCodeListByValue(List<CodeList> list, String value) {
		CodeList code = null;
		
		for (CodeList item : list) {
			if (item.getValue().equals(value.toUpperCase())) {
				code = item;
				break;
			}
		}
		
		return code;
	}
	
}
