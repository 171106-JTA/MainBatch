package p1.revature.beans;

public class DepartmentRoleReadOnly {
	private int _departmentRoleID;
	private String _departmentName, _departmentRoleName;
	public DepartmentRoleReadOnly(int departmentRoleID, String departmentName, String departmentRoleName) {
		super();
		_departmentRoleID = departmentRoleID;
		_departmentName = departmentName;
		_departmentRoleName = departmentRoleName;
	}
	public int getDepartmentRoleID() {
		return _departmentRoleID;
	}
	public void setDepartmentRoleID(int departmentRoleID) {
		this._departmentRoleID = departmentRoleID;
	}
	public String getDepartmentName() {
		return _departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this._departmentName = departmentName;
	}
	public String getDepartmentRoleName() {
		return _departmentRoleName;
	}
	public void setDepartmentRoleName(String departmentRoleName) {
		this._departmentRoleName = departmentRoleName;
	}
	
	
	
	
}
