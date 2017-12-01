package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EmployeeDAOImpl extends ObjectDAO implements EmployeeDAO {
    @Override
    @Transactional
    public String save(Employee emp) throws DuplicateIdException {
        return (String)saveObject(emp);
    }

    @Override
    @Transactional
    public String save(EmployeeAccount acc) throws DuplicateIdException {
        return (String)saveObject(acc);
    }

    @Override
    @Transactional
    public String save(EmployeeInfo info) throws DuplicateIdException {
        return (String)saveObject(info);
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
    @Transactional
    public void deleteAccount(String email) throws NonExistentIdException {
        deleteObject(EmployeeAccount.class, email);
    }
}
