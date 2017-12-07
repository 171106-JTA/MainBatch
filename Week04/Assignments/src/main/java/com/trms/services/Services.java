package com.trms.services;
import com.trms.dao.Dao;
import com.trms.dao.DaoImpl;
import com.trms.obj.Employee; 



public class Services {
		
		public static int userExists(String loginUserId, String email) {
			Dao dao = new DaoImpl();  
			if(dao.loginIdAvailable(loginUserId))
				return 1; 
			if(dao.emailAvailable(email))
				return 2; 
			return 0; 
	}
		
		public static boolean insertEmployee(Employee e) {
			Dao dao = new DaoImpl();
			return dao.insertEmployee(e); 
		}
}
