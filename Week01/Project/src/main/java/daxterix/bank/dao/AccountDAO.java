package daxterix.bank.dao;

import daxterix.bank.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {
    Account select(long accountNumber) throws SQLException;
    List<Account> selectAll() throws SQLException;
    int save(Account user) throws SQLException;
    int update(Account info) throws SQLException;
    int delete(long accountNumber) throws SQLException;
    int deleteForUser(String email) throws SQLException;
}
