package p1.revature.beans;

public class DepartmentRole extends Bean {
	private int departmentRoleID,
		departmentID;
	private String departmentRoleName;
	
	
	
	public DepartmentRole(int departmentRoleID, int departmentID, String departmentRoleName) {
		super();
		this.departmentRoleID = departmentRoleID;
		this.departmentID = departmentID;
		this.departmentRoleName = departmentRoleName;
	}
	public int getDepartmentRoleID() {
		return departmentRoleID;
	}
	public void setDepartmentRoleID(int departmentRoleID) {
		this.departmentRoleID = departmentRoleID;
	}
	public int getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	public String getDepartmentRoleName() {
		return departmentRoleName;
	}
	public void setDepartmentRoleName(String departmentRoleName) {
		this.departmentRoleName = departmentRoleName;
	}
	
	
}
