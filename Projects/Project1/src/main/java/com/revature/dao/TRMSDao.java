package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.revature.beans.Employee;
import com.revature.util.ConnectionUtil;

import static com.revature.util.CloseStreams.close;

public class TRMSDao {
	private static TRMSDao dao;

	private TRMSDao() {}
	/**
	 * 
	 * @param username
	 * @return
	 */
	public static Employee getEmployeeByUsername(String username) {
		Employee emp = null;

		//TODO search database for an employee with username

		return emp;
	}

	public static TRMSDao getDao() {
		if (dao == null) {
			return new TRMSDao();
		} else {
			return dao;
		}
	}

	/**
	 * 
	 * @param emp
	 */
	public void insertEmployee(Employee emp) {
		PreparedStatement ps = null;

		final String sql = "insert into trainer (emp_username, emp_password, emp_fname, "
				+ 		   "emp_lname, emp_address, emp_zipcode, emp_city, emp_state)" + 
				"VALUES(?,?,?,?,?,?,?,?)";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try(Connection conn = ConnectionUtil.getConnection()){

			ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getUsername().toLowerCase());
			ps.setInt(2, emp.getPassword());
			ps.setString(3, emp.getFname());
			ps.setString(4, emp.getLname().toLowerCase());
			ps.setString(5, emp.getAddress().toLowerCase());
			ps.setString(6, emp.getZipcode().toLowerCase());
			ps.setString(7, emp.getCity().toLowerCase());
			ps.setString(8, emp.getState().toLowerCase());
			ps.setString(9, emp.getTitle().toString());
			
			int affected = ps.executeUpdate();
			
			System.out.println(affected + " Rows affected");

		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			close(ps);
		}
	}
}