package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RequestInfoDAOTest extends ObjectDAOTest {
    ReimbursementRequest request2;
    RequestFile file1;
    EventGrade grade1;

    @Before
    public void setup() throws DuplicateIdException {
        super.setup();

        file1 = persistFile(FilePurpose.EVENT_INFO, MimeType.PNG, request1);
        request2 = persistRequest(emp2, RequestStatus.GRANTED, EventType.UNIVERSITY_COURSE);

        grade1 = new EventGrade();
        grade1.setFile(file1);
        grade1.setRequest(request1);
        grade1.setGradePercent(100.0f);
        grade1.setCutoffPercent(60.0f);
        grade1.setPassedFailed(null);
        objectDao.saveObject(grade1, EventGrade::getRequestId);
    }

    @After
    public void tearDown() {
        super.tearDown();

        try {
            objectDao.deleteObject(EventGrade.class, grade1.getRequestId());
        } catch (NonExistentIdException e) {
            // e.printStackTrace();
        }
        try {
            reqInfoDao.deleteFile(file1.getId());
        } catch (NonExistentIdException e) {
            // e.printStackTrace();
        }
        try {
            requestDao.deleteRequest(request2.getId());
        } catch (NonExistentIdException e) {
            // e.printStackTrace();
        }
    }

    @Test
    public void getFilesForRequest() throws NonExistentIdException, DuplicateIdException {
        RequestFile file2 = persistFile(FilePurpose.APPROVAL_EMAIL, MimeType.PNG, request1);
        RequestFile file3 = persistFile(FilePurpose.GRADE_DOCUMENT, MimeType.PNG, request2);


        List<Long> fileIdsForR1 = filesToIds(reqInfoDao.getFilesForRequest(request1.getId()));
        assertEquals(2, fileIdsForR1.size());
        assertTrue(fileIdsForR1.contains(file1.getId()));
        assertTrue(fileIdsForR1.contains(file2.getId()));
        assertFalse(fileIdsForR1.contains(file3.getId()));


        List<Long> fileIdsForR2 = filesToIds(reqInfoDao.getFilesForRequest(request2.getId()));
        assertEquals(1, fileIdsForR2.size());
        assertTrue(fileIdsForR2.contains(file3.getId()));
        assertFalse(fileIdsForR2.contains(file1.getId()));
        assertFalse(fileIdsForR2.contains(file2.getId()));

        reqInfoDao.deleteFile(file3.getId());
        reqInfoDao.deleteFile(file2.getId());
    }

    @Test
    public void deleteFile() throws NonExistentIdException {
        reqInfoDao.deleteFile(file1.getId());
        EventGrade grade1_refetched = objectDao.getObject(EventGrade.class, grade1.getRequestId());
        assertNull(grade1_refetched.getFile());
    }

    @Test(expected=DataIntegrityViolationException.class)
    public void saveInvalidGrade() throws DuplicateIdException, NonExistentIdException {
        RequestFile file2 = persistFile(FilePurpose.APPROVAL_EMAIL, MimeType.PNG, request1);
        EventGrade gradeFor1 = new EventGrade();
        gradeFor1.setFile(file2);
        gradeFor1.setRequest(request1);
        gradeFor1.setGradePercent(100.0f);
        gradeFor1.setCutoffPercent(60.0f);
        gradeFor1.setPassedFailed(false);   // can't make grade pass/fail and percentage-based
        objectDao.saveObject(gradeFor1, EventGrade::getRequestId);
    }

    List<Long> filesToIds(List<RequestFile> files) {
        return files.stream().map(RequestFile::getId).collect(Collectors.toList());
    }


    RequestFile persistFile(String filePurpose, String mimeType, ReimbursementRequest req) throws DuplicateIdException {
        RequestFile file = new RequestFile();
        file.setMimeType(new MimeType(mimeType));
        file.setPurpose(new FilePurpose(filePurpose));
        file.setRequest(req);
        objectDao.saveObject(file, RequestFile::getId);
        return file;
    }
}