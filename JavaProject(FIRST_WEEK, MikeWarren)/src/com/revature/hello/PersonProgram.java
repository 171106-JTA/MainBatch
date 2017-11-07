package com.revature.hello;

import com.revature.outsideHello.AnotherPerson;
import com.revature.outsideHello.YetAnotherPerson;

public class PersonProgram extends YetAnotherPerson{

	public static void main(String[] args) {
		/*
		 * Creating instance of object requires the 'new' keyword
		 */
		// Reference = Assignment
		Person p = new Person();
		
		//System.out.println(p.random); // random is private and thus inaccessible here.
		/* Access modifiers
		 * • public:    can be accessed from anywhere
		 * • protected: can be accessed by subclasses and same packages
		 * • default:   can be accessed by same package 
		 * • private:   can be accessed within class it was made
		 */
		
		System.out.println(p.getName());
		p.setName("Ryan");
		System.out.println(p.getName());
		System.out.println(p);
		
		AnotherPerson p2 = new AnotherPerson();
		
		System.out.println("============================");
		
		/* When dealing with a class that is within the same package as me, I should have access to all: 
		 * to all: public, protected, default
		 */
		p.publicMethod();
		p.protectedMethod();
		p.defaultMethod();
		//p.privateMethod(); // method isn't visible
		
		// since p2 is out of package, only publicMethod() is available
		p2.publicMethod();
		/*p2.protectedMethod();
		p2.defaultMethod();
		p2.privateMethod();*/
		
		/* Though we have access to protected methods within a subclass, the class in which we are executing code in, 
		 * is NOT a subclass of Person, thus cannot access its protected methods.
		 */
		YetAnotherPerson p3 = new YetAnotherPerson();
		p3.publicMethod();
		//p3.protectedMethod();
		Person p4 = new YetAnotherPerson();
		p4.protectedMethod();
		
		System.out.println("===============================");
		Person p5 = new Person();
		System.out.println(p5.getName());
		
		Person p6 = new Person("Ryan", 30, "Bobbert");
		System.out.println(p6.getName());
	}

}
