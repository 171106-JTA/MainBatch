package daxterix.bank.dao;

import daxterix.bank.model.User2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User2 select(String email) {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "SELECT * FROM bankuser WHERE useremail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
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
    public List<User2> selectAll() {
        ResultSet queryRes = null;
        Statement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "SELECT * FROM bankuser";
            stmt = conn.createStatement();
            queryRes = stmt.executeQuery(sql);

            List<User2> users = new ArrayList<>();
            while (queryRes.next())
                users.add(readFromRow(queryRes));
            return users;
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
    public User2 selectForAccount(long accountNumber) {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "SELECT b.* FROM bankuser usr INNER JOIN bankaccount acc " +
                            "WHERE usr.useremail = acc.useremail AND acc.accountnumber = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountNumber);
            queryRes = stmt.executeQuery();

            if (queryRes.next())
                return readFromRow(queryRes);
            return null;
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
    public User2 selectForRequest(long requestId) {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "SELECT b.* FROM bankuser usr INNER JOIN request req " +
                            "WHERE usr.useremail = req.fileremail AND req.requestid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, requestId);
            queryRes = stmt.executeQuery();

            if (queryRes.next())
                return readFromRow(queryRes);
            return null;
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
    public int save(User2 user) {
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "INSERT INTO bankuser(useremail, hashpass, isadmin, islocked) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.isAdmin()? 1 : 0);
            stmt.setInt(4, user.isLocked()? 1 : 0);
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
    public int updateUser(User2 info) {
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "UPDATE bankuser set hashpass = ?, isadmin = ?, islocked = ? WHERE useremail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(2, info.getPassword());
            stmt.setInt(3, info.isAdmin()? 1 : 0);
            stmt.setInt(4, info.isLocked()? 1 : 0);
            stmt.setString(1, info.getEmail());
            return stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.close(stmt);
        }
        return 0;   }

    @Override
    public int deleteByEmail(String email) {
        return 0;
    }


    // TODO are isLockd, and isAdmin WHAT THEY ARE SUPPOSED TO BE?
    private User2 readFromRow(ResultSet rs) throws SQLException {
        User2 u = new User2();
        u.setEmail(rs.getString("useremail"));
        //u.setPassword(rs.getString("password"));
        u.setLocked(rs.getInt("islocked") == 1);
        u.setAdmin(rs.getInt("isAdmin") == 1);
        return u;
    }
}
