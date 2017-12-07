package com.revature.dao;

import com.revature.bean.ReimbursementForm;

public interface TrmsDao {
	public String login(String username, String password);
	public void insertNewReimbursementForm(ReimbursementForm newForm);
}
