package com.revature.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.CodeList;
import com.revature.businessobject.FormStatus;
import com.revature.businessobject.view.FormStatusView;
import com.revature.dao.DAOBusinessObject;

public class GetFormStatus {
	public static List<BusinessObject> getFormStatusByFormId(Integer formId) {
		FormStatus status = new FormStatus();
		status.setFormId(formId);
		
		return DAOBusinessObject.load(status);
	}
	
	public static List<FormStatusView> getFormStatusViewByFormId(Integer formId) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		FormStatus status = new FormStatus();
		List<FormStatusView> views = new ArrayList<>();
		FormStatusView view;
		
		status.setFormId(formId);
		
		for (BusinessObject record : DAOBusinessObject.load(status)) {
			status = (FormStatus)record;
			view = new FormStatusView();
			view.setProcessedBy(GetUser.getUserById(status.getProcessedBy()).getUsername());
			view.setStatus(GetCodeList.getCodeList(new CodeList(status.getStatusId(),null,null,null)).get(0).getValue());
			view.setReimbursement(status.getReimbursement());
			view.setTimestamp(format.format(status.getTimestamp()));
			view.setDescription(status.getDescription());
			// save work done 
			views.add(view);
		}
		
		return views;
	}
}
