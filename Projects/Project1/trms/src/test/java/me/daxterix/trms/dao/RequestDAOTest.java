package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RequestDAOTest extends ObjectDAOTest {
    List<Long> requestsToDelete;

    @Before
    public void setup() throws DuplicateIdException {
        super.setup();
        requestsToDelete = new ArrayList<>();
    }

    @After
    public void tearDown() {
        super.tearDown();
        for (Long id : requestsToDelete)
            try {
                requestDao.deleteRequest(id);
            } catch (NonExistentIdException ignored) {
            }
    }

    @Test
    public void saveAndGet() throws Exception {
        ReimbursementRequest persisted = requestDao.getRequestById(request1.getId());
        assertEquals(request1.getId(), persisted.getId());
    }

    @Test(expected = DuplicateIdException.class)
    public void saveDuplicate() throws Exception {
        requestDao.save(request1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveWithInvalidEventType() throws Exception {
        persistRequest(emp1, RequestStatus.GRANTED, "Invalid EventType");
    }

    @Test(expected = NonExistentIdException.class)
    public void deleteRequest() throws Exception {
        ReimbursementRequest request2 = persistRequest(emp1, RequestStatus.GRANTED, EventType.UNIVERSITY_COURSE);

        ReimbursementRequest persisted = requestDao.getRequestById(request2.getId());
        assertEquals(request2.getId(), persisted.getId());

        requestDao.deleteRequest(request2.getId());
        requestDao.getRequestById(request2.getId());     // NonExistentIdException
    }

    @Test(expected = NonExistentIdException.class)
    public void deleteRequestCheckFiles() throws Exception {
        ReimbursementRequest request2 = persistRequest(emp1, RequestStatus.GRANTED, EventType.UNIVERSITY_COURSE);

        RequestFile file = new RequestFile();
        file.setMimeType(new MimeType(MimeType.PNG));
        file.setPurpose(new FilePurpose(FilePurpose.EVENT_INFO));
        file.setRequest(request2);
        objectDao.saveObject(file, RequestFile::getId);

        requestDao.deleteRequest(request2.getId());
        reqInfoDao.getFilesForRequest(request2.getId());    // NonExistentIdException
    }

    @Test
    public void update() throws Exception {
        ReimbursementRequest persisted = requestDao.getRequestById(request1.getId());
        assertEquals(request1.getId(), persisted.getId());
        assertEquals(request1.getEventCost(), persisted.getEventCost(), delta);
        assertEquals(request1.getFunding(), persisted.getFunding(), delta);
        assertEquals(request1.getId(), persisted.getId());

        request1.setEventCost(200);
        request1.setFunding(80);
        requestDao.update(request1);
        persisted = requestDao.getRequestById(request1.getId());
        assertEquals(request1.getId(), persisted.getId());
        assertEquals(request1.getEventCost(), persisted.getEventCost(), delta);
        assertEquals(request1.getFunding(), persisted.getFunding(), delta);
    }

    @Test(expected = NonExistentIdException.class)
    public void updateNonExistent() throws Exception {
        ReimbursementRequest example = new ReimbursementRequest();
        example.setId(-1);

        requestDao.update(example);
    }

    @Test
    public void getFiledRequests() throws Exception {
        // add to emp1 who now has two filed request
        ReimbursementRequest request2 = persistRequest(emp1, RequestStatus.GRANTED, EventType.UNIVERSITY_COURSE);

        // add to emp2 who now has one filed request
        ReimbursementRequest request3 = persistRequest(emp2, RequestStatus.AWAITING_GRADE, EventType.UNIVERSITY_COURSE);

        List<Long> reqsFor1 = mapReqsToIds(requestDao.getFiledRequests(acc1.getEmail()));

        assertEquals(2, reqsFor1.size());
        assertTrue(reqsFor1.contains(request1.getId()));
        assertTrue(reqsFor1.contains(request2.getId()));

        List<ReimbursementRequest> reqsFor2 = requestDao.getFiledRequests(acc2.getEmail());
        assertEquals(1, reqsFor2.size());
        assertEquals(request3.getId(), reqsFor2.get(0).getId());

        requestDao.deleteRequest(request2.getId());
        requestDao.deleteRequest(request3.getId());
    }

    @Test
    public void getPendingFiledRequests() throws Exception {
        // add granted request to emp1, not a pending request
        ReimbursementRequest request2 = persistRequest(emp1, RequestStatus.GRANTED, EventType.UNIVERSITY_COURSE);

        // add pending request to emp1
        ReimbursementRequest request3 = persistRequest(emp2, RequestStatus.AWAITING_GRADE, EventType.UNIVERSITY_COURSE);

        // add pending request to emp1
        ReimbursementRequest request4 = persistRequest(emp2, RequestStatus.AWAITING_SUPERVISOR, EventType.UNIVERSITY_COURSE);

        List<ReimbursementRequest> pendingFor1 = requestDao.getPendingFiledRequests(emp1.getEmail());
        assertEquals(1, pendingFor1.size());
        assertEquals(request1.getId(), pendingFor1.get(0).getId());

        List<Long> pendingFor2 = mapReqsToIds(requestDao.getPendingFiledRequests(emp2.getEmail()));
        assertEquals(2, pendingFor2.size());
        assertTrue(pendingFor2.contains(request3.getId()));
        assertTrue(pendingFor2.contains(request4.getId()));

        requestDao.deleteRequest(request2.getId());
        requestDao.deleteRequest(request3.getId());
    }

    @Test
    public void getFiledRequestsByStatus() throws Exception {
        int size = 2;
        ReimbursementRequest[] approvedFor1 = new ReimbursementRequest[size];
        ReimbursementRequest[] approvedFor2 = new ReimbursementRequest[size];
        ReimbursementRequest[] deniedFor1 = new ReimbursementRequest[size];

        for (int i = 0; i < size; ++i) {
            approvedFor1[i] = persistRequest(emp1, RequestStatus.GRANTED, EventType.UNIVERSITY_COURSE);
            deniedFor1[i] = persistRequest(emp1, RequestStatus.DENIED, EventType.UNIVERSITY_COURSE);
            approvedFor2[i] = persistRequest(emp2, RequestStatus.GRANTED, EventType.UNIVERSITY_COURSE);
        }

        List<Long> res1 = mapReqsToIds(requestDao.getFiledRequestsByStatus(emp1.getEmail(), RequestStatus.GRANTED));
        assertEquals(size, res1.size());
        assertTrue(res1.containsAll(mapReqsToIds(Arrays.asList(approvedFor1))));

        List<Long> res1_2 = mapReqsToIds(requestDao.getFiledRequestsByStatus(emp1.getEmail(), RequestStatus.DENIED));
        assertEquals(size, res1_2.size());
        assertTrue(res1_2.containsAll(mapReqsToIds(Arrays.asList(deniedFor1))));

        List<Long> res2 = mapReqsToIds(requestDao.getFiledRequestsByStatus(emp2.getEmail(), RequestStatus.GRANTED));
        assertEquals(size, res2.size());
        assertTrue(res2.containsAll(mapReqsToIds(Arrays.asList(approvedFor2))));
    }


    @Test
    public void getRequestsByDepartment() throws Exception {
        int size = 2;
        ReimbursementRequest[] reqsFor1 = new ReimbursementRequest[size];
        ReimbursementRequest[] reqsFor2 = new ReimbursementRequest[size];
        ReimbursementRequest[] moreReqsFor2 = new ReimbursementRequest[size];

        for (int i = 0; i < size; ++i) {
            reqsFor1[i] = persistRequest(emp1, RequestStatus.AWAITING_GRADE, EventType.UNIVERSITY_COURSE);
            reqsFor2[i] = persistRequest(emp2, RequestStatus.DENIED, EventType.UNIVERSITY_COURSE);
            moreReqsFor2[i] = persistRequest(emp2, RequestStatus.AWAITING_SUPERVISOR, EventType.UNIVERSITY_COURSE);
        }

        List<Long> res1 = mapReqsToIds(requestDao.getRequestsByDepartment(Department.RAVENCLAW));
        assertTrue(reqsFor1.length + 1 <= res1.size());  // +1 for request in setup()
        assertTrue(res1.containsAll(mapReqsToIds(Arrays.asList(reqsFor1))));
        assertTrue(res1.contains(request1.getId()));

        List<Long> res2 = mapReqsToIds(requestDao.getRequestsByDepartment(Department.SLYTHERIN));
        assertTrue(reqsFor2.length + moreReqsFor2.length <= res2.size());
        assertTrue(res2.containsAll(mapReqsToIds(Arrays.asList(reqsFor2))));
        assertTrue(res2.containsAll(mapReqsToIds(Arrays.asList(moreReqsFor2))));
    }

    @Test
    public void getPendingRequestsByDepartment() throws Exception {
        int size = 2;
        ReimbursementRequest[] pendingFor1 = new ReimbursementRequest[size];
        ReimbursementRequest[] grantedFor2 = new ReimbursementRequest[size];
        ReimbursementRequest[] pendingFor2 = new ReimbursementRequest[size];

        for (int i = 0; i < size; ++i) {
            pendingFor1[i] = persistRequest(emp1, RequestStatus.AWAITING_GRADE, EventType.UNIVERSITY_COURSE);
            grantedFor2[i] = persistRequest(emp2, RequestStatus.GRANTED, EventType.UNIVERSITY_COURSE);
            pendingFor2[i] = persistRequest(emp2, RequestStatus.AWAITING_SUPERVISOR, EventType.UNIVERSITY_COURSE);
        }

        List<Long> pendingRaven = mapReqsToIds(requestDao.getPendingRequestsByDepartment(Department.RAVENCLAW));
        assertTrue(pendingFor1.length + 1 <= pendingRaven.size());  // +1 for request in setup()
        assertTrue(pendingRaven.containsAll(mapReqsToIds(Arrays.asList(pendingFor1))));
        assertTrue(pendingRaven.contains(request1.getId()));
        for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(pendingRaven.contains(id));
        for (long id : mapReqsToIds(Arrays.asList(pendingFor2))) assertFalse(pendingRaven.contains(id));

        List<Long> pendingSlyth = mapReqsToIds(requestDao.getPendingRequestsByDepartment(Department.SLYTHERIN));
        assertTrue(pendingFor2.length <= pendingSlyth.size());
        assertTrue(pendingSlyth.containsAll(mapReqsToIds(Arrays.asList(pendingFor2))));
        assertFalse(pendingSlyth.contains(request1.getId()));
        for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(pendingSlyth.contains(id));
        for (long id : mapReqsToIds(Arrays.asList(pendingFor1))) assertFalse(pendingSlyth.contains(id));
    }

    @Test
    public void getDepartmentRequestsByStatus() throws Exception {
        int size = 2;
        ReimbursementRequest[] gradeFor1 = new ReimbursementRequest[size];
        ReimbursementRequest[] grantedFor2 = new ReimbursementRequest[size];
        ReimbursementRequest[] supeFor2 = new ReimbursementRequest[size];

        for (int i = 0; i < size; ++i) {
            gradeFor1[i] = persistRequest(emp1, RequestStatus.AWAITING_GRADE, EventType.UNIVERSITY_COURSE);
            grantedFor2[i] = persistRequest(emp2, RequestStatus.GRANTED, EventType.SEMINAR);
            supeFor2[i] = persistRequest(emp2, RequestStatus.AWAITING_SUPERVISOR, EventType.OTHER);
        }

        List<Long> ravenGrade = mapReqsToIds(requestDao.getDepartmentRequestsByStatus(Department.RAVENCLAW, RequestStatus.AWAITING_GRADE));
        assertTrue(gradeFor1.length <= ravenGrade.size());
        assertTrue(ravenGrade.containsAll(mapReqsToIds(Arrays.asList(gradeFor1))));
        assertFalse(ravenGrade.contains(request1.getId()));
        for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(ravenGrade.contains(id));
        for (long id : mapReqsToIds(Arrays.asList(supeFor2))) assertFalse(ravenGrade.contains(id));

        List<Long> ravenSupe = mapReqsToIds(requestDao.getDepartmentRequestsByStatus(Department.RAVENCLAW, RequestStatus.AWAITING_SUPERVISOR));
        assertTrue(1 <= ravenSupe.size());
        assertTrue(ravenSupe.contains(request1.getId()));
        for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(ravenSupe.contains(id));
        for (long id : mapReqsToIds(Arrays.asList(gradeFor1))) assertFalse(ravenSupe.contains(id));
        for (long id : mapReqsToIds(Arrays.asList(supeFor2))) assertFalse(ravenSupe.contains(id));

        List<Long> slythSupe = mapReqsToIds(requestDao.getDepartmentRequestsByStatus(Department.SLYTHERIN, RequestStatus.AWAITING_SUPERVISOR));
        assertTrue(supeFor2.length <= slythSupe.size());
        assertTrue(slythSupe.containsAll(mapReqsToIds(Arrays.asList(supeFor2))));
        assertFalse(slythSupe.contains(request1.getId()));
        for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(slythSupe.contains(id));
        for (long id : mapReqsToIds(Arrays.asList(gradeFor1))) assertFalse(slythSupe.contains(id));

        List<Long> slythGranted = mapReqsToIds(requestDao.getDepartmentRequestsByStatus(Department.SLYTHERIN, RequestStatus.GRANTED));
        assertTrue(grantedFor2.length <= slythGranted.size());
        assertTrue(slythGranted.containsAll(mapReqsToIds(Arrays.asList(grantedFor2))));
        assertFalse(slythGranted.contains(request1.getId()));
        for (long id : mapReqsToIds(Arrays.asList(supeFor2))) assertFalse(slythGranted.contains(id));
        for (long id : mapReqsToIds(Arrays.asList(gradeFor1))) assertFalse(slythGranted.contains(id));
    }

    @Test
    public void getAllRequests() throws Exception {
        int size = 2;
        ReimbursementRequest[] gradeFor1 = new ReimbursementRequest[size];
        ReimbursementRequest[] grantedFor2 = new ReimbursementRequest[size];
        ReimbursementRequest[] supeFor2 = new ReimbursementRequest[size];

        for (int i = 0; i < size; ++i) {
            gradeFor1[i] = persistRequest(emp1, RequestStatus.AWAITING_GRADE, EventType.UNIVERSITY_COURSE);
            grantedFor2[i] = persistRequest(emp2, RequestStatus.GRANTED, EventType.OTHER);
            supeFor2[i] = persistRequest(emp2, RequestStatus.AWAITING_SUPERVISOR, EventType.OTHER);
        }

        List<Long> saved = mapReqsToIds(requestDao.getAllRequests());
        assertTrue(gradeFor1.length + grantedFor2.length + supeFor2.length + 1 <= saved.size());
        assertTrue(saved.containsAll(mapReqsToIds(Arrays.asList(gradeFor1))));
        assertTrue(saved.containsAll(mapReqsToIds(Arrays.asList(supeFor2))));
        assertTrue(saved.containsAll(mapReqsToIds(Arrays.asList(grantedFor2))));
        assertTrue(saved.contains(request1.getId()));

    }

    @Test
    public void getRequestsByStatus() throws Exception {
        int size = 2;
        ReimbursementRequest[] deniedFor1 = new ReimbursementRequest[size];
        ReimbursementRequest[] grantedFor2 = new ReimbursementRequest[size];
        ReimbursementRequest[] supeFor2 = new ReimbursementRequest[size];

        for (int i = 0; i < size; ++i) {
            deniedFor1[i] = persistRequest(emp1, RequestStatus.DENIED, EventType.UNIVERSITY_COURSE);
            grantedFor2[i] = persistRequest(emp2, RequestStatus.GRANTED, EventType.OTHER);
            supeFor2[i] = persistRequest(emp2, RequestStatus.AWAITING_SUPERVISOR, EventType.OTHER);
        }

        List<Long> ravenGrade = mapReqsToIds(requestDao.getRequestsByStatus(RequestStatus.DENIED));
        assertTrue(deniedFor1.length <= ravenGrade.size());
        assertTrue(ravenGrade.containsAll(mapReqsToIds(Arrays.asList(deniedFor1))));
        assertFalse(ravenGrade.contains(request1.getId()));
        for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(ravenGrade.contains(id));
        for (long id : mapReqsToIds(Arrays.asList(supeFor2))) assertFalse(ravenGrade.contains(id));

        List<Long> slythSupe = mapReqsToIds(requestDao.getRequestsByStatus(RequestStatus.AWAITING_SUPERVISOR));
        assertTrue(supeFor2.length + 1 <= slythSupe.size());
        assertTrue(slythSupe.containsAll(mapReqsToIds(Arrays.asList(supeFor2))));
        assertTrue(slythSupe.contains(request1.getId()));
        for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(slythSupe.contains(id));
        for (long id : mapReqsToIds(Arrays.asList(deniedFor1))) assertFalse(slythSupe.contains(id));

        List<Long> slythGranted = mapReqsToIds(requestDao.getRequestsByStatus(RequestStatus.GRANTED));
        assertTrue(grantedFor2.length <= slythGranted.size());
        assertTrue(slythGranted.containsAll(mapReqsToIds(Arrays.asList(grantedFor2))));
        assertFalse(slythGranted.contains(request1.getId()));
        for (long id : mapReqsToIds(Arrays.asList(supeFor2))) assertFalse(slythGranted.contains(id));
        for (long id : mapReqsToIds(Arrays.asList(deniedFor1))) assertFalse(slythGranted.contains(id));
    }

    @Test
    public void getPendingRequests() throws Exception {
        int size = 2;
        ReimbursementRequest[] deniedFor1 = new ReimbursementRequest[size];
        ReimbursementRequest[] grantedFor2 = new ReimbursementRequest[size];
        ReimbursementRequest[] supeFor2 = new ReimbursementRequest[size];

        for (int i = 0; i < size; ++i) {
            deniedFor1[i] = persistRequest(emp1, RequestStatus.DENIED, EventType.SEMINAR);
            grantedFor2[i] = persistRequest(emp2, RequestStatus.GRANTED, EventType.OTHER);
            supeFor2[i] = persistRequest(emp2, RequestStatus.AWAITING_SUPERVISOR, EventType.OTHER);
        }

        List<Long> ravenGrade = mapReqsToIds(requestDao.getPendingRequests());
        assertTrue(supeFor2.length + 1 <= ravenGrade.size());
        assertTrue(ravenGrade.containsAll(mapReqsToIds(Arrays.asList(supeFor2))));
        assertTrue(ravenGrade.contains(request1.getId()));

        for (long id : mapReqsToIds(Arrays.asList(deniedFor1))) assertFalse(ravenGrade.contains(id));
        for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(ravenGrade.contains(id));
    }

    @Test
    public void getWaitingOnSupervisor() throws Exception {
        String acc3Email = "supe";
        try {
            EmployeeAccount acc3 = persistAccount(acc3Email, "supe");
            Employee emp3 = persistEmployee(acc3, emp2, Department.SLYTHERIN, EmployeeRank.SUPERVISOR);

            int size = 2;
            ReimbursementRequest[] awitingEmp1Benco = new ReimbursementRequest[size];
            ReimbursementRequest[] grantedFor2 = new ReimbursementRequest[size];
            ReimbursementRequest[] awaitingEmp1Supe = new ReimbursementRequest[size];
            ReimbursementRequest[] awaitingEmp2Supe = new ReimbursementRequest[size];

            for (int i = 0; i < size; ++i) {
                grantedFor2[i] = persistRequest(emp1, RequestStatus.GRANTED, EventType.OTHER);  // irrelevant for test
                // emp1 has two requests waiting on him as a BENCO
                awitingEmp1Benco[i] = persistRequest(emp1, RequestStatus.AWAITING_BENCO, EventType.SEMINAR);
                // emp1 has two requests waiting on him as a direct supervisor
                awaitingEmp1Supe[i] = persistRequest(emp2, RequestStatus.AWAITING_SUPERVISOR, EventType.OTHER);
                // emp2 now has two requests waiting on him
                awaitingEmp2Supe[i] = persistRequest(emp3, RequestStatus.AWAITING_SUPERVISOR, EventType.TECH_TRAINING);
            }

            List<Long> waitingOn1Supe = mapReqsToIds(requestDao.getWaitingOnSupervisor(emp1.getEmail()));
            assertEquals(awaitingEmp1Supe.length, waitingOn1Supe.size());  // +1 for request1 in ObjectDAOTest
            assertTrue(waitingOn1Supe.containsAll(mapReqsToIds(Arrays.asList(awaitingEmp1Supe))));
            for (long id : mapReqsToIds(Arrays.asList(awitingEmp1Benco))) assertFalse(waitingOn1Supe.contains(id));
            for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(waitingOn1Supe.contains(id));
            for (long id : mapReqsToIds(Arrays.asList(awaitingEmp2Supe))) assertFalse(waitingOn1Supe.contains(id));

            List<Long> waitingOn2Supe = mapReqsToIds(requestDao.getWaitingOnSupervisor(emp2.getEmail()));
            assertEquals(awaitingEmp2Supe.length, waitingOn2Supe.size());
            assertTrue(waitingOn1Supe.containsAll(mapReqsToIds(Arrays.asList(awaitingEmp1Supe))));
            for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(waitingOn2Supe.contains(id));
            for (long id : mapReqsToIds(Arrays.asList(awitingEmp1Benco))) assertFalse(waitingOn2Supe.contains(id));
            for (long id : mapReqsToIds(Arrays.asList(awaitingEmp1Supe))) assertFalse(waitingOn2Supe.contains(id));
        } finally {
            empDao.deleteAccount(acc3Email);
        }
    }

    @Test
    public void getWaitingOnDepartmentHead() throws Exception {
        String acc3Email = "supe";
        try {
            EmployeeAccount acc3 = persistAccount(acc3Email, "supe");
            Employee emp3 = persistEmployee(acc3, emp2, Department.SLYTHERIN, EmployeeRank.DEPARTMENT_HEAD);

            int size = 2;
            ReimbursementRequest[] awitingEmp1Benco = new ReimbursementRequest[size];
            ReimbursementRequest[] grantedFor2 = new ReimbursementRequest[size];
            ReimbursementRequest[] awaitingEmp1Supe = new ReimbursementRequest[size];
            ReimbursementRequest[] awaitingEmp2Supe = new ReimbursementRequest[size];

            for (int i = 0; i < size; ++i) {
                grantedFor2[i] = persistRequest(emp1, RequestStatus.GRANTED, EventType.OTHER);  // irrelevant for test
                // emp1 has two requests waiting on him as a BENCO
                awitingEmp1Benco[i] = persistRequest(emp1, RequestStatus.AWAITING_DEPT_HEAD, EventType.SEMINAR);
                // emp1 has two requests waiting on him as a direct supervisor
                awaitingEmp1Supe[i] = persistRequest(emp2, RequestStatus.AWAITING_SUPERVISOR, EventType.OTHER);
                // emp2 now has two requests waiting on him
                awaitingEmp2Supe[i] = persistRequest(emp3, RequestStatus.AWAITING_SUPERVISOR, EventType.TECH_TRAINING);
            }

            List<Long> waitingOn1Supe = mapReqsToIds(requestDao.getWaitingOnSupervisor(emp1.getEmail()));
            assertEquals(awaitingEmp1Supe.length, waitingOn1Supe.size());  // +1 for request1 in ObjectDAOTest
            assertTrue(waitingOn1Supe.containsAll(mapReqsToIds(Arrays.asList(awaitingEmp1Supe))));
            for (long id : mapReqsToIds(Arrays.asList(awitingEmp1Benco))) assertFalse(waitingOn1Supe.contains(id));
            for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(waitingOn1Supe.contains(id));
            for (long id : mapReqsToIds(Arrays.asList(awaitingEmp2Supe))) assertFalse(waitingOn1Supe.contains(id));

            List<Long> waitingOn2Supe = mapReqsToIds(requestDao.getWaitingOnSupervisor(emp2.getEmail()));
            assertEquals(awaitingEmp2Supe.length, waitingOn2Supe.size());
            assertTrue(waitingOn1Supe.containsAll(mapReqsToIds(Arrays.asList(awaitingEmp1Supe))));
            for (long id : mapReqsToIds(Arrays.asList(grantedFor2))) assertFalse(waitingOn2Supe.contains(id));
            for (long id : mapReqsToIds(Arrays.asList(awitingEmp1Benco))) assertFalse(waitingOn2Supe.contains(id));
            for (long id : mapReqsToIds(Arrays.asList(awaitingEmp1Supe))) assertFalse(waitingOn2Supe.contains(id));
        } finally {
            empDao.deleteAccount(acc3Email);
        }
    }


    @Test
    public void testUpdateOldToUrgent() throws Exception {
        ReimbursementRequest shouldBeUpdated = persistRequest(emp1, RequestStatus.AWAITING_DEPT_HEAD, EventType.OTHER);  // irrelevant for test
        shouldBeUpdated.setTimeFiled(LocalDateTime.of(LocalDate.of(2017, 2, 1), LocalTime.of(12, 0, 0)));
        shouldBeUpdated.setEventStart(LocalDate.of(2017, 12, 1));
        shouldBeUpdated.setEventEnd(LocalDate.of(2017, 12, 1));
        shouldBeUpdated.setUrgent(false);
        requestDao.update(shouldBeUpdated);

        ReimbursementRequest shouldNotBeUpdated = persistRequest(emp1, RequestStatus.AWAITING_SUPERVISOR, EventType.UNIVERSITY_COURSE);
        shouldNotBeUpdated.setTimeFiled(LocalDateTime.now());
        shouldNotBeUpdated.setUrgent(false);
        shouldNotBeUpdated.setTimeFiled(LocalDateTime.of(LocalDate.of(2017, 3, 1), LocalTime.of(12, 0, 0)));
        shouldNotBeUpdated.setEventStart(LocalDate.of(2017, 12, 1));
        shouldNotBeUpdated.setEventEnd(LocalDate.of(2017, 12, 1));
        requestDao.update(shouldNotBeUpdated);

        requestDao.updateOlderToUrgent(LocalDate.of(2017, 2, 28));

        assertTrue(requestDao.getRequestById(shouldBeUpdated.getId()).isUrgent());
        assertFalse(requestDao.getRequestById(shouldNotBeUpdated.getId()).isUrgent());
        // these are deleted when accounts are deleted
    }


    public List<Long> mapReqsToIds(List<ReimbursementRequest> reqs) {
        return reqs.stream()
                .map(ReimbursementRequest::getId)
                .collect(Collectors.toList());
    }

}