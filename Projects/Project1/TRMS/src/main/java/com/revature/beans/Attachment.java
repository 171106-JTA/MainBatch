package com.revature.beans;

import java.sql.Blob;

public class Attachment {
	String name;
	Blob data;
	String descr;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Attachment() {
		super();
	}
	public Blob getData() {
		return data;
	}
	public void setData(Blob data) {
		this.data = data;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Attachment(String name, Blob data, String descr) {
		super();
		this.name = name;
		this.data = data;
		this.descr = descr;
	}
	
}
