package me.daxterix.trms.service;

import me.daxterix.trms.dao.DAOUtils;
import me.daxterix.trms.dao.ObjectDAO;
import me.daxterix.trms.dao.RequestDAO;
import me.daxterix.trms.dao.RequestInfoDAO;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;

import java.util.List;

public class RequestService {
    static RequestDAO reqDao = DAOUtils.getRequestDAO();
    static ObjectDAO objectDAO = DAOUtils.getObjectDAO();
    static RequestInfoDAO infoDAO = DAOUtils.getRequestInfoDAO();

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
        infoDAO.saveFile(requestFile);
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
        EventType eventType = objectDAO.getObject(EventType.class, newReq.getEventType().getType());
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

        reqDao.save(newReq);
        return true;
    }


    public boolean approveRequest(String reqId, Employee approver) throws NonExistentIdException, DuplicateIdException {
        return false;
    }
}
