package com.revature.service;

import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.CodeList;
import com.revature.businessobject.Form;
import com.revature.businessobject.FormStatus;
import com.revature.dao.DAOBusinessObject;

public class GetUserBalance {
	private static CodeList limit = GetCodeList.getCodeListByCode("REIMBURSEMENT-LIMIT").get(0);
	private static CodeList pending = GetCodeList.getCodeList(new CodeList(null, "STATUS", "PENDING", null)).get(0);
	private static CodeList awarded = GetCodeList.getCodeList(new CodeList(null, "STATUS", "AWARDED", null)).get(0);
	private static CodeList approved = GetCodeList.getCodeList(new CodeList(null, "STATUS", "APPROVED", null)).get(0);
	
	public static Float getUserBalanceById(Integer userId) {
		try {
		Float balance = Float.parseFloat(limit.getValue());
		FormStatus status = new FormStatus();
		List<BusinessObject> records;
		FormStatus item;
		
		for (Form form : GetForm.getFormsById(userId)) {
			status.setFormId(form.getId());
			item = null;
			
			for (BusinessObject record : DAOBusinessObject.load(status)) {
				item = (FormStatus) record;
				
				if (item.getStatusId().equals(awarded.getId())) {
					break;
				} else {
					if (item.getStatusId().equals(approved.getId())) {
						break;
					} else {
						if (item.getStatusId().equals(pending.getId())) {
							break;
						} else {
							item = null;
						}
							
					}
				}
			}
			
			if (item != null) 
				balance -= item.getReimbursement();
		}
		
		return balance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0.0f;
	}

	
	public Float getLimit() {
		return Float.parseFloat(limit.getValue());
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	
	
	
}
