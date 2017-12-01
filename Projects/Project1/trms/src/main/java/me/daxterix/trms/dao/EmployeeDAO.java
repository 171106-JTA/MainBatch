package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.Employee;
import me.daxterix.trms.model.EmployeeAccount;
import me.daxterix.trms.model.EmployeeInfo;

public interface EmployeeDAO {

    String save(EmployeeAccount acc) throws DuplicateIdException;

    String save(Employee emp) throws DuplicateIdException;

    String save(EmployeeInfo info) throws DuplicateIdException;

    Employee getEmployee(String email) throws NonExistentIdException;

    EmployeeInfo getInfo(String email) throws NonExistentIdException;

    EmployeeAccount getAccount(String email) throws NonExistentIdException;

    void update(EmployeeInfo info) throws NonExistentIdException;

    void update(Employee emp) throws NonExistentIdException;

    void deleteAccount(String email) throws NonExistentIdException;
}
