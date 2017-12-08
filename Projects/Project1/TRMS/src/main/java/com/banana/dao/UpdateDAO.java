package com.banana.dao;

import com.banana.bean.ReimburseRequest;

public interface UpdateDAO {
	public boolean updateRequestApproval(int roleId, int rrId, int decision);
	public boolean submitRequest(ReimburseRequest request);
	public double getPercentage(int eventId);
	public boolean setNewAmount(int empId, double newAmount);
	public void insertInfoRequest(int rrId, int empId);
	public void UpdateInfoRequest();
}
