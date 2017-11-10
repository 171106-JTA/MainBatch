package com.revature.core.factory.builder;

import com.revature.businessobject.BusinessObject;
import com.revature.core.FieldParams;

/**
 * @author Antony Lulciuc
 */
public interface BusinessObjectBuilder {
	/**
	 * Creates BusinessObject instance 
	 * @param args used to instantiate instance 
	 * @return new BusinessObject on success else null
	 */
	public BusinessObject getBusinessObject(FieldParams args);
	
	/**
	 * Used to ensure all arguments needed to instantiate object
	 * @param args what to check
	 * @return true is arguments for class to create are valid else false
	 */
	public boolean isValid(FieldParams args);
}
