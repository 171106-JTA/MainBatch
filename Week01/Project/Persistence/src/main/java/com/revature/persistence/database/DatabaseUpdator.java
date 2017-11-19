package com.revature.persistence.database;

import com.revature.businessobject.BusinessObject;
import com.revature.core.FieldParams;

public class DatabaseUpdator extends DatabaseDeletor {

	public int update(BusinessObject businessObject) {
		String name = businessObject.getClass().getSimpleName();
		return update(name, fieldParamsFactory.getFieldParams(businessObject), fieldParamsFactory.getFieldParams(businessObject));
	}

	public int update(String name, FieldParams cnds, FieldParams values) {
		// TODO Auto-generated method stub
		return 0;
	}

}
