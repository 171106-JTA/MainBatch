package com.revature.core.factory;

import java.sql.ResultSet;

import com.revature.businessobject.BusinessObject;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.factory.builder.*;

/**
 * Used to generate BusinessObjects from FieldParams
 * @author Antony Lulciuc
 * @see FieldParams
 */
public class BusinessObjectFactory {
	private static BusinessObjectFactory factory;
	
	// Factory builders 
	private static BusinessObjectBuilder userBuilder;
	private static BusinessObjectBuilder userInfoBuilder;
	private static BusinessObjectBuilder accountBuilder;
	private static BusinessObjectBuilder codeListBuilder;
	private static BusinessObjectBuilder invoiceBuilder;
	private static BusinessObjectBuilder receiptBuilder;
	private static BusinessObjectBuilder jointAccountBuilder;
	
	/**
	 * Initializes builders
	 */
	private BusinessObjectFactory() {
		userBuilder = new UserBuilder();
		userInfoBuilder = new UserInfoBuilder();
		accountBuilder = new AccountBuilder();
		codeListBuilder = new CodeListBuilder();
		invoiceBuilder = new InvoiceBuilder();
		receiptBuilder = new ReceiptBuilder();
		jointAccountBuilder = new JointAccountBuilder();
	}
	
	/**
	 * @return Instance of self
	 */
	public static BusinessObjectFactory getFactory() {
		return factory == null ? factory = new BusinessObjectFactory() : factory;
	}
	
	/**
	 * Creates new BusinessObject Instance
	 * @param name desired BusinessObject class name
	 * @param args values used to initialize object
	 * @return new BusinessObject on success else null
	 */
	public BusinessObject getBusinessObject(String name, FieldParams args) {
		switch (name.toLowerCase()) {
			case BusinessClass.USER:
				return userBuilder.getBusinessObject(args);
			case BusinessClass.USERINFO:
				return userInfoBuilder.getBusinessObject(args);
			case BusinessClass.ACCOUNT:
				return accountBuilder.getBusinessObject(args);
			case BusinessClass.CODELIST:
				return codeListBuilder.getBusinessObject(args);
			case BusinessClass.INVOICE:
				return invoiceBuilder.getBusinessObject(args);
			case BusinessClass.RECEIPT:
				return receiptBuilder.getBusinessObject(args);
			case BusinessClass.JOINTACCOUNT:
				return jointAccountBuilder.getBusinessObject(args);
			default:
				return null;
		}
	}
	
	
	public Resultset getResultset(String name, ResultSet res) {
		return null;
	}
	
}
