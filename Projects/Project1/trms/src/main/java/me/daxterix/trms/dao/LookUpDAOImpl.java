package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LookUpDAOImpl extends ObjectDAO implements LookUpDAO {
    public LookUpDAOImpl(SessionFactory sf) {
        super(sf);
    }


    @Override
    @Transactional(readOnly=true)
    public List<Department> getDepartment(String name) {
        return getAllObjects(Department.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<EventType> getEventType(String name) throws NonExistentIdException {
        return getAllObjects(EventType.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<RequestStatus> getRequestStatus(Class<RequestStatus> requestStatusClass, String name) {
        return getAllObjects(RequestStatus.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<MimeType> getMimeType(String name) {
        return getAllObjects(MimeType.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<FilePurpose> getFilePurpose(String name) {
        return getAllObjects(FilePurpose.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<EmployeeRank> getEmployeeRank(String name) {
        return getAllObjects(EmployeeRank.class);
    }
}
