package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.ReimbursementRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestDAO {

    long save(ReimbursementRequest request) throws DuplicateIdException;
    void update(ReimbursementRequest request) throws NonExistentIdException;
    void deleteRequest(long requestId) throws NonExistentIdException;

    ReimbursementRequest getRequestById(long id) throws NonExistentIdException;

    List<ReimbursementRequest> getFiledRequests(String email) throws NonExistentIdException;
    List<ReimbursementRequest> getPendingFiledRequests(String email) throws NonExistentIdException;
    List<ReimbursementRequest> getFiledRequestsByStatus(String email, String status) throws NonExistentIdException;

    List<ReimbursementRequest> getRequestsByDepartment(String department) throws NonExistentIdException;
    List<ReimbursementRequest> getPendingRequestsByDepartment(String department) throws NonExistentIdException;
    List<ReimbursementRequest> getDepartmentRequestsByStatus(String department, String status) throws NonExistentIdException;

    List<ReimbursementRequest> getWaitingOnSupervisor(String supervisorEmail) throws NonExistentIdException;

    List<ReimbursementRequest> getAllRequests();
    List<ReimbursementRequest> getPendingRequests();
    List<ReimbursementRequest> getRequestsByStatus(String status) throws NonExistentIdException;
}
