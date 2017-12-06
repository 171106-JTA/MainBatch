package com.trms.dao;

import static com.trms.util.CloseStreams.close;
import static com.trms.util.ConnectionUtil.getConnection;

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
		
		try(Connection conn = getConnection()) {
			String sql = "SELECT email FROM Employee E join ContactInformation C "
					+ "on E.contactInformationKey = c.Contact_Index where loginUserId = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setString(1, loginUserId);
			rs = ps.executeQuery();
			return rs.next(); 	
			
		}catch(SQLException e) {
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
	
	public int insertEmployee(Employee e) {
		PreparedStatement ps = null;
		int rowsAffectedIn = 0; 
		int rowsAffectedUp = 0; 
		
		try(Connection conn = getConnection()) {
			String sql = "INSERT INTO Employee("
					+ "firstname, lastname, loginUserId, loginPassword" 
					+ ") Values (" 
					+ "? ? ? ?" 
					+ ");";
			ps = conn.prepareStatement(sql); 
			ps.setString(1, e.getFirstName());
			ps.setString(2, e.getLastName());
			ps.setString(3, e.getLoginUserId());
			ps.setString(4, e.getLoginPassword());
			rowsAffectedIn = ps.executeUpdate();
			System.out.println(rowsAffectedIn + " Rows Inserted in Services.insertEmployee(Employee e)");
			
			
			//update the new contact information record created when employee was added
			ContactInformation c = e.getContactInfo(); 
			sql = "UPDATE ContactInformation set "
					+ "email = ?"
					+ ", phone = ?"
					+ ", altPhone = ?"
					+ ", streetAddress = ?"
					+ ", city = ?" 
					+ ", state = ?"
					+ ", zipCode = ?"
					+ " WHERE contact_index = ("
					+ "SELECT contactInformationKey from Employee where loginUserId = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, c.getEmail());
			ps.setString(2, c.getPhone());
			ps.setString(3, c.getAltPhone());
			ps.setString(4, c.getStreetAddr());
			ps.setString(5, c.getCity());
			ps.setString(6, c.getState());
			ps.setString(7, c.getZipCode());
			
			ps.setString(8, e.getLoginUserId());
			
			rowsAffectedUp = ps.executeUpdate(); 
			System.out.println(rowsAffectedUp + "Rows Updated in Services.insertEmployee(Employee e)");
			
		}catch(SQLException s) {
			s.printStackTrace();
		}
		finally {
			close(ps); 
		}		
		
		return rowsAffectedIn + rowsAffectedUp; 
	}

	
	
}
