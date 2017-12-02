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
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RequestDAOTest {


    private static final double delta = .0001;
    private ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    private RequestDAO requestDao;
    private RequestFileDAO fileDao;
    private EmployeeDAO empDao;
    private EmployeeAccount acc1, acc2;
    private Employee emp1, emp2;
    private ObjectDAO objectDao;
    private EventType universityCourseEventType;
    private ReimbursementRequest request;

    {
        requestDao = context.getBean(RequestDAO.class);
        fileDao = context.getBean(RequestFileDAO.class);
        empDao = context.getBean(EmployeeDAO.class);
        objectDao = context.getBean(ObjectDAO.class);
    }

    void cleanup() {
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
            requestDao.deleteRequest(request.getId());
        } catch (NonExistentIdException e) {
            //
        }
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

    void createRequest() throws DuplicateIdException {
        universityCourseEventType = new EventType();
        universityCourseEventType.setType(EventType.UNIVERSITY_COURSE);

        request = new ReimbursementRequest();
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
    }

    @Before
    public void setup() throws DuplicateIdException {
        //cleanup();
        createAccounts();
        createEmployees();
        createRequest();
    }

    @After
    public void tearDown() {
        cleanup();
    }

    @Test
    public void saveAndGet() throws Exception {
        ReimbursementRequest persisted = requestDao.getRequestById(request.getId());
        assertEquals(request.getId(), persisted.getId());

        RequestFile file = new RequestFile();
        file.setMimeType(new MimeType(MimeType.PNG));
        file.setPurpose(new FilePurpose(FilePurpose.EVENT_INFO));
        file.setRequest(request);
        objectDao.saveObject(file);

        List<RequestFile> requestFiles = fileDao.getFilesForRequest(request.getId());
        assertEquals(1, requestFiles.size());
        assertEquals(file.getId(), requestFiles.get(0).getId());

        fileDao.deleteFile(file.getId());
    }

    @Test(expected=NonExistentIdException.class)
    public void deleteRequest() throws Exception {
        ReimbursementRequest persisted = requestDao.getRequestById(request.getId());
        assertEquals(request.getId(), persisted.getId());

        requestDao.deleteRequest(request.getId());
        requestDao.getRequestById(request.getId());     // NonExistentIdException
    }

    @Test(expected=NonExistentIdException.class)
    public void deleteRequestCheckFiles() throws Exception {
        RequestFile file = new RequestFile();
        file.setMimeType(new MimeType(MimeType.PNG));
        file.setPurpose(new FilePurpose(FilePurpose.EVENT_INFO));
        file.setRequest(request);
        objectDao.saveObject(file);

        requestDao.deleteRequest(request.getId());
        fileDao.getFilesForRequest(request.getId());    // NonExistentIdException
    }

    @Test
    public void update() throws Exception {
        ReimbursementRequest persisted = requestDao.getRequestById(request.getId());
        assertEquals(request.getId(), persisted.getId());
        assertEquals(request.getEventCost(), persisted.getEventCost(), delta);
        assertEquals(request.getFunding(), persisted.getFunding(), delta);
        assertEquals(request.getId(), persisted.getId());

        request.setEventCost(200);
        request.setFunding(80);
        requestDao.update(request);
        persisted = requestDao.getRequestById(request.getId());
        assertEquals(request.getId(), persisted.getId());
        assertEquals(request.getEventCost(), persisted.getEventCost(), delta);
        assertEquals(request.getFunding(), persisted.getFunding(), delta);
    }

    @Test(expected=NonExistentIdException.class)
    public void updateNonExistent() throws Exception {
        ReimbursementRequest example = new ReimbursementRequest();
        example.setId(-1);

        requestDao.update(example);
    }

    @Test
    public void getFiledRequests() throws Exception {
        // add to emp1 who now has two filed request
        ReimbursementRequest request2 = new ReimbursementRequest();
        request2.setDescription("this is just something for testing.");
        request2.setEventCost(20);
        request2.setStatus(new RequestStatus(RequestStatus.GRANTED));
        request2.setFunding(100);
        request2.setEventStart(LocalDate.of(12, 1, 1));
        request2.setEventEnd(LocalDate.of(12, 1, 1));
        request2.setFiler(emp1);
        request2.setEventType(universityCourseEventType);
        request2.setUrgent(false);
        request2.setTimeFiled(LocalDateTime.of(11, 12, 15, 12, 12));
        requestDao.save(request2);

        // add to emp2 who now has one filed request
        ReimbursementRequest request3 = new ReimbursementRequest();
        request3.setDescription("this is just something for testing.");
        request3.setEventCost(20);
        request3.setStatus(new RequestStatus(RequestStatus.PENDING_GRADE));
        request3.setFunding(100);
        request3.setEventStart(LocalDate.of(12, 1, 1));
        request3.setEventEnd(LocalDate.of(12, 1, 1));
        request3.setFiler(emp2);
        request3.setEventType(universityCourseEventType);
        request3.setUrgent(false);
        request3.setTimeFiled(LocalDateTime.of(11, 12, 15, 12, 12));
        requestDao.save(request3);

        List<Long> reqsFor1 = requestDao.getFiledRequests(acc1.getEmail()).stream()
                .map(req -> req.getId())
                .collect(Collectors.toList());

        assertEquals(2, reqsFor1.size());
        assertTrue(reqsFor1.contains(request.getId()));
        assertTrue(reqsFor1.contains(request2.getId()));

        List<ReimbursementRequest> reqsFor2 = requestDao.getFiledRequests(acc2.getEmail());
        assertEquals(1, reqsFor1.size());
        assertEquals(request3.getId(), reqsFor2.get(0).getId());

        requestDao.deleteRequest(request2.getId());
        requestDao.deleteRequest(request3.getId());
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