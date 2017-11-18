package daxterix.bank.dao;


import daxterix.bank.model.User2;

import java.util.List;

public interface UserDAO {
    User2 select(String email);

    List<User2> selectAll();

    int save(User2 user);

    int updateUser(User2 info);

    int deleteByEmail(String email);

    User2 selectForAccount(long accountNumber);

    User2 selectForRequest(long requestId);
}
