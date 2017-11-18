package daxterix.bank.dao;

import daxterix.bank.model.Account;

import java.util.List;

public interface AccountDAO {
    Account select(long accountNumber);
    List<Account> selectAll();
    int save(Account user);
    int update(Account info);
    int delete(long accountNumber);
    int deleteForUser(String email);
}
