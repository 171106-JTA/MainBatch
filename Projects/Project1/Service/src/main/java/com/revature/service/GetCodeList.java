package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.CodeList;
import com.revature.dao.DAOBusinessObject;

public class GetCodeList {
	public static List<CodeList> getCodeList(CodeList codelist) {
		return findCodeList(codelist);
	}
	
	public static List<CodeList> getCodeListByCode(String code) {
		return findCodeList(new CodeList(null, code, null, null));
	}
	
	public static List<CodeList> getCodeListByValue(String value) {
		return findCodeList(new CodeList(null, null, value, null));
	}
	
	public static List<CodeList> getCodeListByDescription(String description) {
		return findCodeList(new CodeList(null, null, null, description));
	}
	
	public static CodeList findCodeListNyId(List<CodeList> list, Integer id) {
		List<CodeList> result = list.stream().filter(codelist -> codelist.getId().equals(id)).collect(Collectors.toList());
		return result.size() > 0 ? result.get(0) : null;
	}
	
	
	///
	//	PRIVATE METHODS
	///
	
	private static List<CodeList> findCodeList(CodeList codelist) {
		List<BusinessObject> records = DAOBusinessObject.load(codelist);
		List<CodeList> list = new ArrayList<>(records.size());
		
		// Create list
		for (BusinessObject object : records)
			list.add((CodeList)object);
		
		return list;
	}
	
	
	
}
