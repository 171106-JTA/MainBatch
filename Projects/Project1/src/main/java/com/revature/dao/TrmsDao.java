package com.revature.dao;

import java.util.List;

import com.revature.bean.ReimbursementForm;

public interface TrmsDao {
	public String login(String username, String password);
	public void insertNewReimbursementForm(ReimbursementForm newForm);
	public List<ReimbursementForm> getUserForms(String username);
	public List<ReimbursementForm> getFormsByApprovalStatus(int form_status, String permission);
	public void updateFormApproval(
			int reimbursementID, 
			int directSupervisorApproval,
			int departmentHeadApproval,
			int benCoApproval);
	public List<String> getAddress(int addressID);
	public String getGradingFormat(int gradingFormatID);
	public String getEventType(int eventTypeID);
}
