package com.banana.service;

import com.banana.dao.UpdateDAOImpl;

public class ApproveReimbursement {
	/**
	 * Service for changing the approval status of a reimbursement request
	 * Utilized the UpdateDAO
	 * 
	 * @param roleId the role of the admin
	 * @param rrId the id of the request
	 * @param decision the verdict on the request
	 * @param empId the employee id of the admin
	 * 
	 * @return boolean to determine success
	 */
	public static boolean approve(int roleId, int rrId, int decision, int empId) {
		UpdateDAOImpl udao = new UpdateDAOImpl();
		
		if(decision == -1) {
			UpdateEmployeeAmount.denyAmount(rrId);
		}
		else if(decision == 1 && roleId == 3){
			
			UpdateEmployeeAmount.updateAmountByBenCo(rrId);
		}
		
		return udao.updateRequestApproval(roleId, rrId, decision, empId);
		
	}
}
