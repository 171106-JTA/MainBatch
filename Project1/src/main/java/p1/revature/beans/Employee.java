package p1.revature.beans;

public class Employee extends Bean{
	private int employeeID, managerID; 
	private String firstName, lastName;
	private int departmentRoleID;
	private double employeeSalary;
	private int homeLocationID;
	private String employeeEmail, pass;
	private boolean isLockedOut;
	// additional members, for convenience
	private String managerName;
	private String departmentName, departmentRoleName;
	/**
	 * The constructor for the Employee bean
	 * @param employeeID : the id of the Employee
	 * @param managerID : the id of the Employee's manager
	 * @param firstName : the first name of the Employee
	 * @param lastName : the last name of the Employee
	 * @param departmentRoleID : the id of the Employee's department role
	 * @param employeeSalary : the Employee's salary
	 * @param homeLocationID : the id of the Employee's home location
	 * @param employeeEmail : the employee's email (aka user name)
	 * @param pass : the employee's password
	 * @param isLockedOut : whether or not the user is locked out
	 */
	public Employee(int employeeID, int managerID, String firstName, String lastName, int departmentRoleID,
			double employeeSalary, int homeLocationID, String employeeEmail, String pass, boolean isLockedOut) {
		super();
		this.employeeID = employeeID;
		this.managerID = managerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.departmentRoleID = departmentRoleID;
		this.employeeSalary = employeeSalary;
		this.homeLocationID = homeLocationID;
		this.employeeEmail = employeeEmail;
		this.pass = pass;
		this.isLockedOut = isLockedOut;
	}
	/**
	 * The most detailed Employee constructor, minus employeeID,isLockedOut parameters
	 * @param managerID : the id of the Employee's manager
	 * @param firstName : the first name of the Employee
	 * @param lastName : the last name of the Employee
	 * @param departmentRoleID : the id of the Employee's department role
	 * @param employeeSalary : the Employee's salary
	 * @param homeLocationID : the id of the Employee's home location
	 * @param employeeEmail : the employee's email (aka user name)
	 * @param pass : the employee's password
	 */
	public Employee(int managerID, String firstName, String lastName, int departmentRoleID,
			double employeeSalary, int homeLocationID, String employeeEmail, String pass)
	{
		this(NULL,
				managerID,
				firstName,
				lastName,
				departmentRoleID,
				employeeSalary, 
				homeLocationID,
				employeeEmail, 
				pass, 
				false);
	}
	/**
	 * 
	 * @param employeeID : the id of the Employee
	 * @param managerID : the id of the Employee's manager
	 * @param name : the full name of this Employee
	 * @param departmentRoleID : the id of the Employee's department role
	 * @param employeeSalary : the Employee's salary
	 * @param homeLocationID : the id of the Employee's home location
	 * @param employeeEmail : the employee's email (aka user name)
	 * @param pass : the employee's password
	 * @param isLockedOut : whether or not the user is locked out
	 */
	public Employee(int employeeID, int managerID, String name, int departmentRoleID,
			double employeeSalary, int homeLocationID, String employeeEmail, String pass, boolean isLockedOut)
	{
		this(employeeID, 
				managerID, 
				name.substring(0, name.indexOf(' ')),
				name.substring(name.indexOf(' ') + 1, name.length()),
				departmentRoleID,
				employeeSalary,
				homeLocationID,
				employeeEmail,
				pass, 
				isLockedOut);
	}
	/**
	 * A minimalist constructor for Employee
	 * @param employeeID : the id of the Employee
	 * @param name : the full name of this Employee
	 * @param departmentRoleID : the id of the Employee's department role
	 */
	public Employee(int employeeID, String name, int departmentRoleID)
	{
		this(employeeID, 
				NULL,
				name,
				departmentRoleID,
				NULL,
				NULL,
				"",
				"",
				false);
	}
	public Employee(int employeeID, String name)
	{
		this(employeeID, name, NULL);
	}
	
	/**
	 * Creates Employee object for user validation
	 * @param email : the Employee's username
	 * @param pass  : the Employee's password
	 */
	public Employee(String email, String pass)
	{
		this(NULL,
			"",
			"",
			NULL,
			NULL,
			NULL,
			email, 
			pass);
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public int getManagerID() {
		return managerID;
	}
	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getDepartmentRoleID() {
		return departmentRoleID;
	}
	public void setDepartmentRoleID(int departmentRoleID) {
		this.departmentRoleID = departmentRoleID;
	}
	public double getEmployeeSalary() {
		return employeeSalary;
	}
	public void setEmployeeSalary(double employeeSalary) {
		this.employeeSalary = employeeSalary;
	}
	public int getHomeLocationID() {
		return homeLocationID;
	}
	public void setHomeLocationID(int homeLocationID) {
		this.homeLocationID = homeLocationID;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public boolean isLockedOut() {
		return isLockedOut;
	}
	public void setLockedOut(boolean isLockedOut) {
		this.isLockedOut = isLockedOut;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentRoleName() {
		return departmentRoleName;
	}
	public void setDepartmentRoleName(String departmentRoleName) {
		this.departmentRoleName = departmentRoleName;
	}
}
