package com.revature.bankdb;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.util.ConnectionUtil;

public class UserDAO {
	
	/**
	 * Gets user id username and password from database by username
	 * @param username - account to be found
	 * @return - account information
	 */
	public String[] getLogin(String username)
	{
		PreparedStatement ps =null;
		ResultSet rs = null;
		String[] loginInfo= new String[3];
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM login WHERE ac_username = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs= ps.executeQuery();
			
			while(rs.next()) {
				loginInfo[0]=rs.getString(1);//id
				loginInfo[1]=rs.getString(2);//username
				loginInfo[2]=rs.getString(3);//password
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return loginInfo;
	}
	
	/**
	 * Gets user id username and password from database by account id
	 * @param id - account to be found
	 * @return account information
	 */
	public String[] getLogin(int id)
	{
		PreparedStatement ps =null;
		ResultSet rs = null;
		String[] loginInfo= new String[3];
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM login WHERE ac_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();
			
			while(rs.next()) {
				loginInfo[0]=rs.getString(1);//id
				loginInfo[1]=rs.getString(2);//username
				loginInfo[2]=rs.getString(3);//password
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return loginInfo;
	}
	
	/**
	 * Get acount id, admin status, locked status and approved status  of an account from the database by id
	 * @param id - account to be found
	 * @return account status information
	 */
	public int[] getStatus(int id)
	{
		PreparedStatement ps =null;
		ResultSet rs = null;
		int[] statusInfo= new int[4];
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM status WHERE ac_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();
			
			while(rs.next()) {
				statusInfo[0]=rs.getInt(1);//id
				statusInfo[1]=rs.getInt(2);//admin
				statusInfo[2]=rs.getInt(3);//locked
				statusInfo[3]=rs.getInt(4);//accepted
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return statusInfo;
	}
	
	/**
	 * Get id, balance and loan of an account from the database by id
	 * @param id - account to be found
	 * @return account money information
	 */
	public int[] getMoney(int id)
	{
		PreparedStatement ps =null;
		ResultSet rs = null;
		int[] statusInfo= new int[3];
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM money WHERE ac_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();
			
			while(rs.next()) {
				statusInfo[0]=rs.getInt(1);//ac_id
				statusInfo[1]=rs.getInt(2);//balance
				statusInfo[2]=rs.getInt(3);//loan
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return statusInfo;
	}
	
	/**
	 * Get a list of all usernames in the database
	 * @return list of usernames
	 */
	public ArrayList<String> getAllUsername()
	{
		PreparedStatement ps =null;
		ResultSet rs = null;
		String s= new String();
		ArrayList<String> users= new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT ac_username FROM login";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			
			while(rs.next()) {
				s=rs.getString(1);//username
				users.add(s);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return users;
	}
	
	/**
	 * Get status information of each account along with their id 
	 * @return every accounts status information
	 */
	public ArrayList<int[]> getAllStatus()
	{
		PreparedStatement ps =null;
		ResultSet rs = null;
		int[] i= new int[4];
		ArrayList<int[]> users= new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM status";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			
			while(rs.next()) {
				i[0]=rs.getInt(1);//id
				i[1]=rs.getInt(2);//admin
				i[2]=rs.getInt(3);//locked
				i[3]=rs.getInt(4);//approved
				users.add(i);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return users;
	}
	
	/**
	 * Add new user to the database, creating a record in each table
	 * @param u - username
	 * @param p - password
	 */
	public void addUser(String u, String p)
	{//account id in all based on sequence status
		int x=0;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String s= new String();
		ArrayList<String> users= new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="INSERT INTO login (ac_username, ac_password) values('" + u +"', '" + p +"')";
			ps = conn.prepareStatement(sql);
			x=ps.executeUpdate(sql);
			sql="INSERT INTO status (st_admin, st_locked, st_accepted) values(0,0,0)";//default values
			ps= conn.prepareStatement(sql);
			x=ps.executeUpdate();
			sql="INSERT INTO money (mn_balance, mn_loans) values(0,0)";//default values
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
	 * Update the account of the logged in user with any changes to personal account before allowing new log in 
	 * @param status - updated status information
	 * @param money - updated money information
	 */
	public void logout(int[] status, int[] money) {
		int x=0;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String s= new String();
		ArrayList<String> users= new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql="UPDATE status SET st_admin=" + status[1] +", st_locked=" +status[2] +", st_accepted="+ status[3]
					+" WHERE ac_id=" + status[0];
			ps= conn.prepareStatement(sql);
			x=ps.executeUpdate();//any changes in status
			sql="UPDATE money SET mn_balance=" + money[1] + ", mn_loans="+ money[2] + " WHERE ac_id=" + money[0];
			ps= conn.prepareStatement(sql);
			x=ps.executeUpdate();//any changes in money
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
	 * Delete an account from the database, removing all records through cascade
	 * @param id - account to be deleted
	 */
	public void remove(int id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="DELETE FROM login WHERE ac_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);//account to be removed
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
	 * Update status information of an account by id so that the account is approved
	 * @param id - account to be approved
	 */
	public void approve(int id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="UPDATE status SET st_accepted=1  WHERE ac_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);//account to approve
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
	 * Update status information of an account by id so that the account is admin
	 * @param id - account to be admin
	 */
	public void promote(int id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="UPDATE status SET st_admin=1  WHERE ac_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);//account to promote
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
	 * Update status information of an account by id so that the account is locked or unlocked based on current status
	 * @param id - account to be locked or unlocked
	 */
	public void switchLock(int id, int lock) {
		String sql="";
		PreparedStatement ps =null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection();){
			if(lock==0)//if unlocked, lock
				 sql ="UPDATE status SET st_locked=1  WHERE ac_id = ?";
			else//if locked, unlock
				sql="UPDATE status SET st_locked=0  WHERE ac_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);//account which lock is switched
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
	 * Update money information of an account by id based on loan review choice
	 * @param loanInfo - id to find account. balance and loan to be updated
	 */
	public void loanUpdate(int[] loanInfo) {
		String sql="";
		PreparedStatement ps =null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection();){
			sql="UPDATE money SET mn_balance=" + loanInfo[1] +", mn_loans=" +loanInfo[2] + " WHERE ac_id = " + loanInfo[0];
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
}
