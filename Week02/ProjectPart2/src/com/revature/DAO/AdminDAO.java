package com.revature.DAO;

import com.revature.bank.Admin;
import com.revature.bank.Admins;
import com.revature.bank.User;
import com.revature.bank.Users;

public interface AdminDAO {
	public Admins getAdmins();
	// for checking if a User is an Admin
	public boolean isAdmin(User user);
	// CRUD operation functions
	public Admin getAdminById(Integer id);
	public void promoteToAdmin(User user);	// Admins can't be simply created, only promoted to.
	public int demote(Admin admin);	// The function to use, in most cases, for removing Admins
	public int delete(Admin admin);	// For the most severe of cases
	// for getting the base users; // that is the users who are not admins
}
