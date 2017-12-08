package com.banana.dao;

import com.banana.bean.ReimburseRequest;

public interface SystemUpdateDAO {
	public boolean updateRequestApproval(int roleId, int rrId, int decision);
	public boolean submitRequest(ReimburseRequest request);
	public double getPercentage(int eventId);
}
