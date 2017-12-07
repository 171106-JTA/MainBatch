package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.Form;
import com.revature.dao.DAOBusinessObject;

public class GetForm {
	
	public static List<Form> getAllForms() {
		return findFormData(new Form());
	}
	
	public static List<Form> getFormsById(Integer userId) {
		Form form = new Form();
		
		// prepare data
		form.setUserId(userId);
		
		return findFormData(form);
	}
	
	
	///
	//	PRIVATE METHODS 
	///

	private static List<Form> findFormData(Form form) {
		List<Form> forms = new ArrayList<>();
		
		for (BusinessObject record : DAOBusinessObject.load(form))
			forms.add((Form)record);
		
		return forms;
	}
	
}
