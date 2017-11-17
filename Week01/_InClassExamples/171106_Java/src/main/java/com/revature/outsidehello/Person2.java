package com.revature.outsidehello;

public class Person2 {
	//When creating variables, typically conventions goes:
	//accessModifier datatype variableName = someValue;
	private String name = "Bobbert";
	private int age = 78;
	private String random = "random words";
	
	public void setName(String name){
		/*
		 * 'this' keyword is used to reference the class itself.
		 */
		this.name = name;
	}
	public String getName(){
		return name;
	}
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
	
	public void publicMethod(){
		System.out.println("public");
	}
	protected void protectedMethod(){
		System.out.println("Protected");
	}
	void defaultMethod(){
		System.out.println("Default");
	}
	private void privateMethod(){
		System.out.println("Private");
	}

	
	/*
	 * @Keyword is an example of an Annotation. Annotations are used to enforce extra rules in 
	 * most cases.
	 * 
	 * toString() is a method that is called whenever an object is converted to a string.
	 * e.g. when you syso an object.
	 */
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", random=" + random + "]";
	}
	
}
