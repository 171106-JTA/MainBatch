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
		Transaction tx  = session.beginTransaction();
		
		Bear parent = new Bear(
				null, 
				new HoneyPot(100, 100),
				"Yellow",
				9000,
				60
				);
		session.save(parent.getHoneyPot());
		
		// Bear has child!
		Bear child = new Bear(null,
				new HoneyPot(30, 30),
				"Grey",
				200,
				10);
		parent.getBearCubs().add(child);
		session.save(child.getHoneyPot());
		
		Cave newHome = new Cave(300, "Dank cave");
		parent.setDwelling(newHome);
		child.setDwelling(newHome);
		
		session.save(newHome);
		session.save(parent);
		session.save(child);
		
		tx.commit();
		session.close();
		
	}

	public void getAndLoadDemo()
	{
		Session session = HibernateUtil.getSession();
		Bear bearGet    = (Bear)session.get(Bear.class, 50);
		System.out.println("GET : " + bearGet);
		Bear loadBear   = (Bear)session.load(Bear.class, 50);
	}
}
