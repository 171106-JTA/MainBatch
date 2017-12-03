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
		setCode(code);
		setValue(value);
		setDescription(description);
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
		this.code = BusinessObject.validateString(code);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = BusinessObject.validateString(value);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = BusinessObject.validateString(description);
	}
}
