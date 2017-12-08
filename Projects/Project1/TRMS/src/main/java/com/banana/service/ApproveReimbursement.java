package com.banana.service;

import com.banana.dao.SystemUpdateDAOImpl;

public class ApproveReimbursement {
	public static boolean approve(int roleId, int rrId, int decision) {
		SystemUpdateDAOImpl udao = new SystemUpdateDAOImpl();
		System.out.println("role " + roleId + " " + rrId +" "+ decision);
		return udao.updateRequestApproval(roleId, rrId, decision);
		
	}
}
