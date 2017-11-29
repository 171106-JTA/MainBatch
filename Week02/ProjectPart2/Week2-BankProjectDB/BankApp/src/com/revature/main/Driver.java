package com.revature.main;

import java.sql.Connection;

import com.revature.beans.Account;
import com.revature.beans.Administrator;
import com.revature.beans.Customer;
import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoImpl;
import com.revature.dao.AdminDao;
import com.revature.dao.AdminDaoImpl;
import com.revature.dao.CustomerDaoImpl;
import com.revature.util.ConnectionUtil;

import static com.revature.util.CloseStreams.close;

public class Driver {

	public static void main(String[] args) throws Exception{
		Connection conn; 
		close(conn = ConnectionUtil.getConnection());
		System.out.println("SUCCESS!");


//		//testing cust
//		CustomerDaoImpl custDao = new CustomerDaoImpl();
//		Customer cust = new Customer("Bob", "Bobsly", "525-25-2525", "bbobsly", "1234");
//		AccountDaoImpl acctDao = new AccountDaoImpl();
//		Account acct = new Account(0.00);
//		acctDao.createAcct(acct);
//		cust.setAccount(acct);
//		custDao.createCust(cust);
		
		//testing admin
//		AdminDaoImpl dao = new AdminDaoImpl();
//		Administrator admin = new Administrator ("adminX", "bankAppPW");
		//dao.createAdmin(admin);
		
		//testing Accounts
//		AccountDaoImpl dao = new AccountDaoImpl();
//		Account acct = new Account( 1999999, 100.00);
//		dao.createAcct(acct);
//		
//		Account testAcct = dao.selectAcctById(1999999);
//		
//		System.out.println(testAcct);
//		
//		dao.updateAcctBalance(1000000, 500.00);
//		dao.updateAcctBalance(2000000, 600.00);
//		
//		dao.displayAccts();
		
		
	}
	/*
	 * SQL
	 * create user minibobbert identified by p4ssw0rd;
	 * grant dba to minibobbert;
	 * commit;
	 */

}
