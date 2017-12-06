package p1.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import com.revature.util.ConnectionUtil;

import p1.revature.beans.Bean;
import p1.revature.beans.Department;
import p1.revature.beans.Employee;

public class EmployeeDao {
	/**
	 * Adds the specified employee to the employees table
	 * @param emp : the {@code Employee} to add
	 */
	public int createNewEmployee(Employee emp)
	{
		int rowsAffected     = 0;
		PreparedStatement ps = null;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			// if the user does not already exist...
			if (!employeeExists(emp))
			{
				boolean empIDIsNull = emp.getEmployeeID() == Bean.NULL;
				if (empIDIsNull) return createNewEmployeeNoID(emp);
				// ... we create them
				String sql = "INSERT INTO p1_employees(employee_id, employee_first_name, employee_last_name, "
						+ "department_role_id, manager_id, employee_salary, employee_email, employee_pass) VALUES"
						+ " (?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, emp.getEmployeeID());
				ps.setString(2, emp.getFirstName());
				ps.setString(3,emp.getLastName());
				ps.setInt(4, emp.getDepartmentRoleID());
				if (emp.getManagerID() == Bean.NULL)
					ps.setNull(5, Types.NUMERIC);
				else
					ps.setInt(5, emp.getManagerID());
				if (emp.getEmployeeSalary() == Bean.NULL)
					ps.setNull(6, Types.NUMERIC);
				else
					ps.setDouble(6, emp.getEmployeeSalary());
				ps.setString(7, emp.getEmployeeEmail());
				ps.setString(8, emp.getPass());
				rowsAffected = ps.executeUpdate();
			}
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally 
		{
			close(ps);
		}
		return rowsAffected; 
	}
	
