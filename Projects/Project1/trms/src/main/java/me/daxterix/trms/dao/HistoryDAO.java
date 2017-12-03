package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.RequestHistory;

import java.util.List;

public interface HistoryDAO {
    long save(RequestHistory hist) throws DuplicateIdException;
    List<RequestHistory> getHistoryByRequest(long requestId) throws NonExistentIdException;
    List<RequestHistory> getHistoryByDepartment(String dept) throws NonExistentIdException;
    List<RequestHistory> getAllHistory();
    List<RequestHistory> getHistoryByEmployee(String email) throws NonExistentIdException;
}
