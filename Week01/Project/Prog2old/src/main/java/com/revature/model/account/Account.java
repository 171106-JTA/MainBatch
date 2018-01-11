package com.revature.model.account;

import java.io.Serializable;
import java.util.Observable;

import com.revature.model.Properties;

public abstract class Account extends Observable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2615945033686770721L;
	private String user, pass;
	protected char permissions;
	private static int id = 0;
	private int uid;
	private int age;
	@SuppressWarnings("unused")
	private boolean ageFlag = false;

	// for instance comparisons in reflection
	public Account() {
		super();
	}

	public Account(String user, String pass) {
		this.user = user;
		this.pass = pass;
		setPermissions();
		synchronized (Account.class) {
			uid = id;
			id++;
		}
	}

	public Account(String user, String pass, int age, int uid) {
		this.user = user;
		this.pass = pass;
		this.age = age;
		this.uid = uid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
		if(this instanceof BasicUserAccount && age >= Properties.SENIOR_MIN_AGE) {
			ageFlag = true;
			setChanged();
			notifyObservers();
		}
	}


	public int getUid() {
		return uid;
	}

	public char getPermissions() {
		return permissions;
	}
	
	@Override
	public boolean equals(Object o) {
		Account a1 = (Account) o;
		Account a2 = this;
		return a1.getUser().equals(a2.getUser()) && a1.getPass().equals(a2.getPass()) &&
				a1.getAge() == a2.getAge() && a1.getUid() == a2.getUid();
	}

	protected abstract void setPermissions();

	public abstract String toString();

}