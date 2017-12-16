package com.revature.dao;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.beans.Employee;
import com.revature.util.HibernateUtil;

public class EmployeeDao {
	public int insertEmployee(Employee emp)
	{
		// we use session compoonent to hold the connnection to our database
		Session session = HibernateUtil.getSession();
		// we use transaction component to perform actual operations on data. 
		Transaction tx  = null;
		
		Integer empId   = null;
		
		try 
		{
			// open up a transaction stream for our session.
			tx    = session.beginTransaction();
			// save writes the data to the database
			// it returns the created ID that represents this record.
			empId = (Integer)session.save(emp);
			tx.commit();
		}
		catch (HibernateException e)
		{
			if (tx != null)
			{
				// if a transaction fails, you gotta roll it back. 
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			// never have open streams of anything.
			session.close();
		}
		return empId;
	}
	
	public Employee getEmployeeById(Integer id)
	{
		Session session = HibernateUtil.getSession();
		Transaction tx  = null;
		Employee emp    = null;
		try 
		{
			tx  = session.beginTransaction();
			emp = (Employee)session.get(Employee.class, id);
			
			
		}
		catch (HibernateException ex)
		{
			if (tx != null)
			{
				tx.rollback();
				ex.printStackTrace();
			}
		}
		finally
		{
			session.close();
		}
		return emp;
	}
	
	public List<Employee> getAllEmployees()
	{
		List<Employee> emps = null;
		Session session     = HibernateUtil.getSession();
		Transaction tx      = null;
		
		try 
		{
			tx   = session.beginTransaction();
			emps = session.createQuery("FROM Employee").list();
		}
		catch (HibernateException ex)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			ex.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return emps;
		
	}
	
	public void updateEmployeeSalary(Integer id, Double newSalary)
	{
		Employee emp    = null;
		Session session = HibernateUtil.getSession();
		Transaction tx  = null;
		
		try 
		{
			tx   = session.beginTransaction();
			emp  =  (Employee)session.get(Employee.class, id);
			if (emp != null)
			{
				emp.setSalary(newSalary);
				session.update(emp);
				// tells database to sync up with current object
				tx.commit();
			}
		}
		catch (HibernateException ex)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			ex.printStackTrace();
		}
		finally
		{
			session.close();
		}
		
	}
	
	public void deleteEmployee(Integer id)
	{
		Employee emp    = null;
		Session session = HibernateUtil.getSession();
		Transaction tx  = null;
		
		try 
		{
			tx   = session.beginTransaction();
			emp  =  (Employee)session.get(Employee.class, id);
			if (emp != null)
			{
				session.delete(emp);
				// tells database to sync up with current object
				tx.commit();
			}
		}
		catch (HibernateException ex)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			ex.printStackTrace();
		}
		finally
		{
			session.close();
		}
		
	}

}
