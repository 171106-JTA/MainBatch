package com.trms.dao;

import java.util.ArrayList;

import com.trms.obj.Employee;
import com.trms.obj.ReimbRequest;

public interface Dao {

	public boolean loginIdAvailable(String loginUserId);
	public boolean emailAvailable(String email); 
	public boolean insertEmployee(Employee e); 
	
	
	
	
	
}
