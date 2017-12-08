package com.revature.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.Employee;
import com.revature.util.HibernateUtil;

public class EmployeeDao {
	public int insertEmployee(Employee emp){
		/*
		 * We use the session component to hold the connection
		 * to our database.
		 */
		Session session = HibernateUtil.getSession();
		//We use the transaction component to perform
		//actual operations on data.
		Transaction tx = null;
		
		Integer empId = null;
		
		try{
			//Open of a transaction stream for our session.
			tx = session.beginTransaction();
			empId = (Integer)session.save(emp);
			//Save will persist the data to the database.
			//It returns the created ID that represent this record.
			tx.commit();
		}catch(HibernateException e){
			if(tx!=null){
				//If a transaction fails, ya gotta roll it back.
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			//Never have open streams of anything...
			session.close();
		}
		
		return empId;
	}
	
	public Employee getEmployeeById(Integer id){
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		Employee emp = null;
		
		try{
			tx = session.beginTransaction();
			emp = (Employee)session.get(Employee.class, id);
			
		}catch(HibernateException e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		return emp;
	}
	
	public List<Employee> getAllEmployees() {
		List<Employee> emps = null;
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			emps = session.createQuery("FROM Employee").list();
			
		}catch(HibernateException e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return emps;
	}

	public void updateEmployeeSalary(Integer id, Integer newSalary) {
		Employee emp = null;
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			emp = (Employee)session.get(Employee.class, id);
			if(emp!=null){
				emp.setSalary(newSalary);
				session.update(emp);
				//Tells database to sync up with current object
				tx.commit();
			}

			
			
		}catch(HibernateException e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	public void deleteEmployee(Integer id) {
		Employee emp = null;
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			emp = (Employee)session.get(Employee.class, id);
			if(emp!=null){
				session.delete(emp);
				tx.commit();
			}

			
			
		}catch(HibernateException e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
}
