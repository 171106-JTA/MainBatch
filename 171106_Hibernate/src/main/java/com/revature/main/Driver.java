package com.revature.main;

import java.util.List;

import com.revature.beans.Employee;
import com.revature.dao.EmployeeDao;

public class Driver {
	public static void main(String[] args)
	{
		EmployeeDao ed = new EmployeeDao();
		Employee   emp = new Employee(
				"bobbert",
				"bobson",
				"bob@bob.com",
				50000);
		Employee  emp2 = new Employee(
				"ryan",
				"lessley",
				"ryan.lessley@revature.com",
				500000);
		Employee  emp3 = new Employee(
				"Edgar",
				"Edgarson",
				"bob@bob.com",
				150000);
		System.out.printf("Emp one inserted with id : %s\n", ed.insertEmployee(emp));
		System.out.printf("Emp2 one inserted with id : %s\n", ed.insertEmployee(emp2));
		System.out.printf("Emp3 one inserted with id : %s\n", ed.insertEmployee(emp3));
		
		System.out.println(ed.getEmployeeById(2));
		
		List<Employee> emps = ed.getAllEmployees();
		for (Employee e : emps)
		{
			System.out.println(e);
		}
		
		ed.updateEmployeeSalary(3, 75000.00);
		for (Employee e : emps)
		{
			System.out.println(e);
		}
		
		ed.deleteEmployee(3);
		
		for (Employee e : emps)
		{
			System.out.println(e);
		}
	}
}
