package com.revature.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.revature.beans.Occupation;
import com.revature.beans.Person;

public class Driver2 {

	public static void main(String[] args) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("beans2.xml");
		
		System.out.println("=====DEFAULT SINGLETON EXAMPLE=====");
		Person bob1 = (Person)context.getBean("personBean");
		Person bob2 = (Person)context.getBean("personBean");
		System.out.println(bob1);
		System.out.println(bob2);
		System.out.println(bob1==bob2);
		
		bob2.getOccupation().setSalary(300000);
		bob2.setName("Robert?");
		
		System.out.println(bob1);
		System.out.println(bob2);
		System.out.println(bob1==bob2);
		/*
		 * By default, beans are singletons. Therefore any
		 * instances of Person will be set to the same static instance.
		 * We can change this via the scope attribute in our bean.xml
		 */
		
		System.out.println("=====PROTOTYPE EXAMPLE=====");
		bob1 = (Person)context.getBean("personBeanProto");
		bob2.setOccupation((Occupation)context.getBean("occupationBeanProto"));
		
		System.out.println(bob1);
		System.out.println(bob2);
		bob1.setAge(7);
		bob1.getOccupation().setSalary(300000);
		System.out.println(bob1);
		System.out.println(bob2);
	}

}
