package com.revature.persistence.database.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;

public class WhereClauseBuilder {
	private static WhereClauseBuilder builder;
	private static Logger logger = Logger.getLogger(WhereClauseBuilder.class);
	
	private WhereClauseBuilder() {
		// do nothing
	}
	
	
	public static WhereClauseBuilder getBuilder() {
		return builder == null ? builder = new WhereClauseBuilder() : builder;
	}

	
	public boolean build(String name, PreparedStatement statement, FieldParams params) {
		switch (name.toLowerCase()) {
			case BusinessClass.ADMIN:
			case BusinessClass.CUSTOMER:
			case BusinessClass.USER:
				return buildUserWhereClause(statement, params);
			case BusinessClass.USERINFO:
				return buildUserInfoWhereClause(statement, params);
			case BusinessClass.CREDIT:
				return buildCreditWhereClause(statement, params);
			case BusinessClass.CHECKING:
				return buildCheckingWhereClause(statement, params);
			case BusinessClass.ACCOUNT:
				return buildAccountWhereClause(statement, params);
			default:
				return false;
		}
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	
	private boolean buildUserWhereClause(PreparedStatement statement, FieldParams params) {
		int pos = 1;
		
		for (String key : params.keySet()) {
			try {
				// Attempt to assemble where clause
				switch (key) {
					case User.ID:
						statement.setLong(pos, Long.parseLong(params.get(key)));
						break;
					case User.USERNAME:
					case User.PASSWORD:
						statement.setString(pos, params.get(key));
						statement.setString(pos, params.get(key));
						break;
				}
			} catch (NumberFormatException | SQLException e) {
				logger.warn("failed to build user where clause, message=" + e.getMessage());
				e.printStackTrace();
				return false;
			}
			
			// increment counter
			++pos;
		}
		
		return true;
	}
	
	private boolean buildUserInfoWhereClause(PreparedStatement statement, FieldParams params) {
		int pos = 1;
		
		for (String key : params.keySet()) {
			try {
				// Attempt to assemble where clause
				switch (key) {
					case UserInfo.SSN:
					case UserInfo.STATECITYID:
					case UserInfo.STATUSID:
					case UserInfo.ROLEID:
						statement.setLong(pos, Long.parseLong(params.get(key)));
						break;
					case UserInfo.EMAIL:
					case UserInfo.PHONENUMBER:
					case UserInfo.FIRSTNAME:
					case UserInfo.LASTNAME:
					case UserInfo.ADDRESS1:
					case UserInfo.ADDRESS2:
					case UserInfo.POSTALCODE:
						statement.setString(pos,  params.get(key));
						break;
				}
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
				logger.warn("failed to build userinfo where clause, message=" + e.getMessage());
				return false;
			}
			
			// increment counter
			++pos;
		}
		
		return true;
	}
	
	private boolean buildAccountWhereClause(PreparedStatement statement, FieldParams params) {
		int pos = 1;
		
		for (String key : params.keySet()) {
			try {
				// Attempt to assemble where clause
				switch (key) {
					case Account.NUMBER:
					case Account.CREATED:
						statement.setString(pos, params.get(key));
						break;
					case Account.TYPEID:
					case Account.STATUSID:
						statement.setLong(pos, Long.parseLong(params.get(key)));
						break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("failed to build userinfo where clause, message=" + e.getMessage());
				return false;
			}
			
			// increment counter
			++pos;
		}
		
		return true;
	}
	
	private boolean buildCheckingWhereClause(PreparedStatement statement, FieldParams params) {
		int pos = 1;
		
		for (String key : params.keySet()) {
			try {
				// Attempt to assemble where clause
				switch (key) {
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("failed to build userinfo where clause, message=" + e.getMessage());
				return false;
			}
			
			// increment counter
			++pos;
		}
		
		return true;
	}
	
	private boolean buildCreditWhereClause(PreparedStatement statement, FieldParams params) {
		int pos = 1;
		
		for (String key : params.keySet()) {
			try {
				// Attempt to assemble where clause
				switch (key) {
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("failed to build userinfo where clause, message=" + e.getMessage());
				return false;
			}
			
			// increment counter
			++pos;
		}
		
		return true;
	}
	
	private boolean buildSavingsWhereClause(PreparedStatement statement, FieldParams params) {
		int pos = 1;
		
		for (String key : params.keySet()) {
			try {
				// Attempt to assemble where clause
				switch (key) {
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("failed to build userinfo where clause, message=" + e.getMessage());
				return false;
			}
			
			// increment counter
			++pos;
		}
		
		return true;
	}
	
	private boolean buildCodeListWhereClause(PreparedStatement statement, FieldParams params) {
		int pos = 1;
		
		for (String key : params.keySet()) {
			try {
				// Attempt to assemble where clause
				switch (key) {
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("failed to build userinfo where clause, message=" + e.getMessage());
				return false;
			}
			
			// increment counter
			++pos;
		}
		
		return true;
	}
	
	private boolean buildReceiptWhereClause(PreparedStatement statment, FieldParams params) {
		int pos = 1;
		
		for (String key : params.keySet()) {
			try {
				// Attempt to assemble where clause
				switch (key) {
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("failed to build userinfo where clause, message=" + e.getMessage());
				return false;
			}
			
			// increment counter
			++pos;
		}
		
		return true;;
	}
	
	
	
}
