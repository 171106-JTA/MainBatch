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

/* 
 * connection handled by dao, no longer need driver stuff.
 */
public class FlashCardDaoImpl implements FlashCardDao {
	private Statement stmt = null;

	public void createFlashCard(FlashCard fc) {
		/*
		 * try-with-resources in a try block you can include any streams as a parameter
		 * java will manage it for you and close it automaticaly w/o you having to wory
		 * about it any class that implements autocloseable can do this
		 */

		try (Connection conn = ConnectionUtil.getConnection();) {
			String sql = "INSERT INTO flash_cards VALUES("+ fc.getId() + ", '" + fc.getQuestion() + "', '"
					+ fc.getAnswer() + "')";
//			String sql = "INSERT INTO flash_cards(fc_question,fc_answer)" + "VALUES('" + fc.getQuestion() + "', '"
//					+ fc.getAnswer() + "')";
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			System.out.println(affected + " rows affected");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	};

	public FlashCard selectFlashCardById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null; // ResultSets hold query data
		FlashCard fc = null;

		try (Connection conn = ConnectionUtil.getConnection();) {
			String sql = "SELECT * FROM flash_cards WHERE fc_id = ?";
			ps = conn.prepareStatement(sql);
			// Setting your variables takes two arguments:
			// The first refers to which question mark we are replacing
			// From left to right, each question mark is assigned a value from 1 and
			// above, sequentially.
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				fc = new FlashCard(rs.getString("fc_question"), // NOTE: you can pull either by column # or name.
						rs.getString(3) // Grab data from column 3
				);

				fc.setId(rs.getInt(1)); // Grab data from column 1
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}

		return fc;
	}

	public void createFlashCardSP(FlashCard fc) {
		CallableStatement cs = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String q = fc.getQuestion();
			String a = fc.getAnswer();
			String sql = "{call insert_fc_procedure(?,?)}";// Syntax is different for storedProcedures
			cs = conn.prepareCall(sql);
			cs.setString(1, q);
			cs.setString(2, a);
			cs.executeQuery();
			System.out.println("Successful procedure call.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
		}
	}

	public FlashCard getAnswerByQuestion(FlashCard fc) {
		CallableStatement cs = null;

		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "{call get_answer(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, fc.getQuestion());
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.executeQuery();

			fc = new FlashCard("blah", cs.getString(2));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
		}

		return fc;
	}

	@Override
	public List<FlashCard> getAllFlashCards() {
		List<FlashCard> listfc = new ArrayList<FlashCard>();
		PreparedStatement ps = null;
		ResultSet rs = null; // ResultSets hold query data
		FlashCard fc = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM flash_cards";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				fc = new FlashCard(rs.getString(2), rs.getString(3));
				listfc.add(fc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}

		return listfc;
	}

	/* 
	 * take given flashcard, get id
	 * at id, replaces current fc w new one
	 */
	@Override
	public int updateFlashCardByID(FlashCard fc) {
		
		
		Integer fc_id = fc.getId();		//gets id from fc
		PreparedStatement ps = null;
		
		Integer affected = 0;
		try (Connection conn = ConnectionUtil.getConnection();) {
			String sql = "UPDATE flash_cards SET fc_question ='Is this the most fun ever?', fc_answer ='You betcha~!' WHERE fc_id = " + fc_id; //gets 
			ps = conn.prepareStatement(sql);
			affected = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return affected; // num rows affected
	}

	@Override
	public int deleteFlashCardByID(Integer id) {
		PreparedStatement ps = null;
		int affected = 0;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM flash_cards WHERE fc_id = " + id;
			ps = conn.prepareStatement(sql);
			affected = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
		return affected; //rows affected
	}
}
