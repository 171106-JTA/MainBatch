package com.revature.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.businessobject.CodeList;
import com.revature.businessobject.FormStatus;
import com.revature.businessobject.view.FormView;
import com.revature.dao.DAOBusinessObject;

public class UpdateFormStatus {
	public static FormView updateFormStatus(HttpServletRequest request) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		HttpSession session = request.getSession(false);
		Integer statusId = GetCodeList.getCodeList(new CodeList(null,"STATUS", request.getParameter("status"), null)).get(0).getId();
		Integer formId = Integer.parseInt(request.getParameter("formId"));
		FormView view = GetForm.getFormViewById(formId);
		Float balance = GetUserBalance.getUserBalanceById(view.getUserId());
		Float amount =  Float.parseFloat(request.getParameter("reimbursement"));;
		FormStatus status = null;
		
		if ((balance - amount) < 0.0f)
			throw new Exception("Cannot process, balance exceeded!");

		status = new FormStatus(null, formId, (Integer)session.getAttribute("id"),
					statusId, Float.parseFloat(request.getParameter("reimbursement")), request.getParameter("description"), 
					new Date(format.parse(request.getParameter("timestamp")).getTime()));
			
		DAOBusinessObject.insert(status);

		return GetForm.getFormViewById(request);
	}
}
