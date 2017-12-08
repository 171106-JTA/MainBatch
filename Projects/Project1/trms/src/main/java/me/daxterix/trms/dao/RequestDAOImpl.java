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
        return (Long)saveObject(request, req -> req.getId());
    }

    @Override
    @Transactional  // todo: test thoroughly; note order of deletion is (very) important
    public void deleteRequest(long requestId) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ReimbursementRequest request = session.get(ReimbursementRequest.class, requestId);
            if (request == null) {
                session.getTransaction().rollback();
                throw new NonExistentIdException("Request does not exist");
            }

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
            if (emp == null) {
                session.getTransaction().rollback();
                throw new NonExistentIdException("Request does not exist");
            }
            List<ReimbursementRequest> requests = emp.getRequests();
            requests.size();    // force lazy loading
            session.getTransaction().commit();
            return requests;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getPendingFiledRequests(String email) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Employee emp = session.get(Employee.class, email);
            if (emp == null) {
                session.getTransaction().rollback();
                throw new NonExistentIdException("Request does not exist");
            }
            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate emailPredicate = critBuild.equal(getFilerEmailPath(requestRoot), email);
            Predicate statuspredicate1 = critBuild.notEqual(getReqStatusNamePath(requestRoot), RequestStatus.GRANTED);
            Predicate statusPredicate2 = critBuild.notEqual(getReqStatusNamePath(requestRoot), RequestStatus.DENIED);
            Predicate allPredicates = critBuild.and(emailPredicate, statuspredicate1, statusPredicate2);

            critQuery.select(requestRoot).where(allPredicates);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            List<ReimbursementRequest> reqs = query.getResultList();
            session.getTransaction().commit();
            return reqs;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getFiledRequestsByStatus(String email, String status) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Employee emp = session.get(Employee.class, email);
            if (emp == null) {
                session.getTransaction().rollback();
                throw new NonExistentIdException("Request does not exist");
            }
            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate emailPredicate = critBuild.equal(getFilerEmailPath(requestRoot), email);
            Predicate statusPredicate = critBuild.equal(getReqStatusNamePath(requestRoot), status);
            Predicate allPredicates = critBuild.and(emailPredicate, statusPredicate);

            critQuery.select(requestRoot).where(allPredicates);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            List<ReimbursementRequest> reqs = query.getResultList();
            session.getTransaction().commit();
            return reqs;
        }
    }






    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getRequestsByDepartment(String department) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Department emp = session.get(Department.class, department);
            if (emp == null) {
                session.getTransaction().rollback();
                throw new NonExistentIdException("Department does not exist");
            }
            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate deptPredicate = critBuild.equal(getFilerDeptNamePath(requestRoot), department);
            critQuery.select(requestRoot).where(deptPredicate);

            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            List<ReimbursementRequest> reqs = query.getResultList();
            session.getTransaction().commit();
            return reqs;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getPendingRequestsByDepartment(String department) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Department dept = session.get(Department.class, department);
            if (dept == null) {
                session.getTransaction().rollback();
                throw new NonExistentIdException(String.format("Department %s does not exist", department));
            }
            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate deptPredicate = critBuild.equal(getFilerDeptNamePath(requestRoot), department);
            Predicate statusPredicate1 = critBuild.notEqual(getReqStatusNamePath(requestRoot), RequestStatus.GRANTED);
            Predicate statusPredicate2 = critBuild.notEqual(getReqStatusNamePath(requestRoot), RequestStatus.DENIED);
            Predicate allPredicates = critBuild.and(deptPredicate, statusPredicate1, statusPredicate2);

            critQuery.select(requestRoot).where(allPredicates);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            List<ReimbursementRequest> reqs = query.getResultList();
            session.getTransaction().commit();
            return reqs;

        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getDepartmentRequestsByStatus(String department, String status) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Department dept = session.get(Department.class, department);
            if (dept == null) {
                session.getTransaction().rollback();
                throw new NonExistentIdException(String.format("Department %s does not exist", department));
            }
            RequestStatus rStatus = session.get(RequestStatus.class, status);
            if (rStatus == null) {
                session.getTransaction().commit();
                throw new NonExistentIdException("invalid status");
            }
            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate deptPredicate = critBuild.equal(getFilerDeptNamePath(requestRoot), department);
            Predicate statusPredicate = critBuild.equal(getReqStatusNamePath(requestRoot), status);
            Predicate allPredicates = critBuild.and(deptPredicate, statusPredicate);

            critQuery.select(requestRoot).where(allPredicates);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            List<ReimbursementRequest> reqs = query.getResultList();
            session.getTransaction().commit();
            return reqs;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getWaitingOnSupervisor(String supervisorEmail) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (session.get(Employee.class, supervisorEmail) == null) {
                session.getTransaction().rollback();
                throw new NonExistentIdException("No such supervisor");
            }
            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate supePredicate = critBuild.equal(getFilerSupeEmailPath(requestRoot), supervisorEmail);
            Predicate statusPredicate = critBuild.equal(getReqStatusNamePath(requestRoot), RequestStatus.AWAITING_SUPERVISOR);
            Predicate allPredicates = critBuild.and(supePredicate, statusPredicate);

            critQuery.select(requestRoot).where(allPredicates);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            List<ReimbursementRequest> reqs = query.getResultList();
            session.getTransaction().commit();
            return reqs;
        }
    }







    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getAllRequests() {
        return getAllObjects(ReimbursementRequest.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getPendingRequests() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate statusPredicate1 = critBuild.notEqual(getReqStatusNamePath(requestRoot), RequestStatus.DENIED);
            Predicate statusPredicate2 = critBuild.notEqual(getReqStatusNamePath(requestRoot), RequestStatus.GRANTED);
            Predicate allPredicates = critBuild.and(statusPredicate1, statusPredicate2);

            critQuery.select(requestRoot).where(allPredicates);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);
            List<ReimbursementRequest> reqs = query.getResultList();
            session.getTransaction().commit();
            return reqs;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReimbursementRequest> getRequestsByStatus(String status) throws NonExistentIdException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (session.get(RequestStatus.class, status) == null) {
                session.getTransaction().rollback();
                throw new NonExistentIdException("invalid request status");
            }
            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<ReimbursementRequest> critQuery = critBuild.createQuery(ReimbursementRequest.class);
            Root<ReimbursementRequest> requestRoot = critQuery.from(ReimbursementRequest.class);

            Predicate statusPredicate = critBuild.equal(getReqStatusNamePath(requestRoot), status);

            critQuery.select(requestRoot).where(statusPredicate);
            TypedQuery<ReimbursementRequest> query = session.createQuery(critQuery);

            List<ReimbursementRequest> reqs = query.getResultList();
            session.getTransaction().commit();
            return reqs;
        }
    }






    private Path<String> getFilerDeptNamePath(Root<ReimbursementRequest> requestRoot) {
        return requestRoot.<Employee>get("filer").<Department>get("department").<String>get("name");
    }

    private Path<String> getReqStatusNamePath(Root<ReimbursementRequest> requestRoot) {
        return requestRoot.<RequestStatus>get("status").<String>get("status");
    }

    private Path<String> getFilerSupeEmailPath(Root<ReimbursementRequest> requestRoot) {
        return requestRoot.<Employee>get("filer").<Employee>get("supervisor").<String>get("email");
    }

    private Path<String> getFilerEmailPath(Root<ReimbursementRequest> requestRoot) {
        return requestRoot.<Employee>get("filer").<String>get("email");
    }
}
