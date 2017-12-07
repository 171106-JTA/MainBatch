package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.revature.bean.ReimbursementForm;
import com.revature.util.ConnectionUtil;

public class TrmsDaoImplement implements TrmsDao{

	@Override
	public String login(String username, String password) {		
		String permission = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Login WHERE username=? AND password=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next()) {
				username = rs.getString("username");
				password = rs.getString("password");
				permission = rs.getString("permission");
			}
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			 e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return permission;
	}
	
	public void insertNewReimbursementForm(ReimbursementForm newForm) {
		CallableStatement cs1 = null;
		try (Connection conn = ConnectionUtil.getConnection()) {			
			String sql = "{call newReimbursementForm(?, ?, ?, "
												  + "?, ?, ?, " 
												  + "?, ?, ?, "
												  + "?, ?)}";
			String username = newForm.getUsername();
			String [] months =  {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
			String DateAndTime = 
					newForm.getDay() + "-" + 
					months[Integer.parseInt(newForm.getMonth())-1] + "-" +
					newForm.getYear() + " " + 
					newForm.getHour() + ":" +
					newForm.getMinute();
			cs1 = conn.prepareCall(sql);
			cs1.setString(1, username);
			cs1.setString(2, newForm.getStreet());
			cs1.setString(3, newForm.getCity());
			cs1.setString(4, newForm.getState());
			cs1.setString(5, newForm.getZip());
			cs1.setString(6, "-1"); //Ignore apt numbers for now.
			cs1.setString(7, DateAndTime);
			cs1.setString(8, newForm.getDescription());
			cs1.setString(9, newForm.getCost());
			cs1.setString(10, newForm.getGradingFormat());
			cs1.setString(11, newForm.getEventType());
			
			int rows_changed = cs1.executeUpdate();
			if(rows_changed == 0) {
				throw new SQLException("Address Insertion Failed!");
			}
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			 e.printStackTrace();
		} finally {
			if (cs1 != null) {
				try {
					cs1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
