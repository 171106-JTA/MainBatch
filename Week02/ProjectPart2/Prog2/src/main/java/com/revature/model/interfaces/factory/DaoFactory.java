package com.revature.model.interfaces.factory;

import com.revature.model.daoImpl.*;
import com.revature.model.interfaces.dao.Dao;

public class DaoFactory {

//	@Override
	public static <T> Dao<T> createProduct(Factory product) {
		switch(product) {
		case CRED:
			return new CredentialsDaoImpl<T>();
		default:
			break;
		}
		return null;
	}
	
	public static enum Factory {
		USER, ADMIN, LOAN, REQ, TRANS, CRED
	}

}
