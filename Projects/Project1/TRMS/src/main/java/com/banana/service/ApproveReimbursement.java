package com.banana.service;

import com.banana.dao.UpdateDAOImpl;

public class ApproveReimbursement {
	public static boolean approve(int roleId, int rrId, int decision, int empId) {
		UpdateDAOImpl udao = new UpdateDAOImpl();
		
		if(decision == -1) {
			UpdateEmployeeAmount.denyAmount(rrId);
		}
		else if(decision == 1 && roleId == 3){
			System.out.println("Benco");
			UpdateEmployeeAmount.updateAmountByBenCo(rrId);
		}
		
		return udao.updateRequestApproval(roleId, rrId, decision, empId);
		
	}
}
