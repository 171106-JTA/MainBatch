package com.emeraldbank.dao;

import static com.emeraldbank.controller.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.emeraldbank.accounts.Acct;
import com.emeraldbank.controller.ConnectionUtil;
import com.emeraldbank.users.User; 

public class EmeraldBankDaoImpl implements EmeraldBankDao{

	@Override
	public User getUserByLoginId(String userLoginId) {
		return getUserByLoginId(userLoginId, true); 		
	}

	
	/**
	 * Gets a user specified by the given user's login ID. The redacted flag
	 * determines whether the method will query for and return the user's password
	 * @param userLoginId - the login id of the user to fetch
	 * @param redacted - true to query and return the user's password with the rest of
	 * the user's object data, false returns the default user password
	 * @return - the user object with the matching user login ID. Returns null
	 * if not found. 
	 */
	public User getUserByLoginId(String userLoginId, boolean redacted) {

		PreparedStatement ps = null;
		ResultSet rs = null; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String fields = (redacted) ? "loginid, firstname, lastname, statuscode, isAdmin" : "*"; 
			String sql = "select " + fields + " from Users where loginId = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setString(1, userLoginId);
			rs = ps.executeQuery(); 

			User u = null; 
			while(rs.next()) {
				u = new User(); 
				u.setFirstName(rs.getString("firstname")); 
				u.setLastName(rs.getString("lastname")); 
				u.setUserID(rs.getString("loginId")); 
				if(!redacted) 
					u.setPassword(rs.getString("loginpassword")); 
				u.setStatus(rs.getInt("statuscode")); 
				u.setAdmin((rs.getInt("isAdmin") == 1)); 
			}

			return u; 


		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps); 
			close(rs); 
		}

		return null;

	}

	/**
	 * Fetch the bankaccount with the matching account number given
	 * @param acctNum - The account number for the bank account to fetch
	 * @return - The bank account object fetched. Returns null of not found
	 */
	@Override
	public Acct getAcctByAcctNum(String acctNum) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from bankaccounts where accountnumber = ?"; 
			ps = conn.prepareStatement(sql);
			ps.setLong(1, Long.parseLong(acctNum));
			rs = ps.executeQuery();


			Acct a = new Acct(); 
			while(rs.next()) {
				a.setAcctNum(Long.toString(rs.getLong("accountnumber"))); 
				a.setBalance(rs.getDouble("balance"));
				a.setOwnerID(rs.getString("ownerloginid"));
				a.setStatus(rs.getInt("statuscode"));
			}
			return a; 

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps); 
			close(rs); 
		}

		return null;
	}
	
	/**
	 * Queries the database and returns a flag to indicate
	 * whether the user matching the given user login id is an administrator.
	 * @param userLoginId - the login id of the user to query.
	 * @return - 1 if the user is an administrator.
	 * 0 if the user was not found.
	 * -1 if the user is not an administrator.
	 */
	public int getUserIsAdminByLoginId(String userLoginId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int isAdmin = 0; 
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select isAdmin from Users where loginId = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userLoginId);
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getInt(1) == 1) 
					isAdmin = 1; 
				else isAdmin = -1; 
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps); 
			close(rs); 
		}
		return isAdmin;
	}
	
	/**
	 * Returns a list of the accounts owned by the user matching the given
	 * user login id. 
	 * @param userLoginId - the login id of the user to fetch
	 * @return - A list reference to the collection of account objects, each of which 
	 * has an owner login id matching the given user login id. 
	 * 
	 */
	@Override
	public List<Acct> getPortfolioByLoginId(String userLoginId) {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		List<Acct> accts = new ArrayList<>(); 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from BankAccounts where ownerloginId = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setString(1, userLoginId);
			rs = ps.executeQuery(); 
			while(rs.next()) {
				Acct a = new Acct(); 
				a.setAcctNum(Long.toString(rs.getLong("accountnumber")));
				a.setBalance(rs.getDouble("balance")); 
				a.setStatus(rs.getInt("statusCode"));
				a.setOwnerID(userLoginId);
				accts.add(a); 
			}


		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps); 
			close(rs); 
		}

		return accts; 	
	}

	/**
	 * Inserts the given user object into the database, inputing the data given
	 * by the user object, with the db filling in default data that can be changed later
	 * @param u - the user object to insert into the database
	 * @return - 1 if the user was successfully inserted into the database.  
	 * 0 otherwise. 
	 */
	@Override
	public int createNewUser(User u) {
		PreparedStatement ps = null;
		int rowsAffected = 0; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "insert into Users(loginID, loginPassword, firstname, lastname) "
					+ "values(?, ?, ?, ?)"; 
			ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUserID()); 
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getLastName());
			rowsAffected = ps.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps); 
		}

		return rowsAffected; 
	}

	/**
	 * Inserts a new bank account into the database. Only explicitly inserts the 
	 * account number and login id of the account's owner for the new bank account.
	 * The rest is set to default values to be changed later. 
	 * @param acctNum - The account number for the new bank account.
	 * @param ownerLoginId - the login id of the owner of the new bank account
	 * @return - 1 if the bank account was successfully inserted into the database.
	 * 0 otherwise. 
	 */
	@Override
	public int createNewBankAcct(String acctNum, String ownerLoginId) {
		PreparedStatement ps = null; 
		int rowsAffected = 0; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "insert into BankAccounts(accountNumber, ownerLoginId) "
					+ "values(?, ?)"; 
			ps = conn.prepareStatement(sql);
			ps.setLong(1, Long.parseLong(acctNum));
			ps.setString(2, ownerLoginId);
			rowsAffected = ps.executeUpdate(); 

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps); 
		}

		return rowsAffected; 
	}
	
	/**
	 * Removes a user from the Users table in the database.
	 * @param userLoginId - the login id of the user to remove 
	 * @return - 1 if the user was successfully removed from the database,
	 * 0 otherwise. 
	 */
	@Override
	public int deleteUser(String userLoginId) {
		PreparedStatement ps = null;
		int rowsAffected = 0; 
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "delete from Users where loginId = ?";
			ps = conn.prepareStatement(sql); 
			ps.setString(1, userLoginId);
			rowsAffected = ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps); 
		}
		
		return rowsAffected;
	}
	
	/**
	 * Removes a bank account from the BankAccounts table in the database.
	 * @param acctNum - the account number of the banka ccount to remove 
	 * @return - 1 if the account was successfully removed from the database,
	 * 0 otherwise. 
	 */
	@Override
	public int closeBankAcct(String acctNum) {
		PreparedStatement ps = null;
		int rowsAffected = 0; 
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "delete from BankAccounts where accountNumber = ?";
			ps = conn.prepareStatement(sql); 
			ps.setLong(1, Long.parseLong(acctNum));
			rowsAffected = ps.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps); 
		}
		
		return rowsAffected;
	}

	/**
	 * Fetches the balance of a bank account with the given account number fromt he database.
	 * @param acctNum - account number of the account to fetch
	 * @return balance of the account as a double. 
	 */
	@Override
	public double getAcctBalance(String acctNum) {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		double balance = 0;

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select balance from bankaccounts where accountnumber = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setLong(1, Long.parseLong(acctNum));
			rs = ps.executeQuery(); 

			while(rs.next())
				balance = rs.getDouble("balance"); 

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps); 
			close(rs);
		}

		return balance;
	}

	/**
	 * Deposits an amount of funds into the given bank account via transaction in the database.
	 * @param acctNum - Account number for the given bank account to deposit to.
	 * @param amt - amount of funds to deposit into the given account, as a double.
	 * @return The new balance of the account, after the attempt to deposit. May not change
	 * if the deposit was unsuccessful. 
	 */
	@Override
	public double depositIntoAcct(String acctNum, double amt) {
		CallableStatement cs = null;
		double balance = -1.00D; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "call deposit_into_acct(?, ?, ?)"; 
			cs = conn.prepareCall(sql);
			cs.setLong(1, Long.parseLong(acctNum)); 
			cs.setDouble(2, amt);
			cs.registerOutParameter(3, Types.FLOAT);
			cs.executeUpdate();
			balance = cs.getDouble(3);

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return balance;
	}

	/**
	 * Withdraws an amount of funds from the given bank account via transaction in the database.
	 * @param acctNum - Account number for the given bank account to withdraw from.
	 * @param amt - amount of funds to withdraw from the given account, as a double.
	 * @return The new balance of the account, after the attempt to withdraw. May not change
	 * if the deposit was unsuccessful. 
	 */
	@Override
	public double withdrawFromAcct(String acctNum, double amt) {
		CallableStatement cs = null;
		double balance = 0.00D; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "call withdraw_from_acct(?, ?, ?)"; 
			cs = conn.prepareCall(sql); 
			cs.setLong(1, Long.parseLong(acctNum));
			cs.setDouble(2, amt);
			cs.registerOutParameter(3, Types.FLOAT);
			cs.executeUpdate();

			balance = cs.getDouble(3); 


		} catch(SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return balance;
	}

	/**
	 * Returns the activity status of the given user as an integer flag.
	 * @param userLoginId - the login id of the user to fetch.
	 * @return - 1 if the user's account is active and usable for transactions.
	 * 0 if the user's account is inactive and not yet activated for use.
	 * -1 if the user's account is locked and unable to perform actions. 
	 */
	@Override
	public int getUserStatus(String userLoginId) {
		PreparedStatement ps = null; 
		ResultSet rs = null;
		int st = 0; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select statuscode from Users where loginid = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setString(1, userLoginId);
			rs = ps.executeQuery();
			while(rs.next()) {
				st = rs.getInt(1); 
				if(st == -1)
					return (st - 1); 
			}

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return st;
	}

	/**
	 * Returns the activity status of the given user as an string.
	 * @param userLoginId - the login id of the user to fetch.
	 * @return - "ACTIVE" if the user's account is active and usable for transactions.
	 * "INACTIVE" if the user's account is inactive and not yet activated for use.
	 * "LOCKED" if the user's account is locked and unable to perform actions. 
	 */
	@Override
	public String  getUserStatusString(String userLoginId) {
		PreparedStatement ps = null; 
		ResultSet rs = null;
		String st = ""; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select statusstr from Users where loginid = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setString(1, userLoginId);
			rs = ps.executeQuery();
			while(rs.next())
				st = rs.getString(1); 

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return st;
	}

	/**
	 * Returns the activity status of the given bank account as an integer flag.
	 * @param acctNum - the account number for the bank account to query
	 * @return - 1 if the bank  account is active and usable for transactions.
	 * 0 if the bank  account is inactive and not yet activated for use.
	 * -1 if the bank account is locked and unable to perform actions. 
	 */
	@Override
	public int getBankAcctStatus(String acctNum) {
		PreparedStatement ps = null; 
		ResultSet rs = null;
		int st = 0; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select statuscode from BankAccounts where accountNumber = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setLong(1, Long.parseLong(acctNum));
			rs = ps.executeQuery();
			while(rs.next()) {
				st = rs.getInt(1);
				if(st < 1)
					return (st - 1); 
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return st;
	}

	/**
	 * Returns the activity status of the given bank account as an integer flag.
	 * @param acctNum - the account number for the bank account to query
	 * @return - "ACTIVE" if the bank  account is active and usable for transactions.
	 * "INACTVE"  if the bank  account is inactive and not yet activated for use.
	 * "LOCKED"if the bank account is locked and unable to perform actions. 
	 */
	public String getBankAcctStatusString(String acctNum) {
		PreparedStatement ps = null; 
		ResultSet rs = null;
		String st = ""; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select statusstr from BankAccounts where accountNumber = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setLong(1, Long.parseLong(acctNum));
			rs = ps.executeQuery();
			while(rs.next()) {
				st = rs.getString(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return st;
	}

	/**
	 * Activates the given user's account for use.
	 * @param userLoginId - The login id of the given user to activate.
	 * @return - 1 if the user was activated successfully. 0
	 * otherwise. 
	 */
	@Override
	public int activateUser(String userLoginId) {
		PreparedStatement ps = null; 
		int rslt = 0; 
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update Users set statuscode = 1 where loginid = ?";
			ps = conn.prepareStatement(sql); 
			ps.setString(1, userLoginId);
			rslt = ps.executeUpdate(); 

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {

		}

		return rslt; 

	}
	/**
	 * Activates the given bank account for use.
	 * @param acctNum - The account number of the given account to activate.
	 * @return - 1 if the account was activated successfully. 0 
	 * otherwise. 
	 */
	@Override
	public int activateBankAcct(String acctNum) {
		PreparedStatement ps = null; 
		int rslt = 0; 
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update BankAccounts set statuscode = 1 where accountnumber = ?";
			ps = conn.prepareStatement(sql); 
			ps.setLong(1, Long.parseLong(acctNum));
			rslt = ps.executeUpdate(); 
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return rslt; 
	}

	/**
	 * Locks the given user's account from  use and locks all their bank accounts
	 * @param userLoginId - The login id of the given user to lock.
	 * @return - 1 if the user was locked successfully. 0
	 * otherwise. 
	 */
	@Override
	public int lockUser(String userLoginId) {
		PreparedStatement ps = null; 
		int rslt = 0; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update Users set statuscode = -1 where loginid = ?";
			ps = conn.prepareStatement(sql); 
			ps.setString(1, userLoginId);
			rslt = ps.executeUpdate(); 

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return rslt; 
	}

	/**
	 * Locks the given bank account and bars it from use
	 * @param acctNum - The account number of the given account to lock.
	 * @return - 1 if the account was locked successfully. 0 
	 * otherwise. 
	 */
	@Override
	public int lockAcct(String acctNum) {
		PreparedStatement ps = null; 
		int rslt = 0; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update BankAccounts set statuscode = -1 where accountnumber = ?";
			ps = conn.prepareStatement(sql); 
			ps.setLong(1, Long.parseLong(acctNum));
			rslt = ps.executeUpdate(); 
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return rslt; 
	}

	/**
	 * Promotes the given user's role to administrator via integer flag in its database record.
	 * @param acctNum - The account number of the given account to promote
	 * @return - 1 if the account was promoted successfully. 0 
	 * otherwise. 
	 */
	@Override
	public int promoteToAdmin(String userLoginID) {
		PreparedStatement ps = null; 
		int rslt = 0; 

		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update Users set isAdmin = 1 where loginid = ?";
			ps = conn.prepareStatement(sql); 
			ps.setString(1, userLoginID);
			rslt = ps.executeUpdate(); 
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return rslt; 
	}



}
