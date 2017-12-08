package com.banana.dao;

import java.util.List;

import com.banana.bean.ReimburseRequest;

public interface ReimbursementDAO {
	public ReimburseRequest getSingleRequest(int rrId);
	public List<ReimburseRequest> getEmployeeRequests(int empId);
	public List<ReimburseRequest> getAllRequests();
}
