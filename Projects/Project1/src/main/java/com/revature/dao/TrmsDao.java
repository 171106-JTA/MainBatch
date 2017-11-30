package com.revature.dao;

import java.util.ArrayList;

import com.revature.bean.User;

public interface TrmsDao {
	public ArrayList<User> login(String username, String password);
}
