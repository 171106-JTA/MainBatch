/**
 * Class Role
 * 
 * It seems a good idea to have a Role class since we can
 * expect to see new types of customers or changes on the current ones.
 * this will be the base of the way permissions are granted.
 * 
 * @Instructor Ryan Lessley
 * @author Mahamadou
 * @Version 1.0
 * @Date 11/13/2017
 * 
 */package com.revature.objects;

import java.util.ArrayList;

public class Role {
	/*
	 * It seems a good idea to have a Role class since we can
	 * expect to see new types of customers or changes on the current ones
	 * */
	
	//Properties
	private int roleID;
	private String roleName;
	private ArrayList<Integer> permissions;

	//Constructor
	public Role(int roleID, String roleName) {
		this.roleID = roleID;
		this.roleName = roleName;
	}

	//Getters and Setters
	public int getRoleID() {
		return roleID;
	}

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public ArrayList<Integer> getPermissions() {
		return permissions;
	}
	public void setPermissions(ArrayList<Integer> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return roleID + " - " + roleName + " - " + permissions;
	}
	
	

}
