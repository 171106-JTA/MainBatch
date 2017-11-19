package daxterix.bank.dao;


import daxterix.bank.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    /**
     * obtain the record for a User, given the user's email
     *
     * @param email
     * @return
     * @throws SQLException
     */
    User select(String email) throws SQLException;

    /**
     * select all saved users
     *
     * @return
     * @throws SQLException
     */
    List<User> selectAll() throws SQLException;

    /**
     * persists a new user
     *
     * @param user
     * @return
     * @throws SQLException
     */
    int save(User user) throws SQLException;

    /**
     * update a user's info
     *
     * @param info
     * @return
     * @throws SQLException
     */
    int update(User info) throws SQLException;

    /**
     * delete a user (and all filed requests, and accounts)
     *
     * @param email
     * @return
     * @throws SQLException
     */
    int delete(String email) throws SQLException;

    /**
     * return the user that owns the given account
     *
     * @param accountNumber
     * @return
     * @throws SQLException
     */
    User selectForAccount(long accountNumber) throws SQLException;

    /**
     * given a request id, return the user that filed the request
     *
     * @param requestId
     * @return
     * @throws SQLException
     */
    User selectForRequest(long requestId) throws SQLException;

    /**
     * deletes all Users, and all records (and all filed requests, and accounts)
     *
     * @return - number of rows affected
     */
    int deleteAll() throws SQLException;
}
