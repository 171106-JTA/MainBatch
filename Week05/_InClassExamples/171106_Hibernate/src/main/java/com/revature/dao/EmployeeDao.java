package com.revature.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.revature.beans.Bear;
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
			
			System.out.println("About to call the same get...");
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
	public void HQL(){
		Session session = HibernateUtil.getSession();
		Query query;
		String hql;
		Transaction tx;
		
		System.out.println("====GET ALL BEARS!====");
		hql = "FROM com.revature.beans.Bear";
		query = session.createQuery(hql);
		//For basic selection, HQL omits 'SELECT FROM'
		//Instead of hitting tablenames, HQL selects from class
		//name. What this means is nothing case sensitive EXCEPT for
		//class names and packages.
		List<Bear> bears = query.list(); //the .list() function executes the query
		System.out.println(bears);
		
		/*
		 * WHERE statements:
		 * -FROM com.revature.beans.Bear b WHERE b.height < 85;
		 * 
		 * 
		 */
		System.out.println("====Using parameterized queries====");
		hql = "FROM Bear WHERE bearId = :bearNum"; //Note the colon
		query = session.createQuery(hql);
		query.setParameter("bearNum", 50);
		Bear bear = (Bear)query.uniqueResult(); //uniqueResult for result sets of one record
		System.out.println(bear);
		/*
		 * You can use HQL to perform all SQL related actives, such as
		 * update and delete.
		 */
		
	}
	
	public void criteriaDemo(){
		Session session = HibernateUtil.getSession();
		Criteria criteria;
		
		System.out.println("====GET ALL BEARS===");
		List<Bear> bears = session.createCriteria(Bear.class).list();
		//As before, .list() executes
		System.out.println(bears);
		
		System.out.println("===Criteria With Conditions===");
		bears = session.createCriteria(Bear.class)
				.add(Restrictions.ilike("bearColor", "yellow")).list();
		//NOTE: like is case sensitive, ilike is not.
		System.out.println(bears);
		
		/*
		 * Note you can chain multiple criterions with the '.add'
		 * method.
		 * You can also apply aggragate functions to your results
		 * using the 'Projection' object.
		 */
		
		System.out.println("====BearCount====");
		Long bearcount = (Long)session.createCriteria(Bear.class).setProjection(
					Projections.count("bearId")
				).uniqueResult();
		System.out.println(bearcount);
		
	}
	
	public void executeNamedQueries(){
		Session session = HibernateUtil.getSession();
		Query query = session.getNamedQuery("getAllHoneypots");
		Query nativeQuery = session.getNamedQuery("getSmallHoneypots");
		nativeQuery.setParameter("maxsize", 40);
		
		System.out.println("====Named Query===");
		System.out.println(query.list());
		System.out.println("====Named Native SQL Query===");
		System.out.println(nativeQuery.list());
	}
}
