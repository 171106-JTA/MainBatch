package com.revature.dao;

import com.revature.bean.FlashCard;
import com.revature.util.CloseStreams;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlashCardDaoImpl implements FlashCardDao {

    @Override
    public void createFlashCard(FlashCard f) {
        PreparedStatement stmt = null;
        try(Connection conn = ConnectionUtil.getConnection();) {
            String sql = "INSERT INTO flash_cards(fc_question, fc_answer) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, f.getQuestion());
            stmt.setString(2, f.getAnswer());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            CloseStreams.close(stmt);
        }
    }

    @Override
    public FlashCard selectFlashCardById(Integer id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try(Connection conn = ConnectionUtil.getConnection();) {
            String sql = "SELECT * FROM flash_cards WHERE fc_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            FlashCard fc = new FlashCard();
            if (rs.next()) {
                fc.setId(rs.getInt(1)); // grab data from col 1;
                fc.setQuestion(rs.getString("fc_question")); // can grab by index or by column name
                fc.setAnswer(rs.getString("fc_answer"));
            }
            return fc;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            CloseStreams.close(stmt);
            CloseStreams.close(rs);
        }
        return null;
    }

    @Override
    public void createFlashCardSP(FlashCard fc) {
        CallableStatement cs = null;

        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "{call insert_fc_procedure(?, ?)}";
            cs = conn.prepareCall(sql);
            cs.setString(1, fc.getQuestion());
            cs.setString(2, fc.getAnswer());
            cs.executeQuery();
            System.out.println("Successful procedure call");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            CloseStreams.close(cs);
        }
    }

    @Override
    public FlashCard getFlashCardByQuestion(FlashCard fc) {
        CallableStatement cs = null;
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "{call get_answer_procedure(?, ?)}";
            cs = conn.prepareCall(sql);
            cs.setString(1, fc.getQuestion());
            cs.registerOutParameter(2, Types.VARCHAR);  // set this as an out parameter

            cs.executeQuery();
            return new FlashCard(fc.getQuestion(), cs.getString(2));    // we can only recieve the out parameters
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            CloseStreams.close(cs);
        }
        return null;
    }

    @Override
    public List<FlashCard> getAllFlashCards() {
        List<FlashCard> cards = null;
        Statement stmt = null;
        ResultSet rs = null;
        try(Connection conn = ConnectionUtil.getConnection()) {
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
            CloseStreams.close(stmt);
            CloseStreams.close(rs);
        }
    }

    private FlashCard readFromRow(ResultSet rs) throws SQLException {
        FlashCard fc = new FlashCard();
        fc.setId(rs.getInt(1)); // grab data from col 1;
        fc.setQuestion(rs.getString("fc_question")); // can grab by index or by column name
        fc.setAnswer(rs.getString("fc_answer"));
        return fc;
    }


    @Override
    public int updateFlashCard(FlashCard fc) {
        PreparedStatement stmt = null;
        try(Connection conn = ConnectionUtil.getConnection();) {
            String sql = "UPDATE flash_cards SET fc_question=?, fc_answer=? WHERE fc_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fc.getQuestion());
            stmt.setString(2, fc.getAnswer());
            stmt.setInt(3, fc.getId());
            return stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            CloseStreams.close(stmt);
        }
        return -1;
    }

    @Override
    public int deleteFlashCardById(int id) {
        PreparedStatement stmt = null;
        try(Connection conn = ConnectionUtil.getConnection();) {
            String sql = "DELETE FROM flash_cards WHERE fc_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            CloseStreams.close(stmt);
        }
        return -1;
    }
}

