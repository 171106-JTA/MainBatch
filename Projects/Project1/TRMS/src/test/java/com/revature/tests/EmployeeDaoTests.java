package com.revature.tests;

import static com.revature.util.CloseStreams.close;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.TRMSDao;
import com.revature.util.ConnectionUtil;

public class EmployeeDaoTests {
	private TRMSDao dao= new TRMSDao();
	@Before
	public void TestStart() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into employee values(-2,'test', 'testerson', 'test@test.email',5,10,-1,-2)";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
	}
	@After
	public void TestEnd() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="delete employee where emp_id=-2";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
	}
	
	@Test
	public void getLoginsTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		ArrayList<String[]> test= new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into login values(-2,'testU','testP')";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			
			test=dao.getLogins();
			
			sql="delete login where emp_id=-2";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		boolean found=false;
		for(String[] login: test)
		{
			if(login[1].equals("testU"))
				found=true;
		}
		assertTrue(found);
	}
	
	@Test
	public void checkSuperiorTest() {
		boolean test=dao.checkSuperior(-2);
		assertTrue(test);
	}
	
	@Test
	public void getIDTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test= 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into login values(-2,'testU','testP')";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			
			test=dao.getId("testU");
			
			sql="delete login where emp_id=-2";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		assertEquals(test, -2);
	}
	
	@Test
	public void getEmpIdOfAppTest()
	{
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test= 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into repay_app values(-3,-2)";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			
			test=dao.getEmpIdofApp(-3);
			
			sql="delete repay_app where app_id=-3";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		System.out.println(test);
		assertEquals(test, -2);
	}
	
	@Test
	public void isBencoOrChairTest()
	{
		PreparedStatement ps =null;
		ResultSet rs = null;
		int[] test= new int[2];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into emp_status values(-2,0,1)";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			
			test=dao.isBencoOrChair(-2);
			
			sql="delete emp_status where emp_id=-2";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		System.out.println(test[0]);
		assertEquals(test[0], 1);
	}
	@Test
	public void getDepartmentTest() {
		int test=dao.getDepartment(-2);
		assertEquals(test, -1);
	}
	@Test
	public void getPendingTest() {
		int test=dao.getPending(-2);
		assertEquals(test, 10);
	}
	
	
}



