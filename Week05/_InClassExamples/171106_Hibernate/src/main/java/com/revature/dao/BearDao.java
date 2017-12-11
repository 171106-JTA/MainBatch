package com.revature.dao;

import org.hibernate.Hibernate;
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
	
	public void getAndLoadDemo(){
		Session session = HibernateUtil.getSession();
		Bear bearThatIObtainedUsingTheGetMethod = (Bear)session.get(Bear.class, 50);
		System.out.println("GET: " + bearThatIObtainedUsingTheGetMethod.toString());
		
		Bear bearObtainedViaTheLoadFunctionality = (Bear)session.load(Bear.class, 50);
		System.out.println("LOAD: " + bearObtainedViaTheLoadFunctionality);
		
		bearThatIObtainedUsingTheGetMethod = (Bear)session.get(Bear.class, 100);
		System.out.println("GET: " + bearThatIObtainedUsingTheGetMethod);
		
		bearObtainedViaTheLoadFunctionality = (Bear)session.load(Bear.class, 100);
		System.out.println("Data loaded...");
		System.out.println("LOAD: " + bearObtainedViaTheLoadFunctionality);
		session.close();
		/*
		 * The retrieval of data is designated to one of two methods:
		 * -GET and LOAD
		 * The key difference between these two retrieval methods is that one
		 * can be considered lazy, while the other can be considered eager.
		 * GET: This is our eager retrieval example. Get will hit the database
		 * immediatly and populate all data on the spot for you. If no data exists
		 * it can provide 'null' as the data. 
		 * Typically, you want to use this if you are not sure that the object exists.
		 * 
		 * LOAD: This is our considered lazy retrieval. It provides back to us a proxy
		 * of the object.
		 * -A proxy is just a simple implmentation of the object structure that is 
		 * 	provided for us to use. This is why we have access to the object's
		 * 	getters and setters.
		 * The database is only hit when you need to access the actual data within.
		 * Since up to that point, hibernate assumed the object existed, and when it
		 * finds out that the data isn't actually, we are greeted with an Exception.
		 * Typically use this, only when you are 100% sure that the data exists.
		 */
		
	}
	
	public void saveVsPersist(){
		Employee bob = new Employee("Bobbert","Bobson", "email", 12378);
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		System.out.println("BOB ID before save: "  + bob.getId());
		session.save(bob);
		System.out.println("BOB ID after save: " + bob.getId());

		
		bob = new Employee("Tommy","tom","tomail",123);
		
		System.out.println("BOB ID before persist: "  + bob.getId());
		session.persist(bob);
		System.out.println("BOB ID after persist: " + bob.getId());
		/*
		 * The difference between SAVE and PERSIST:
		 * -They are not EAGER or LAZY. But one could compare it to that.
		 * -When you call save on an object. You persist it with the database.
		 * -Therefore, upon calling save, you are returned a number indicating
		 * 	Which record it was inserted as.
		 * -With persist, however, it is not inserted into the database right
		 * 	away. It is inserted at seemingly randomly points during the transaction.
		 * 	This is why the persist returns void. It can't give you a number for it's
		 * 	insert record because it technically MIGHT not have inserted right away.
		 * 	The only guarantee with persist is that it will be inserted by flush time
		 * 	for the database. (Flush time is the point where all data is persisted with the database)
		 */
		
		
		tx.commit();
		session.close();
		
		
	}
	
	public void mergeVsUpdate(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Employee emp = new Employee("Tombert", "Tombpson", "Tomail", 74);
		//At this point the persistent object is in the TRANSIENT state.
		
		session.save(emp);
		//At this point the persistent object is in the PERSISTENT state.
		tx.commit();
		session.close();
		
		System.out.println(emp.getId());
		Session session2 = HibernateUtil.getSession();
		Transaction tx2 = session2.beginTransaction();
		Employee emp2 =  new Employee();
		emp2 = (Employee)session2.get(Employee.class, emp.getId());
		emp2.setFirstName("ryan");
		//session2.update(emp); //Throws an exception since a different object already represents it.
		
		System.out.println("Emp2 first name before merge: " + emp2.getFirstName());
		session2.merge(emp);
		System.out.println("Emp2 first name after merge: " + emp2.getFirstName());
		tx2.commit();
		session2.close();
		
		//At this point the persistent object is in the DETACHED state.
		
		//Merge vs update: 
		/*
		 * -Update and merge will re-attach a detached object to a persistent
		 * state.
		 * -Update throws and exception if you try to invoke it while a session
		 * is currently using a different object for persistence.
		 * -Merge will update the cache if a different object is being used by
		 * the session.
		 * -You use merge if you dont care about the state of the session.
		 */
	}
}
