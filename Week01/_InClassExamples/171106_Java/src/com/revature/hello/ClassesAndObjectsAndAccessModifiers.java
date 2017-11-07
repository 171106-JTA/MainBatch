package com.revature.hello;

import com.revature.outsidehello.Person2;
import com.revature.outsidehello.Person3;

public class ClassesAndObjectsAndAccessModifiers extends Person3{

	public static void main(String[] args) {
		/*
		 * Creating an instance of an object requires the 
		 * 'new' keyword.
		 */
		//NOTE: THERE ARE NO POINTERS IN JAVA!
	//	Reference = Assignment
		Person p = new Person();
		
		//System.out.println(p.random); //random is private, 
										//and thus no accessible outside of the class
		/*
		 * I have access to the Person classes' 'random' attribute
		 * because it is set to public.
		 * I don't access to name and age because it is set to 'private'
		 * These are two of four access modifiers.
		 * 
		 * Public:		Item can be accessed from anywhere
		 * Protected:	Item can be accessed by subclasses, and same packages
		 * Default:		Item can be accessed by same package
		 * Private:		Item can only be accessed within class it was made
		 */
		
		System.out.println(p.getName());
		p.setName("Ryan");
		System.out.println(p.getName());

		System.out.println(p);
		
		Person2 p2 = new Person2();
		Person3 p3 = new Person3();
		
		System.out.println("======================");
		
		/*
		 * When dealing with a class that is within the same package as me, I should have access
		 * to all: public, protected, default
		 */
		p.publicMethod();
		p.protectedMethod();
		p.defaultMethod();
		//p.privateMethod(); <-- method isnt visible
		
		//Since person 2 is out of package, only public is allowed.
		p2.publicMethod();
/*		p2.protectedMethod();
		p2.defaultMethod();
		p2.privateMethod();*/
		
		/*
		 * Though we have access to protected methods within a subclass, the
		 * class in which we are executing code in, is NOT a subclass of Person, thus
		 * cannot access it's protected methods.
		 */
		p3.publicMethod();
		//p3.protectedMethod();
		Person p4 = new Person3();
		p4.protectedMethod();
		
		System.out.println("================================");
		Person p5 = new Person();
		System.out.println(p5.getName());
		
		Person p6 = new Person("Ryan", 0, "Bobbert");
		System.out.println(p6.getName());
		
	}

}
