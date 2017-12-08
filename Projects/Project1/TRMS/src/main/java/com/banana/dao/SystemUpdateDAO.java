package com.banana.dao;

public interface SystemUpdateDAO {
	public boolean updateRequestApproval(int roleId, int rrId, int decision);
}
