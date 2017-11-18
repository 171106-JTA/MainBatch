package daxterix.bank.dao;

import daxterix.bank.model.UserRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAOImpl implements RequestDAO {
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

            return stmt.executeUpdate();
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

            return stmt.executeUpdate();
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

            return stmt.executeUpdate();
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

            return stmt.executeUpdate();
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
            return stmt.executeUpdate(sql);
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
