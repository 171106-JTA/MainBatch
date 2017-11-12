package com.revature.processor;

import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Transaction;
import com.revature.businessobject.user.Checkpoint;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.processor.handler.BankingRequestHandler;
import com.revature.server.session.require.Require;

/**
 * Perform complex calculations
 * @author Antony Lulciuc
 */
public class BankingProcessor implements Processorable {
	private static BankingRequestHandler BRH = new BankingRequestHandler();
	
	public Resultset process(Request request) throws RequestException {
		Resultset res = null;
		
		switch (request.getTranstype()) {
			case "CHECKINGADDFUNDS":
				Require.requireCheckpoint(new String[] { Checkpoint.CUSTOMER }, request);
				Require.requireAllQuery(new String[] { Account.NUMBER }, request);
				Require.requireTransaction(new String[] { Transaction.AMOUNT }, request);
				Require.requireSelfQuery(request);
				res = BRH.checkingAddFunds(request);
				break;
			case "CHECKINGREMOVEFUNDS":
				Require.requireCheckpoint(new String[] { Checkpoint.CUSTOMER }, request);
				Require.requireAllQuery(new String[] { Account.NUMBER }, request);
				Require.requireTransaction(new String[] { Transaction.AMOUNT }, request);
				Require.requireSelfQuery(request);
				res = BRH.checkingRemoveFunds(request);
				break;
			default:
				throw new RequestException(request, "Transtype=[\'" + request.getRoute() + "." + request.getTranstype() + "\'] is unknown!");
		}
		
		
		return res;
	}
}
