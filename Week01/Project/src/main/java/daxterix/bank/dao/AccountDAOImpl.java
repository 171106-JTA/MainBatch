package daxterix.bank.dao;

import daxterix.bank.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public Account select(long accountNumber) {
        ResultSet queryRes = null;
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "SELECT * FROM bankaccount WHERE accountnumber = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountNumber);
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
    public List<Account> selectAll() {
        List<Account> cards;
        Statement stmt = null;
        ResultSet rs = null;
        try(Connection conn = DbUtils.getConnection()) {
            String sql = "SELECT * FROM flash_cards";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            cards = new ArrayList<>();
            while (rs.next())
                cards.add(readFromRow(rs));
            return cards;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DbUtils.close(stmt);
            DbUtils.close(rs);
        }
    }

    @Override
    public int save(Account user) {
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "INSERT INTO bankaccount(useremail, balance) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setDouble(2, user.getBalance());
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
    public int update(Account info) {
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "UPDATE bankaccount set useremail = ?, balance = ? WHERE accountnumber = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, info.getEmail());
            stmt.setDouble(2, info.getBalance());
            stmt.setDouble(3, info.getNumber());
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
    public int delete(long accountNumber) {
        PreparedStatement stmt = null;

        try (Connection conn = DbUtils.getConnection()) {
            String sql = "DELETE FROM bankaccount WHERE accountnumber = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, accountNumber);
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
            String sql = "DELETE FROM bankaccount WHERE useremail = ?";
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
        return 0;
    }

    private Account readFromRow(ResultSet rs) throws SQLException {
        Account a = new Account();
        a.setNumber(rs.getLong("accountnumber"));
        a.setEmail(rs.getString("useremail"));
        a.setBalance(rs.getDouble("balance"));
        return a;
    }
}
