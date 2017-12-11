package com.revature.service;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.businessobject.CodeList;
import com.revature.businessobject.Form;
import com.revature.businessobject.view.FormView;
import com.revature.dao.DAOBusinessObject;
import com.revature.service.util.ServiceUtil;

public class CreateForm {
	private static List<CodeList> departments = GetCodeList.getCodeListByCode("DEPARTMENT");
	private static List<CodeList> grading = GetCodeList.getCodeListByCode("GRADING-TYPE");
	private static List<CodeList> rates = GetCodeList.getCodeListByCode("REIMBURSEMENT-RATE");
	
	
	public static FormView createForm(HttpServletRequest request) throws ServletException, IOException {
		FormView view = null;
		
		if (validateFormParams(request)) {
			Form form = buildForm(request);
			DAOBusinessObject.insert(form);
			view = GetForm.getFormViews(form).get(0);
		}
		
		return view;
	}
	
	
	///
	//	PRIVATE METHODS
	///
	
	private static boolean validateFormParams(HttpServletRequest request) {
		boolean result = !ServiceUtil.isNullOrEmpty(request.getParameter("firstName"));
		
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("firstName"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("lastName"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("phoneNumber"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("address"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("email"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("eventDateTimeStart"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("eventDateTimeEnd"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("eventCost"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("timeOffBegin"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("timeOffEnd"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("state"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("city"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("reimbursementType"));
		result = result && !ServiceUtil.isNullOrEmpty(request.getParameter("gradingType"));
		
		return result;
	}
	
	private static boolean validateAttachmentParams(HttpServletRequest request) {
		return false;
	}
	
	private static Form buildForm(HttpServletRequest request) {
		Integer departmentId = GetCodeList.findCodeListByValue(departments, request.getParameter("department")).getId();
		Integer stateCityId = GetCodeList.getCodeList(new CodeList(null,null, request.getParameter("state"),request.getParameter("city"))).get(0).getId();
		Integer gradingId = GetCodeList.findCodeListByValue(grading, request.getParameter("gradingType")).getId();
		Integer ratesId = GetCodeList.findCodeListByValue(rates, request.getParameter("reimbursementType")).getId();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		HttpSession session = request.getSession(false);
		Form form = null;

		
		try {
			
			String time = request.getParameter("eventDateTimeStart");
			long t = format.parse(time).getTime();
			Date start = new Date(t);
			String s = format.format(start);
			
			form = new Form(null, (Integer)session.getAttribute("id"), departmentId, stateCityId,gradingId,ratesId,
							request.getParameter("firstName"), request.getParameter("lastName"), request.getParameter("phoneNumber"),
							request.getParameter("address"), request.getParameter("email"),
							new Date(format.parse(request.getParameter("eventDateTimeStart")).getTime()), 
							new Date(format.parse(request.getParameter("eventDateTimeEnd")).getTime()),
							Float.parseFloat(request.getParameter("eventCost")), request.getParameter("description"),
							new Date(format.parse(request.getParameter("timeOffBegin")).getTime()),
							new Date(format.parse(request.getParameter("timeOffEnd")).getTime()));
		} catch (NumberFormatException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return form;
	}
}
