package management;

import java.io.Serializable;

public class User implements Serializable{

	protected String userName;
	protected String password;
	protected int rank;
	protected int balance;
	
	
	public User(String a, String b, int c, int d) {
		this.userName = a;
		this.password = b;
		this.rank = c;
		this.balance = d;
	}
	
	
	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}


	public void setBalance(int balance) {
		this.balance = balance;
	}


	public String getUsername() {
		return this.userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public static String displayUser(User user) {
		return "User: " + user.userName + "\npassword: " + user.password + "\nRank: " + user.rank + "\nBalance: " + user.balance;
	}


	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", rank=" + rank + ", balance=" + balance
				 + "]\n";
	}
	
	
	

}
