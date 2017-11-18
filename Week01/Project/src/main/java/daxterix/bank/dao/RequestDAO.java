package daxterix.bank.dao;

import daxterix.bank.model.UserRequest;

import java.sql.SQLException;
import java.util.List;

public interface RequestDAO {
    UserRequest select(long id) throws SQLException;
    List<UserRequest> selectForUser(String email) throws SQLException;
    List<UserRequest> selectAll() throws SQLException;
    int save(UserRequest req) throws SQLException;
    int update(UserRequest info) throws SQLException;
    int delete(long id) throws SQLException;
    int deleteForUser(String email) throws SQLException;
    int deleteAll() throws SQLException;
}
