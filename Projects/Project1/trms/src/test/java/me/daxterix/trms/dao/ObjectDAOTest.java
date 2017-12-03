package me.daxterix.trms.dao;


import me.daxterix.trms.config.Config;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ObjectDAOTest {
    static final double delta = .0001;
    ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    RequestDAO requestDao;
    RequestInfoDAO reqInfoDao;
    EmployeeDAO empDao;
    EmployeeAccount acc1, acc2;
    Employee emp1, emp2;
    ObjectDAO objectDao;
    ReimbursementRequest request1;

    {
        requestDao = context.getBean(RequestDAO.class);
        reqInfoDao = context.getBean(RequestInfoDAO.class);
        empDao = context.getBean(EmployeeDAO.class);
        objectDao = context.getBean(ObjectDAO.class);
    }

    void cleanup() {
        try {
            empDao.deleteAccount(acc1.getEmail());
            empDao.deleteAccount(acc2.getEmail());
            requestDao.deleteRequest(request1.getId());
        }
        catch (NonExistentIdException e) {
            //
        }
    }

    ReimbursementRequest persistRequest(Employee filer, String reqStatus, String eventType) throws DuplicateIdException {
        ReimbursementRequest req = new ReimbursementRequest();
        req.setFiler(filer);
        req.setStatus(new RequestStatus(reqStatus));
        EventType type = new EventType();
        type.setType(eventType);
        req.setEventType(type);

        req.setDescription("this is just something for testing.");
        req.setEventCost(20);
        req.setFunding(100);
        req.setEventStart(LocalDate.of(12, 1, 1));
        req.setEventEnd(LocalDate.of(12, 1, 1));

        req.setUrgent(false);
        req.setTimeFiled(LocalDateTime.of(11, 12, 15, 12, 12));
        requestDao.save(req);
        return req;
    }

    void createAccounts() throws DuplicateIdException {
        acc1 = new EmployeeAccount();
        acc1.setEmail("test");
        acc1.setPassword("test");
        empDao.save(acc1);

        acc2 = new EmployeeAccount();
        acc2.setEmail("test2");
        acc2.setPassword("test2");
        empDao.save(acc2);
    }

    void createEmployees() throws DuplicateIdException {
        emp1 = new Employee();
        emp1.setAccount(acc1);
        emp1.setDepartment(new Department(Department.RAVENCLAW));
        emp1.setFirstname("first_name");
        emp1.setLastname("last_name");
        emp1.setAvailableFunds(1000);
        emp1.setRank(new EmployeeRank(EmployeeRank.BENCO));
        empDao.save(emp1);

        emp2 = new Employee();
        emp2.setAccount(acc2);
        emp2.setSupervisor(emp1);
        emp2.setDepartment(new Department(Department.SLYTHERIN));
        emp2.setFirstname("first_name2");
        emp2.setLastname("last_name2");
        emp2.setAvailableFunds(1000);
        emp2.setRank(new EmployeeRank(EmployeeRank.REGULAR));
        empDao.save(emp2);
    }

    public void setup() throws DuplicateIdException {
        //cleanup();
        createAccounts();
        createEmployees();
        request1 = persistRequest(emp1, RequestStatus.AWAITING_SUPERVISOR, EventType.UNIVERSITY_COURSE);
    }

    public void tearDown() {
        cleanup();
    }
}