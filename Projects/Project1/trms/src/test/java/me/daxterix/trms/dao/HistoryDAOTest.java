package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HistoryDAOTest extends ObjectDAOTest {
    HistoryDAO histDao = context.getBean(HistoryDAO.class);
    RequestHistory hist1_1, hist1_2, hist2_1, hist2_2;
    ReimbursementRequest request2;

    @Before
    public void setup() throws DuplicateIdException {
        super.setup();

        request2 = persistRequest(emp2, RequestStatus.AWAITING_DEPT_HEAD, EventType.CERTIFICATION_PREP);

        hist1_1 = persistHistory(request1, emp1);
        hist1_2 = persistHistory(request1, emp1);
        hist2_1 = persistHistory(request2, emp1);
        hist2_2 = persistHistory(request2, emp2);
    }

    @After
    public void tearDown() {
        super.tearDown();

        try {
            objectDao.deleteObject(RequestHistory.class, hist1_1.getId());
        } catch (NonExistentIdException e) {
            // e.printStackTrace();
        }

        for (RequestHistory hist : new RequestHistory[]{hist1_1, hist1_2, hist2_1, hist2_2}) {
            try {
                objectDao.deleteObject(RequestHistory.class, hist.getId());
            } catch (NonExistentIdException e) {
                //
            }
        }
    }

    RequestHistory persistHistory(ReimbursementRequest req, Employee approver) throws DuplicateIdException {
        RequestHistory hist = new RequestHistory();
        hist.setRequest(req);
        hist.setApprover(approver);
        histDao.save(hist);
        return hist;
    }

    @Test
    public void getHistoryByDepartment() throws NonExistentIdException {
        List<Long> slythHist = histsToIds(histDao.getHistoryByDepartment(Department.SLYTHERIN));
        assertTrue(2 <= slythHist.size());
        assertTrue(slythHist.contains(hist2_1.getId()));
        assertTrue(slythHist.contains(hist2_2.getId()));
        assertFalse(slythHist.contains(hist1_1.getId()));
        assertFalse(slythHist.contains(hist1_2.getId()));

        List<Long> ravenHist = histsToIds(histDao.getHistoryByDepartment(Department.RAVENCLAW));
        assertTrue(2 <= ravenHist.size());
        assertFalse(ravenHist.contains(hist2_1.getId()));
        assertFalse(ravenHist.contains(hist2_2.getId()));
        assertTrue(ravenHist.contains(hist1_1.getId()));
        assertTrue(ravenHist.contains(hist1_2.getId()));
    }


    @Test
    public void getHistoryByRequest() throws NonExistentIdException {
        List<Long> req2Hist = histsToIds(histDao.getHistoryByRequest(request2.getId()));
        assertTrue(2 <= req2Hist.size());
        assertTrue(req2Hist.contains(hist2_1.getId()));
        assertTrue(req2Hist.contains(hist2_2.getId()));
        assertFalse(req2Hist.contains(hist1_1.getId()));
        assertFalse(req2Hist.contains(hist1_2.getId()));

        List<Long> req1Hist = histsToIds(histDao.getHistoryByRequest(request1.getId()));
        assertTrue(2 <= req1Hist.size());
        assertFalse(req1Hist.contains(hist2_1.getId()));
        assertFalse(req1Hist.contains(hist2_2.getId()));
        assertTrue(req1Hist.contains(hist1_1.getId()));
        assertTrue(req1Hist.contains(hist1_2.getId()));
    }

    @Test
    public void getHistoryByApprover() throws NonExistentIdException {
        List<Long> emp2approved = histsToIds(histDao.getHistoryByApprover(emp2.getEmail()));
        assertTrue(1 <= emp2approved.size());
        assertFalse(emp2approved.contains(hist2_1.getId()));
        assertTrue(emp2approved.contains(hist2_2.getId()));
        assertFalse(emp2approved.contains(hist1_1.getId()));
        assertFalse(emp2approved.contains(hist1_2.getId()));

        List<Long> emp1approved = histsToIds(histDao.getHistoryByApprover(emp1.getEmail()));
        assertTrue(3 <= emp1approved.size());
        assertFalse(emp1approved.contains(hist2_1.getId()));
        assertTrue(emp1approved.contains(hist2_2.getId()));
        assertTrue(emp1approved.contains(hist1_1.getId()));
        assertTrue(emp1approved.contains(hist1_2.getId()));
    }

    @Test
    public void getHistoryByFiler() throws NonExistentIdException {
        List<Long> emp2Hist = histsToIds(histDao.getHistoryByFiler(emp2.getEmail()));
        assertTrue(2 <= emp2Hist.size());
        assertTrue(emp2Hist.contains(hist2_1.getId()));
        assertTrue(emp2Hist.contains(hist2_2.getId()));
        assertFalse(emp2Hist.contains(hist1_1.getId()));
        assertFalse(emp2Hist.contains(hist1_2.getId()));

        List<Long> emp1Hist = histsToIds(histDao.getHistoryByFiler(emp1.getEmail()));
        assertTrue(2 <= emp1Hist.size());
        assertFalse(emp1Hist.contains(hist2_1.getId()));
        assertFalse(emp1Hist.contains(hist2_2.getId()));
        assertTrue(emp1Hist.contains(hist1_1.getId()));
        assertTrue(emp1Hist.contains(hist1_2.getId()));
    }

    List<Long> histsToIds(List<RequestHistory> hists) {
        return hists.stream().map(RequestHistory::getId).collect(Collectors.toList());
    }
}