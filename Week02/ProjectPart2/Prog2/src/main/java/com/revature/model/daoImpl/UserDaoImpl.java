package com.revature.model.daoImpl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import com.revature.model.DBProperties;
import com.revature.model.Loan;
import com.revature.model.Transaction;
import com.revature.model.account.UserAccount;
import com.revature.model.interfaces.dao.*;
import com.revature.model.request.AccountRequest;
import com.revature.model.request.CancelRequest;
import com.revature.model.request.LoanRequest;
import com.revature.util.JDBC;

public class UserDaoImpl implements UserDao {
	private final String table = DBProperties.USER_TAB;
	private static String insert = DBProperties.ROOT_SQL + CredentialsDaoImpl.class.getSimpleName() + DBProperties.INSERT;
	private static String update = DBProperties.ROOT_SQL + CredentialsDaoImpl.class.getSimpleName() + DBProperties.UPDATE;
	private static String delete = DBProperties.ROOT_SQL + CredentialsDaoImpl.class.getSimpleName() + DBProperties.DELETE;
	private static String fetch = DBProperties.ROOT_SQL + CredentialsDaoImpl.class.getSimpleName() + DBProperties.FETCH;
	private String query = "";
	
	public UserDaoImpl() {
		
	}

	@Override
	public Map<String, UserAccount> getUsers() {
		HashMap<String, UserAccount> ret = new HashMap<String, UserAccount>();
		
//		Dao<UserAccount> daos;
//		List<String> queries = new LinkedList<String>();
//		try(Stream<String> stream = Files.lines(Paths.get(fetch))){
//			query += stream.map(line -> line + "\n");
//			queries.add(String.format(query, table));
//		}
//		daos = JDBC.querySet(queries, true);
//		for(Dao d : daos) {
//			UserAccount u = (UserAccount) d.getT();
////			UserAccount user = new UserAccount(u.getU, u.getPass(), u.getAge(), u.getSSN(), u.getLimit(), u.getPenalty(), u.getStatus()
////					u.getInterest(), u.getBalance(), u.getStart(), u.getSuccessiveMillis(), u.getSuccessiveDays(), u.isBalanceNonNeg(),
////					u.isStateChange(), u.getDays(), u.getYears(), u.getTransactions(), u.getLoans(), u.getApprovedLoanRequests(), 
////					u.getRejectedLoanRequests(), u.getApprovedAccountRequests(), u.getRejectedAccountRequests(), 
////					u.getApprovedRollbackRequests(), u.getRejectedRollbackRequests());
//			ret.put(((UserAccount) d.getT()).getUser(), new UserAccount());
		}
		return 
	}

	@Override
	public boolean addUser(boolean atomic) {
		
		return false;
	}

	@Override
	public boolean updateUser(boolean atomic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(boolean atomic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Set<String>> getCreds(boolean atomic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAccount fetchUser(boolean atomic) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
