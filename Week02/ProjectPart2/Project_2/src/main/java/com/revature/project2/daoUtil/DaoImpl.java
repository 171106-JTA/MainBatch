package com.revature.project2.daoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.project2.connector.ConnectorUtil;

//import static com.revature.project2.connector.*;

public class DaoImpl implements DaoInterface{

	@Override
	public boolean verify(String username, String password) {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		ResultSet r = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("select pass from users where username = ?");
			p.setString(1, username);
			r = p.executeQuery();
			r.next();
			if(password.equals(r.getString(1)) && !r.getString(1).isEmpty())
				return true;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
		
	}
	
	public boolean verifyAdmin(String username, String password){
		PreparedStatement p = null;
		ResultSet r = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("select promoted from users where username = ?");
			p.setString(1, username);
			r = p.executeQuery();
			r.next();
			
			if(r.getInt(1) == 1 )
				return true;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}
	

	@Override
	public double getDeposit(String username) {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		ResultSet r = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("select deposit from accounts where username = ?");
			p.setString(1, username);
			r = p.executeQuery();
			r.next();
			return r.getDouble(1);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0;
	}

	@Override
	public double getDebt(String username) {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		ResultSet r = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("select debt from accounts where username = ?");
			p.setString(1, username);
			r = p.executeQuery();
			r.next();
			return r.getDouble(1);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0;
	}

	@Override
	public double getLoan(String username) {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		ResultSet r = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("select loan from accounts where username = ?");
			p.setString(1, username);
			r = p.executeQuery();
			r.next();
			return r.getDouble(1);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0;
	}

	@Override
	public List<String> getLoanPending() {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		ResultSet r = null;
		List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("select username from accounts where loan > 0");
			r = p.executeQuery();
			while(r.next()){
				list.add(r.getString(1));
			}
			return list;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public List<String> getNormalUsers() {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		ResultSet r = null;
		List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("select username from accounts");
			r = p.executeQuery();
			while(r.next()){
				list.add(r.getString(1));
			}
			return list;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public List<String> getPendingUsers() {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		ResultSet r = null;
		List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("select a.username from users a "
					+ "inner join accounts b "
					+ "on a.username != b.username");
			r = p.executeQuery();
			while(r.next()){
				list.add(r.getString(1));
			}
			return list;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}


	@Override
	public List<String> getBlockedUsers() {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		ResultSet r = null;
		List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("select username from users where blocked = 1");
			r = p.executeQuery();
			while(r.next()){
				list.add(r.getString(1));
			}
			return list;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public List<String> getUnblockedUsers() {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		ResultSet r = null;
		List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("select username from users where blocked = 0");
			r = p.executeQuery();
			while(r.next()){
				list.add(r.getString(1));
			}
			return list;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public void makePayment(String username, double money) {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		//ResultSet r = null;
		//List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("update accounts set debt = debt - ? where username = ?");
			p.setDouble(1, money);
			p.setString(2, username);
			p.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				//r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void makeDeposit(String username, double money) {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		//ResultSet r = null;
		//List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("update accounts set deposit = deposit + ? where username = ?");
			p.setDouble(1, money);
			p.setString(2, username);
			p.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				//r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void grantLoan(String username) {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		//ResultSet r = null;
		//List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("update accounts set debt = debt + loan, loan = 0 where username = ?");
			//p.setDouble(1, money);
			p.setString(1, username);
			p.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				//r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void denyLoan(String username) {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		//ResultSet r = null;
		//List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("update accounts set loan = 0 where username = ?");
			//p.setDouble(1, money);
			p.setString(1, username);
			p.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				//r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void blockUser(String username) {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		//ResultSet r = null;
		//List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("update users set blocked = 1 where username = ?");
			//p.setDouble(1, money);
			p.setString(1, username);
			p.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				//r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void unblockUser(String username) {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		//ResultSet r = null;
		//List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("update users set blocked = 0 where username = ?");
			//p.setDouble(1, money);
			p.setString(1, username);
			p.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				//r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void createUser(String username, String password) {
		// TODO Auto-generated method stub
		PreparedStatement p = null;
		//ResultSet r = null;
		//List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("insert into users values(?,?,0,0)");
			//p.setDouble(1, money);
			p.setString(1, username);
			p.setString(2, password);
			p.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				//r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void approveAccount(String username){
		PreparedStatement p = null;
		//ResultSet r = null;
		//List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("insert into accounts values(?,0,0,0)");
			//p.setDouble(1, money);
			p.setString(1, username);
			p.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				//r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void makeAdmin(String username){
		PreparedStatement p = null;
		//ResultSet r = null;
		//List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("update users set promoted = 1 where username = ?");
			//p.setDouble(1, money);
			p.setString(1, username);
			p.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				//r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void makeWithdrawal(String username, double money){
		PreparedStatement p = null;
		//ResultSet r = null;
		//List<String> list = new ArrayList<>();
		try(Connection conn = ConnectorUtil.getConnected()){
			p = conn.prepareStatement("update accounts set deposit = deposit - ? where username = ?");
			p.setDouble(1, money);
			p.setString(2, username);
			p.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				//r.close();
				p.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
