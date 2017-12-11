package me.daxterix.trms.bootstrap;


import me.daxterix.trms.dao.DAOUtils;
import me.daxterix.trms.dao.EmployeeDAO;
import me.daxterix.trms.dao.ObjectDAO;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;


public class TestEntitiesCreator {
    private static ObjectDAO objectDao = DAOUtils.getObjectDAO();
    private static EmployeeDAO employeeDao = DAOUtils.getEmployeeDAO();
    static EmployeeAccount slythHeadAcc, acc2, bencoAcc;
    static Employee slythHeadEmp, emp2, bencoEmp;
    static String slythHeadEmail = "slythHeadEmail", email2 = "email2", bencoEmail = "bencoEmail", pass = "pass";

    public static void main(String[] args) throws DuplicateIdException, NonExistentIdException {
        try {
            cleanup();
        }
        catch(NonExistentIdException e) {System.out.println("can't delete what we don't have");};

        bencoAcc = createAccount(bencoEmail, pass);
        bencoEmp = createEmployee(bencoAcc, null, null, EmployeeRank.BENCO);

        slythHeadAcc = createAccount(slythHeadEmail, pass);
        slythHeadEmp = createEmployee(slythHeadAcc, null, Department.SLYTHERIN, EmployeeRank.DEPARTMENT_HEAD);

        acc2 = createAccount(email2, pass);
        emp2 = createEmployee(acc2, slythHeadEmp, Department.SLYTHERIN, EmployeeRank.REGULAR);
    }

    static EmployeeAccount createAccount(String email, String password) throws DuplicateIdException {
        EmployeeAccount acc1 = new EmployeeAccount();
        acc1.setEmail(email);
        acc1.setPassword(password);
        employeeDao.save(acc1);
        return acc1;
    }

    static void cleanup() throws NonExistentIdException {
        employeeDao.deleteAccount(bencoEmail);
        employeeDao.deleteAccount(slythHeadEmail);
        employeeDao.deleteAccount(email2);
    }

    static Employee createEmployee(EmployeeAccount account, Employee supervisor, String department, String rank) throws DuplicateIdException {
        Employee emp = new Employee();
        emp.setAccount(account);
        emp.setFirstname("first_name");
        emp.setLastname("last_name");
        emp.setAvailableFunds(1000);
        if (department != null)
            emp.setDepartment(new Department(department));
        emp.setRank(new EmployeeRank(rank));
        if (supervisor != null)
            emp.setSupervisor(supervisor);
        employeeDao.save(emp);
        return emp;
    }

}
