package daxterix.bank.dao;

import daxterix.bank.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {
    /**
     * obtain the record for an Account, given the account's number
     *
     * @param accountNumber
     * @return
     * @throws SQLException
     */
    Account select(long accountNumber) throws SQLException;

    /**
     * select all persisted accounts
     *
     * @return
     * @throws SQLException
     */
    List<Account> selectAll() throws SQLException;

    /**
     * select all account for given user
     *
     * @param email
     * @return
     * @throws SQLException
     */
    List<Account> selectForUser(String email) throws SQLException;

    /**
     * save the given account, auto assigns an account number to the account (given number is ignored)
     *
     * @param account
     * @return
     * @throws SQLException
     */
    int save(Account account) throws SQLException;

    /**
     * update account with given info, info must have number of the record to be updated
     *
     * @param info
     * @return
     * @throws SQLException
     */
    int update(Account info) throws SQLException;

    /**
     * delete given request (with ON DELETE CASCADE)
     *
     * @param accountNumber
     * @return
     * @throws SQLException
     */
    int delete(long accountNumber) throws SQLException;

    /**
     * delete all records for given user (with ON DELETE CASCADE)
     *
     * @param email
     * @return
     * @throws SQLException
     */
    int deleteForUser(String email) throws SQLException;

    /**
     * delete all records in table (with ON DELETE CASCADE)
     *
     * @return
     * @throws SQLException
     */
    int deleteAll() throws SQLException;
}
