package me.daxterix.trms.service;

import me.daxterix.trms.dao.*;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class RequestService {
    static RequestDAO reqDao = DAOUtils.getRequestDAO();
    static ObjectDAO objectDao = DAOUtils.getObjectDAO();
    static RequestInfoDAO infoDao = DAOUtils.getRequestInfoDAO();
    static HistoryDAO historyDao = DAOUtils.getHistoryDAO();

    /**
     * assimes given request and requestFile is valid and exists
     *
     * @param request
     * @param requestFile
     * @return
     */
    public static void addRequestFile(ReimbursementRequest request, RequestFile requestFile) throws DuplicateIdException {
        // todo: update request status if a request email
        requestFile.setRequest(request);
        infoDao.saveFile(requestFile);
    }

    /**
     * todo calculate urgency based on time; calculate whether user can submit because of time constraints
     *
     *
     * Assumes filer exists, and was retrieved from db
     * Assumes file type exists
     *
     * inserts the new request and returns true if; otherwise
     * @param newReq
     * @return
     */
    public static boolean addRequest(ReimbursementRequest newReq) throws NonExistentIdException, DuplicateIdException {
        EventType eventType = objectDao.getObject(EventType.class, newReq.getEventType().getType());
        Employee emp = newReq.getFiler();

        float subsidizedCost = newReq.getEventCost() * eventType.getPercentOff() / 100.0f;

        List<ReimbursementRequest> pendingRequests = reqDao.getPendingFiledRequests(emp.getEmail());
        float remainingFunds = emp.getAvailableFunds();
        for (ReimbursementRequest req : pendingRequests)
            remainingFunds -= req.getFunding();

        if (remainingFunds >= subsidizedCost)
            newReq.setFunding(subsidizedCost);
        else if (remainingFunds > 0)
            newReq.setFunding(remainingFunds);
        else
            return false;

        addHistForRequest(newReq, newReq.getTimeFiled(), RequestStatus.AWAITING_SUPERVISOR, null, null);
        reqDao.save(newReq);
        return true;
    }


    /**
     * logs request filing and approval history to the database
     *
     * @param request
     * @param time
     * @param postStatus
     * @param approver
     * @param approvingEmail
     * @throws DuplicateIdException
     */
    private static void addHistForRequest(ReimbursementRequest request, LocalDateTime time, String postStatus,
                                          Employee approver, RequestFile approvingEmail) throws DuplicateIdException {

        RequestHistory creationHist = new RequestHistory();
        if (approver != null)
            creationHist.setApprover(approver);
        if (approvingEmail != null)
            creationHist.setFile(approvingEmail);

        creationHist.setRequest(request);
        creationHist.setPostStatus(new RequestStatus(postStatus));
        creationHist.setTime(time);
        historyDao.save(creationHist);
    }


    public boolean approveRequest(String reqId, Employee approver) throws NonExistentIdException, DuplicateIdException {
        return false;
    }
}
