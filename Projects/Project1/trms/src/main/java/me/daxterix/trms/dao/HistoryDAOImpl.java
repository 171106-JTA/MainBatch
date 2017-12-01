package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.Department;
import me.daxterix.trms.model.Employee;
import me.daxterix.trms.model.ReimbursementRequest;
import me.daxterix.trms.model.RequestHistory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class HistoryDAOImpl extends ObjectDAO implements HistoryDAO {
    public HistoryDAOImpl(SessionFactory sf) {
        super(sf);
    }

    @Override
    @Transactional
    public long save(RequestHistory hist) throws DuplicateIdException {
        return (Long)saveObject(hist);
    }

    @Override
    @Transactional(readOnly=true)
    public List<RequestHistory> getHistoryByRequest(long requestId) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            if(session.get(ReimbursementRequest.class, requestId) == null)
                throw new NonExistentIdException("given request does not exist");

            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<RequestHistory> critQuery = critBuild.createQuery(RequestHistory.class);
            Root<RequestHistory> requestRoot = critQuery.from(RequestHistory.class);

            Predicate requestPredicate = critBuild.equal(getRequestIdPath(requestRoot), requestId);

            critQuery.select(requestRoot).where(requestPredicate);
            TypedQuery<RequestHistory> query = session.createQuery(critQuery);
            return query.getResultList();
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<RequestHistory> getHistoryByDepartment(String dept) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            if(session.get(Department.class, dept) == null)
                throw new NonExistentIdException("given department does not exist");

            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<RequestHistory> critQuery = critBuild.createQuery(RequestHistory.class);
            Root<RequestHistory> requestRoot = critQuery.from(RequestHistory.class);

            Predicate deptPredicate = critBuild.equal(getReqDeptNamePath(requestRoot), dept);
            critQuery.select(requestRoot).where(deptPredicate);
            TypedQuery<RequestHistory> query = session.createQuery(critQuery);
            return query.getResultList();
        }
    }


    @Override
    @Transactional(readOnly=true)
    public List<RequestHistory> getAllHistory() {
        return getAllObjects(RequestHistory.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<RequestHistory> getHistoryByEmployee(String email) throws NonExistentIdException {
        try (Session session = sessionFactory.openSession()) {
            Employee emp = session.get(Employee.class, email);
            if (emp == null)
                throw new NonExistentIdException("given employee does not exist");
            return emp.getHistory();
        }
    }


    private Path<String> getReqDeptNamePath(Root<RequestHistory> requestRoot) {
        return requestRoot.<ReimbursementRequest>get("request").<Department>get("department").<String>get("name");
    }

    private Path<Long> getRequestIdPath(Root<RequestHistory> requestRoot) {
        return requestRoot.<ReimbursementRequest>get("request").<Long>get("id");
    }
}
