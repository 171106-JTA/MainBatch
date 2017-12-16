package com.revature.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory =
			new Configuration().configure().
			buildSessionFactory();
	//The configure method, can take a filename, but by
	//default, it looks for a configuration file
	//name: 'hibernate.cfg.xml
	
	public static Session getSession(){
		return sessionFactory.openSession();
	}
}
