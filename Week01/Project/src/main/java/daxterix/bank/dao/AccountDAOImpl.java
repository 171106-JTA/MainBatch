package daxterix.bank.dao;

import daxterix.bank.model.Account;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
    private final Logger logger = Logger.getLogger(getClass());
    private ConnectionManager connectionManager;

    public AccountDAOImpl(ConnectionManager connMan) {
        connectionManager = connMan;

    }

    @Override
    public Account select(long accountNumber) throws SQLException {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM bankaccount WHERE accountnumber = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountNumber);
            queryRes = stmt.executeQuery();
            if (queryRes.next())
                return readFromRow(queryRes);
        }
        catch (SQLException e){
            logger.error(String.format("[%s select()] Exception while selecting %d:\n%s", getClass(), accountNumber, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
            Closer.close(queryRes);
        }
        return null;
    }

    @Override
    public List<Account> selectAll() throws SQLException {
        List<Account> accts;
        Statement stmt = null;
        ResultSet rs = null;
        try(Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM bankaccount";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            accts = new ArrayList<>();
            while (rs.next())
                accts.add(readFromRow(rs));
            return accts;
        }
        catch (SQLException e){
            logger.error(String.format("[%s selectAll()] Exception while selecting all:\n%s", getClass(), e));
            throw e;
        }
        finally {
            Closer.close(stmt);
            Closer.close(rs);
        }
    }

    @Override
    public List<Account> selectForUser(String email) throws SQLException {
        List<Account> accts;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try(Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM bankaccount WHERE useremail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            accts = new ArrayList<>();
            while (rs.next())
                accts.add(readFromRow(rs));
            return accts;
        }
        catch (SQLException e){
            logger.error(String.format("[%s selectForUser()] Exception while selecting for user %s:\n%s", getClass(), email, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
            Closer.close(rs);
        }
    }

    @Override
    public int save(Account account) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "INSERT INTO bankaccount(useremail, balance) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.getEmail());
            stmt.setDouble(2, account.getBalance());
            int affected = stmt.executeUpdate();
            logger.debug(String.format("[%s save()] saved %s; %d rows affected", getClass(), account, affected));
            return affected;
        }
        catch (SQLException e){
            logger.error(String.format("[%s save()] Exception while saving %s:\n%s", getClass(), account, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
        }
    }

    @Override
    public int update(Account info) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "UPDATE bankaccount set useremail = ?, balance = ? WHERE accountnumber = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, info.getEmail());
            stmt.setDouble(2, info.getBalance());
            stmt.setDouble(3, info.getNumber());
            int affected = stmt.executeUpdate();
            logger.debug(String.format("[%s update()] updated %s; %d rows affected", getClass(), info, affected));
            return affected;
        }
        catch (SQLException e){
            logger.error(String.format("[%s update()] Exception while updating %s:\n%s", getClass(), info, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
        }
    }

    @Override
    public int delete(long accountNumber) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "DELETE FROM bankaccount WHERE accountnumber = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountNumber);
            int affected = stmt.executeUpdate();
            logger.debug(String.format("[%s delete()] deleted %d; %d rows affected", getClass(), accountNumber, affected));
            return affected;
        }
        catch (SQLException e){
            logger.error(String.format("[%s delete()] Exception while deleting %d:\n%s", getClass(), accountNumber, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
        }
    }

    @Override
    public int deleteForUser(String email) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "DELETE FROM bankaccount WHERE useremail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            int affected = stmt.executeUpdate();
            logger.debug(String.format("[%s deleteForUser()] deleted for user %s; %d rows affected", getClass(), email, affected));
            return affected;
        }
        catch (SQLException e){
            logger.error(String.format("[%s deleteForUser()] Exception while deleting for user %s:\n%s", getClass(), email, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
        }
    }

    @Override
    public int deleteAll() throws SQLException {
        Statement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "DELETE FROM bankaccount";
            stmt = conn.createStatement();
            int affected = stmt.executeUpdate(sql);
            logger.debug(String.format("[%s deleteAll()] deleted all; %d rows affected", getClass(), affected));
            return affected;
        }
        catch (SQLException e){
            logger.error(String.format("[%s deleteAll()] Exception while deleting all:\n%s", getClass(), e));
            throw e;
        }
        finally {
            Closer.close(stmt);
        }
    }

    private Account readFromRow(ResultSet rs) throws SQLException {
        Account a = new Account();
        a.setNumber(rs.getLong("accountnumber"));
        a.setEmail(rs.getString("useremail"));
        a.setBalance(rs.getDouble("balance"));
        return a;
    }
}