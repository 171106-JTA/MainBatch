package com.revature.core.factory.builder;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.CodeList;
import com.revature.core.FieldParams;

public class CodeListBuilder implements BusinessObjectBuilder {

	/**
	 * Creates User instance 
	 * @param args used to instantiate instance 
	 * @return new User on success else null
	 */
	@Override
	public BusinessObject getBusinessObject(FieldParams args) {
		BusinessObject object = null;
		
		if (!isValid(args))
			return null;

		object = new CodeList(Long.parseLong(args.get(CodeList.ID)), args.get(CodeList.CODE),
							  args.get(CodeList.VALUE), args.get(CodeList.DESCRIPTION));
		
		return object;
	}

	/**
	 * Used to ensure all arguments needed to instantiate object
	 * @param args what to check
	 * @return true is arguments for class to create are valid else false
	 */
	@Override
	public boolean isValid(FieldParams args) {
		boolean result = args != null;
		
		result = result && args.get(CodeList.ID) != null;
		result = result && args.get(CodeList.CODE) != null;
		result = result && args.get(CodeList.VALUE) != null;
		result = result && args.get(CodeList.DESCRIPTION) != null;
		
		return result;
	}

}
