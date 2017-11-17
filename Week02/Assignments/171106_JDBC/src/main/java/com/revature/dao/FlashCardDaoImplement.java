package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.FlashCard;
import com.revature.util.ConnectionUtil;

public class FlashCardDaoImplement implements FlashCardDao {

	private Statement stmt;

	@Override
	public void createFlashCard(FlashCard fc) {
		/*
		 * Try-With-Resources
		 * In a try block you can include any streams as a parameter and
		 *   Java will manage it for you and close it automatically without you having
		 *   to worry about it.
		 * Any class that implements autocloseable can do this.
		 */
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO flash_cards(fc_question, fc_answer)" +
					"VALUES('" + fc.getQuestion() + "', '" + fc.getAnswer() +
					"')";
			
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
				System.out.println("created rows: " + affected);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}

	}

	@Override
	public FlashCard selectFlashCardByID(Integer id) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		FlashCard fc = null;

		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM flash_cards WHERE fc_id = ?";
			ps = conn.prepareStatement(sql);

			//2 arguments. First refers to which question mark we are replacing
			//from left to right, each question mark is assigned a value from 1 and above, sequentially
			ps.setInt(1, id); 
			rs = ps.executeQuery();

			while (rs.next()) {
				fc = new FlashCard(rs.getString("fc_question"),
						rs.getString(3));
				fc.setFid(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}

		return fc;
	}

	@Override
	public void createFlashCardSP(FlashCard fc) {
		
		CallableStatement cs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String q = fc.getQuestion();
			String a = fc.getAnswer();
			String sql = "{call insert_fc_procedure(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, q);
			cs.setString(2, a);
			cs.executeQuery();
			System.out.println("Successful procedure call.");
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
		}
	}
	
	public FlashCard getAnswerByQuestion(FlashCard fc) {
		CallableStatement cs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "{call get_answer(?,?)";
			
			cs = conn.prepareCall(sql);
			cs.setString(1, fc.getQuestion());
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.executeQuery();
			fc = new FlashCard("A unique question", cs.getString(2));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
		}
		
		return fc;
	}

	/**
	 * @return a list of all of the flash cards in the DB
	 */
	@Override
	public List<FlashCard> getAllFlashCards() {
		List<FlashCard> flash_cards_list = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT * FROM flash_cards";
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			//traverse through result set and create each flash card from the data and add it to the set
			while (rs.next()) {
				 final int id = rs.getInt("fc_id");
				 final String question = rs.getString("fc_question");
				 final String answer = rs.getString("fc_answer");
				
				 final FlashCard fc = new FlashCard(question, answer);
				 fc.setFid(id);
				 flash_cards_list.add(fc);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
		}
		return flash_cards_list;
	}

	/**
	 * Update a flash card in the db with the the flash card passed in
	 * @return affected rows
	 */
	@Override
	public int updateFlashCardByID(FlashCard fc) {
		PreparedStatement ps = null;
		int effected = 0;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE FLASH_CARDS SET FC_QUESTION = ?, FC_ANSWER = ? WHERE FC_ID = ?";
			
			String answer = fc.getAnswer();
			String question = fc.getQuestion();
			int id = fc.getFid();
			
			ps = conn.prepareStatement(sql);
			ps.setInt(3, id);
			ps.setString(1, question); 
			ps.setString(2, answer); 

			effected = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(ps);
		}
		return effected;
	}

	/**
	 * Delete a flash card from the db with the given id
	 * @return affected rows
	 */
	@Override
	public int deleteFlashCardByID(Integer id) {
		PreparedStatement ps = null;
		int effected = 0;
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "DELETE FROM FLASH_CARDS WHERE FC_ID = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			effected = ps.executeUpdate();

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(ps);
		}
		return effected;
	}
}