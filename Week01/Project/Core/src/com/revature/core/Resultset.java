package com.revature.core;

import java.util.Collection;
import java.util.LinkedList;

import com.revature.businessobject.BusinessObject;

public class Resultset extends LinkedList<BusinessObject> {
	private int recordsModified;

	
	public Resultset() {
		super();
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
}
