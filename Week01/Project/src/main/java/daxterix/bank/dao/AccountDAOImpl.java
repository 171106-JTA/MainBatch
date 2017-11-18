package daxterix.bank.dao;

import daxterix.bank.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
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
        finally {
            Closer.close(stmt);
            Closer.close(queryRes);
        }
        return null;
    }

    @Override
    public List<Account> selectAll() throws SQLException {
        List<Account> cards;
        Statement stmt = null;
        ResultSet rs = null;
        try(Connection conn = connectionManager.getConnection()) {
            String sql = "SELECT * FROM flash_cards";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            cards = new ArrayList<>();
            while (rs.next())
                cards.add(readFromRow(rs));
            return cards;
        }
        finally {
            Closer.close(stmt);
            Closer.close(rs);
        }
    }

    @Override
    public int save(Account user) throws SQLException {
        PreparedStatement stmt = null;

        try (Connection conn = connectionManager.getConnection()) {
            String sql = "INSERT INTO bankaccount(useremail, balance) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setDouble(2, user.getBalance());
            return stmt.executeUpdate();
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
            return stmt.executeUpdate();
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
            String sql = "DELETE FROM bankaccount WHERE useremail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            return stmt.executeUpdate();
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
