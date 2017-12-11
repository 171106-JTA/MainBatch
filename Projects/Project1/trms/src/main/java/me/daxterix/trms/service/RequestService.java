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
    public static void addRequestFile(ReimbursementRequest request, RequestFile requestFile) throws DuplicateIdException, NonExistentIdException {
        if (requestFile.getPurpose().getPurpose().equals(FilePurpose.APPROVAL_EMAIL)) {
            switch (request.getStatus().getStatus()) {
                case RequestStatus.AWAITING_SUPERVISOR:
                    request.setStatus(new RequestStatus(RequestStatus.AWAITING_DEPT_HEAD));
                    break;
                case RequestStatus.AWAITING_DEPT_HEAD:
                    request.setStatus(new RequestStatus(RequestStatus.AWAITING_BENCO));
                    break;
            }
            requestFile.setRequest(request);
            requestDao.update(request);
        }
        // todo: polish, add grade
        else if (requestFile.getPurpose().getPurpose().equals(FilePurpose.GRADE_DOCUMENT) &&
                request.getStatus().getStatus().equals(RequestStatus.AWAITING_GRADE)) {

            request.setStatus(new RequestStatus(RequestStatus.GRANTED));
        }

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
    public static ReimbursementRequest addRequest(ReimbursementRequest newReq) throws NonExistentIdException, DuplicateIdException {
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
            return null;

        requestDao.save(newReq);
        addHistForRequest(newReq, newReq.getTimeFiled(), RequestStatus.AWAITING_SUPERVISOR, null, null);
        return newReq;
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
        creationHist.setPreStatus(new RequestStatus(postStatus));
        creationHist.setPostStatus(new RequestStatus(postStatus));
        creationHist.setTime(time);
        historyDao.save(creationHist);
    }

    public static void doApproval(HttpServletResponse response, Employee approver, ReimbursementRequest theRequest, Float amount)
            throws NonExistentIdException, IOException, DuplicateIdException {
        String requestStatus = theRequest.getStatus().getStatus();
        String filerSupeEmail = theRequest.getFiler().getSupervisor().getEmail();
        String approverEmail = approver.getEmail();
        String approverRank = approver.getRank().getRank();
        String filerDept = theRequest.getFiler().getDepartment().getName();

        PrintWriter out = response.getWriter();
        response.setStatus(200);
        response.setContentType("application/json");

        String finalStatus = null;
        switch (requestStatus) {
            case (RequestStatus.AWAITING_SUPERVISOR):
                if (filerSupeEmail.equals(approverEmail))// approval by only direct supervisor
                    switch (approverRank) {
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
                if (filerDept.equals(approver.getDepartment().getName()) && (approverRank.equals(EmployeeRank.DEPARTMENT_HEAD)))
                    finalStatus = RequestStatus.AWAITING_BENCO;
                break;
            case (RequestStatus.AWAITING_BENCO):
                if (approverRank.equals(EmployeeRank.BENCO))
                    finalStatus = RequestStatus.AWAITING_GRADE;
                break;
            default:
                doFinalApproval(response, approver, theRequest, amount);
        }
        if (finalStatus == null) {
            response.setStatus(401);
            out.println("invalid credentials for request");
        } else {
            out.println("approved successfully!");
            persistRequestApproval(approver, theRequest, finalStatus, null);
        }
    }


    public static void doFinalApproval(HttpServletResponse response, Employee approver, ReimbursementRequest theRequest,
                                       Float grantAmount) throws DuplicateIdException, NonExistentIdException, IOException {
        String approverRank = approver.getRank().getRank();
        String requestStatus = theRequest.getStatus().getStatus();

        response.setStatus(401);
        response.setContentType("application/json");
        if (!(requestStatus.equals(RequestStatus.AWAITING_GRADE) && approverRank.equals(EmployeeRank.BENCO)))
            response.getWriter().println("invalid credentials for request");

        if (grantAmount != null && grantAmount > theRequest.getFunding()) {
            theRequest.setExceedsFunds(true);
            theRequest.setFunding(grantAmount);
        }

        response.setStatus(200);
        persistRequestApproval(approver, theRequest, RequestStatus.GRANTED, null);
    }


    /**
     * todo: edit to not auto promote
     *
     * @param response
     * @param approver
     * @param theRequest
     * @param thefile
     * @throws DuplicateIdException
     * @throws NonExistentIdException
     * @throws IOException
     */
    public static void doFileApproval(HttpServletResponse response, Employee approver, ReimbursementRequest theRequest,
                                      RequestFile thefile) throws DuplicateIdException, NonExistentIdException, IOException {
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
        response.setContentType("application/json");

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
        } else {
            if (reason == null)
                reason = "no reason given";
            persistRequestApproval(approver, theRequest, RequestStatus.DENIED, reason);
            out.println("denied successfully!");
        }
    }

    private static void persistRequestApproval(Employee approver, ReimbursementRequest theRequest, String finalStatus,
                                               String reason) throws NonExistentIdException, DuplicateIdException {
        requestDao.update(theRequest);
        historyDao.save(new RequestHistory(
                theRequest, approver, null, theRequest.getStatus(), new RequestStatus(finalStatus),
                LocalDateTime.now(), reason
        ));
    }
}
