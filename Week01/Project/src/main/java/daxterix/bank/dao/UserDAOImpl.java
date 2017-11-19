package daxterix.bank.dao;

import daxterix.bank.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final Logger logger = Logger.getLogger(getClass());
    private ConnectionManager connectionManager;

    public UserDAOImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }


    @Override
    public User select(String email) throws SQLException {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM bankuser WHERE useremail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            queryRes = stmt.executeQuery();
            if (queryRes.next())
                return readFromRow(queryRes);
        }
        catch (SQLException e){
            logger.error(String.format("[%s select()] Exception while selecting %s:\n%s", getClass(), email, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
            Closer.close(queryRes);
        }
        return null;
    }

    @Override
    public List<User> selectAll() throws SQLException {
        ResultSet queryRes = null;
        Statement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM bankuser";
            stmt = conn.createStatement();
            queryRes = stmt.executeQuery(sql);

            List<User> users = new ArrayList<>();
            while (queryRes.next())
                users.add(readFromRow(queryRes));
            return users;
        }
        catch (SQLException e){
            logger.error(String.format("[%s selectAll()] Exception while selecting all:\n%s", getClass(), e));
            throw e;
        }
        finally {
            Closer.close(stmt);
            Closer.close(queryRes);
        }
    }

    @Override
    public List<User> selectByLockStatus(boolean lockStatus) throws SQLException {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT usr.* FROM bankuser usr " +
                            "WHERE usr.islocked = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lockStatus? 1 : 0);
            queryRes = stmt.executeQuery();

            List<User> users = new ArrayList<>();
            while (queryRes.next())
                users.add(readFromRow(queryRes));
            return users;
        }
        catch (SQLException e){
            logger.error(String.format("[%s selectByLockStatus()] Exception while selecting %s:\n%s", getClass(), lockStatus? "locked" : "unlocked", e));
            throw e;
        }
        finally {
            Closer.close(stmt);
            Closer.close(queryRes);
        }
    }

    @Override
    public User selectForAccount(long accountNumber) throws SQLException {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT usr.* FROM bankuser usr INNER JOIN bankaccount acc " +
                            "ON usr.useremail = acc.useremail AND acc.accountnumber = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountNumber);
            queryRes = stmt.executeQuery();

            if (queryRes.next())
                return readFromRow(queryRes);
        }
        catch (SQLException e){
            logger.error(String.format("[%s selectForAccount()] Exception while selecting for account %d:\n%s", getClass(), accountNumber, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
            Closer.close(queryRes);
        }
        return null;
    }

    @Override
    public User selectForRequest(long requestId) throws SQLException {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT usr.* FROM bankuser usr INNER JOIN request req " +
                            "ON usr.useremail = req.fileremail AND req.requestid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, requestId);
            queryRes = stmt.executeQuery();

            if (queryRes.next())
                return readFromRow(queryRes);
        }
        catch (SQLException e){
            logger.error(String.format("[%s selectForRequest()] Exception while selecting for request %d:\n%s", getClass(), requestId, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
            Closer.close(queryRes);
        }
        return null;
    }

    @Override
    public int save(User user) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "INSERT INTO bankuser(useremail, passhash, isadmin, islocked) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.isAdmin()? 1 : 0);
            stmt.setInt(4, user.isLocked()? 1 : 0);
            int affected = stmt.executeUpdate();
            logger.debug(String.format("[%s save()] saved %s; %d rows affected", getClass(), user, affected));
            return affected;
        }
        catch (SQLException e){
            logger.error(String.format("[%s save()] Exception while saving %s:\n%s", getClass(), user, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
        }
    }

    @Override
    public int update(User info) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "UPDATE bankuser set passhash = ?, isadmin = ?, islocked = ? WHERE useremail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, info.getPassword());
            stmt.setInt(2, info.isAdmin()? 1 : 0);
            stmt.setInt(3, info.isLocked()? 1 : 0);
            stmt.setString(4, info.getEmail());
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
    public int delete(String email) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "DELETE FROM bankuser WHERE useremail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            int affected = stmt.executeUpdate();
            logger.debug(String.format("[%s delete()] deleted %s; %d rows affected", getClass(), email, affected));
            return affected;
        }
        catch (SQLException e){
            logger.error(String.format("[%s delete()] Exception while deleting %s:\n%s", getClass(), email, e));
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
            String sql = "DELETE FROM bankuser";
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

    private User readFromRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setEmail(rs.getString("useremail"));
        u.setPassword(rs.getString("passhash"));
        u.setLocked(rs.getInt("islocked") == 1);
        u.setAdmin(rs.getInt("isAdmin") == 1);
        return u;
    }
}
