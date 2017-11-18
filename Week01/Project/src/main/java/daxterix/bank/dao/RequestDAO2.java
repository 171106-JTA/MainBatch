package daxterix.bank.dao;

import daxterix.bank.model.UserRequest2;

import java.util.List;

public interface RequestDAO2 {
    UserRequest2 select(long id);
    List<UserRequest2> selectForUser(String email);
    List<UserRequest2> selectAll();
    int save(UserRequest2 req);
    int update(UserRequest2 info);
    int delete(long id);
    int deleteForUser(String email);
}
