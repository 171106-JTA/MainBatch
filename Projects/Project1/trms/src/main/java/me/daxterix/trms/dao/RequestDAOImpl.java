package me.daxterix.trms.dao;

import me.daxterix.trms.model.*;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class RequestDAOImpl extends ObjectDAO implements RequestDAO {
    public RequestDAOImpl(SessionFactory sf) {
        super(sf);
    }

    @Override
    @Transactional
    public long save(ReimbursementRequest request) throws DuplicateIdException {
        return (Long)saveObject(request);
    }

    @Override
    @Transactional  // todo: test thoroughly; note order of deletion is (very) important
    public void deleteRequest(long requestId) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ReimbursementRequest request = session.get(ReimbursementRequest.class, requestId);
            if (request == null)
                throw new NonExistentIdException("Request does not exist");

            EventGrade grade = request.getGrade();
            if (grade != null)
                session.delete(grade);

            for (RequestFile file : request.getFiles())
                session.delete(file);

            for (RequestHistory history : request.getHistory())
                session.delete(history);

            session.delete(request);
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional
    public void update(ReimbursementRequest request) throws NonExistentIdException {
        updateObject(request, request.getId());
    }

    @Override
    @Transactional(readOnly=true)
    public ReimbursementRequest getRequestById(long id) throws NonExistentIdException {
        return getObject(ReimbursementRequest.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getFiledRequests(String email) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Employee emp = session.get(Employee.class, email);
            if (emp == null)
                throw new NonExistentIdException("Request does not exist");
            List<ReimbursementRequest> requests = emp.getRequests();
            requests.size();    // force lazy loading
            session.getTransaction().commit();
            return requests;
        }
    }


    // todo: enclose blow queries in transactions

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getPendingFiledRequests(String email) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            Employee emp = session.get(Employee.class, email);
            if (emp == null)
                throw new NonExistentIdException("Request does not exist");

            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate emailPredicate = critBuild.equal(getFilerEmailPath(requestRoot), email);
            Predicate statuspredicate1 = critBuild.notEqual(getStatusNamePath(requestRoot), RequestStatus.GRANTED);
            Predicate statusPredicate2 = critBuild.notEqual(getStatusNamePath(requestRoot), RequestStatus.DENIED);
            Predicate allPredicates = critBuild.and(emailPredicate, statuspredicate1, statusPredicate2);

            critQuery.select(requestRoot).where(allPredicates);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            return query.getResultList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getApprovedFiledRequests(String email) throws NonExistentIdException {
        return getFiledRequestsByStatus(email, RequestStatus.GRANTED);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getDeniedFiledRequests(String email) throws NonExistentIdException {
        return getFiledRequestsByStatus(email, RequestStatus.DENIED);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getPendingGradeFiledRequests(String email) throws NonExistentIdException {
        return getFiledRequestsByStatus(email, RequestStatus.PENDING_GRADE);
    }

    @Transactional(readOnly = true)
    List<ReimbursementRequest> getFiledRequestsByStatus(String email, String status) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            Employee emp = session.get(Employee.class, email);
            if (emp == null)
                throw new NonExistentIdException("Request does not exist");

            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate emailPredicate = critBuild.equal(getFilerEmailPath(requestRoot), email);
            Predicate statusPredicate = critBuild.equal(getStatusNamePath(requestRoot), status);
            Predicate allPredicates = critBuild.and(emailPredicate, statusPredicate);

            critQuery.select(requestRoot).where(allPredicates);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            return query.getResultList();
        }
    }





    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getRequestsByDepartment(String department) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            Department emp = session.get(Department.class, department);
            if (emp == null)
                throw new NonExistentIdException("Department does not exist");

            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate deptPredicate = critBuild.equal(getDeptNamePath(requestRoot), department);
            critQuery.select(requestRoot).where(deptPredicate);

            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            return query.getResultList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getPendingRequestsByDepartment(String department) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            Department dept = session.get(Department.class, department);
            if (dept == null)
                throw new NonExistentIdException(String.format("Department %s does not exist", department));

            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate deptPredicate = critBuild.equal(getDeptNamePath(requestRoot), department);
            Predicate statusPredicate1 = critBuild.notEqual(getStatusNamePath(requestRoot), RequestStatus.GRANTED);
            Predicate statusPredicate2 = critBuild.notEqual(getStatusNamePath(requestRoot), RequestStatus.DENIED);
            Predicate allPredicates = critBuild.and(deptPredicate, statusPredicate1, statusPredicate2);

            critQuery.select(requestRoot).where(allPredicates);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            return query.getResultList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getApprovedRequestsByDepartment(String department) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            Department dept = session.get(Department.class, department);
            if (dept == null)
                throw new NonExistentIdException(String.format("Department %s does not exist", department));

            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate deptPredicate = critBuild.equal(getDeptNamePath(requestRoot), department);
            Predicate statusPredicate = critBuild.equal(getStatusNamePath(requestRoot), RequestStatus.GRANTED);
            Predicate allPredicates = critBuild.and(deptPredicate, statusPredicate);

            critQuery.select(requestRoot).where(allPredicates);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            return query.getResultList();
        }
    }






    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getAllRequests() {
        return getAllObjects(ReimbursementRequest.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getAllPendingRequests() {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate statusPredicate1 = critBuild.notEqual(getStatusNamePath(requestRoot), RequestStatus.DENIED);
            Predicate statusPredicate2 = critBuild.notEqual(getStatusNamePath(requestRoot), RequestStatus.GRANTED);
            Predicate allPredicates = critBuild.and(statusPredicate1, statusPredicate2);

            critQuery.select(requestRoot).where(allPredicates);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            return query.getResultList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getAllApprovedRequests() {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate statusPredicate = critBuild.equal(getStatusNamePath(requestRoot), RequestStatus.GRANTED);

            critQuery.select(requestRoot).where(statusPredicate);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            return query.getResultList();
        }
    }






    private Path<String> getDeptNamePath(Root<ReimbursementRequest> requestRoot) {
        return requestRoot.<Department>get("department").<String>get("name");
    }

    private Path<String> getStatusNamePath(Root<ReimbursementRequest> requestRoot) {
        return requestRoot.<RequestStatus>get("status").<String>get("name");
    }

    private Path<String> getFilerEmailPath(Root<ReimbursementRequest> requestRoot) {
        return requestRoot.<Employee>get("filer").<String>get("email");
    }
}
