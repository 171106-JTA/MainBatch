package com.revature.outsideHello;

public class AnotherPerson {
	private String name = "Bobbert";
	private int age = 78;
	private String random = "random words";
	
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
