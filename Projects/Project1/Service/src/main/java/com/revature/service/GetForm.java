package com.revature.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.CodeList;
import com.revature.businessobject.Form;
import com.revature.businessobject.FormAttachment;
import com.revature.businessobject.FormStatus;
import com.revature.businessobject.view.FormView;
import com.revature.dao.DAOBusinessObject;

public class GetForm {
	private static List<CodeList> grading = GetCodeList.getCodeListByCode("GRADING-TYPE");
	private static List<CodeList> rembursement = GetCodeList.getCodeListByCode("REIMBURSEMENT-RATE");
	private static List<CodeList> departments = GetCodeList.getCodeListByCode("DEPARTMENT");
	private static List<CodeList> stateCity = GetCodeList.getCodeListByCode("CITY-CODE-GROUP");
	private static List<CodeList> state = GetCodeList.getCodeListByCode("US-STATE");
	private static List<CodeList> city = GetCodeList.getCodeListByCode("CITY-CODE");
	
	public static List<FormView> getAllForms() {
		return getFormViews(new Form());
	}
	
	public static List<Form> getFormsById(Integer userId) {
		Form form = new Form();
		
		// prepare data
		form.setUserId(userId);
		
		return findFormData(form);
	}
	
	public static List<FormView> getFormViewsByUserId(Integer userId) {
		Form form = new Form();
		
		// prepare data
		form.setUserId(userId);
		
		return getFormViews(form);
	}
	
	public static FormView getFormViewById(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Form form = new Form();
		List<FormView> views;
		FormView view = null;
		
		form.setId(Integer.parseInt(request.getParameter("formId")));
		
		if ((views = getFormViews(form)).size() > 0) {
			view = views.get(0);
			
			// Employee should only get their forms 
			if (session.getAttribute("role").equals("EMPLOYEE")) {
				if (!view.getUserId().equals(session.getAttribute("id")))
					view = null;
			}
		}
		
		return view;
	}

	public static FormView getFormViewById(Integer formId) {
		Form form = new Form();
		List<FormView> views;
		
		form.setId(formId);

		return (views = getFormViews(form)).size() > 0 ? views.get(0) : null;
	}
	
	public static List<FormView> getFormViews(Form form) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<FormView> views = new ArrayList<>();
		List<String> gradeAttachments;
		List<String> otherAttachments;
		List<Integer> gradeAttachmentIds;
		List<Integer> otherAttachmentIds;
		FormView view;
		CodeList item;
		
		for (Form record : findFormData(form)) {
			view = new FormView();
			view.setFormId(record.getId());
			view.setUserId(record.getUserId());
			view.setDepartment(GetCodeList.findCodeListById(departments, record.getDepartmentId()).getValue());
			view.setFirstName(record.getFirstName());
			view.setLastName(record.getLastName());
			view.setEmail(record.getEmail());
			view.setPhoneNumber(record.getPhoneNumber());
			view.setDescription(record.getDescription());
			view.setAddress(record.getAddress());
			view.setEventCost(record.getEventCost());
			view.setEventDateTimeStart(format.format(record.getEventDateTimeStart()));
			view.setEventDateTimeEnd(format.format(record.getEventDateTimeEnd()));
			view.setTimeOffBegin(format.format(record.getTimeOffBegin()));
			view.setTimeOffEnd(format.format(record.getTimeOffEnd()));
			view.setStatus(getFormStatus(record.getId()));
			view.setGradingType(GetCodeList.findCodeListById(grading, record.getGradingTypeId()).getValue());
			view.setReimbursementRate(GetCodeList.findCodeListById(rembursement, record.getReimbursementRateId()).getValue());
			
			// Set State and city values 
			item = GetCodeList.findCodeListById(stateCity, record.getEventStateCityId());
			view.setState(GetCodeList.findCodeListByValue(state, item.getValue()).getValue());
			view.setCity(GetCodeList.findCodeListByValue(city, item.getDescription()).getValue());
			
			// add attachment data
			gradeAttachments = new ArrayList<>();
			otherAttachments = new ArrayList<>();
			gradeAttachmentIds = new ArrayList<>();
			otherAttachmentIds = new ArrayList<>();
			
			view.setGradeAttachments(gradeAttachments);
			view.setOtherAttachments(otherAttachments);
			view.setGradeAttachmentIds(gradeAttachmentIds);
			view.setOtherAttachmentIds(otherAttachmentIds);
			
			for (FormAttachment attachment : getFormAttachmentsById(record.getId())) {
				if (attachment.getAttachmentType().equals("GRADE-ATTACHMENT")) {
					gradeAttachments.add(attachment.getAttachmentName());
					gradeAttachmentIds.add(attachment.getId());
				} else { 
					otherAttachments.add(attachment.getAttachmentName());
					otherAttachmentIds.add(attachment.getId());
				}
			}
			
			// Save work done
			views.add(view);
		}
		
		
		return views;
	}
	
	public static List<FormAttachment> getFormAttachmentsById(Integer formId) {
		List<FormAttachment> attachments = new ArrayList<>();
		FormAttachment form = new FormAttachment();
		
		form.setFormId(formId);
		
		for (BusinessObject record :  DAOBusinessObject.load(form))
			attachments.add((FormAttachment)record);
		
		return attachments;
	}
	
	public static String getFormStatus(Integer formId) {
		List<BusinessObject> records;
		FormStatus formStatus = null;
		FormStatus largest = null;
		String status = "NEW";
		
		formStatus = new FormStatus();
		formStatus.setFormId(formId);
		
		for (BusinessObject record : DAOBusinessObject.load(formStatus)) {
			if (largest == null)
				largest = (FormStatus)record;
			else {
				formStatus = (FormStatus)record;
				
				if (formStatus.getTimestamp().compareTo(largest.getTimestamp()) > 0) {
					largest = formStatus;
				}
			}
		}
		
		if (largest != null) {
			status = GetCodeList.getCodeList(new CodeList(largest.getStatusId(), null, null, null)).get(0).getValue();
		}
		
		return status;
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
