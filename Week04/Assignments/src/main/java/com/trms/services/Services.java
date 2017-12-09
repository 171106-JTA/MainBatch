package com.trms.services;
import java.util.List;

import com.trms.dao.Dao;
import com.trms.dao.DaoImpl;
import com.trms.obj.Employee;
import com.trms.obj.PrivilegesSummary;
import com.trms.obj.ReimbRequest; 



public class Services {

	public static int userExists(String loginUserId, String email) {
		Dao dao = new DaoImpl();  
		if(dao.loginIdAvailable(loginUserId))
			return 1; 
		if(dao.emailAvailable(email))
			return 2; 
		return 0; 
	}

	public static boolean insertEmployee(Employee e) {
		Dao dao = new DaoImpl();
		return dao.insertEmployee(e); 
	}

	public static boolean verifyCredentials(String username, String password) {
		Dao dao = new DaoImpl(); 
		return dao.verifyCredentials(username, password); 
	}
	
	public static void submitReimbursementRequestForm(ReimbRequest trr) {
		Dao dao = new DaoImpl();
		dao.submitReimbursementRequestForm(trr);
	}
	
	public static List<ReimbRequest> getRequests(String loginUserId) {
		Dao dao = new DaoImpl(); 
		return dao.getRequests(loginUserId);
	}
	
	public static PrivilegesSummary getPrivilegesByUserId(String loginUserId) {
		Dao dao = new DaoImpl();
		return dao.getPrivilegesByUserId(loginUserId);
	}
	
	public static int approve(String code) {
		Dao dao = new DaoImpl();
		String approvalType = "";
		if(code.substring(0, 1).equals("S")) 
			approvalType = "supervisorApproved";
		if(code.substring(0, 1).equals("D")) 
			approvalType = "deptHeadApproved";
		if(code.substring(0, 1).equals("B")) 
			approvalType = "benCoApproved";
		System.out.println("trackingnumber=" + Integer.parseInt(code.substring(1)));
		
		return dao.approveOrDeny(Integer.parseInt(code.substring(1)), approvalType, 1);
		
	}
	public static int deny(String code) {
		Dao dao = new DaoImpl();
		String approvalType = "";
		if(code.substring(0, 1).equals("S")) 
			approvalType = "supervisorApproved";
		if(code.substring(0, 1).equals("D")) 
			approvalType = "deptHeadApproved";
		if(code.substring(0, 1).equals("B")) 
			approvalType = "benCoApproved";
		System.out.println("trackingnumber=" + Integer.parseInt(code.substring(1)));
		
		return dao.approveOrDeny(Integer.parseInt(code.substring(1)), approvalType, 0);		
	}
}
