package com.revature.dao;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public Employee getEmployeeByUsername(String username) {
		Employee emp = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		final String sql = "SELECT * FROM EMPLOYEE WHERE EMP_USERNAME = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try(Connection conn = ConnectionUtil.getConnection()){

			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();

			while (rs.next()) {
				emp = new Employee(rs.getString("emp_username"), rs.getInt("emp_password"), rs.getString("emp_fname"), 
						rs.getString("emp_lname"), rs.getString("emp_phonenumber"), Title.getTitle(rs.getString("emp_title")),
						rs.getString("emp_address"), rs.getString("zipcode"), rs.getString("city"),  
						rs.getString("state"));

				return emp;
			}

		} catch(SQLException e){
			e.printStackTrace();
			return null;
		} finally{
			close(ps);
		}

		return null;
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
	public boolean insertRequest(Request req) throws FileNotFoundException {
		System.out.println("insertRequest");
		PreparedStatement ps = null;

		final String sql = "INSERT INTO REQUEST (EMP_USERNAME, REQ_EVENTNAME, REQ_EVENTDATE, "
				+ 		   "REQ_LOCATION, REQ_COST, REQ_DESCRIPTION, REQ_GRADINGFORMAT, REQ_FILES"
				+ ", REQ_SUBMISSIONDATE) " + 
				"VALUES(?,?,?,?,?,?,?,?,?)";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try(Connection conn = ConnectionUtil.getConnection()){

			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getUsername().toLowerCase());
			ps.setString(2, req.getEvent());
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(9, date);
			req.setSubmissionDate(date);
			ps.setString(4, req.getLocation());
			ps.setDouble(5, req.getCost());
			ps.setString(6, req.getDescription());
			ps.setString(7, req.getGradingFormat());
			ps.setBlob(8, req.getInputStream());
			ps.setDate(3, req.getDateOfEvent());

			ps.executeUpdate();

			return true;
		} catch(SQLException e){
			e.printStackTrace();
			return false;
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

		try(Connection conn = ConnectionUtil.getConnection()) {

			ps = conn.prepareStatement(sql);
			ps.setString(1, username.toLowerCase());
			rs = ps.executeQuery();

			while (rs.next()) {
				final int id = rs.getInt("REQ_ID");
				final String eventName = rs.getString("REQ_EVENTNAME");
				final Date eventDate = rs.getDate("REQ_EVENTDATE");
				final Date submissionDate = rs.getDate("REQ_SUBMISSIONDATE");
				final String eventLocation = rs.getString("REQ_LOCATION");
				final int eventCost = rs.getInt("REQ_COST");
				final String gradingFormat = rs.getString("REQ_GRADINGFORMAT");
				final String eventDescription = rs.getString("REQ_DESCRIPTION");

				final Blob data = rs.getBlob("REQ_FILES");
				final InputStream reqFile = data.getBinaryStream();

				final Request req = new Request(id, username, eventName, eventLocation,
						eventDescription, eventCost, gradingFormat, submissionDate, eventDate, reqFile);
				System.out.println("pulling this request from the DB: " + req);
				requests.add(req);
			}

			return requests;
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("an error?");
			return null;
		} finally {
			close(ps);
		}
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