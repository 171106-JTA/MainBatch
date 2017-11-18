package daxterix.bank.dao;

import daxterix.bank.model.UserRequest;

import java.util.List;

public interface RequestDAO {
    UserRequest select(long id);
    List<UserRequest> selectForUser(String email);
    List<UserRequest> selectAll();
    int save(UserRequest req);
    int update(UserRequest info);
    int delete(long id);
    int deleteForUser(String email);
}
