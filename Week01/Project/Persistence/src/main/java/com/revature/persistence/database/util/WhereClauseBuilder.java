package com.revature.persistence.database.util;

import java.sql.Statement;

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
		
		
		return false;
	}
	
}
