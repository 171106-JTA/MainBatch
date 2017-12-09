package main.java.com.revature.main;

import main.java.com.revature.beans.Employee;
import main.java.com.revature.dao.EmployeeDAO;

public class BearDriver {

	public static void main(String[] args) {
//		BearDAO beardao = new BearDAO();
//		beardao.insertBears();
//		beardao.getAndLoadDemo();
//		beardao.saveVsPersist();
//		EmployeeDAO ed = new EmployeeDAO();
//		System.out.println(ed.getAllEmployees());
		EmployeeDAO ed = new EmployeeDAO();
		ed.insertEmployee(new Employee("blah", "bloo", 12345));
		EmployeeDAO ed2 = new EmployeeDAO();
		ed2.HQL();
	}

}
