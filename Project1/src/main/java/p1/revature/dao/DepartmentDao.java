package p1.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.revature.util.ConnectionUtil;

import p1.revature.beans.Department;

public class DepartmentDao {
	/**
	 * Gets all Departments
	 * @return List of all Departments
	 */
	public List<Department> getAllDepartments()
	{
		ResultSet rs   = null;
		Statement stmt = null;
		
		List<Department> departments = new LinkedList<>();
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_departments";
			stmt = conn.createStatement();
			rs   = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				departments.add(new Department(rs.getInt("DEPARTMENT_ID"),
					rs.getString("DEPARTMENT_NAME")));
			}
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally 
		{
			close(stmt);
			close(rs);
		}
		return departments;
	}
	/**
	 * Tries to get the Department having a specified name
	 * @param departmentName : the name of the Department
	 * @return the Department having that name, if it exists
	 */
	public Department getDepartmentByName(String departmentName)
	{
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		Department dept = null;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_departments WHERE department_name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, departmentName);
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				dept = new Department(rs.getInt("DEPARTMENT_ID"),
						rs.getString("DEPARTMENT_NAME"));
			}
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally 
		{
			close(ps);
			close(rs);
		}
		return dept;
	}
}
