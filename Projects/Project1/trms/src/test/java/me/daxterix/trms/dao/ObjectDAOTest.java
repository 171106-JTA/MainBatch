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
            requestDao.deleteRequest(request1.getId());
            empDao.deleteAccount(acc1.getEmail());
            empDao.deleteAccount(acc2.getEmail());
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

    EmployeeAccount persistAccount(String email, String pass) throws DuplicateIdException {
        EmployeeAccount acc = new EmployeeAccount();
        acc.setEmail(email);
        acc.setPassword(pass);
        empDao.save(acc);
        return acc;
    }

    void createAccounts() throws DuplicateIdException {
        acc1 =  persistAccount("test", "test");
        acc2 = persistAccount("test2", "test2");
    }

    Employee persistEmployee(EmployeeAccount account, Employee supervisor, String department, String rank) throws DuplicateIdException {
        Employee emp = new Employee();
        emp.setAccount(account);
        emp.setFirstname("first_name");
        emp.setLastname("last_name");
        emp.setAvailableFunds(1000);
        emp.setDepartment(new Department(department));
        emp.setRank(new EmployeeRank(rank));
        emp.setSupervisor(supervisor);
        empDao.save(emp);
        return emp;
    }

    void createEmployees() throws DuplicateIdException {
        emp1 = persistEmployee(acc1, null, Department.RAVENCLAW, EmployeeRank.BENCO);

        // don't think BENCOs have departments, but doesn't matter for the tests below
        emp2 = persistEmployee(acc2, emp1, Department.SLYTHERIN, EmployeeRank.REGULAR);
    }

    public void setup() throws DuplicateIdException {
        //cleanup();
        createAccounts();
        createEmployees();
        request1 = persistRequest(emp1, RequestStatus.AWAITING_SUPERVISOR, EventType.UNIVERSITY_COURSE);
    }


    RequestFile persistFile(String filePurpose, String mimeType, ReimbursementRequest req) throws DuplicateIdException {
        RequestFile file = new RequestFile();
        file.setMimeType(new MimeType(mimeType));
        file.setPurpose(new FilePurpose(filePurpose));
        file.setRequest(req);
        reqInfoDao.saveFile(file);
        return file;
    }

    public void tearDown() {
        cleanup();
    }
}