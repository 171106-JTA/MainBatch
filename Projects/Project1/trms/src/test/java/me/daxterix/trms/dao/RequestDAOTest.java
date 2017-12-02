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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestDAOTest {


    private ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    private RequestDAO requestDao;
    private EmployeeDAO empDao;
    private EmployeeAccount acc1, acc2;
    private Employee emp1, emp2;
    private Address address;
    private ObjectDAO objectDao;
    private EventType universityCourseEventType;

    {
        requestDao = context.getBean(RequestDAO.class);
        empDao = context.getBean(EmployeeDAO.class);
        objectDao = context.getBean(ObjectDAO.class);
    }

    @Before
    public void setup() throws DuplicateIdException {

        try {
            empDao.deleteAccount("test");
        } catch (NonExistentIdException e) {
            //e.printStackTrace();
        }
        try {
            empDao.deleteAccount("test2");
        } catch (NonExistentIdException e) {
            //e.printStackTrace();
        }

        acc1 = new EmployeeAccount();
        acc1.setEmail("test");
        acc1.setPassword("test");
        empDao.save(acc1);

        emp1 = new Employee();
        emp1.setAccount(acc1);
        emp1.setDepartment(new Department(Department.RAVENCLAW));
        emp1.setFirstname("first_name");
        emp1.setLastname("last_name");
        emp1.setAvailableFunds(1000);
        emp1.setRank(new EmployeeRank(EmployeeRank.BENCO));
        empDao.save(emp1);

        acc2 = new EmployeeAccount();
        acc2.setEmail("test2");
        acc2.setPassword("test2");
        empDao.save(acc2);

        emp2 = new Employee();
        emp2.setAccount(acc2);
        emp2.setSupervisor(emp1);
        emp2.setDepartment(new Department(Department.SLYTHERIN));
        emp2.setFirstname("first_name2");
        emp2.setLastname("last_name2");
        emp2.setAvailableFunds(1000);
        emp2.setRank(new EmployeeRank(EmployeeRank.REGULAR));
        empDao.save(emp2);

        address = new Address();
        address.setAddress("87 Ube Road");
        address.setCity("Chicago");
        address.setState("Illinois");
        address.setZip("48890");
        objectDao.saveObject(address);

        universityCourseEventType = new EventType();
        universityCourseEventType.setType(EventType.UNIVERSITY_COURSE);

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
        try {
            objectDao.deleteObject(Address.class, address.getId());
        } catch (NonExistentIdException e) {
            //
        }
    }

    @Test
    public void saveAndGet() throws Exception {
        ReimbursementRequest request = new ReimbursementRequest();
        request.setAddress(address);
        request.setDescription("this is just something for testing.");
        request.setEventCost(20);
        request.setStatus(new RequestStatus(RequestStatus.AWAITING_SUPERVISOR));
        request.setFunding(100);
        request.setEventStart(LocalDate.of(12, 1, 1));
        request.setEventEnd(LocalDate.of(12, 1, 1));
        request.setFiler(emp1);
        request.setEventType(universityCourseEventType);
        request.setUrgent(false);
        request.setTimeFiled(LocalDateTime.of(11, 12, 15, 12, 12));

        try {
            long saveId = requestDao.save(request);

            boolean containsIt = false;
            List<ReimbursementRequest> persisted = requestDao.getAllRequests();
            for (ReimbursementRequest saved : persisted) {
                if (saved.getId() == saveId) {
                    containsIt = true;
                    break;
                }
            }
            assertTrue(containsIt);
        }
        finally {
            requestDao.deleteRequest(request.getId());
        }
    }

    @Test
    public void deleteRequest() throws Exception {
        ReimbursementRequest request = new ReimbursementRequest();
        request.setAddress(address);
        request.setDescription("this is just something for testing.");
        request.setEventCost(20);
        request.setStatus(new RequestStatus(RequestStatus.AWAITING_SUPERVISOR));
        request.setFunding(100);
        request.setEventStart(LocalDate.of(12, 1, 1));
        request.setEventEnd(LocalDate.of(12, 1, 1));
        request.setFiler(emp1);
        request.setEventType(universityCourseEventType);
        request.setUrgent(false);
        request.setTimeFiled(LocalDateTime.of(11, 12, 15, 12, 12));
        requestDao.save(request);

        RequestFile file = new RequestFile();
        file.setMimeType(new MimeType(MimeType.PNG));
        file.setPurpose(new FilePurpose(FilePurpose.EVENT_INFO));
        file.setRequest(request);
        objectDao.saveObject(request);

        boolean containsIt = false;
        List<ReimbursementRequest> persisted = requestDao.getAllRequests();
        for (ReimbursementRequest saved : persisted) {
            if (saved.getId() == request.getId()) {
                containsIt = true;
                break;
            }
        }
        assertTrue(containsIt);
        requestDao.deleteRequest(request.getId());

        containsIt = false;
        persisted = requestDao.getAllRequests();
        for (ReimbursementRequest saved : persisted) {
            if (saved.getId() == request.getId()) {
                containsIt = true;
                break;
            }
        }
        assertFalse(containsIt);
    }

    @Test
    public void update() throws Exception {
        ReimbursementRequest request = new ReimbursementRequest();
        request.setAddress(address);
        request.setDescription("this is just something for testing.");
        request.setEventCost(20);
        request.setStatus(new RequestStatus(RequestStatus.AWAITING_SUPERVISOR));
        request.setFunding(100);
        request.setEventStart(LocalDate.of(12, 1, 1));
        request.setEventEnd(LocalDate.of(12, 1, 1));
        request.setFiler(emp1);
        request.setEventType(universityCourseEventType);
        request.setUrgent(false);
        request.setTimeFiled(LocalDateTime.of(11, 12, 15, 12, 12));
        requestDao.save(request);

        RequestFile file = new RequestFile();
        file.setMimeType(new MimeType(MimeType.PNG));
        file.setPurpose(new FilePurpose(FilePurpose.EVENT_INFO));
        file.setRequest(request);
        objectDao.saveObject(request);

        boolean containsIt = false;
        List<ReimbursementRequest> persisted = requestDao.getAllRequests();
        for (ReimbursementRequest saved : persisted) {
            if (saved.getId() == request.getId()) {
                containsIt = true;
                break;
            }
        }
        assertTrue(containsIt);
        requestDao.deleteRequest(request.getId());

        containsIt = false;
        persisted = requestDao.getAllRequests();
        for (ReimbursementRequest saved : persisted) {
            if (saved.getId() == request.getId()) {
                containsIt = true;
                break;
            }
        }
        assertFalse(containsIt);
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