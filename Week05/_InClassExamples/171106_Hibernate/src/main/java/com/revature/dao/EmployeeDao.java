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
			System.out.println();
			
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
	public void HQL() {
		Session session = HibernateUtil.getSession();
		Query query;
		String hql;
		Transaction tx;
		System.out.println("-----------GET ALL BEARES!!=============");
		hql = "FROM com.revature.beans.Bear";
		query= session.createQuery(hql);
		// For basic selection, HQL omits 'SELECT FROM';
		// Instead of hitting tablenames, HQL selects from class name
		// What this means is nothing case sensitiev EXCEPT for class
		// names and packages.
		List<Bear> bears = query.list(); // the .list() fnction executes the query
		System.out.println(bears);
		System.out.println(bears.get(0).getWeight());
		
		/*
		 * WHERE statements:
		 * - 'FROM com.revature.beans.Bear (as) b WHERE b.height < 85'
		 * */
		System.out.println("============Using parametrized queries======");
		hql = "FROM Bear WHERE bearId = :bearNum"; // note the colon
		// column names and watever name should be how its written in java objects
		query = session.createQuery(hql);
		query.setParameter("bearNum", 50);
		Bear bear = (Bear)query.uniqueResult(); //uniqueResult for result sets of one record
		System.out.println(bear);
		/*
		 * U can use HQL to perform all SQL related activities such as update and delete.
		 * 
		 * 
		 * */		
	}
	public void criteriaDemo() {
		Session session = HibernateUtil.getSession();
		Criteria criteria;
		System.out.println("==========get all bears=================");
		List<Bear> bears = session.createCriteria(Bear.class).list();
		System.out.println(bears);
		
		System.out.println("=====Criteria with conditions");
		bears = session.createCriteria(Bear.class)
				.add(Restrictions.ilike("bearColor", "brown")).list();// can use .add .add to customize more
		//NOTE: like is case sensitive, ilike is not
		/*
		 * Note you can chain multiple criterion with '.add' methods
		 * You can also apply aggragate function to your results 
		 * using the Projection object.
		 */
		System.out.println("==========BEARCOUNT++=======");
		long bearCount = (Long) session.createCriteria(Bear.class).setProjection(
				Projections.count("bearId")
				).uniqueResult();
		System.out.println(bearCount);
	}
	
	public void executeNamedQueries() {
		Session session =HibernateUtil.getSession();
		Query query = session.getNamedQuery("getAllHoneypots");
		Query nativeQuery = session.getNamedQuery("getSmallHoneypots");
		nativeQuery.setParameter("maxsize", 40);
		System.out.println("-----named Query--");
		System.out.println(query.list());
		System.out.println("----nemed ative sql query");
		System.out.println(nativeQuery.list());
	}
}
