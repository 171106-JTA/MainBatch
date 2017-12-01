package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.ReimbursementRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestDAO {
    long save(ReimbursementRequest request) throws DuplicateIdException;
    void deleteRequest(long requestId) throws NonExistentIdException;
    void update(ReimbursementRequest request) throws NonExistentIdException;

    List<ReimbursementRequest> getFiledRequests(String email) throws NonExistentIdException;
    List<ReimbursementRequest> getPendingFiledRequests(String email) throws NonExistentIdException;
    List<ReimbursementRequest> getApprovedFiledRequests(String email) throws NonExistentIdException;

    List<ReimbursementRequest> getDeniedFiledRequests(String email) throws NonExistentIdException;

    @Transactional(readOnly = true)
    List<ReimbursementRequest> getPendingGradeFiledRequests(String email) throws NonExistentIdException;

    List<ReimbursementRequest> getRequestsByDepartment(String department) throws NonExistentIdException;
    List<ReimbursementRequest> getPendingRequestsByDepartment(String department) throws NonExistentIdException;
    List<ReimbursementRequest> getApprovedRequestsByDepartment(String department) throws NonExistentIdException;

    List<ReimbursementRequest> getAllRequests();
    List<ReimbursementRequest> getAllPendingRequests();
    List<ReimbursementRequest> getAllApprovedRequests();
}
