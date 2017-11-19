package com.revature.core.factory.builder;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;

/**
 * Initializes Objects of type UserInfo
 * @author Antony Lulciuc
 */
public class UserInfoBuilder implements BusinessObjectBuilder {

	/**
	 * Creates UserInfo instance 
	 * @param args used to instantiate instance 
	 * @return new UserInfo on success else null
	 */
	@Override
	public BusinessObject getBusinessObject(FieldParams args) {
		BusinessObject object = null;
		
		if (isValid(args)) {
			object = new UserInfo(Long.parseLong(args.get(UserInfo.USERID)), Long.parseLong(args.get(UserInfo.SSN)),
							args.get(UserInfo.EMAIL), args.get(UserInfo.PHONENUMBER), args.get(UserInfo.ADDRESS1),
							args.get(UserInfo.ADDRESS2), args.get(UserInfo.FIRSTNAME), args.get(UserInfo.LASTNAME),
							args.get(UserInfo.POSTALCODE), Long.parseLong(args.get(UserInfo.STATECITYID)), 
							Long.parseLong(args.get(UserInfo.ROLEID)), Long.parseLong(args.get(UserInfo.STATUSID)));
		}
		
		return object;
	}
		
	public Resultset getBusinessObject(ResultSet args) {
		Resultset data = new Resultset();
		UserInfo info;
		
		try {
			while (args.next()) {
				info = new UserInfo(args.getLong(UserInfo.USERID), args.getLong(UserInfo.SSN), args.getString(UserInfo.EMAIL),
						args.getString(UserInfo.PHONENUMBER), args.getString(UserInfo.ADDRESS1), args.getString(UserInfo.ADDRESS2),
						args.getString(UserInfo.FIRSTNAME), args.getString(UserInfo.LASTNAME), args.getString(UserInfo.POSTALCODE),
						args.getLong(UserInfo.STATECITYID), args.getLong(UserInfo.ROLEID), args.getLong(UserInfo.STATUSID));
				
				data.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * Used to ensure all arguments needed to instantiate object
	 * @param args what to check
	 * @return true is arguments for class to create are valid else false
	 */
	@Override
	public boolean isValid(FieldParams args) {
		// TODO Auto-generated method stub
		return args != null && args.get(UserInfo.USERID) != null && args.get(UserInfo.SSN) != null;
	}

}