	/**
	 * Creates new Employee without ID. 
	 * @param emp : the Employee to create in the database.
	 * @return the number of rows affected
	 */
	public int createNewEmployeeNoID(Employee emp)
	{
		int rowsAffected     = 0;
		PreparedStatement ps = null;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "INSERT INTO p1_employees(employee_first_name, employee_last_name, department_role_id, "
					+ "manager_id, employee_salary, employee_email, employee_pass) VALUES (?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1,emp.getFirstName());
			ps.setString(2,emp.getLastName());
			ps.setInt(3, emp.getDepartmentRoleID());
			if (emp.getManagerID() == Bean.NULL)
				ps.setNull(4, Types.NUMERIC);
			else
				ps.setInt(4, emp.getManagerID());
			if (emp.getEmployeeSalary() == Bean.NULL)
				ps.setNull(5, Types.NUMERIC);
			else
				ps.setDouble(5, emp.getEmployeeSalary());
			ps.setString(6, emp.getEmployeeEmail());
			ps.setString(7, emp.getPass());
			rowsAffected = ps.executeUpdate();
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			close(ps);
		}
		return rowsAffected;
	}
	
	// TODO: either refactor this to fit the stored procedure or change the stored procedure itself 
	public int registerNewEmployee(Employee emp)
	{
		CallableStatement cs = null;
		int rowsAffected = 0;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "{call register_new_user(?,?,?,?,?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, emp.getFirstName());
			cs.setString(2, emp.getLastName());
			cs.setString(3, emp.getDepartmentName());
			cs.setString(4, emp.getDepartmentRoleName());
			cs.setString(5, 
					getManagerByDeptInfo(new Department(emp.getDepartmentName()), 
							emp.getDepartmentRoleName(),
							emp.getManagerName())
					.getManagerName());
			
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			close(cs);
		}
		return rowsAffected;
	}
	
	/**
	 * Checks if the specified Employee is in the system
	 * @param emp : the Employee to verify 
	 * @return whether or not that Employee exists
	 */
	public boolean employeeExists(Employee emp)
	{
		PreparedStatement ps   = null;
		ResultSet rs           = null;
		boolean employeeExists = false;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			// since e-mails are guaranteed unique, we can verify if someone exists in the system by whether or
			//	not their email does
			String sql = "SELECT count(*) FROM p1_employees WHERE employee_email = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getEmployeeEmail());
			rs = ps.executeQuery();
			employeeExists = (rs.next() && rs.getInt(1) > 0);
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally 
		{
			close(rs);
			close(ps);
		}
		return employeeExists;
	}
	
	/**
	 * Checks the Employee for valid login credentials
	 * @param emp : The Employee trying to login. Must have at least username,password
	 * @return whether or not there is Employee with these credentials currently in the system
	 */
	public boolean validLoginCredentials(Employee emp)
	{
		CallableStatement cs   = null;
		ResultSet rs           = null;
		boolean employeeExists = false;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			// since e-mails are guaranteed unique, we can verify if someone exists in the system by whether or
			//	not their email does
			String sql = "{? = call p1_authenticate_user(?,?)}";
			cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, Types.NUMERIC);
			cs.setString(2, emp.getEmployeeEmail());
			cs.setString(3, emp.getPass());
			cs.execute();
			employeeExists = (cs.getInt(1) > 0);
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally 
		{
			close(rs);
			close(cs);
		}
		return employeeExists;

	}
	
	/**
	 * Gets the manager of the specified Employee
	 * @param emp : the Employee
	 * @return the Employee that manages {@code emp}
	 */
	public Employee getManagerOf(Employee emp)
	{
		PreparedStatement ps = null;
		ResultSet rs         = null;
		Employee manager     = null;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_employees WHERE manager_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, emp.getEmployeeID());
			while (rs.next())
			{
				manager = new Employee(rs.getInt("EMPLOYEE_ID"), 
						rs.getInt("MANAGER_ID"), 
						rs.getString("EMPLOYEE_FIRST_NAME"), 
						rs.getString("EMPLOYEE_LAST_NAME"),
						rs.getInt("DEPARTMENT_ROLE_ID"),
						rs.getDouble("EMPLOYEE_SALARY"),
						rs.getInt("EMPLOYEE_HOME_LOCATION_ID"),
						rs.getString("EMPLOYEE_EMAIL"),
						rs.getString("EMPLOYEE_PASS"),
						false);
			}
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally 
		{
			close(rs);
		}
		return manager; 
	}
	
	/**
	 * Sets the manager of employee to manager, by their e-mail addresses (or id first, if avaiilable).
	 * @param emp : The employee to set manager of 
	 * @param manager : The new manager of {@code emp}
	 * @return the number of rows affected
	 */
	public int setManagerOf(Employee emp, Employee manager)
	{
		PreparedStatement ps = null;
		int rowsAffected = 0;

		boolean empIDNull     = emp.getEmployeeID() == Bean.NULL,
				managerIDNull = manager.getEmployeeID() == Bean.NULL;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = String.format("UPDATE p1_employees SET manager_id = ? WHERE %s = ?", 
				(empIDNull) ? "employee_email" : "employee_id");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, getEmployeeID(manager));
			if (empIDNull)
				ps.setString(2, emp.getEmployeeEmail());
			else
				ps.setInt(2, emp.getEmployeeID());
			
			rowsAffected = ps.executeUpdate();
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			close(ps);
		}
		return rowsAffected;
	}
	
	
	/**
	 * Determines whether or not this Employee is a manager
	 * @param emp : the Employee to check 
	 * @return whether or not this Employee is a manager
	 */
	public boolean isManager(Employee emp)
	{
		CallableStatement cs = null;
		ResultSet rs         = null;
		boolean empIsManager = false;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "{? = call user_is_manager(?)}";
			cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, Types.NUMERIC);
			cs.setInt(2, emp.getEmployeeID());
			rs = cs.executeQuery();
			
			empIsManager = (rs.next() && rs.getInt(1) > 0);
		}
		
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally  
		
		{
			close(rs);
			close(cs);
		}
		return empIsManager;
	}
	
	public List<Employee> getManagersByDepartment(Department dept)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Employee> managers = new LinkedList<>();
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			// TODO: Fix this view.
			String sql = "SELECT manager_name FROM p1_department_managers_view WHERE department_name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, dept.getDepartmentName());
			rs = ps.executeQuery();
			while (rs.next())
			{
				managers.add(new Employee(rs.getInt("MANAGER_ID"),
						rs.getString("MANAGER_NAME")));
			}
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally 
		{
			close(rs);
			close(ps);
		}
		return managers;		
	}
	
	public Employee getManagerByDeptInfo(Department dept, String deptRole, String managerName)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee manager = null;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			// TODO: make this a stored procedure...
			String sql = "SELECT manager_id,manager_name FROM p1_department_managers_view "
					+ "WHERE department_name = ? AND department_role_name = ? AND manager_name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, dept.getDepartmentName());
			ps.setString(2, deptRole);
			ps.setString(3, managerName);
			rs = ps.executeQuery();
			if (rs.next())
			{
				manager = new Employee(rs.getInt("MANAGER_ID"),
						rs.getString("MANAGER_NAME"));
			}
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			close(rs);
			close(ps);
		}
		
		return manager;
	}
	
	/** 
	 * Returns the EmployeeID of the Employee/fetches it from the server
	 * @param emp : the Employee whose ID we want
	 * @return the ID, or {@code Bean.NULL} if it can't be fetched 
	 */
	public int getEmployeeID(Employee emp)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		// employee id should already be a member of emp. Let's get that.
		int employeeID = emp.getEmployeeID();
		// if managerID is not NULL, then we're done
		if (employeeID != Bean.NULL) return employeeID;
		// otherwise, we get it via SQL query
		try (Connection conn = ConnectionUtil.getConnection())
		{
			// based on the employee's email address
			String sql = "SELECT employee_id FROM p1_employees WHERE employee_email = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getEmployeeEmail());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				employeeID = rs.getInt("EMPLOYEE_ID");
			}
		}
		catch(SQLException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			close(ps);
			close(rs);
		}
		return employeeID;
	}
	
	/**
	 * Gets the id of the manager of the Employee
	 * @param emp : the Employee whose {@code manager_id} we're trying to retrieve
	 * @return the id of the manager, or Bean.NULL if the employee doesn't have one
	 */
	public int getManagerID(Employee emp) 
	{ 
		PreparedStatement ps = null;
		ResultSet rs = null;
		// manager id should already be a member of emp. Let's get that.
		int managerID = emp.getManagerID();
		// if managerID is not NULL, then we're done
		if (managerID != Bean.NULL) return managerID;
		// otherwise, we get it via SQL query
		try (Connection conn = ConnectionUtil.getConnection())
		{
			// based on the employee's email address
			String sql = "SELECT manager_id FROM p1_employees WHERE employee_email = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getEmployeeEmail());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				managerID = rs.getInt("MANAGER_ID");
			}
		}
		catch(SQLException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			close(ps);
			close(rs);
		}
		return managerID;
	}
}
