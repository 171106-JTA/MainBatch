package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.EventGrade;
import me.daxterix.trms.model.ReimbursementRequest;
import me.daxterix.trms.model.RequestFile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RequestInfoDAOImpl extends ObjectDAO implements RequestInfoDAO {
    public RequestInfoDAOImpl(SessionFactory sf) {
        super(sf);
    }


    @Override
    @Transactional(readOnly = true)
    public RequestFile getFile(long id) throws NonExistentIdException {
        return getObject(RequestFile.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestFile> getFilesForRequest(long requestId) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ReimbursementRequest request = session.get(ReimbursementRequest.class, requestId);
            if (request == null)
                throw new NonExistentIdException(String.format("Request %d does not exist", requestId));

            List<RequestFile> files = request.getFiles();
            files.size();   // force lazy fetching
            session.getTransaction().commit();
            return files;
        }
    }

    @Override
    @Transactional
    public long saveFile(RequestFile file) throws DuplicateIdException {
        return (Long)saveObject(file, RequestFile::getId);
    }

    @Override
    @Transactional
    public void update(RequestFile file) throws NonExistentIdException {
        updateObject(file, file.getId());
    }

    @Override
    @Transactional
    public void deleteFile(long fileId) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            RequestFile file = session.get(RequestFile.class, fileId);
            if (file == null)
                throw new NonExistentIdException("Request File does not exist");
            EventGrade assocGrade = file.getEventGrade();
            if (assocGrade != null)
                assocGrade.setFile(null);

            session.delete(file);
            session.getTransaction().commit();
        }
    }
}
