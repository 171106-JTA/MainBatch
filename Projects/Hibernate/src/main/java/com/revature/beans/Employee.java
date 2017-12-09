package main.java.com.revature.beans;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name="Employee_Emp")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class Employee {
	@Id
	@Column(name="EMP_ID")
	@SequenceGenerator(sequenceName="EMP_SEQ", name="EMP_SEQ")
	@GeneratedValue(generator="EMP_SEQ", strategy=GenerationType.SEQUENCE)
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
