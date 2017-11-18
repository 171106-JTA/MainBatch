package com.revature.businessobject.info;

import com.revature.businessobject.BusinessObject;

public class CodeList extends BusinessObject {
	private long id;
	private String code;
	private String value;
	private String description;
	
	public CodeList() {
	}
	
	public CodeList(long id, String code, String value, String description) {
		super();
		this.id = id;
		this.code = code;
		this.value = value;
		this.description = description;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
}
