package me.daxterix.trms.dao;

import me.daxterix.trms.config.Config;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RequestDAOTest {


    private ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    private RequestDAO requestDao;
    private EmployeeDAO empDao;
    private EmployeeAccount acc1, acc2;
    private Address address;
    private ObjectDAO objectDao;

    {
        requestDao = context.getBean(RequestDAO.class);
        empDao = context.getBean(EmployeeDAO.class);
    }

    @Before
    public void setup() throws DuplicateIdException {

        acc1 = new EmployeeAccount();
        acc1.setEmail("test");
        acc1.setPassword("test");
        empDao.save(acc1);

        Employee emp = new Employee();
        emp.setAccount(acc1);
        emp.setDepartment(new Department(Department.RAVENCLAW));
        emp.setFirstname("first_name");
        emp.setLastname("last_name");
        emp.setAvailableFunds(1000);
        emp.setRank(new EmployeeRank(EmployeeRank.BENCO));

        empDao.save(emp);

        acc2 = new EmployeeAccount();
        acc2.setEmail("test2");
        acc2.setPassword("test2");
        empDao.save(acc2);

        Employee emp2 = new Employee();
        emp2.setAccount(acc2);
        emp2.setDepartment(new Department(Department.RAVENCLAW));
        emp2.setFirstname("first_name2");
        emp2.setLastname("last_name2");
        emp2.setAvailableFunds(1000);
        emp2.setRank(new EmployeeRank(EmployeeRank.BENCO));
        empDao.save(emp2);

        address = new Address();
        address.setAddress("87 Ube Road");
        address.setCity("Chicago");
        address.setState("Illinois");
        address.setZip("48890");
        long addrId = (Long) objectDao.saveObject(address);
        address.setId(addrId);
    }

    @After
    public void tearDown() {
        try {
            empDao.deleteAccount(acc1.getEmail());
        } catch (NonExistentIdException e) {
            e.printStackTrace();
        }
        try {
            empDao.deleteAccount(acc2.getEmail());
        } catch (NonExistentIdException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void save() throws Exception {
        ReimbursementRequest request = new ReimbursementRequest();
        request.setAddress(address);
        request.setAddress(address);
        requestDao.save(request);
    }

    @Test
    public void deleteRequest() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void getFiledRequests() throws Exception {
    }

    @Test
    public void getPendingFiledRequests() throws Exception {
    }

    @Test
    public void getApprovedFiledRequests() throws Exception {
    }

    @Test
    public void getDeniedFiledRequests() throws Exception {
    }

    @Test
    public void getPendingGradeFiledRequests() throws Exception {
    }

    @Test
    public void getRequestsByDepartment() throws Exception {
    }

    @Test
    public void getPendingRequestsByDepartment() throws Exception {
    }

    @Test
    public void getApprovedRequestsByDepartment() throws Exception {
    }

    @Test
    public void getAllRequests() throws Exception {
    }

    @Test
    public void getAllPendingRequests() throws Exception {
    }

    @Test
    public void getAllApprovedRequests() throws Exception {
    }

}