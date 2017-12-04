package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.revature.beans.Application;
import com.revature.beans.Employee;
import com.revature.servlets.util.ConnectionUtil;
import static com.revature.servlets.util.CloseStreams.close;

public class MyDao {
	public void insertNewApplication(Application a){
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO Applications (Applications_empID, Applications_appID, Applications_empFirst, Applications_empLast, Applications_moment, Applications_locus, Applications_description, Applications_grading, Applications_eventType, Applications_justification)" +
					"VALUES(?,?,?,?,?,?,?,?,?,?)";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection()){
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getEmpid());
			ps.setInt(2, a.getAppID());
			ps.setDouble(3, a.getExpense());
			ps.setInt(4, a.getEventType());
			ps.setString(5, a.getDescription());
			ps.setString(6, a.getJustification());
			ps.setString(7, a.getEmpFirst());
			ps.setString(8, a.getEmpLast());
			ps.setString(9, a.getLocus());
			ps.setString(10, a.getEmail());
			
			
			int affected = ps.executeUpdate();
			
			System.out.println(affected + " ROWS INSERTED");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
	}
	
	public void insertNewEmployee(Employee e){
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO Employees (Employees_empID, Employees_appID, Employees_empFirst, Employees_empLast, Employees_moment, Employees_locus, Employees_description, Employees_grading, Employees_eventType, Employees_justification)" +
					"VALUES(?,?,?,?,?,?,?,?,?,?)";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection()){
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, e.getEmpID());
			ps.setInt(2, e.getDeptNum());
			ps.setDouble(3, e.getStatus());
			ps.setString(4, e.getUsername());
			ps.setString(5, e.getPassword());
			ps.setString(6, e.getEmail());
			ps.setString(7, e.getFname());
			ps.setString(8, e.getLname());

			
			int affected = ps.executeUpdate();
			
			System.out.println(affected + " ROWS INSERTED");
		}catch(SQLException a){
			a.printStackTrace();
		}finally{
			close(ps);
		}
	}
	
	public Application selectApplicationByAppID(Integer appID){
		Application a = null;
		String sql = "SELECT * FROM Applications WHERE Applications_appID = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection()){
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, appID);
			rs = ps.executeQuery();
			
			while(rs.next()){
				a = new Application(
							rs.getInt(1),
							rs.getInt(2),
							rs.getDouble(3),
							rs.getInt(4),
							rs.getString(5),
							rs.getString(6),
							rs.getString(7),
							rs.getString(8),
							rs.getString(9),
							rs.getString(10)
						);
			}

			
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
		
		return a;
	}
	public Employee selectEmployeeByUsername(String username){
		Employee e = null;
		String sql = "SELECT * FROM Employees WHERE Applications_username = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection()){
			
			ps = conn.prepareStatement(sql);
			ps.setString(4, username);
			rs = ps.executeQuery();
			
			while(rs.next()){
				e = new Employee(
							rs.getInt(1),
							rs.getInt(2),
							rs.getInt(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getString(7),
							rs.getString(8)
						);
			}

//			this.empID = empID;
//			this.deptNum = deptNum;
//			this.status = status;
//			this.username = username;
//			this.password = password;
//			this.email = email;
//			this.fname = fname;
//			this.lname = lname;
			
		}catch(SQLException a){
			a.printStackTrace();
		}finally{
			close(ps);
		}
		
		return e;
	}
	public Employee selectEmployeeByIDNum(int empID){
		Employee e = null;
		String sql = "SELECT * FROM Employees WHERE Employees_empID = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection()){
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empID);
			rs = ps.executeQuery();
			
			while(rs.next()){
				e = new Employee(
							rs.getInt(1),
							rs.getInt(2),
							rs.getInt(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getString(7),
							rs.getString(8)
						);
			}

//			this.empID = empID;
//			this.deptNum = deptNum;
//			this.status = status;
//			this.username = username;
//			this.password = password;
//			this.email = email;
//			this.fname = fname;
//			this.lname = lname;
			
		}catch(SQLException a){
			a.printStackTrace();
		}finally{
			close(ps);
		}
		
		return e;
	}
	
	public List<Application> returnAllApps(){
		List<Application> applications = new ArrayList<Application>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Application app = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Applications";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				app = new Application();
				app.setAppid(rs.getInt("APPLICATIONS_APPID"));
				// get employee object from userID
				applications.add(app);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return applications;
	}
	public List<Employee> returnAllEmps(){
		List<Employee> employees = new ArrayList<Employee>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee e = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Applications";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				e = new Employee();
				e.setUsername(rs.getString("EMPLOYEES_USERNAME"));
				// get employee object from userID
				employees.add(e);
			}
		} catch (SQLException a) {
			// TODO Auto-generated catch block
			a.printStackTrace();
		}
		return employees;
	}
	public List<Employee> getAllEmployees() {
		List<Employee> ems = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee emp = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Employees";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				emp = new Employee();
				emp.setUsername(rs.getString("EMPLOYEES_USERNAME"));
				// get employee object from userID
				ems.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(ps);
		}
		return ems;
	}

	public List<Application> getAllApplications() {
		List<Application> apps = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Application app = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Application";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				app = new Application();
				app.setAppid(rs.getInt("APPLICATIONS_APPID"));
				// get employee object from userID
				apps.add(app);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(ps);
		}
		return apps;
	}
}
	//maybe make this class the driver??? 
