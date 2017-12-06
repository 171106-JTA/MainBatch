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
import p1.revature.beans.DepartmentRole;

public class DepartmentRoleDao {
	/**
	 * Gets all the department roles
	 * @return all the department roles
	 */
	public List<DepartmentRole> getDepartmentRoles()
	{
		Statement stmt = null;
		ResultSet rs   = null;
		
		List<DepartmentRole> deptRoles = new LinkedList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM p1_department_roles";
			stmt = conn.createStatement();
			rs   = stmt.executeQuery(sql);
					
			while (rs.next())
			{
				deptRoles.add(new DepartmentRole(rs.getInt("DEPARTMENT_ROLE_ID"),
						rs.getInt("DEPARTMENT_ID"),
						rs.getString("DEPARTMENT_ROLE_NAME")));
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
		return deptRoles;
	}
	
	/**
	 * fetches the {@code DepartmentRole}s iff they have the specified department  role name
	 * @param departmentRoleName : the name of the DepartmentRole to search by 
	 * @return the {@code DepartmentRole}s that have that name
	 */
	public List<DepartmentRole> getDepartmentRolesByName(String departmentRoleName)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<DepartmentRole> deptRoles = new LinkedList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM p1_department_roles WHERE department_id = ("
					+ "SELECT department_id FROM p1_departments WHERE department_name = ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, departmentRoleName);
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				deptRoles.add(new DepartmentRole(rs.getInt("DEPARTMENT_ROLE_ID"),
						rs.getInt("DEPARTMENT_ID"),
						rs.getString("DEPARTMENT_ROLE_NAME")));
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
		return deptRoles;
	}
	
	/**
	 * Fetches a list of department roles that have a department
	 * @param department : the {@code Department} that the {@code DepartmentRole}s should have in common
	 * @return the list of department roles having that Department in common
	 */
	public List<DepartmentRole> getDepartmentRolesFor(Department department)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<DepartmentRole> deptRoles = new LinkedList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = String.format("SELECT * FROM p1_department_roles WHERE department_id = ?"
					+ " OR department_name = ?");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, department.getDepartmentID());
			ps.setString(2, department.getDepartmentName());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				deptRoles.add(new DepartmentRole(rs.getInt("DEPARTMENT_ROLE_ID"),
						rs.getInt("DEPARTMENT_ID"),
						rs.getString("DEPARTMENT_ROLE_NAME")));
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
		return deptRoles;
	}
	
	public DepartmentRole getDepartmentRole(Department department, String departmentRoleName)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		DepartmentRole deptRole = null;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_department_roles_view WHERE department_name = ? AND "
					+ "department_role_name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, department.getDepartmentName());
			ps.setString(2, departmentRoleName);
			rs = ps.executeQuery();
			if (rs.next())
			{
				deptRole = new DepartmentRole(rs.getInt("DEPARTMENT_ROLE_ID"),
						department.getDepartmentID(),
						departmentRoleName);
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
		return deptRole;
	}
}
