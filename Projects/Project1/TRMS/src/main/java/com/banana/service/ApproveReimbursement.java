package com.banana.service;

import com.banana.dao.UpdateDAOImpl;

public class ApproveReimbursement {
	public static boolean approve(int roleId, int rrId, int decision) {
		UpdateDAOImpl udao = new UpdateDAOImpl();
		System.out.println("role " + roleId + " " + rrId +" "+ decision);
		return udao.updateRequestApproval(roleId, rrId, decision);
		
	}
}
