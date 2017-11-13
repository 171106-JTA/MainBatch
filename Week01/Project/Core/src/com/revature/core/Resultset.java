package com.revature.core;

import java.util.Collection;
import java.util.LinkedList;

import com.revature.businessobject.BusinessObject;
import com.revature.core.exception.RequestException;

/**
 * Stores result of request
 * @author Antony Lulciuc
 */
public class Resultset extends LinkedList<BusinessObject> {
	private int recordsModified;
	private RequestException exception;
	
	/**
	 * No-args constructor
	 */
	public Resultset() {
		super();
	}

	/**
	 * Used to indicate number of records affected by request 
	 * @param recordsModified - number of records modified 
	 */
	public Resultset(int recordsModified) {
		super();
		this.recordsModified = recordsModified;
	}
	
	/**
	 * Used if exception was thrown during execution
	 * @param exception - what happened 
	 */
	public Resultset(RequestException exception) {
		super();
		this.exception = exception;
	}

	public Resultset(Collection<? extends BusinessObject> c) {
		super(c);
		this.recordsModified = c.size();
	}

	/**
	 * @return total number of records modified by request 
	 */
	public int getRecordsModified() {
		return recordsModified;
	}	
	
	/**
	 * @return non-null value if request failed to execute
	 */
	public RequestException getException() {
		return exception;
	}
}
