package com.revature.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.Bear;
import com.revature.beans.Cave;
import com.revature.beans.HoneyPot;
import com.revature.util.HibernateUtil;

public class BearDao {
	public void insertBears() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Bear parent = new Bear(
				null,
				new HoneyPot(100,100),
				"Yellow",
				9,
				9);
		session.save(parent.getHoneypot());
		
		//Bear has a child!
		Bear child = new Bear(null, 
							new HoneyPot(30,30),
							"Grey",
							2,
							1000);
		
		parent.getBearCubs().add(child);
		session.save(child.getHoneypot());
		
		Cave newHome = new Cave(300, "Dank cave");
		
		parent.setDwelling(newHome);
		child.setDwelling(newHome);
		
		
		session.save(newHome);
		session.save(parent);
		session.save(child);
		tx.commit();
		session.close();
	}
}
