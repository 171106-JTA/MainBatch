package com.revature.hello;

public class Person {
	private String name;
	private int age;
	private String random;
	
	/*
	 * The default constructor is provided by the JVM when you don't write one. 
	 * If you write any constructor at all, the JVM will NOT provide a default constructor.
	 * If YOU write a constructor with no arguments, this is called "no-args constructor".
	 * 
	 * A constructor is simply the method that instantiates the class.
	 * A constructor is called with the "new" keyword.
	 * A constructor is a method with the following syntax: 
	 * [Access modifier]  ClassName([args])
	 */
	public Person() { 
		name   = "Bobbert";
		age    = 78;
		random = "random words";
	}
	
	public Person(String name, int age, String random) {
		this.name   = name;
		this.age    = age;
		this.random = random; 
	}
	
	public void setName(String name ) { 
		this.name = name;
	}
	public String getName() { return name; }
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
	
	public void publicMethod() {
		System.out.println("public");
		
	}
	
	protected void protectedMethod() { 
		System.out.println("protected");
	}
	
	void defaultMethod()
	{
		System.out.println("default");
	}
	
	private void privateMethod()
	{
		System.out.println("private");
	}
	
	/*
	 * @Keyword is example of Annotation. Annotations are used to enforce extra rules in most cases.
	 * 
	 * toString() is method that is called whenever an object is converted to a string. 
	 * e.g. when you syso an object. 
	 */
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", random=" + random + "]";
	}
}
