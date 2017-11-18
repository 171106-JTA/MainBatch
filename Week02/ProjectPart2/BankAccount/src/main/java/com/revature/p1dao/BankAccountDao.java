package main.java.com.revature.p1dao;

import main.java.com.revature.p1.User;

public interface BankAccountDao {
	public void createUser(User u);
	public User selectUser(Integer index);
	public void deleteUser(Integer index);
	public void updateUser(User u);
}
