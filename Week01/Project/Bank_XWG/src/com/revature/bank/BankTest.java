package com.revature.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
/**
 * Tests for Bank_XWG application
 * @author Xavier Garibay
 *
 */
public class BankTest {
	int money=500;

	/**
	 * Marks test logs as tests
	 */
	@Before
	public void testStart()
	{
		UI ui= new UI();
		ui.logger.info("TEST");
	}
	/**
	 * Checks if User deposit() works correctly
	 */
	@Test
	public void depositTest()
	{
		User u=new User("","");
		u.deposit(money);
		assertEquals(u.getBalance(), 500,0);
	}
	
	/**
	 * Checks if User withdraw() works correctly
	 */
	@Test
	public void withdrawTest() {
		User u= new User("","");
		u.deposit(money);
		u.withdraw(2*money);
		u.withdraw(money/2);
		System.out.println(u.getBalance());
		assertEquals(u.getBalance(), 250,0);
	}
	
	/**
	 * checks if User switchLock will lock an account
	 */
	@Test
	public void lockTest()
	{
		User u= new User("","");
		u.switchLock();
		assertTrue(u.getLock());
	}
	
	/**
	 * checks if User switchLock will unlock an account
	 */
	@Test
	public void unlockTest()
	{
		User u= new User("","");
		u.switchLock();
		u.switchLock();
		assertFalse(u.getLock());
	}
	
	/**
	 * Checks if User Approve() works correctly
	 */
	@Test
	public void approveTest()
	{
		User u= new User("","");
		u.Approve();
		assertTrue(u.isApproved());
	}
	
	
	/**
	 * Checks if UI reviewRequest() removes rejected users
	 */
	@Test
	public void reviewAccTestReject() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("reviewTest.txt"));
			ui.read= new Scanner(System.in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ui.users.add(new User("1",""));
		ui.users.add(new User("2",""));
		ui.reviewRequest();
		assertEquals(ui.users.size(),1);
	}
	
	/**
	 * Checks if UI reviewRequest() approves accepted users
	 */
	@Test
	public void reviewAccTestApprove() {
		int count=0;
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("reviewTest.txt"));
			ui.read= new Scanner(System.in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ui.users.add(new User("1",""));
		ui.users.add(new User("2",""));
		User u = new User("3", " ");
		u.Approve();
		ui.users.add(u);
		ui.reviewRequest();
		for(User x: ui.users)
		{
			if(x.isApproved())
				count++;
		}
		assertEquals(count,2);
	}
	
	/**
	 * checks if UI createAccount() adds account to user list
	 */
	@Test
	public void createAccTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("createTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ui.createAccount();
		assertEquals(ui.users.size(),1);
	}
	
	/**
	 * Checks if UI promoteUser() promotes a user to admin
	 */
	@Test
	public void promoteTest() {
		int count=0;
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("promoteTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User u= new User("test", "tpass");
		ui.users.add(u);
		ui.promoteUser();
		for(User x: ui.users)
		{
			if(x.isAdmin())
				count++;
		}
		assertEquals(count,1);
	}
	
	/**
	 * checks if UI lockOrNot() will lock a unlocked user
	 */
	@Test
	public void lockerTest() {
		int count=0;
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("lockTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User u= new User("test", "tpass");
		ui.users.add(u);
		ui.lockOrNot();
		for(User x: ui.users)
		{
			if(x.getLock())
				count++;
		}
		assertEquals(count,1);
		
	}
	
	/**
	 * checks if UI lockOrNot() will unlock a locked user
	 */
	@Test
	public void unlockerTest() {
		int count=0;
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("lockTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User u= new User("test", "tpass");
		u.switchLock();
		ui.users.add(u);
		ui.lockOrNot();
		for(User x: ui.users)
		{
			if(!x.getLock())
				count++;
		}
		assertEquals(count,1);		
	}

	/*
	 * Checks if file is correctly created by UI writeRecord()
	 */
	@Test
	public void writeTest() {
		UI ui= new UI();
		String test="writeTest";
		User u=new User("testU", "testP");
		ui.users.add(u);
		ui.writeRecord(test);
		ui.users=new ArrayList<User>();
		try {
		ObjectInputStream ois= new ObjectInputStream(new FileInputStream(test));
		ui.users=(ArrayList<User>)ois.readObject();	
		if(ois!=null)
			ois.close();
		}
		catch(FileNotFoundException e) {
			;//skip if not found, will happen on first execution so is expected to occur
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(ui.users.size(),1);		
	}
	
	/**
	 * Checks if UI start() adds the default users to the user list
	 */
	@Test
	public void startTest() {
		UI ui= new UI();
		ui.users= ui.start();
		assertEquals(ui.users.size(),2);		
	}	

	/**
	 * Checks if UI run() directs user to account creation correctly
	 */
	@Test
	public void runTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("runTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ui.run();
		assertEquals(ui.users.size(),1);		
	}
	
	/**
	 * checks if manageAccount() directs a user to deposit correctly
	 */
	@Test
	public void manageNormTest() {
		UI ui= new UI();
		User u= new User("", "");
		ui.users.add(u);
		try {
			System.setIn(new FileInputStream("normTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ui.manageAccount(u);
		assertEquals(u.getBalance(),200,0);		
	}
	
	
	/**
	 * Checks if UI manageAccount() directs an admin user to review requests correctly
	 */
	@Test
	public void manageAdminTest() {
		int count= 0;
		UI ui= new UI();
		User u= new User("", "");
		u.Approve();
		u.makeAdmin();
		ui.users.add(u);
		User x= new User("User","Pass");
		ui.users.add(x);
		try {
			System.setIn(new FileInputStream("adminTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ui.manageAccount(u);
		for(User y: ui.users)
		{
			if(y.isApproved())
				count++;
		}
		assertEquals(count,2);		
	}
	
	/**
	 * Checks if UI login() allows user to login and use their account
	 */
	@Test
	public void loginTest() {
		UI ui= new UI();
		User u= new User("username", "password");
		u.Approve();
		ui.users.add(u);
		try {
			System.setIn(new FileInputStream("loginTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ui.login();
		assertEquals(u.getBalance(),200,0);
	}
	
	/**
	 * Checks if UI loanRequest() allows a user to approve a loan
	 */
	@Test
	public void loanTest() {
		UI ui= new UI();
		User admin = new User("admin", "admin");
		User loan = new User("loan", "request");
		loan.setLoan(-1000);
		ui.users.add(admin);
		ui.users.add(loan);
		try {
			System.setIn(new FileInputStream("loanTest.txt"));
			ui.read= new Scanner(System.in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ui.loanRequest(admin);
		assertEquals(loan.getLoan(),1000,0);
	}
	
	/**
	 *  checks if UI payBack() allows a user to pay back loan with correct amount in account balance afterwards
	 */
	@Test
	public void payBackTest() {
		UI ui= new UI();
		User loan = new User("loan", "request");
		loan.setLoan(1000);
		loan.deposit(1500);
		ui.users.add(loan);
		try {
			System.setIn(new FileInputStream("payBackTest.txt"));
			ui.read= new Scanner(System.in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ui.payBack(loan);
		assertEquals(loan.getBalance(),500,0);
	}
	
}
