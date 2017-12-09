package main.java.com.revature.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import main.java.com.revature.beans.Bear;
import main.java.com.revature.beans.Employee;
import main.java.com.revature.util.HibernateUtil;

public class EmployeeDAO {
	public int insertEmployee(Employee emp) {
		// We use the session component to hold the connection to our database
		Session session = HibernateUtil.getSession();
		// We use the transaction component to perform actual operations on data
		Transaction tx = null;
		Integer empid = null;
		try {
			// Open of a transaction stream for our session
			tx = session.beginTransaction();
			empid = (Integer)session.save(emp);
			// Save will persist the data to the database.
			// It returns the created id that represents this record.
			tx.commit();
		} catch(HibernateException e) {
			if(tx != null) {
				// If a transaction fails, must roll back
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			// Never have open streams of anything
			session.close();
		}
		return empid;
	}
	public Employee getEmployeeById(Integer id) {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		Employee emp = null;
		try {
			tx = session.beginTransaction();
			emp = (Employee)session.get(Employee.class, id);
			
		} catch(HibernateException e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return emp;
	}
	public List<Employee> getAllEmployees(){
		List<Employee> emps = null;
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			emps = session.createQuery("FROM Employee").list();
		} catch(HibernateException e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return emps;
	}
	public void updateEmployeeSalary(int id, Float salary) {
		Employee emp = null;
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			emp = (Employee)session.get(Employee.class, id);
			if(emp != null) {
				emp.setSalary(salary);
				session.update(emp);
				tx.commit();
			}
		} catch(HibernateException e) {
			if(tx != null) {
				// If a transaction fails, must roll back
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			// Never have open streams of anything
			session.close();
		}
	}
	public void deleteEmployee(int id){
		Employee emp = null;
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			emp = (Employee)session.get(Employee.class, id);
			session.delete(emp);
		} catch(HibernateException e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public void HQL() {
		Session session = HibernateUtil.getSession();
		Query query;
		String hql;
		Transaction trans;
		System.out.println("Get all the bears");
		hql = "FROM com.revature.beans.Bear";
		query = session.createQuery(hql);
		// For basic selection, HQL omits 'SELECT FROM'
		// Instead of hitting tablenames, HQL selects from class
		// name. What this means is nothing case sensitive EXCEPT for
		// class names and packages.
		List<Bear> bears = query.list(); // list() executes the query
		System.out.println(bears);
		System.out.println(bears.get(0).getWeight());
		query.setParameter("bearNum", 8520);
		Bear bear3 = (Bear)query.uniqueResult(); // unique result for result sets of one record
		System.out.println(bear3);
	}
	public void criteriaDemo() {
		Session session = HibernateUtil.getSession();
		Criteria criteria;
		System.out.println("Get all bears");
		List<Bear> bears = session.createCriteria(Bear.class).list();
		System.out.println(bears);
		System.out.println("Criteria with conditions");
		bears = session.createCriteria(Bear.class).add(Restrictions.ilike("bearcolor", "blue")).list();
		System.out.println(bears);
		// can append .add() chain to custom criteria before list()
		// can also apply aggregate functions to your results using the 'Projection' object
		System.out.println("bear count");
		int count = (Integer)session.createCriteria(Bear.class).setProjection(Projections.count("bearid")).uniqueResult();
		System.out.println(count);
	}
	public void executeNamedQueries() {
		Session session = HibernateUtil.getSession();
		Query query = session.getNamedQuery("getAllHoneypots");
		Query nativeQuery = session.getNamedQuery("getSmallHoneypots");
		nativeQuery.setParameter("maxsize", 40);
		System.out.println("Named query");
		System.out.println(query.list());
		System.out.println("Named native query");
		System.out.println(nativeQuery.list());
	}
}
