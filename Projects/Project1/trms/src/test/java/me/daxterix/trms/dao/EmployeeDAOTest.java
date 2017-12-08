package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EmployeeDAOTest extends ObjectDAOTest {
    @Before
    public void setUp() throws DuplicateIdException {
        super.setup();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test   // check that deleting employees leaves the resolved requests alone
    public void deleteEmployeeLeaveResolvedRequests() throws DuplicateIdException, NonExistentIdException {
        ReimbursementRequest r1 = null, r2 = null, r3 = null, r4 = null;
        try {
            r1 = persistRequest(emp1, RequestStatus.GRANTED, EventType.UNIVERSITY_COURSE);
            r2 = persistRequest(emp1, RequestStatus.DENIED, EventType.UNIVERSITY_COURSE);
            r3 = persistRequest(emp2, RequestStatus.DENIED, EventType.UNIVERSITY_COURSE);
            r4 = persistRequest(emp2, RequestStatus.AWAITING_DEPT_HEAD, EventType.UNIVERSITY_COURSE);

            empDao.deleteAccount(emp1.getEmail());
            empDao.deleteAccount(emp2.getEmail());

            List<Long> reqIds = requestDao.getAllRequests().stream().map(req -> req.getId()).collect(Collectors.toList());
            assertTrue(reqIds.contains(r1.getId()));
            assertTrue(reqIds.contains(r2.getId()));
            assertTrue(reqIds.contains(r3.getId()));
            assertFalse(reqIds.contains(r4.getId()));
        }
        finally {
            requestDao.deleteRequest(r1.getId());
            requestDao.deleteRequest(r2.getId());
            requestDao.deleteRequest(r3.getId());
        }
    }

    @Test
    public void deleteEmployeeHandlesRequestAndFiles() throws DuplicateIdException, NonExistentIdException {
        ReimbursementRequest r1 = null, r2 = null, r3 = null;
        RequestFile f1 = null, f2 = null, f3 = null;
        try {
            r1 = persistRequest(emp1, RequestStatus.GRANTED, EventType.UNIVERSITY_COURSE);
            r2 = persistRequest(emp1, RequestStatus.DENIED, EventType.UNIVERSITY_COURSE);
            // will be deleted with acct
            r3 = persistRequest(emp2, RequestStatus.AWAITING_DEPT_HEAD, EventType.UNIVERSITY_COURSE);

            f1 = persistFile(FilePurpose.APPROVAL_EMAIL, MimeType.MS_OUTLOOK, r1);
            f2 = persistFile(FilePurpose.APPROVAL_EMAIL, MimeType.MS_OUTLOOK, r2);
            f3 = persistFile(FilePurpose.APPROVAL_EMAIL, MimeType.MS_OUTLOOK, r3);  // will be deleted

            empDao.deleteAccount(emp1.getEmail());
            empDao.deleteAccount(emp2.getEmail());

            List<Long> reqIds = requestDao.getAllRequests().stream().map(req -> req.getId()).collect(Collectors.toList());
            assertTrue(reqIds.contains(r1.getId()));
            assertTrue(reqIds.contains(r2.getId()));
            assertFalse(reqIds.contains(r3.getId()));

            List<Long> fileIds = objectDao.getAllObjects(RequestFile.class).stream().map(f -> f.getId()).collect(Collectors.toList());
            assertTrue(fileIds.contains(f1.getId()));
            assertTrue(fileIds.contains(f2.getId()));
            assertFalse(fileIds.contains(f3.getId()));
        }
        finally {
            requestDao.deleteRequest(r1.getId());
            requestDao.deleteRequest(r2.getId());
        }
    }

}