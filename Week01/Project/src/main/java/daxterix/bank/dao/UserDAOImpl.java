package daxterix.bank.dao;

import daxterix.bank.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

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
            String sql = "SELECT b.* FROM bankuser usr INNER JOIN bankaccount acc " +
                            "WHERE usr.useremail = acc.useremail AND acc.accountnumber = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountNumber);
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
    public User selectForRequest(long requestId) throws SQLException {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT b.* FROM bankuser usr INNER JOIN request req " +
                            "WHERE usr.useremail = req.fileremail AND req.requestid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, requestId);
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
    public int save(User user) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "INSERT INTO bankuser(useremail, passhash, isadmin, islocked) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.isAdmin()? 1 : 0);
            stmt.setInt(4, user.isLocked()? 1 : 0);
            return stmt.executeUpdate();
        }
        finally {
            Closer.close(stmt);
        }
    }

    @Override
    public int updateUser(User info) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "UPDATE bankuser set passhash = ?, isadmin = ?, islocked = ? WHERE useremail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, info.getPassword());
            stmt.setInt(2, info.isAdmin()? 1 : 0);
            stmt.setInt(3, info.isLocked()? 1 : 0);
            stmt.setString(4, info.getEmail());
            return stmt.executeUpdate();
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
            String sql = "DELETE FROM bankuser";
            stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        }
        finally {
            Closer.close(stmt);
        }
    }


    // TODO are isLocked, and isAdmin WHAT THEY ARE SUPPOSED TO BE?
    private User readFromRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setEmail(rs.getString("useremail"));
        //u.setPassword(rs.getString("password"));
        u.setLocked(rs.getInt("islocked") == 1);
        u.setAdmin(rs.getInt("isAdmin") == 1);
        return u;
    }
}
