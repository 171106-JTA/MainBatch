package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Repository
public class ObjectDAO {
    protected SessionFactory sessionFactory;

    protected ObjectDAO() {}

    public ObjectDAO(SessionFactory sf) {
        sessionFactory = sf;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public void setSessionFactory(SessionFactory sf) {
        sessionFactory = sf;
    }

    @Transactional(readOnly = true)
    public <T> T getObject(Class<T> klass, Serializable id) throws NonExistentIdException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            T res = session.get(klass, id);
            if (res == null)
                throw new NonExistentIdException();

            session.getTransaction().commit();
            return res;
        }
    }

    @Transactional(readOnly = true)
    public <T> List<T> getAllObjects(Class<T> klass) {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder critBuild = session.getCriteriaBuilder();
            CriteriaQuery<T> critQuery = critBuild.createQuery(klass);
            Root<T> resultRoot = critQuery.from(klass);
            critQuery.select(resultRoot);

            TypedQuery<T> query = session.createQuery(critQuery);
            return query.getResultList();
        }
    }

    @Transactional
    public void updateObject(Object obj, Serializable id) throws NonExistentIdException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            if(session.get(obj.getClass(), id) == null)
                throw new NonExistentIdException();

            session.update(obj);
            session.getTransaction().commit();
        }
    }


    /**
     * saves a generic hibernate annotated bean instance, returns the
     * (potentially generated) id of the persisted object
     *
     * @param <T>
     * @param obj
     * @return
     * @throws DuplicateIdException
     */
    @Transactional(readOnly = true)
    public Serializable saveObject(Object obj) throws DuplicateIdException {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Serializable persistedObjId = session.save(obj);
            session.getTransaction().commit();
            return persistedObjId;
        }
    }

    @Transactional
    public <T> void deleteObject(Class<T> klass, Serializable id) throws NonExistentIdException {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            Object obj = session.get(klass, id);
            if (obj == null)
                throw new NonExistentIdException();

            session.delete(obj);
            session.getTransaction().commit();
        }
    }
}
