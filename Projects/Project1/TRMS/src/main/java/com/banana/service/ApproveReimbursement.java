package com.banana.service;

import com.banana.dao.SystemUpdateDAOImpl;

public class ApproveReimbursement {
	public static boolean approve(int roleId, int rrId, int decision) {
		SystemUpdateDAOImpl dao = new SystemUpdateDAOImpl();
		System.out.println("role " + roleId + " " + rrId +" "+ decision);
		return dao.updateRequestApproval(roleId, rrId, decision);
		
	}
}
