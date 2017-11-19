package daxterix.bank.dao;

import daxterix.bank.model.UserRequest;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAOImpl implements RequestDAO {
    private final Logger logger = Logger.getLogger(getClass());
    private ConnectionManager connectionManager;

    public RequestDAOImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public UserRequest select(long id) throws SQLException {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM request WHERE requestid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            queryRes = stmt.executeQuery();
            if (queryRes.next())
                return readFromRow(queryRes);
        }
        catch (SQLException e){
            logger.error(String.format("[%s select()] Exception while selecting %d:\n%s", getClass(), id, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
            Closer.close(queryRes);
        }
        return null;
    }

    @Override
    public List<UserRequest> selectForUser(String email) throws SQLException {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM request WHERE fileremail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            queryRes = stmt.executeQuery();

            List<UserRequest> requests = new ArrayList<>();
            while (queryRes.next())
                requests.add(readFromRow(queryRes));
            return requests;
        }
        catch (SQLException e){
            logger.error(String.format("[%s selectForUser()] Exception while selecting for user %s:\n%s", getClass(), email, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
            Closer.close(queryRes);
        }
    }

    @Override
    public List<UserRequest> selectAll() throws SQLException {
        ResultSet queryRes = null;
        Statement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM request";
            stmt = conn.createStatement();
            queryRes = stmt.executeQuery(sql);

            List<UserRequest> requests = new ArrayList<>();
            while (queryRes.next())
                requests.add(readFromRow(queryRes));
            return requests;
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
    public int save(UserRequest req) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "INSERT INTO request (fileremail, filedate, requesttype) VALUES(?,?,?)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, req.getFilerEmail());
            Timestamp ts = java.sql.Timestamp.valueOf(req.getFileDate());
            stmt.setTimestamp(2, ts);
            stmt.setInt(3, req.getType());
            int affected = stmt.executeUpdate();
            logger.debug(String.format("[%s save()] saved %s; %d rows affected", getClass(), req, affected));
            return affected;
        }
        catch (SQLException e){
            logger.error(String.format("[%s save()] Exception while saving %s:\n%s", getClass(), req, e));
            throw e;
        }
        finally {
            Closer.close(stmt);
        }
    }

    @Override
    public int update(UserRequest info) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "UPDATE request SET fileremail=?, filedate=?, requesttype=? WHERE requestid=?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, info.getFilerEmail());
            Timestamp ts = java.sql.Timestamp.valueOf(info.getFileDate());
            stmt.setTimestamp(2, ts);
            stmt.setInt(3, info.getType());
            stmt.setLong(4, info.getId());
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
    public int delete(long id) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "DELETE FROM request WHERE requestid=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            int affected = stmt.executeUpdate();
            logger.debug(String.format("[%s delete()] deleted %d; %d rows affected", getClass(), id, affected));
            return affected;
        }
        catch (SQLException e){
            logger.error(String.format("[%s delete()] Exception while deleting %d:\n%s", getClass(), id, e));
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
            String sql = "DELETE FROM request WHERE fileremail=?";
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
            String sql = "DELETE FROM request";
            stmt = conn.createStatement();

            int affected = stmt.executeUpdate(sql);
            logger.debug(String.format("[%s deleteAll()] deleted all; %d rows affected", getClass(), affected));
            return affected;       }
        catch (SQLException e){
            logger.error(String.format("[%s deleteAll()] Exception while deleting all:\n%s", getClass(), e));
            throw e;
        }
        finally {
            Closer.close(stmt);
        }
    }

    private UserRequest readFromRow(ResultSet rs) throws SQLException {
        UserRequest req = new UserRequest();
        java.sql.Timestamp timestamp = rs.getTimestamp("filedate");
        req.setFileDate(timestamp.toLocalDateTime());
        req.setFilerEmail(rs.getString("fileremail"));
        req.setType(rs.getInt("requesttype"));
        req.setId(rs.getInt("requestid"));
        return req;
    }
}
