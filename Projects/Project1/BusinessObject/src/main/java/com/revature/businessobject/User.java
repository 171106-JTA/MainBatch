package com.revature.businessobject;

public class User implements BusinessObject {
	private Integer id;
	private Integer roleId;
	private String username;
	private String password;
	
	public User(Integer id, Integer roleId, String username, String password) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.username = username;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
