package daxterix.bank.dao;


import daxterix.bank.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User select(String email) throws SQLException;

    List<User> selectAll() throws SQLException;

    int save(User user) throws SQLException;

    int updateUser(User info) throws SQLException;

    int delete(String email) throws SQLException;

    User selectForAccount(long accountNumber) throws SQLException;

    User selectForRequest(long requestId) throws SQLException;

    int deleteAll() throws SQLException;
}
