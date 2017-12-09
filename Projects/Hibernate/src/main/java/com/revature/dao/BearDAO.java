package main.java.com.revature.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import main.java.com.revature.beans.Bear;
import main.java.com.revature.beans.Cave;
import main.java.com.revature.beans.Employee;
import main.java.com.revature.beans.HoneyPot;
import main.java.com.revature.util.HibernateUtil;

public class BearDAO {
	public Integer insertBears() {
		Session session = HibernateUtil.getSession();
		Transaction trans = session.beginTransaction();
		Bear parent = new Bear(new HoneyPot(100,100), null, "Yellow", 9, 9);
		session.save(parent.getHoneypot());
		Bear child = new Bear(new HoneyPot(50,30), null, "Blue", 2, 9000);
		parent.getBearcubs().add(child);
		session.save(child.getHoneypot());
		Cave newhome = new Cave(300, "Dank Cave");
		parent.setDwelling(newhome);
		child.setDwelling(newhome);
		session.save(newhome);
		session.save(parent);
		session.save(child);
		trans.commit();
		session.close();
		return 0;
	}
	public void getAndLoadDemo() {
		Session session = HibernateUtil.getSession();
		Bear getbear = (Bear)session.get(Bear.class, 50);
		System.out.println("GET: " + getbear);
		Bear loadbear = (Bear)session.get(Bear.class, 50);
		System.out.println("LOAD: " + loadbear);
		Bear getbadbear = (Bear)session.get(Bear.class, 100); // Will result in null if object does not exist in database
		System.out.println("GET: " + getbadbear);
		Bear loadbadbear = (Bear)session.get(Bear.class, 100); // Will give an object but throw an error if object does not exist in database
		System.out.println("LOAD: " + loadbadbear);
		// GET is an eager retrieval
		// LOAD is a lazy retrieval that provides a proxy of the object and is why we have access to the object's getters and setters
		// The database is only hit when requesting real data. Since Hibernate assumes the object exists.
		// An exception is thrown once Hibernate is unable to grab the object.
		// Use LOAD when you are not sure if the data exists or GET when you are sure the data does exist.
	}
	public void saveVsPersist() {
		Employee bob = new Employee("Bip", "Bobbert", 12354);
		Session session = HibernateUtil.getSession();
		Transaction trans = session.beginTransaction();
		session.save(bob);
		System.out.println("Inserted ID: " + bob);
		bob = new Employee("Tib", "Tommert", 45321);
		session.persist(bob);
		System.out.println("Inserted ID: " + bob);
		// SAVE is very much like EAGER though it is not exactly
		// SAVE persists with the database therefore calling it
		// will return a number indicating which record it was inserted as
		// PERSIST is very much like LAZY though it is not exactly
		// PERSIST is NOT inserted into the database immediately.
		// It is inserted at a seemingly random point during the transaction
		// This is why PERSIST returns void. It cannot give a number like SAVE
		// does because it may not have inserted yet. It will insert once database
		// is flushed. Flush time is the point where all data is persisted.
		trans.commit();
		session.close();
	}
	public void mergeVsUpdate() {
		Session session = HibernateUtil.getSession();
		Transaction trans = session.getTransaction();
		Employee emp = new Employee("blip", "blop"); // Transient
		trans.begin();
		session.save(emp); // Persistent
		trans.commit();
		session.close(); // Detached
		Session session2 = HibernateUtil.getSession();
		Transaction trans2 = session2.getTransaction();
		Employee emp2 = new Employee(); // Transient
		emp2 = (Employee)session2.get(Employee.class, emp.getId());
		emp2.setFirstname("blah");
		trans2.begin();
		System.out.println(emp2.getFirstname());
		// session2.update(emp); // will not allow, throws exception since a detached object is persistent (emp in this case)
		session2.merge(emp);
		System.out.println(emp2.getFirstname());
		trans2.commit();
		session2.close();
		// MERGE and UPDATE will re-attach a detached object to a persistent state
		// UPDATE throws an exception if a the session already has a persistent object
		// MERGE will update the cache if a different object is present
	}
}
