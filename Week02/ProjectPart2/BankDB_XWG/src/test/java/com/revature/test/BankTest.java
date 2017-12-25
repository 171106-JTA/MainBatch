package com.revature.test;


import static com.revature.util.CloseStreams.close;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.bankdb.UI;
import com.revature.util.ConnectionUtil;
/**
 * Tests for UI class
 * @author Xavier Garibay
 *
 */
public class BankTest {
	int money=500;
	UI ui;
	/**
	 * adds record to database to use in test
	 */
	@Before
	public void testStart()
	{
		UI ui= new UI();
		UI.logger.info("TEST");
		
		int x=0;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String s= new String();
		ArrayList<String> users= new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="INSERT INTO login values(-3,'username', 'password')";
			ps = conn.prepareStatement(sql);
			x=ps.executeUpdate(sql);
			sql="INSERT INTO status values(-3,0,0,1)";//default values
			ps= conn.prepareStatement(sql);
			x=ps.executeUpdate();
			sql="INSERT INTO money values(-3,317,0)";//default values
			ps= conn.prepareStatement(sql);
			x=ps.executeUpdate();
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
	}
	/**
	 * removes test record
	 */
	@After
	public void testEnd()
	{
		PreparedStatement ps =null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="DELETE FROM login WHERE ac_id = -3";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
	}
	
	/**
	 * check that UI login() works
	 */
	@Test
	public void loginTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/loginTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ui.login();
		int[] i=ui.dao.getMoney(-3);
		assertEquals(i[1],517,0);
	}
	
	/**
	 * Check that UI deposit works
	 */
	@Test
	public void depositTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/depositTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int x=500;
		x=ui.deposit(x);
		assertEquals(x,1000);
	}
	
	/**
	 * check that UI run() works
	 */
	@Test
	public void runTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/runTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ui.run();
		int[] i=ui.dao.getMoney(-3);
		assertEquals(i[1],517,0);
	}
	
	/**
	 * check that UI manageAccount() works with a non-admin user
	 */
	@Test
	public void manageNormTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/normTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ui.manageAccount(-3,0);
		int[] i=ui.dao.getMoney(-3);
		assertEquals(i[1],517,0);
	}
	
	/**
	 * check that UI manageAccount() works with a admin user
	 */
	@Test
	public void manageAdminTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/adminTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		newUser();
		ui.manageAccount(-3,0);
		ArrayList<String> names= ui.dao.getAllUsername();
		boolean found=false;
		for(String y: names)
		{
			if(y.equals("usernames"))
				found=true;
		}
		assertTrue(found);
		ui.dao.remove(-4);
	}
	
	/**
	 * checks that UI withdraw() works
	 */
	@Test
	public void withdrawTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/withdrawTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int x=500;
		x=ui.withdraw(x);
		assertEquals(x,400);
	}
	
	/**
	 * checks that UI applyLoan() works
	 */
	@Test
	public void applyLoanTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/loanTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int x=0;
		x=ui.applyLoan(x);
		assertEquals(x,-100);
	}
	
	/**
	 * checks that UI numInput() works
	 */
	@Test
	public void inputTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/inputTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int x=ui.numInput();
		assertEquals(x,1);
	}
	
	/**
	 * checks that UI reviewRequest() works
	 */
	@Test
	public void reviewAccountTest()
	{
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/reviewTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		newUser();
		ui.reviewRequest();
		ArrayList<String> names= ui.dao.getAllUsername();
		boolean found=false;
		for(String y: names)
		{
			if(y.equals("usernames"))
				found=true;
		}
		assertTrue(found);
		ui.dao.remove(-4);
	}
	
	/**
	 * checks that UI promote() works
	 */
	@Test
	public void promoteTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/promoteTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ui.promoteUser();
		int[] i=ui.dao.getStatus(-3);
		assertEquals(i[1],1);
	}

	/**
	 * checks that UI lockOrNot() works
	 */
	@Test
	public void lockTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/lockTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ui.lockOrNot();
		int[] i=ui.dao.getStatus(-3);
		assertEquals(i[2],1);
	}
	
	/**
	 * checks that UI loanRequest() works
	 */
	@Test
	public void approveLoanTest() {
		UI ui= new UI();
		try {
			System.setIn(new FileInputStream("src/test/resources/applyLoanTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int x=0;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String s= new String();
		ArrayList<String> users= new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="INSERT INTO login values(-4,'usernames', 'passwords')";
			ps = conn.prepareStatement(sql);
			x=ps.executeUpdate(sql);
			sql="INSERT INTO status values(-4,0,0,0)";//default values
			ps= conn.prepareStatement(sql);
			x=ps.executeUpdate();
			sql="INSERT INTO money values(-4,317,-500)";//default values
			ps= conn.prepareStatement(sql);
			x=ps.executeUpdate();
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		ui.loanRequest(-3);
		int[] i=ui.dao.getMoney(-4);
		assertEquals(i[1],817);
	}
	
	/**
	 * checks that UI payback() works
	 */
	@Test
	public void paybackTest()
	{
		UI ui= new UI();
		int[] money = {-3,500,400};
		try {
			System.setIn(new FileInputStream("src/test/resources/payBackTest.txt"));
			ui.read=new Scanner(System.in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		money=ui.payBack(money);
		assertEquals(money[1],100);
		
	}
	
	public void newUser() {
		int x=0;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String s= new String();
		ArrayList<String> users= new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="INSERT INTO login values(-4,'usernames', 'passwords')";
			ps = conn.prepareStatement(sql);
			x=ps.executeUpdate(sql);
			sql="INSERT INTO status values(-4,0,0,0)";//default values
			ps= conn.prepareStatement(sql);
			x=ps.executeUpdate();
			sql="INSERT INTO money values(-4,317,0)";//default values
			ps= conn.prepareStatement(sql);
			x=ps.executeUpdate();
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
	}
	
	
}
