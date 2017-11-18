package daxterix.bank.dao;


import daxterix.bank.model.User;

import java.util.List;

public interface UserDAO {
    User select(String email);

    List<User> selectAll();

    int save(User user);

    int updateUser(User info);

    int deleteByEmail(String email);

    User selectForAccount(long accountNumber);

    User selectForRequest(long requestId);
}
