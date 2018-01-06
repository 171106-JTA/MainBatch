package com.revature.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.revature.beans.HelloBean;

public class Driver {

	public static void main(String[] args) {
		/*
		 *ApplicationContext is our Spring Container. It encapsulates all mapped
		 *beans for use in our project.
		 *There are 3 different versions of the ApplicationContext we can use:
		 *-ClassPathXmlapplicationContext
		 *	-Creates instance using xml located in project
		 *-FileSystemXmlApplicationContext
		 *	-Creates instance using xml located on a file server
		 *-XmlWebApplicationContext
		 *	-Creates the instance using xml located on an application server
		 */
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("beans.xml");
		
		HelloBean hb = (HelloBean)context.getBean("helloBean");
		System.out.println(hb.getMessage());
	}

}
