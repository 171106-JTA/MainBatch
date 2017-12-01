package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;

import java.util.List;

public interface LookUpDAO {
    List<Department> getDepartment(String name);
    List<EventType> getEventType(String name) throws NonExistentIdException;
    List<RequestStatus> getRequestStatus(Class<RequestStatus> requestStatusClass, String name);
    List<MimeType> getMimeType(String name);
    List<FilePurpose> getFilePurpose(String name);
    List<EmployeeRank> getEmployeeRank(String name);
}
