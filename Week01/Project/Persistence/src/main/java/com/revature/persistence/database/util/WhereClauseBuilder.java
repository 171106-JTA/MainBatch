package com.revature.persistence.database.util;

import java.sql.Statement;

import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;

public class WhereClauseBuilder {
	private static WhereClauseBuilder builder;
	
	private WhereClauseBuilder() {
		// do nothing
	}
	
	
	public static WhereClauseBuilder getBuilder() {
		return builder == null ? builder = new WhereClauseBuilder() : builder;
	}

	
	public boolean build(String name, Statement statement, FieldParams params) {
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
	
	
	private boolean buildUserWhereClause(Statement statement, FieldParams params) {
		return false;
	}
	
	private boolean buildUserInfoWhereClause(Statement statement, FieldParams params) {
		return false;
	}
	
	private boolean buildAccountWhereClause(Statement statement, FieldParams params) {
		return false;
	}
	
	private boolean buildCheckingWhereClause(Statement statement, FieldParams params) {
		return false;
	}
	
	private boolean buildCreditWhereClause(Statement statement, FieldParams params) {
		return false;
	}
	
	private boolean buildSavingsWhereClause(Statement statement, FieldParams params) {
		return false;
	}
	
	private boolean buildCodeListWhereClause(Statement statement, FieldParams params) {
		return false;
	}
	
	private booleans buildReceiptWhereClause(Statement statment, FieldParams params) {
		return false;
	}
	
	
	
}
