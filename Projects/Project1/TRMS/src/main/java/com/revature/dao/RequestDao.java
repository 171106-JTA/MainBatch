package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.beans.Request;

public interface RequestDao {

	/**
	 * Insert a row into the employee table.
	 * @param e The employee to insert.
	 * @throws SQLException
	 */
	public void addRequest(Request r) throws SQLException;

	/**
	 * Retrieve all request.
	 * @return TODO
	 * @return A list of all requests.
	 * @throws SQLException
	 */
	public List<Request> getRequests() throws SQLException;
	
	/**
	 * Retrieve all requests made by an employee.
	 * @param employeeId of the employee to pull requests from
	 * @return A list of all requests by said employee.
	 * @throws SQLException
	 */
	public List<Request> getRequests(int employeeId) throws SQLException;
	
	/**
	 * Retrieve all requests made by direct supervisees of an employee.
	 * @param employeeId of the employee to pull requests from
	 * @return A list of all requests by said employee.
	 * @throws SQLException
	 */
	public List<Request> getSubordinateRequests(int employeeId) throws SQLException;
	
	/**
	 * Retrieve request with the specified ID.
	 * @param id The id of the desired request.
	 * @return The request with that id.
	 * @throws SQLException
	 */
	public Request getRequest(int id) throws SQLException;
	
	/**
	 * Update an employee with the given email.
	 * @param email The existing employee to update.
	 * @param e The new employee data.
	 * @throws SQLException
	 */
	public void modifyRequest(int id, Request r) throws SQLException;
	
	/**
	 * Delete the request with the given ID number.
	 * @param id The ID number of the request to delete.
	 * @throws SQLException
	 */
	public void deleteRequest(int id) throws SQLException;

	public void modifyRequestStatus(int id, int s) throws SQLException;

	List<Request> getDepartmentRequests(int departmentId) throws SQLException;
	
}