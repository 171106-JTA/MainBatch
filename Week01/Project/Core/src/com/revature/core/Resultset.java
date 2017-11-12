package com.revature.core;

import java.util.Collection;
import java.util.LinkedList;

import com.revature.businessobject.BusinessObject;
import com.revature.core.exception.RequestException;

public class Resultset extends LinkedList<BusinessObject> {
	private int recordsModified;
	private RequestException exception;
	
	public Resultset() {
		super();
	}

	public Resultset(RequestException exception) {
		super();
		this.exception = exception;
	}

	
	public Resultset(Collection<? extends BusinessObject> c) {
		super(c);
		this.recordsModified = c.size();
	}

	public Resultset(int recordsModified) {
		super();
		this.recordsModified = recordsModified;
	}

	public int getRecordsModified() {
		return recordsModified;
	}	
	
	public RequestException getException() {
		return exception;
	}
}
