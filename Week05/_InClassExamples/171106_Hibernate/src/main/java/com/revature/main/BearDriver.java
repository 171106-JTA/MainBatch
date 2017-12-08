package com.revature.main;

import com.revature.dao.BearDao;
import com.revature.dao.EmployeeDao;

public class BearDriver {
	
	
	public static void main(String[] args) {
		BearDao bd = new BearDao();
		
		bd.insertBears();
		
		//bd.getAndLoadDemo();
		
		/*
		bd.saveVsPersist();
		EmployeeDao ed = new EmployeeDao();
		System.out.println(ed.getAllEmployees());*/
		
/*		bd.mergeVsUpdate();
		EmployeeDao ed = new EmployeeDao();
		List<Employee> emps =  ed.getAllEmployees();
		for(Employee e : emps){
			System.out.println(e);
		}*/
		/*EmployeeDao ed = new EmployeeDao();
		int i = ed.insertEmployee(new Employee("bobbert","bobson","bob",123));
		System.out.println(ed.getEmployeeById(i));
		*/
		EmployeeDao ed = new EmployeeDao();
		ed.HQL();
		ed.criteriaDemo();
		ed.executeNamedQueries();
	}
	


}
