package com.revature.processor.handler;

import com.revature.businessobject.info.Info;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Transaction;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.StringFormater;
import com.revature.core.exception.RequestException;
import com.revature.server.Server;

public final class BankingRequestHandler {
	public Resultset checkingAddFunds(Request request) throws RequestException {
		FieldParams transact = request.getTransaction();
		FieldParams query = request.getQuery();
		Checking account;
		Resultset data;
		float amount;
		
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
			
			// Get current account 
			if ((data = Server.database.select(BusinessClass.ACCOUNT, query)).size() == 0)
					throw new RequestException(request, "Account not found!");
			
			// Perform calculation
			account = (Checking)data.get(0);
			amount += account.getTotal();
			
			// Update account total
			transact.put(Checking.TOTAL, StringFormater.currency(amount));
			
			// Update account
			return new Resultset(Server.database.update(BusinessClass.ACCOUNT, request.getQuery(), transact));
		}
	}

	
	public Resultset checkingRemoveFunds(Request request) throws RequestException {
		FieldParams transact = request.getTransaction();
		FieldParams query = request.getQuery();
		Checking account;
		Resultset data;
		float amount;
		
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
			
			// Get current account 
			if ((data = Server.database.select(BusinessClass.ACCOUNT, query)).size() == 0)
					throw new RequestException(request, "Account not found!");
			
			// Perform calculation
			account = (Checking)data.get(0);
			amount -= account.getTotal();
			
			// Prevent account overdraw
			if (amount < 0.0f)
				throw new RequestException(request, "You do not have enough funds to preform transaction!");
			
			// Update account total
			transact.put(Checking.TOTAL, StringFormater.currency(amount));
			
			// Update account
			return new Resultset(Server.database.update(BusinessClass.ACCOUNT, request.getQuery(), transact));
		}
	}
}
