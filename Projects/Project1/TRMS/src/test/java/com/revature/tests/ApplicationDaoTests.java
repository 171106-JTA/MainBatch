package com.revature.tests;

import static com.revature.util.CloseStreams.close;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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

public class ApplicationDaoTests {
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
			sql ="insert into repay_app values(-3,-2)";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();
			sql ="insert into app_status values(-3,0,0,0,0,0,0,0,0,'11-JAN-2017')";
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
			String sql ="delete app_status where app_id=-3";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			sql ="delete repay_app where app_id=-3";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();
			sql ="delete employee where emp_id=-2";
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
	}
	
	@Test 
	public void getAppsStatusTest() {
		ArrayList<int[]> test= new ArrayList<>();
		test=dao.getAppsStatus();
		boolean found=false;
		for(int[] app: test)
		{
			if(app[0]==-3)
				found=true;
		}
		assertTrue(found);
	}
	@Test 
	public void getAppStatusTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int[] test= new int[4];
		test=dao.getAppStatus(-3);
		assertEquals(test[2],0);
	}
	
	@Test
	public void getAppStatusWithDateTest() {
		ArrayList<String[]> test= new ArrayList<>();
		test=dao.getAppStatusWithDate();
		boolean found=false;
		for(String[] app: test)
		{
			if(app[0].equals("-3"))
				found=true;
		}
		assertTrue(found);
	}
	

	@Test 
	public void getAppSupTest() {
		int test=dao.getAppSup(-2);
		assertEquals(test,-2);
		
	}
	
	@Test 
	public void getAppDeptTest() {
		int test=dao.getAppDept(-3);
		assertEquals(test,-1);
	}
	@Test 
	public void getAppInfoTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		String[] test= new String[9];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into app_info values(-3,'11-JAN-2017','test event','test time',400,1,1)";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			test=dao.getAppInfo(-3);
			sql ="delete app_info where app_id=-3";
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
		assertTrue(test[5].equals("400"));
	}
	@Test
	public void getAppUserInfoTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		String[] test= new String[11];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into app_info values(-3,'11-JAN-2017','test event','test time',400,1,1)";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			test=dao.getAppUserInfo(-3);		
			sql ="delete app_info where app_id=-3";
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
		assertTrue(test[3].equals("400"));
	}
	@Test
	public void supApproveTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test=-1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			dao.supApprove(-3);
			String sql ="select sup_apr from app_status where app_id=-3";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				test=rs.getInt(1);
			}		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		assertEquals(test,1);
	}
	@Test
	public void chairApproveTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test=-1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			dao.chairApprove(-3);
			String sql ="select chair_apr from app_status where app_id=-3";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				test=rs.getInt(1);
			}	
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		assertEquals(test,1);
	}
	@Test
	public void bencoApproveTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test=-1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			dao.bencoApprove(-3,"test");
			String sql ="select benco_apr from app_status where app_id=-3";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				test=rs.getInt(1);
			}
			sql ="delete benco_approved where app_id=-3";
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
		assertEquals(test,1);
	}
	@Test
	public void supRejectTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test=0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			dao.supReject(-3,"test");
			String sql ="select sup_apr from app_status where app_id=-3";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				test=rs.getInt(1);
			}	
			sql ="delete app_rejected where app_id=-3";
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
		assertEquals(test,-1);
	}
	@Test 
	public void chairRejectTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test=0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			dao.chairReject(-3,"test");
			String sql ="select chair_apr from app_status where app_id=-3";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				test=rs.getInt(1);
			}
			sql ="delete app_rejected where app_id=-3";
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
		assertEquals(test,-1);
	}
	@Test
	public void bencoRejectTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test=0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			dao.bencoReject(-3,"test");
			String sql ="select benco_apr from app_status where app_id=-3";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				test=rs.getInt(1);
			}
			sql ="delete app_rejected where app_id=-3";
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
		assertEquals(test,-1);
	}

	@Test 
	public void getUserAppsTest() {
		ArrayList<Integer> test= dao.getUserApps(-2);
		boolean found=false;
		for(Integer app:test)
		{
			if(app==-3)
				found=true;
		}
		assertTrue(found);
	}

	@Test
	public void BencoGradeApproveTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test=0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			dao.BencoGradeApprove(-3);
			String sql ="select passed from app_status where app_id=-3";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				test=rs.getInt(1);
			}		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		assertEquals(test,1);
	}
	@Test 
	public void SupGradeApproveTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test=0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			dao.BencoGradeApprove(-3);
			String sql ="select passed from app_status where app_id=-3";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				test=rs.getInt(1);
			}	
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		assertEquals(test,1);
	}
	@Test
	public void CompleteInfoTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int[] test= new int[5];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into app_info values(-3,'11-JAN-2017','test event','test time',400,1,1)";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			test=dao.CompleteInfo(-3);	
			sql ="delete app_info where app_id=-3";
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
		assertEquals(test[0],-3);
	}
	@Test 
	public void updateCostTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test= 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into app_info values(-3,'11-JAN-2017','test event','test time',400,1,1)";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			dao.updateCost(-3,500,10);
			sql ="select ev_cost from app_info where app_id=-3";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				test=rs.getInt(1);
			}
			sql ="delete app_info where app_id=-3";
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
		assertEquals(test,500);
	}
	
	@Test 
	public void infoHoldTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test= -1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			dao.infoHold(-3);
			String sql ="select info_hold from app_status where app_id=-3";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				test=rs.getInt(1);
			}		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		assertEquals(test,1);
	}
	
}
