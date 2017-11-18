package daxterix.bank.dao;

import daxterix.bank.model.UserRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAOImpl implements RequestDAO {
    @Override
    public UserRequest select(long id) {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "SELECT * FROM request WHERE requestid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            queryRes = stmt.executeQuery();
            if (queryRes.next())
                return readFromRow(queryRes);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.close(stmt);
            DbUtils.close(queryRes);
        }
        return null;
    }

    @Override
    public List<UserRequest> selectForUser(String email) {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "SELECT * FROM request WHERE fileremail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            queryRes = stmt.executeQuery();

            List<UserRequest> requests = new ArrayList<>();
            while (queryRes.next())
                requests.add(readFromRow(queryRes));
            return requests;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.close(stmt);
            DbUtils.close(queryRes);
        }
        return null;
    }

    @Override
    public List<UserRequest> selectAll() {
        ResultSet queryRes = null;
        Statement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "SELECT * FROM request";
            stmt = conn.createStatement();
            queryRes = stmt.executeQuery(sql);

            List<UserRequest> requests = new ArrayList<>();
            while (queryRes.next())
                requests.add(readFromRow(queryRes));
            return requests;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.close(stmt);
            DbUtils.close(queryRes);
        }
        return null;
    }

    @Override
    public int save(UserRequest req) {
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "INSERT INTO request (requestid, fileremail, filedate, requesttype) VALUES(?,?,?,?)";
            stmt = conn.prepareStatement(sql);

            stmt.setLong(1, req.getId());
            stmt.setString(2, req.getFilerEmail());
            Timestamp ts = java.sql.Timestamp.valueOf(req.getFileDate());
            stmt.setTimestamp(3, ts);
            stmt.setInt(4, req.getType());

            return stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.close(stmt);
        }
        return 0;
    }

    @Override
    public int update(UserRequest info) {
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "UPDATE request SET fileremail=?, filedate=?, requesttype=?) WHERE requestid=?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, info.getFilerEmail());
            Timestamp ts = java.sql.Timestamp.valueOf(info.getFileDate());
            stmt.setTimestamp(2, ts);
            stmt.setInt(3, info.getType());
            stmt.setLong(4, info.getId());

            return stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.close(stmt);
        }
        return 0;
    }

    @Override
    public int delete(long id) {
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "DELETE FROM request WHERE requestid=?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            return stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.close(stmt);
        }
        return 0;
    }

    @Override
    public int deleteForUser(String email) {
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "DELETE FROM request WHERE fileremail=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            return stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.close(stmt);
        }
        return 0;   }


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
