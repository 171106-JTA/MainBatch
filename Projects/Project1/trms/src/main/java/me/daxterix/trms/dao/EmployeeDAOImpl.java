package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EmployeeDAOImpl extends ObjectDAO implements EmployeeDAO {
    @Override
    @Transactional
    public String save(Employee emp) throws DuplicateIdException {
        return (String)saveObject(emp, Employee::getEmail);
    }

    @Override
    @Transactional
    public String save(EmployeeAccount acc) throws DuplicateIdException {
        return (String)saveObject(acc, EmployeeAccount::getEmail);
    }

    @Override
    @Transactional
    public String save(EmployeeInfo info) throws DuplicateIdException {
        return (String)saveObject(info, EmployeeInfo::getEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployee(String email) throws NonExistentIdException {
        return getObject(Employee.class, email);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeInfo getInfo(String email) throws NonExistentIdException {
        return getObject(EmployeeInfo.class, email);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeAccount getAccount(String email) throws NonExistentIdException {
        return getObject(EmployeeAccount.class, email);
    }

    @Override
    @Transactional
    public void update(EmployeeInfo info) throws NonExistentIdException {
        updateObject(info, info.getEmail());
    }

    @Override
    @Transactional
    public void update(Employee emp) throws NonExistentIdException {
        updateObject(emp, emp.getEmail());
    }

    @Override
    @Transactional  // todo: test thoroughly; note order of deletion is (very) important
    public void deleteAccount(String email) throws NonExistentIdException {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            EmployeeAccount acc = session.get(EmployeeAccount.class, email);
            if (acc == null) {
                session.getTransaction().rollback();
                throw new NonExistentIdException("Account does not exist");
            }

            EmployeeInfo info = acc.getInfo();
            if (info != null)
                session.delete(info);

            Employee emp = acc.getEmployee();
            if (emp != null) {
                for (Employee subject: emp.getSupervisees())
                    subject.setSupervisor(null);

                for (ReimbursementRequest request: emp.getRequests()) {
                    EventGrade grade = request.getGrade();
                    if (grade != null)
                        session.delete(grade);

                    for (RequestFile file: request.getFiles())
                        session.delete(file);

                    for (RequestHistory history: request.getHistory())
                        session.delete(history);

                    if (request.getStatus().getStatus().equals(RequestStatus.GRANTED) ||
                            request.getStatus().getStatus().equals(RequestStatus.DENIED))
                        request.setFiler(null); // leave these for auditing purposes
                    else
                        session.delete(request);
                }
                session.delete(emp);
            }
            session.delete(acc);
            session.getTransaction().commit();
        }
    }
}
