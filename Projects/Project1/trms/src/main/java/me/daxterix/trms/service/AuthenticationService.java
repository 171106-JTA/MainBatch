package me.daxterix.trms.service;

import me.daxterix.trms.dao.DAOUtils;
import me.daxterix.trms.dao.EmployeeDAO;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.Employee;

import javax.servlet.http.HttpSession;

public class AuthenticationService {
    static EmployeeDAO employeeDao = DAOUtils.getEmployeeDAO();

    /**
     * checks that the user information indeed matches, and returns the corresponding employee
     * if it does
     *
     * @param email
     * @param givenPassword
     * @return
     */
    public static Employee checkEmployee(String email, String givenPassword) {
        Employee emp;
        try {
            emp = employeeDao.getEmployee(email);
        } catch (NonExistentIdException e) {
            return null;
        }
        if (!doPasswordsMatch(emp.getAccount().getPassword(), givenPassword))
            return null;

        return emp;
    }

    static boolean doPasswordsMatch(String expected, String actual) {
        // todo password hashing
        return expected.equals(actual);
    }

}
