package me.daxterix.trms.service;

import me.daxterix.trms.dao.*;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RequestService {
    private static RequestDAO requestDao = DAOUtils.getRequestDAO();
    private static ObjectDAO objectDao = DAOUtils.getObjectDAO();
    private static RequestInfoDAO infoDao = DAOUtils.getRequestInfoDAO();
    private static HistoryDAO historyDao = DAOUtils.getHistoryDAO();

    /**
     * assimes given request and requestFile is valid and exists
     *
     * @param request
     * @param requestFile
     * @return
     */
    public static void addRequestFile(ReimbursementRequest request, RequestFile requestFile) throws DuplicateIdException {
        // todo: update request status if a request email, and log history
        requestFile.setRequest(request);
        infoDao.saveFile(requestFile);
    }

    /**
     * <p>
     * <p>
     * Assumes filer exists, and was retrieved from db
     * Assumes file type exists
     * <p>
     * inserts the new request and returns true if; otherwise
     *
     * @param newReq
     * @return
     */
    public static boolean addRequest(ReimbursementRequest newReq) throws NonExistentIdException, DuplicateIdException {
        EventType eventType = objectDao.getObject(EventType.class, newReq.getEventType().getType());
        Employee emp = newReq.getFiler();

        float subsidizedCost = newReq.getEventCost() * eventType.getPercentOff() / 100.0f;

        List<ReimbursementRequest> pendingRequests = requestDao.getPendingFiledRequests(emp.getEmail());
        float remainingFunds = emp.getAvailableFunds();
        for (ReimbursementRequest req : pendingRequests)
            remainingFunds -= req.getFunding();

        if (remainingFunds >= subsidizedCost)
            newReq.setFunding(subsidizedCost);
        else if (remainingFunds > 0)
            newReq.setFunding(remainingFunds);

        LocalDate dateFiled = newReq.getTimeFiled().toLocalDate();
        LocalDate eventStartDate = newReq.getEventStart();
        if (eventStartDate.isAfter(dateFiled.plusDays(13)))
            newReq.setUrgent(false);
        else if (eventStartDate.isAfter(dateFiled.plusDays(6)))
            newReq.setUrgent(true);
        else
            return false;

        requestDao.save(newReq);
        addHistForRequest(newReq, newReq.getTimeFiled(), RequestStatus.AWAITING_SUPERVISOR, null, null);
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
        creationHist.setApprover(approver);
        creationHist.setFile(approvingEmail);
        creationHist.setRequest(request);
        creationHist.setPostStatus(new RequestStatus(postStatus));
        creationHist.setTime(time);
        historyDao.save(creationHist);
    }


    public static void doApproval(HttpServletResponse response, Employee approver, ReimbursementRequest theRequest)
            throws NonExistentIdException, IOException, DuplicateIdException {
        String requestStatus = theRequest.getStatus().getStatus();
        String filerSupeEmail = theRequest.getFiler().getSupervisor().getEmail();
        String approverEmail = approver.getEmail();
        String approverRank = approver.getRank().getRank();
        String filerDept = theRequest.getFiler().getDepartment().getName();

        PrintWriter out = response.getWriter();
        response.setStatus(200);
        response.setContentType("plain/text");

        String finalStatus = null;
        switch (requestStatus) {
            case (RequestStatus.AWAITING_SUPERVISOR):
                if (filerSupeEmail.equals(approverEmail))// approval by only direct supervisor
                    switch(approverRank) {
                        case EmployeeRank.SUPERVISOR:
                            finalStatus = RequestStatus.AWAITING_DEPT_HEAD;
                            break;
                        case EmployeeRank.DEPARTMENT_HEAD:
                            finalStatus = RequestStatus.AWAITING_BENCO;
                            break;
                        default:
                            out.print("nothing happened(Part 2): check this");
                    }
                break;
            case (RequestStatus.AWAITING_DEPT_HEAD):    // benco cannot be a Dept Head
                if (filerDept.equals(approver.getDepartment()) && (approverRank.equals(EmployeeRank.DEPARTMENT_HEAD)))
                    finalStatus = RequestStatus.AWAITING_BENCO;
                break;
            case (RequestStatus.AWAITING_BENCO):
                if (approverRank.equals(EmployeeRank.BENCO))
                    finalStatus = RequestStatus.AWAITING_GRADE;
                break;
            case (RequestStatus.AWAITING_GRADE):
                if (approverRank.equals(EmployeeRank.BENCO))
                    finalStatus = RequestStatus.GRANTED;
                break;
            default:
                out.println("NOTHING HAPPENDED; CHECK THIS");
        }
        if (finalStatus == null) {
            response.setStatus(401);
            out.println("invalid credentials for request");
        }
        else {
            out.println("approved successfully!");
            persistRequestApproval(approver, theRequest, finalStatus, null);
        }
    }


    public static void doApproval(HttpServletResponse response, Employee approver, ReimbursementRequest theRequest, float grantAmount) throws DuplicateIdException, NonExistentIdException, IOException {
        String approverRank = approver.getRank().getRank();
        String requestStatus = theRequest.getStatus().getStatus();

        response.setStatus(401);
        response.setContentType("plain/text");
        if (!(requestStatus.equals(RequestStatus.AWAITING_GRADE) && approverRank.equals(EmployeeRank.BENCO)))
            response.getWriter().println("invalid credentials for request");

        if (grantAmount > theRequest.getFunding())
            theRequest.setExceedsFunds(true);
        theRequest.setFunding(grantAmount);

        response.setStatus(200);
        persistRequestApproval(approver, theRequest, RequestStatus.GRANTED, null);
    }


    public static void doDenial(HttpServletResponse response, Employee approver, ReimbursementRequest theRequest, String reason)
            throws NonExistentIdException, DuplicateIdException, IOException {
        String requestStatus = theRequest.getStatus().getStatus();
        String filerSupeEmail = theRequest.getFiler().getSupervisor().getEmail();
        String approverRank = approver.getRank().getRank();
        String approverEmail = approver.getEmail();
        String filerDept = theRequest.getFiler().getDepartment().getName();

        PrintWriter out = response.getWriter();
        response.setStatus(200);
        response.setContentType("plain/text");

        boolean willDeny = false;
        switch (requestStatus) {
            case (RequestStatus.AWAITING_SUPERVISOR):
                if (filerSupeEmail.equals(approverEmail))    // approval by only direct supervisor
                    willDeny = true;
                break;
            case (RequestStatus.AWAITING_DEPT_HEAD):    // benco cannot be a Dept Head
                if (filerDept.equals(approver.getDepartment()) && (approverRank.equals(EmployeeRank.DEPARTMENT_HEAD)))
                    willDeny = true;
                break;
            case (RequestStatus.AWAITING_BENCO):
                if (approverRank.equals(EmployeeRank.BENCO))
                    willDeny = true;
                break;
            case (RequestStatus.AWAITING_GRADE):
                if (approverRank.equals(EmployeeRank.BENCO))
                    willDeny = true;
                break;
            default:
                out.println("NOTHING HAPPENDED; CHECK THIS");
        }
        if (!willDeny) {
            response.setStatus(401);
            out.println("invalid credentials for request");
        }
        else {
            out.println("denied successfully!");
            persistRequestApproval(approver, theRequest, RequestStatus.DENIED, reason);
        }
    }

    private static void persistRequestApproval(Employee approver, ReimbursementRequest theRequest, String finalStatus,
                                               String reason) throws NonExistentIdException, DuplicateIdException { requestDao.update(theRequest);
        historyDao.save(new RequestHistory(
                theRequest, approver, null, theRequest.getStatus(), new RequestStatus(finalStatus),
                LocalDateTime.now(), reason
        ));
    }
}
