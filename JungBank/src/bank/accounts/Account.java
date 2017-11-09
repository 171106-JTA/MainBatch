package bank.accounts;

import java.io.Serializable;

public class Account implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7208156833103705447L;
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int pinNumber;
	private int accountNumber;
	private int balance;
	static int currentAccountNumber = 0;
	
	public Account() {
		this.username = null;
		this.password = null;
		this.firstName = null;
		this.lastName = null;
		this.balance = 0;
		this.pinNumber = 0;
		this.setAccountNumber(currentAccountNumber);
		currentAccountNumber++;
	}
	
	public Account(String username, String password, String firstName, String lastName, int pinNumber) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pinNumber = pinNumber;
		this.balance = 0;
		this.setAccountNumber(currentAccountNumber);
		currentAccountNumber++;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", pinNumber=" + pinNumber + ", accountNumber=" + accountNumber + ", balance=" + balance
				+ "]";
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
}
