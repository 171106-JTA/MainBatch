package com.revature.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.revature.beans.Employee;
import com.revature.beans.Request;
import com.revature.beans.Title;
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
	public boolean insertEmployee(Employee emp) {
		PreparedStatement ps = null;

		final String sql = "INSERT INTO EMPLOYEE (emp_username, emp_password, emp_fname, "
				+ 		   "emp_lname, emp_address, emp_zipcode, emp_city, emp_state, emp_title, "
				+ "emp_date, emp_phonenumber)" + 
				"VALUES(?,?,?,?,?,?,?,?,?,?,?)";

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
			ps.setString(11, emp.getPhoneNumber().toLowerCase());
			ps.setString(5, emp.getAddress().toLowerCase());
			ps.setString(6, emp.getZipcode().toLowerCase());
			ps.setString(7, emp.getCity().toLowerCase());
			ps.setString(8, emp.getState().toLowerCase());
			ps.setString(9, emp.getTitle().toString());

			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(10, date);

			ps.executeUpdate();

		} catch(SQLException e){
			e.printStackTrace();
			return false;
		} finally{
			close(ps);
		}
		return true;
	}

	public Employee validateLogin(String username, int password) {
		Employee emp = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		final String sql = "SELECT emp_username, emp_password, emp_title, emp_fname, emp_lname FROM EMPLOYEE";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try(Connection conn = ConnectionUtil.getConnection()){

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				String e_username = rs.getString("emp_username");
				int e_password = rs.getInt("emp_password");
				String title = rs.getString("emp_title");
				String fname = rs.getString("emp_fname");
				String lname = rs.getString("emp_lname");
				emp = new Employee(e_username, fname, lname, title);

				if (e_username.toLowerCase().equals(username.toLowerCase()) && e_password == password) {
					return emp;
				}
			}

		} catch(SQLException e){
			e.printStackTrace();
			return null;
		} finally{
			close(ps);
		}

		return null;
	}

	/**
	 * 
	 * @param req
	 * @throws FileNotFoundException 
	 */
	public void insertRequest(Request req) throws FileNotFoundException {
		PreparedStatement ps = null;

		final String sql = "INSERT INTO REQUEST (emp_username, REQ_EVENT, REQ_DATE, "
				+ 		   "REQ_LOCATION, REQ_COST, REQ_DESCRIPTION, REQ_GRADINGFORMAT, REQ_FILES" + 
				"VALUES(?,?,?,?,?,?,?,?)";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try(Connection conn = ConnectionUtil.getConnection()){

			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getUsername().toLowerCase());
			ps.setString(2, req.getEvent());

			ps.setString(4, req.getLocation());
			ps.setInt(5, req.getCost());
			ps.setString(6, req.getDescription());
			ps.setString(7, req.getGradingFormat());

			File file = req.getFile();

			if (file != null) {
				FileInputStream  fis = new FileInputStream(file);
				ps.setBinaryStream(8, fis, (int) file.getTotalSpace());
			} else {
				ps.setBinaryStream(8, null);
			}

			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(3, date);
			req.setSubmissionDate(date);
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			close(ps);
		}
	}
	public List<Request> getRequests(String username) {
		List<Request> requests = new ArrayList<Request>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		final String sql = "SELECT * FROM REQUEST WHERE EMP_USERNAME = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try(Connection conn = ConnectionUtil.getConnection()){

			ps = conn.prepareStatement(sql);
			ps.setString(1, username.toLowerCase());
			rs = ps.executeQuery();

			while (rs.next()) {
				String eventName = rs.getString("REQ_EVENT");
				Date eventDate = rs.getDate("REQ_DATE");
				String eventLocation = rs.getString("REQ_LOCATION");
				int eventCost = rs.getInt("REQ_COST");
				String gradingFormat = rs.getString("REQ_GRADINGFORMAT");
				
				//TODO FILES
				
				
			}

		} catch(SQLException e){
			e.printStackTrace();
			return null;
		} finally{
			close(ps);
		}

		return null;
	}

	public List<Employee> getUnverifiedEmployees() {
		List<Employee> unvEmps = new ArrayList<Employee>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		final String sql = "SELECT * FROM EMPLOYEE WHERE EMP_TITLE = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try(Connection conn = ConnectionUtil.getConnection()){

			ps = conn.prepareStatement(sql);
			ps.setString(1, Title.UNVERIFIED.toString().toUpperCase());

			rs = ps.executeQuery();

			while (rs.next()) {

				String username = rs.getString(1);
				String fname = rs.getString(3);
				String lname = rs.getString(4);

				unvEmps.add(new Employee(username, fname, lname));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			close(ps);
		}

		return unvEmps;
	}
	
	public List<Employee> getDirectSupervisors() {
		List<Employee> unvEmps = new ArrayList<Employee>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		final String sql = "SELECT * FROM EMPLOYEE WHERE EMP_TITLE = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try(Connection conn = ConnectionUtil.getConnection()){

			ps = conn.prepareStatement(sql);
			ps.setString(1, Title.DIRECT_SUPERVISOR.toString().toUpperCase());

			rs = ps.executeQuery();

			while (rs.next()) {

				String username = rs.getString(1);
				String fname = rs.getString(3);
				String lname = rs.getString(4);

				unvEmps.add(new Employee(username, fname, lname));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			close(ps);
		}

		return unvEmps;
	}
	
	/**
	 * 
	 * @param empUsername
	 * @param diSupUsername
	 */
	public void assignDirectSupervisor(final String empUsername, final String dirSupUsername) {
		PreparedStatement ps = null;

		final String sql = "UPDATE EMPLOYEE "
				+ "SET EMP_SUPERVISOR = lower(?), "
				+ "EMP_TITLE = ? "
				+ "WHERE lower(EMP_USERNAME) = ?";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try(Connection conn = ConnectionUtil.getConnection()){

			ps = conn.prepareStatement(sql);
			String dirSup = dirSupUsername.toLowerCase();
			String title = Title.EMPLOYEE.toString().toUpperCase();
			System.out.println("dirSup: " + dirSup + " title: " + title + " empUsername: " + empUsername.toLowerCase());
			
			ps.setString(1, dirSup);
			ps.setString(2, title);
			ps.setString(3, empUsername.toLowerCase());
			int affected = ps.executeUpdate();
			
			System.out.println("rows affected from adding a supervisor to employee " + empUsername.toLowerCase() + " is " + affected);

		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			close(ps);
		}
	}
}