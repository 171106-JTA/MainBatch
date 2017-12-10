package com.revature.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.logging.LoggingService;
import com.revature.util.ConnectionUtil;
import com.revature.beans.Request;

import oracle.jdbc.OracleTypes;


public class RequestDaoImpl {

ConnectionUtil cUtil;
	
	public RequestDaoImpl() {
		cUtil = ConnectionUtil.getInstance();
	}
		
	public void addRequest(Request r) throws SQLException {
		// TODO Auto-generated method stub

		if(r == null) {
			LoggingService.getLogger().debug("empty employee passed to "
					+ "EmployeeDAOImpl.addEmployee()");
			return;
		}

		String sql = "{call insert_request(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			CallableStatement call = conn.prepareCall(sql);
			
			System.out.println(r.getStreetAddress() + " " + r.getCity()+ " " + r.getState() + " " +r.getZip());
			
			call.setInt(1, r.getEmployeeId());
			call.setDouble(2, r.getCost());
			call.setDate(3, new Date(1));
			call.setString(4, r.getStreetAddress());
			call.setString(5, r.getCity());
			call.setString(6, r.getState());
			call.setString(7, r.getZip());
			call.setString(8, r.getDescription());
			call.setInt(9, r.getEventType());
			call.setInt(10, r.getGradingFormat());
			call.setInt(11, r.getDaysMissed());
			call.setString(12, r.getJustification());
			call.setInt(13, 0);
			
			call.executeUpdate();
			LoggingService.getLogger().info("Employee " + r.getEmployeeId() + 
					" request for " + r.getCost() + " added to requests");
		}
	}

	
	public List<Request> getRequests() throws SQLException {
		System.out.println("in RequestDAO at get Results");
		String sql = "{call read_all_requests(?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			
			CallableStatement call = conn.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.CURSOR);
			call.executeQuery();
			
			ResultSet rs = (ResultSet)call.getObject(1);
			
			ArrayList<Request> requests = new ArrayList<Request>();
			
			while(rs.next()) {
				Request req = requestFromResultSet(rs);
				requests.add(req);
			}
			return requests;
		}
		
	}
	
	

	public List<Request> getRequests(int employeeId) throws SQLException {

		String sql = "{call read_requests_by_employee(?,?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			
			CallableStatement call = conn.prepareCall(sql);
			call.setInt(1, employeeId);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.executeQuery();
			
			ResultSet rs = (ResultSet)call.getObject(2);
			
			ArrayList<Request> requests = new ArrayList<Request>();
			
			while(rs.next()) {
				Request req = requestFromResultSet(rs);
				requests.add(req);
			}
			return requests;
		}
		
	}


	public List<Request> getSubordinateRequests(int employeeId) throws SQLException {
		
		String sql = "{call read_sub_requests_by_employee(?,?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			
			CallableStatement call = conn.prepareCall(sql);
			call.setInt(1, employeeId);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.executeQuery();
			
			ResultSet rs = (ResultSet)call.getObject(2);
			
			ArrayList<Request> requests = new ArrayList<Request>();
			
			while(rs.next()) {
				Request req = requestFromResultSet(rs);
				requests.add(req);
			}
			System.out.println("Sub list: " + requests.toString());
			return requests;
		}
	}


	public List<Request> getDepartmentRequests(int departmentId) throws SQLException {
		
		String sql = "{call read_sub_requests_by_dept(?,?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			
			CallableStatement call = conn.prepareCall(sql);
			call.setInt(1, departmentId);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.executeQuery();
			
			ResultSet rs = (ResultSet)call.getObject(2);
			
			ArrayList<Request> requests = new ArrayList<Request>();
			
			while(rs.next()) {
				Request req = requestFromResultSet(rs);
				requests.add(req);
			}
			return requests;
		}
	}


	public Request getRequest(int id) throws SQLException {

		String sql = "{call read_request_by_id(?,?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			
			CallableStatement call = conn.prepareCall(sql);
			call.setInt(1, id);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.executeQuery();
			
			ResultSet rs = (ResultSet)call.getObject(2);
			
			Request req = null;
			if(rs.next()) {
				req = requestFromResultSet(rs);
			}
			
			return req;
		}
	}


	public void modifyRequestJustification(int id, Request r) throws SQLException {

		String sql = "{call update_request_with_id(?,?,?,?,?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			CallableStatement call = conn.prepareCall(sql);
			call.setInt(1, r.getId());
			call.setInt(2, r.getEmployeeId());
			call.setString(3, r.getJustification());
			call.setInt(4, r.getStatus());
			call.setDouble(5,  r.getCost());
			
			call.executeUpdate();
			LoggingService.getLogger().info("Request " + r.getId() + " ($" + r.getCost() + ") updated");
		}

	}

	public void modifyRequest(int id, Request r) throws SQLException {

		String sql = "{call update_request(?,?,?,?,?,?,?,?,?,?,?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			CallableStatement call = conn.prepareCall(sql);
			call.setInt(1, r.getId());
			call.setDouble(2, r.getCost());
			call.setString(3, r.getStreetAddress());
			call.setString(4, r.getCity());
			call.setString(5, r.getState());
			call.setString(6, r.getZip());
			call.setString(7, r.getDescription());
			call.setInt(8, r.getEventType());
			call.setInt(9, r.getGradingFormat());
			call.setInt(10, r.getDaysMissed());
			call.setString(11, r.getJustification());
			
			call.executeUpdate();
			LoggingService.getLogger().info("Request " + r.getId() + " ($" + r.getCost() + ") updated");
		}

	}


	public void modifyRequestStatus(int id, int s) throws SQLException {

		String sql = "{call update_request_status(?,?)}";
		
		try(Connection conn = cUtil.getConnection()) {
			CallableStatement call = conn.prepareCall(sql);
			
			call.setInt(1, id);
			call.setInt(2, s);
			
			call.executeUpdate();
			LoggingService.getLogger().info("Request " + id + " status updated to " + s);
		}

	}


	public void deleteRequest(int id) throws SQLException {
		
		String sql = "{call delete_request_with_id(?)}";
		
		try (Connection conn = cUtil.getConnection()){
			CallableStatement call = conn.prepareCall(sql);
			call.setInt(1, id);
			call.executeUpdate();
			LoggingService.getLogger().info("Deleted request " + id);
		}

	}
	
	private Request requestFromResultSet(ResultSet rs) throws SQLException {
		System.out.println(rs.toString());
		
		Request req = new Request();
		req.setId(rs.getInt(1));
		req.setEmployeeId(rs.getInt(2));
		req.setCost(rs.getDouble(6));
		req.setStatus(rs.getInt(8));
		req.setStreetAddress(rs.getString("address_street_address"));
		req.setCity(rs.getString("address_city"));
		req.setState(rs.getString("address_state"));
		req.setZip(rs.getString("address_zip"));
		req.setDescription(rs.getString(13));
		req.setEventType(rs.getInt(14));
		req.setGradingFormat(rs.getInt(16));
		req.setDaysMissed(rs.getInt(18));
		req.setJustification(rs.getString(19));
		return req;
	}
	
	
}
