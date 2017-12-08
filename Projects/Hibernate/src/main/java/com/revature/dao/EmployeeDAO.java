package main.java.com.revature.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
}
