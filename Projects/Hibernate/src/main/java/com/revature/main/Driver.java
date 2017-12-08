package main.java.com.revature.main;

import java.util.List;

import main.java.com.revature.beans.Employee;
import main.java.com.revature.dao.EmployeeDAO;

public class Driver {
	public static void main(String[] args) {
		EmployeeDAO ed = new EmployeeDAO();
		Employee emp0 = new Employee(
				"bobbert",
				"bobson",
				"bob@bob.bob",
				5000
			);
		Employee emp1 = new Employee(
				"Django",
				"Django",
				"django@django.django",
				10000000
			);
		System.out.println("Emp zero inserted with id: " + ed.insertEmployee(emp0));
		System.out.println("Emp one inserted with id: " + ed.insertEmployee(emp1));
		System.out.println(ed.getEmployeeById(0));
		List<Employee> emps = ed.getAllEmployees();
		for(Employee e : emps) {
			System.out.println(e);
		}
		ed.updateEmployeeSalary(0, 1.0f);
		emps = ed.getAllEmployees();
		for(Employee e : emps) {
			System.out.println(e);
		}
		ed.deleteEmployee(1);
		emps = ed.getAllEmployees();
		for(Employee e : emps) {
			System.out.println(e);
		}
	}
}
