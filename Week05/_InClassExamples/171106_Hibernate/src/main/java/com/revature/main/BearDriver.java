package com.revature.main;

import com.revature.beans.Employee;
import com.revature.dao.BearDao;
import com.revature.dao.EmployeeDao;

public class BearDriver {

	public static void main(String[] args) {
		BearDao bd = new BearDao();
		bd.insertBears();
//		
//		//bd.getAndLoadDemo();
//		EmployeeDao ed = new EmployeeDao();
//		bd.saveVsPersist();
//		System.out.println(ed.getAllEmployees());
//		bd.mergeVsUpdate();
//		EmployeeDao ed = new EmployeeDao();
//		List<Employee> emps = ed.getAllEmployees();
//		for(Employee e: emps) {
//			System.out.println(e);
//		}
		/*EmployeeDao ed = new EmployeeDao();
		ed.insertEmployee(new Employee("bobert","bobson","bob",123));
		System.out.println(ed.getEmployeeById(1));*/
		EmployeeDao ed = new EmployeeDao();
		ed.HQL();
	}
}
