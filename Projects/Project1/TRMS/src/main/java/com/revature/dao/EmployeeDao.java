package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.beans.Employee;


public interface EmployeeDao {
	
	/**
	 * Insert a row into the employee table.
	 * @param e The employee to insert.
	 * @throws SQLException
	 */
	public void addEmployee(Employee e) throws SQLException;
	
	/**
	 * Retrieve all employees.
	 * @return TODO
	 * @return A list of all employees.
	 * @throws SQLException
	 */
	public List<Employee> getEmployees() throws SQLException;
	
	/**
	 * Retrieve all employees with the specified department ID.
	 * @param departmentId The department to select employees from.
	 * @return The employees in the department.
	 * @throws SQLException
	 */
	public List<Employee> getEmployees(int departmentId) throws SQLException;
	
	/**
	 * Retrieve all employees with the specified department name.
	 * @param department The department to select employees from.
	 * @return The employees in the department.
	 * @throws SQLException
	 */
	public List<Employee> getEmployees(String department) throws SQLException;
	
	/**
	 * Retrieve the employee with the given email.
	 * @param email The employee's email.
	 * @return An employee object.
	 * @throws SQLException
	 */
	public Employee getEmployee(String email) throws SQLException;
	
	/**
	 * Retrieve the the employee with the given ID number.
	 * @param id The employee's ID number.
	 * @return An employee object.
	 * @throws SQLException
	 */
	public Employee getEmployee(int id) throws SQLException;
	
	/**
	 * Get the password of a user with the specified email address. Used
	 * for verifying passwords.
	 * @param email The email of the employee.
	 * @return The hashed password of the specified user.
	 * @throws SQLException
	 */
	public String getEmployeePassword(String email) throws SQLException;
	
	/**
	 * Update an employee with the given email.
	 * @param email The existing employee to update.
	 * @param e The new employee data.
	 * @throws SQLException
	 */
	public void modifyEmployee(String email, Employee e) throws SQLException;
	
	/**
	 * Update an employee with the given ID number.
	 * @param id The existing employee to update.
	 * @param e The new employee data.
	 * @throws SQLException
	 */
	public void modifyEmployee(int id, Employee e) throws SQLException;
	
	/**
	 * Delete the employee with the given email.
	 * @param email The email of the employee to delete.
	 * @throws SQLException
	 */
	public void deleteEmployee(String email) throws SQLException;
	
	/**
	 * Delete the employee with the given ID number.
	 * @param id The ID number of the employee to delete.
	 * @throws SQLException
	 */
	public void deleteEmployee(int id) throws SQLException;

	int availableFunds(Employee emp);
	

}

