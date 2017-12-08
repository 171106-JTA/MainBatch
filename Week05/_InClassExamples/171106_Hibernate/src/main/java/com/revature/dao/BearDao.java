package com.revature.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.Bear;
import com.revature.beans.Cave;
import com.revature.beans.Employee;
import com.revature.beans.HoneyPot;
import com.revature.util.HibernateUtil;

public class BearDao {
	public void insertBears() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();

		Bear parent = new Bear(null, new HoneyPot(100, 100), "Yellow", 9, 9);

		// persisting-fying honeypot
		session.save(parent.getHoneypot());

		// Bear has child!
		Bear child = new Bear(null, new HoneyPot(30, 30), "Grey", 2, 1000);

		parent.getBearCubs().add(child);
		session.save(child.getHoneypot());

		Cave newHome = new Cave(300, "Dank Cave");

		parent.setDwelling(newHome);
		parent.setDwelling(newHome);

		session.save(newHome);
		session.save(parent);
		session.save(child);
		tx.commit();
		session.close();
	}

	public void getAndLoadDemo() {
		Session session = HibernateUtil.getSession();
		Bear getBear = (Bear) session.get(Bear.class, 50);
		System.out.println("GET: " + getBear);

		Bear loadBear = (Bear) session.load(Bear.class, 50);

		System.out.println("LOAD: " + loadBear);
		/*
		 * The retreival of data is designated to one of two methods: - GET and LOAD The
		 * key difference between these two retrieval method is that one can be
		 * considered lazy, while other can be considered eager. GET: This is our eager
		 * retrieval example. GET will hit the database immediatelty and populate all
		 * data one the spot for you. If no data exists it can provide 'null' as the
		 * data. Typically you want to use this if you're not sure if that object exists
		 * LOAD: This is our considered lazy retrieval. It provides back to us a proxy
		 * of the object - A proxy is just a simple implementation of the object
		 * structure that is providedfor us to use. This is why we have access to the
		 * objects getters and settter.
		 * 
		 * The database is only hit when you need to access the actual data within.
		 * Since up to that point, hibernate assumed the object existed and when it
		 * finds out that the data isnt actually there, we are greeted with an
		 * Exception. Typically use this, only when you are 100% sure that the data
		 * exists.
		 */
		session.close();
	}

	public void saveVsPersist() {
		Employee bob = new Employee("Bobbert", "Bobson", "email", 12378);
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		System.out.println("id before save "+ bob.getId());

		session.save(bob);
		System.out.println("id after save "+ bob.getId());

		bob = new Employee("Tommy", "tom", "tomail", 123);
		System.out.println("id before persist "+ bob.getId());

		session.persist(bob);
		System.out.println("id after persist "+ bob.getId());
		/*
		 * Difference between SAVE and PERSIST:
		 *  - They are not EAGER or LAZY. But you can compare it like EAGER or LAZY
		 *  When you call save on an object you persist it with the database.
		 *  Therefore, upon calling save, you are returned a number indicating which record it was inserted as
		 *  With persist, however, it is not inserted in to the database right away
		 *  It is inserted at seemingly random points during the transaction.
		 *  This is why the persist returns void. It can;t give you a number for its insert 
		 *  record because it techinally MIGHT not have inserted right away.
		 *  The only guarentee with persist is that it will be inserted by flush time for the database.
		 *  (Flushtime is the point where all data is persisted in the database. 
		 * */
		tx.commit();
		session.close();
	}
	public void mergeVsUpdate() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Employee emp = new Employee("Tommy", "tom", "tomail", 123);
		//object is in transient state
		session.save(emp);
		// At this point the persistnet object is in the persistent state
		tx.commit();
		session.close();
		//object is in detached state
		
		Session session2 = HibernateUtil.getSession();
		Transaction tx2 = session2.beginTransaction();
		Employee emp2 = new Employee();
		emp2 = (Employee)session2.get(Employee.class, emp.getId());
		emp2.setFirstName("tyan");
		session2.update(emp2);
		// with update it will not let you interfere with another object with the same ID, cannot  .update(emp) 
		// Since another object already represents it.
		System.out.println(emp2.getFirstName());
		session2.merge(emp);
		System.out.println(emp2.getFirstName());
		tx2.commit();
		session2.close();
		
		/* Merge vs update:
		 * 
		 * - Update and merge will re-attach a detached object to a persistent state. 
		 * - Update throws and exception if you try to invoke it while a session is currently using a different
		 * object for persistence.
		 *  
		 * -Merge will update the cach of a different object is being used by the session.
		 * ULtimately, you use merge if you dont care about the state of the session.
		 * 
		 * */
		
	}
	
}
