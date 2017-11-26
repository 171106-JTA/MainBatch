/**
 * 
 */
package com.revature.businessobject;

/**
 * @author Antony Lulciuc
 *
 */
public class CodeList implements BusinessObject {
	private Integer id;
	private String code;
	private String value;
	private String description;
	
	public CodeList() {
		// do nothing
	}
	
	public CodeList(Integer id, String code, String value, String description) {
		super();
		this.id = id;
		this.code = code;
		this.value = value;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
