package com.trms.dao;

import static com.trms.util.CloseStreams.close;
import static com.trms.util.ConnectionUtil.getConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.trms.obj.ContactInformation;
import com.trms.obj.Employee;

public class DaoImpl implements Dao {


	public boolean verifyCredentials(String username, String password) {

		return false;
	}

	public boolean loginIdAvailable(String loginUserId) {
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			Class.forName ("oracle.jdbc.OracleDriver");
			Connection conn = getConnection();
			System.out.println("Connection successfully established");
			String sql = "SELECT loginUserId from Employee where loginUserId = ?";
			sql = "select * from employee"; 
			ps = conn.prepareStatement(sql); 
			rs = ps.executeQuery();
			return (rs.getRow() > 0); 
//			ps = conn.prepareStatement(sql); 
//			ps.setString(1, loginUserId);
//			rs = ps.executeQuery();
//			return (rs.getRow() > 0);
		} 
		catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs); 
			close(ps); 
		}



		return true;//return true on a failure, to bar requesting user from progressing
	}

	@Override
	public boolean emailAvailable(String email) {
		ResultSet rs = null;
		PreparedStatement ps = null;

		try(Connection conn = getConnection()) {
			String sql = "SELECT email FROM Employee E join ContactInformation C "
					+ "on E.contactInformationKey = c.Contact_Index where email = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setString(1, email);
			rs = ps.executeQuery();
			return rs.next(); 			

		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs); 
			close(ps); 
		}

		return true; //return true on a failure, to bar requesting user from progressing
	}

	public boolean insertEmployee(Employee e) {
		CallableStatement cs = null;
		boolean result = false; 
		ContactInformation c = e.getContactInfo();

		try(Connection conn = getConnection()) {
			String sql = "call Create_Employee_Full(?, ?, ?, ?,"
					+ "?, ?, ?,"
					+ "?, ?, ?, ?, ?, ?)";
			cs = conn.prepareCall(sql); 
			System.out.println(e);
			
			cs.setString(1, e.getFirstName());
			cs.setString(2, e.getLastName());
			cs.setString(3, e.getLoginUserId());
			cs.setString(4, e.getLoginPassword());
			System.out.println(e.getDepartment() + " " + e.getPosition() + " " + e.getSupervisor());
			cs.setString(5, e.getDepartment());
			cs.setString(6, e.getPosition());
			cs.setString(7, e.getSupervisor());
			
			cs.setString(8, c.getEmail());
			cs.setString(9, c.getPhone());
			cs.setString(10, c.getStreetAddr());
			cs.setString(11, c.getCity());
			cs.setString(12, c.getState());
			cs.setString(13, c.getZipCode());
			
			result = cs.execute(); 
			
			return result; 

		}catch(SQLException s) {
			s.printStackTrace();
		}
		finally {
			close(cs); 
		}		
		return false; 
 
	}



}
