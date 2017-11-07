package d2.revature.pillars;

import java.io.Serializable;

public class Employee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3887700479697458902L;
	/*
	 * Serializable is a "marker interface". This means it has NO methods.
	 * It is simply used to mark a class as something. 
	 */
	
	/*
	 * Serialization is the act of converting an object to byte code and saving it as a file.
	 * This file can be sent to other applications, who assuming they have the proper method
	 * to decrypt it, can decrypt the file and restore it as the intended object with all 
	 * data intact.
	 * 
	 * A design pattern is a tried and true methodology that is agreed upon to be the most
	 * efficient means for it's goal.
	 * In this case, we have created a POJO (Plain Old Java Object)
	 * A typical POJO has, getters, setters, constructors, toString
	 */
	
	private String name;
	private int age;
	private transient int ssn; //Transient prevents this data from getting serialized and sent.
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getSsn() {
		return ssn;
	}
	public void setSsn(int ssn) {
		this.ssn = ssn;
	}
	public Employee(String name, int age, int ssn) {
		super();
		this.name = name;
		this.age = age;
		this.ssn = ssn;
	}
	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", ssn=" + ssn + "]";
	}
	
}
