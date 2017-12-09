package com.trms.dao;

import java.util.ArrayList;
import java.util.List;

import com.trms.obj.Employee;
import com.trms.obj.ReimbRequest;

public interface Dao {

	public boolean loginIdAvailable(String loginUserId);
	public boolean emailAvailable(String email); 
	public boolean insertEmployee(Employee e); 
	public boolean verifyCredentials(String loginUserId, String loginPassword); 
	public void submitReimbursementRequestForm(ReimbRequest trr);
	public List<ReimbRequest> getRequests(String loginUserId);
	
	
	
	
	
}
