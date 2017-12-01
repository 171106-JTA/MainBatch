package me.daxterix.trms;

import me.daxterix.trms.config.Config;
import me.daxterix.trms.dao.EmployeeDAO;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.Department;
import me.daxterix.trms.model.Employee;
import me.daxterix.trms.model.EmployeeAccount;
import me.daxterix.trms.model.EmployeeRank;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    static ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

    public static void main(String[] args) throws DuplicateIdException, NonExistentIdException {
        initHibernate();
        try {
            cleanupTest();
        }
        catch(Exception e){
            System.out.println(e);
        }
        runTest();
    }

    static void initHibernate() {
        EmployeeDAO repo = context.getBean(EmployeeDAO.class);
    }

    static void cleanupTest() throws NonExistentIdException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        EmployeeDAO repo = context.getBean(EmployeeDAO.class);
        repo.deleteAccount("email");
        repo.deleteAccount("email2");
    }

    static void runTest() throws DuplicateIdException {
        EmployeeDAO repo = context.getBean(EmployeeDAO.class);

        EmployeeAccount acc = new EmployeeAccount();
        acc.setEmail("email");
        acc.setPassword("password");
        repo.save(acc);

        Employee emp = new Employee();
        emp.setAccount(acc);
        emp.setDepartment(new Department(Department.RAVENCLAW));
        emp.setFirstname("first_name");
        emp.setLastname("last_name");
        emp.setAvailableFunds(1000);
        emp.setRank(new EmployeeRank(EmployeeRank.BENCO));

        // not saving assigned department or rank use session.load???
        // check @JoinColumn annotation attributes (insertable = false, updatable = false)
        repo.save(emp);


        EmployeeAccount acc2 = new EmployeeAccount();
        acc2.setEmail("email2");
        acc2.setPassword("password2");
        repo.save(acc2);
    }
}
