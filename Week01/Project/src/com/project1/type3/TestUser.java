package com.project1.type3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

public class TestUser {
	
	/**
	 * This will not only test that if the constructor initialized a user
	 * but will also test that the getters as well.
	 */
	@Test
	public void testUserConstructor() {
		User user = new User("Nasir", "Alauddin");
		assertNotNull(user);
		assertEquals("Nasir", user.getUsername());
		assertEquals("Alauddin", user.getPassword());
		assertEquals(0.0f, user.getDebt(), 0.01);
		assertEquals(0.0f, user.getLoan(), 0.01);
		assertEquals(0.0f, user.getDeposit(), 0.01);
		assertEquals(false, user.isAdmin());
		assertEquals(false, user.isApproved());
		assertEquals(false, user.isLoanPending());
		assertEquals(false, user.isLocked());
	}
	
	/**
	 * This will test the usermenu class for its userAccount method and 
	 * will test the other transaction methods that were not tested in the 
	 * previous test.
	 */
	@Test
	public void testUserMenu(){
		UserMenu menu = new UserMenu();
		ArrayList<User> accounts = new ArrayList<User>();
		User admin = new User("Bob", "hello");
		admin.makeAdmin();
		admin.makeApproved();
		User user = new User("Jim", "1234");
		accounts.add(admin);
		accounts.add(user);
		//menu.userAccount(user);
		
		assertEquals(true, admin.isAdmin());
		assertEquals(true, admin.isApproved());
		
		/*
		 * Testing to see if: 
		 * the user deposits $500
		 * the user applies for $200 loan
		 */
		menu.userAccount(user);
		assertEquals(500, user.getDeposit(), 0.01);
		assertEquals(true, user.isLoanPending());
		assertEquals(200, user.getLoan(), 0.01);
		assertEquals(0.0f, user.getDebt(), 0.01);
		
		/*
		 * Testing to see if:
		 * the admin approves a loan
		 * admin approves an account
		 * admin blocks user account
		 */
		menu.adminAccount(accounts, admin.getUsername());
		assertEquals(false, user.isLoanPending());
		assertEquals(true, user.isApproved());
		assertEquals(true, user.isLocked());
		
		/*
		 * Testing to see if:
		 * the admin approves a unlocked account
		 * admin promotes user
		 */
		
		menu.adminAccount(accounts, admin.getUsername());
		assertEquals(false, user.isLocked());
		assertEquals(true, user.isAdmin());
		
		/*
		 * Testing to see if:
		 * user pays back loan
		 * user applies for another loan
		 * admin denies loan
		 */
		
		menu.userAccount(user);//TO DO: take loan of any amount
		menu.adminAccount(accounts, admin.getUsername());//TO DO: deny the loan request
		assertEquals(false, user.isLoanPending());
		assertEquals(0.0f, user.getDebt(), 0.01);
		
		
	}
	
	/**
	 * This is to confirm that the users are being serialized without losing data
	 */
	@Test
	public void testSerliazingUsers(){
		int count = 0;
		//int size = 4;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		ArrayList<User> accounts1 = new ArrayList<User>();
		ArrayList<User> accounts2 = null;
		User user1 = new User("jcd","bionicman");
		User user2 = new User("anavarre","scryspc");
		User user3 = new User("ghermann","schadenfreude");
		User user4 = new User("ajacobson","calvo");
		
		accounts1.add(user1);
		accounts1.add(user2);
		accounts1.add(user3);
		accounts1.add(user4);
		
		
		 //try catch blocks to serialize the accounts
		try{
			oos = new ObjectOutputStream(new FileOutputStream("userstest.ser"));
			oos.writeObject(accounts1);
		}catch(IOException e){
			System.err.println("Could not serliaze accounts1");
			e.printStackTrace();
		}finally{
			if(oos != null){
				try{
					oos.close();
				}catch(IOException e){
					System.err.println("Could not close output stream");
					e.printStackTrace();
				}
			}
		}
		//end of serialization try catch 
		
		//try catch blocks to load file to accounts2
		try{
			ois = new ObjectInputStream(new FileInputStream("userstest.ser"));
			accounts2  = (ArrayList<User>) ois.readObject();
		}catch(IOException | ClassNotFoundException e){
			System.err.println("Could not load ser file");
			e.printStackTrace();
		}finally{
			if(ois != null){
				try{
					ois.close();
				}catch(IOException e){
					System.err.println("Could not close input stream");
					e.printStackTrace();
				}
			}
		}
		//end of loading try catch blocks
		
		
		//checks to see that the sizes are the same
		assertEquals(accounts1.size(), accounts2.size());
		
		//checks to see that users have been loaded correctly
		for(User u : accounts2){
			if(count == 0){
				assertEquals("jcd", u.getUsername());
				assertEquals("bionicman", u.getPassword());
			}
			else if(count == 1){
				assertEquals("anavarre", u.getUsername());
				assertEquals("scryspc", u.getPassword());
			}
			else if(count == 2){
				assertEquals("ghermann", u.getUsername());
				assertEquals("schadenfreude", u.getPassword());
			}
			else if(count == 3){
				assertEquals("ajacobson", u.getUsername());
				assertEquals("calvo", u.getPassword());
			}
			count++;
		}
	}

}
