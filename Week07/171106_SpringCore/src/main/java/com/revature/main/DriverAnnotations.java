package com.revature.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.revature.beans.House;

public class DriverAnnotations {

	public static void main(String[] args) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("annotations.xml");
		House h1 = context.getBean("house", House.class);
		System.out.println(h1);
	}

}
