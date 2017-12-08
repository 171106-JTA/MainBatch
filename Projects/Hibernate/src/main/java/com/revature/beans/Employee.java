package main.java.com.revature.beans;

public class Employee {
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private float salary;
	
	public Employee() {
		super();
	}
	public Employee(int id) {
		super();
		this.id = id;
	}
	public Employee(String lastname, String email) {
		super();
		this.lastname = lastname;
		this.email = email;
	}
	public Employee(String lastname, String email, float salary) {
		super();
		this.lastname = lastname;
		this.email = email;
		this.salary = salary;
	}
	public Employee(String firstname, String lastname, String email, float salary) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.salary = salary;
	}
	public Employee(int id, String firstname, String lastname, String email, float salary) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.salary = salary;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
}
