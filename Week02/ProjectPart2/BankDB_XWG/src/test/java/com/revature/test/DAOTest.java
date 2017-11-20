package com.revature.test;

import static com.revature.util.CloseStreams.close;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.revature.bankdb.UI;
import com.revature.bankdb.UserDAO;
import com.revature.util.ConnectionUtil;

/**
 * Tests for UserDOA class
 * @author Xavier Garibay
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOTest {
	/**
	 * add record to database to test with
	 */
	@BeforeClass
	public static void testStart()
	{
		UI ui= new UI();
		UI.logger.info("TEST");
		
		int x=0;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String s= new String();
		ArrayList<String> users= new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="INSERT INTO login values(-2,'test', 'test')";
			ps = conn.prepareStatement(sql);
			x=ps.executeUpdate(sql);
			sql="INSERT INTO status values(-2,0,0,0)";//default values
			ps= conn.prepareStatement(sql);
			x=ps.executeUpdate();
			sql="INSERT INTO money values(-2,317,0)";//default values
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
	 * remove test record
	 */
	@AfterClass
	public static void testEnd()
	{
		PreparedStatement ps =null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="DELETE FROM login WHERE ac_id = -2";
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
	 * checks that UserDAO getLogin(String) works
	 */
	@Test
	public void AgetLoginUsernameTest()
	{
		UserDAO dao= new UserDAO();
		String[] x = new String[3];
		x= dao.getLogin("test");
		int y=Integer.parseInt(x[0]);
		assertEquals(y, -2);
	}
	
	/**
	 * checks that UserDAO getLogin(int) works
	 */
	@Test
	public void BgetLoginIDTest()
	{
		UserDAO dao= new UserDAO();
		String[] x = new String[3];
		x= dao.getLogin(-2);
		int y=Integer.parseInt(x[0]);
		assertEquals(y, -2);
	}
	
	/**
	 * checks that UserDAO getStatus() works
	 */
	@Test
	public void CgetStatusTest()
	{
		UserDAO dao= new UserDAO();
		int[] x = new int[4];
		x= dao.getStatus(-2);
		assertEquals(x[0], -2);
	}
	
	/**
	 * checks that UserDAO getMoney() works
	 */
	@Test
	public void DgetMoneyTest()
	{
		UserDAO dao= new UserDAO();
		int[] x = new int[3];
		x= dao.getMoney(-2);
		assertEquals(x[1], 317);
	}
	
	/**
	 * checks that UserDAO getAllUsername() works
	 */
	@Test
	public void EgetAllUsernamesTest()
	{
		UserDAO dao= new UserDAO();
		ArrayList<String> x = new ArrayList<>();
		x= dao.getAllUsername();
		boolean found=false;
		for(String y: x)
		{
			if(y.equals("test"))
				found=true;
		}
		assertTrue(found);
	}
	
	/**
	 * checks that UserDAO logout() works
	 */
	@Test
	public void FlogoutTest()
	{
		UserDAO dao= new UserDAO();
		int[] status = {-2,0,0,0};
		int[] money= {-2,532,12};
		int x=0;
		dao.logout(status, money);
		PreparedStatement ps =null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT ac_id FROM money WHERE mn_balance = 532";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			
			while(rs.next()) {
				x=rs.getInt(1);//ac_id

			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		
		assertEquals(x, -2);
	}
	
	/**
	 * checks that UserDAO remove() works
	 */
	@Test
	public void GremoveTest()
	{
		UserDAO dao= new UserDAO();
		dao.remove(-2);
		ArrayList<int[]> x = new ArrayList<>();
		x= dao.getAllStatus();
		boolean found=false;
		for(int[] y: x)
		{
			if(y[0]==-2)
				found=true;
		}
		assertFalse(found);
		testStart();
	}
	
	/**
	 * checks that UserDAO approve() works
	 */
	@Test
	public void HapproveTest()
	{
		UserDAO dao= new UserDAO();
		dao.approve(-2);
		int[] x = new int[4];
		x= dao.getStatus(-2);
		assertEquals(x[3], 1);
	}
	
	/**
	 * checks that UserDAO promote() works
	 */
	@Test
	public void IpromoteTest()
	{
		UserDAO dao= new UserDAO();
		dao.promote(-2);
		int[] x = new int[4];
		x= dao.getStatus(-2);
		assertEquals(x[1], 1);
	}
	
	/**
	 * checks that UserDAO switchLock() works
	 */
	@Test
	public void JlockTest()
	{
		UserDAO dao= new UserDAO();
		dao.switchLock(-2,0);
		int[] x = new int[4];
		x= dao.getStatus(-2);
		assertEquals(x[2], 1);
	}
	
	/**
	 * checks that UserDAO loanUpdate() works
	 */
	@Test
	public void KloanTest()
	{
		UserDAO dao= new UserDAO();
		int[] x = {-2,532,1000};
		dao.loanUpdate(x);
		int[] y= dao.getMoney(-2);
		assertEquals(y[2], 1000);
	}
}
