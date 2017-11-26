package com.revature.dao.factory;

import java.sql.ResultSet;
import java.util.List;

import com.revature.businessobject.BusinessObject;

public final class BusinessObjectFactory {
	private static BusinessObjectFactory factory = new BusinessObjectFactory();
	
	private BusinessObjectFactory() {
		// do nothing
	}
	
	public static BusinessObjectFactory getFactory() {
		return factory;
	}
	
	public List<BusinessObject> getBusinessObject(ResultSet res, Class<? extends BusinessObject> type) {
		return null;
	}
	
	private List<String> getParams(BusinessObject businessObject) {
		return null;
	}
}
