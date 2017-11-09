package com.revature.assignment;

public class Person implements Comparable<Person> {
	private String name;
	private String address;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public Person(String name, String address, int age) {
		super();
		this.name = name;
		this.address = address;
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", address=" + address + ", age=" + age
				+ "]";
	}
	@Override
	public int compareTo(Person o) {
		int result;
		
		if ((result = name.compareTo(o.getName())) == 0) {
			if ((result = address.compareTo(o.getAddress())) == 0) 
				return Integer.compare(age, o.getAge());
		}
			
		return result;
	}
	
	
}
