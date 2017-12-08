package com.revature.main;

import java.util.List;

import com.revature.beans.Employee;
import com.revature.dao.EmployeeDao;

public class Driver {

	public static void main(String[] args) {
		EmployeeDao ed = new EmployeeDao();
		Employee emp1 = new Employee(
					"bobbert",
					"bobson",
					"bob@bob.bob",
					5000
				);
		Employee emp2 = new Employee(
				"ryan",
				"lessley",
				"ryan.lessley@revature.com",
				0
			);
		Employee emp3 = new Employee(
				"Edgar",
				"Edgarson",
				"bob@bob.bob",
				125000
			);
		
		System.out.println("Emp1 one inserted with id: "
				+ ed.insertEmployee(emp1));
		System.out.println("Emp2 one inserted with id: "
				+ ed.insertEmployee(emp2));
		System.out.println("Emp3 one inserted with id: "
				+ ed.insertEmployee(emp3));
		
		
		System.out.println(ed.getEmployeeById(2));
		
		List<Employee> emps = ed.getAllEmployees();
		for(Employee e : emps){
			System.out.println(e);
		}
		
		ed.updateEmployeeSalary(3, 7500);
		emps = ed.getAllEmployees();
		for(Employee e : emps){
			System.out.println(e);
		}
		
		ed.deleteEmployee(3);
		emps = ed.getAllEmployees();
		for(Employee e : emps){
			System.out.println(e);
		}
	}

}
