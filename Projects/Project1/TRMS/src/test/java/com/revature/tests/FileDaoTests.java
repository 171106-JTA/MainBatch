package com.revature.tests;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import com.revature.dao.TRMSDao;
import com.revature.util.ConnectionUtil;

public class FileDaoTests {
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
			sql ="insert into app_files values(-1,-3,'test file',null,'test file desc')";
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
			String sql ="delete app_files where file_id=-1";
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
	public void getAppFilesTest() {
		ArrayList<String[]> test= dao.getAppFiles(-3);
		boolean found=false;
		for(String[] file:test)
		{
			if(file[0].equals("-1"))
				found=true;
		}
		assertTrue(found);
		
	}
	@Test
	public void getFileIdsTest() {
		ArrayList<Integer> test= dao.getFileIds();
		boolean found=false;
		for(int file:test)
		{
			if(file==-1)
				found=true;
		}
		assertTrue(found);
	}
	@Test
	public void markGradeTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int test= 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into app_pass values(-3,-1,0)";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			dao.markGrade(-3,-1);
			sql="select grade_input_mark from app_pass where app_id=-3";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				test=rs.getInt(1);
			}
			sql ="delete app_pass where app_id=-3";
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
		assertEquals(test,1);
	}
	@Test//COME BACK TO
	public void getGradesTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		ArrayList<String[]> test= new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into app_pass values(-3,-1,0)";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			sql ="insert into app_status values(-3,0,0,0,0,0,0,0,0,'11-JAN-2017')";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();
			sql ="insert into app_info values(-3,'11-JAN-2017','test event','test time',400,1,1)";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();
			test=dao.getGrades();
			sql ="delete app_pass where app_id=-3";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();
			sql ="delete app_info where app_id=-3";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();
			sql ="delete app_status where app_id=-3";
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
		for(String[] app: test)
		{
			if(app[0].equals("-3"));
				found=true;
		}
		assertTrue(found);
	}
	@Test
	public void getGradeTest() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		String[] test= new String[9];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="insert into app_pass values(-3,-1,0)";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();
			sql ="insert into app_status values(-3,0,0,0,0,0,0,0,0,'11-JAN-2017')";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();
			sql ="insert into app_info values(-3,'11-JAN-2017','test event','test time',400,1,1)";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();
			test=dao.getGrade(3);
			sql ="delete app_pass where app_id=-3";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();
			sql ="delete app_info where app_id=-3";
			ps = conn.prepareStatement(sql);
			x= ps.executeUpdate();
			sql ="delete app_status where app_id=-3";
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
		if(test[0].equals("-3"));
			found=true;
		assertTrue(found);	
	}
}
