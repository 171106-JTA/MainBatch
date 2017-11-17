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

import oracle.jdbc.OracleTypes;

public class FlashCardDaoImplement implements FlashCardDao {
	private Statement stmt = null;

	public void createFlashCard(FlashCard fc) {
		/*
		 * Try-With-Resources In a try block, you can include any streams as a parameter
		 * and Java will manage it for you and close it automatically without you having
		 * to worry about it
		 */
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO ew_1.FLASH_CARDS(FC_QUESTION, FC_ANSWER)" + "VALUES('" + fc.getQuestion() + "', '"
					+ fc.getAnswer() + "')";
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);

			System.out.println(affected + " Rows Affected");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	};

	public FlashCard selectFlashCardByID(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null; // ResultSets hold query data
		FlashCard fc = null;

		try (Connection conn = ConnectionUtil.getConnection();) {
			String sql = "SELECT * FROM FLASH_CARDS WHERE FC_ID = ?";
			ps = conn.prepareStatement(sql);

			// Setting vars takes 2 args
			// The first refers to which question mark we are replacing.
			// From left to right, each qustion mark is assigned a value sequentially from 1
			// up.
			// The above select statement has one ?, labeled as 1
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				fc = new FlashCard(rs.getString("fc_question"), // Note: you can pull either by column number or name!
						rs.getString(3) // Grab dta from column 3
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
	};

	public void createFlashCardSP(FlashCard fc) {
		CallableStatement cs = null;

		try (Connection conn = ConnectionUtil.getConnection();) {
			String q = fc.getQuestion();
			String a = fc.getAnswer();
			String sql = "{call INSERT_FC_PROCEDURE(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, q);
			cs.setString(2, a);
			cs.executeQuery();
			System.out.println("Successful Procedure Call!");
		} catch (SQLException e) {
			System.out.println("Not Successful" + e.getStackTrace());
			e.getStackTrace();
		} finally {
			close(cs);
		}
	}

	public FlashCard getAnswerByQuestion(FlashCard fc) {
		CallableStatement cs = null;

		try (Connection conn = ConnectionUtil.getConnection();) {
			String sql = "{call get_answer(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, fc.getQuestion());
			cs.registerOutParameter(2, java.sql.Types.VARCHAR); // VARCHAR represents all the VARCHARs (Including
																// VARCHAR2)
			cs.executeQuery(); // Stores results in itself

			fc = new FlashCard(cs.getString(1), cs.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
		}

		return fc;
	}

	public List<FlashCard> getAllFlashCards() {
		CallableStatement cs = null;
		ResultSet rs = null;
		List<FlashCard> return_FC = new ArrayList<FlashCard>();
		try (Connection conn = ConnectionUtil.getConnection();) {
			
			String sql = "{call fetch_all_flashcards(?)}";
			cs = conn.prepareCall(sql);
			
			//From https://www.mkyong.com/jdbc/jdbc-callablestatement-stored-procedure-cursor-example/
			//Accessed 11/17/2017
			cs.registerOutParameter(1,  OracleTypes.CURSOR);
			cs.executeQuery();
			rs = (ResultSet) cs.getObject(1);
			while(rs.next()) {
				int id = rs.getInt("FC_ID"); 
				String question = rs.getString("FC_QUESTION"); 
				String answer = rs.getString("FC_ANSWER");
				
				return_FC.add(new FlashCard(question, answer));
			}
			
			return return_FC;
		} catch (SQLException e) {
			System.out.println("Not Successful" + e.getStackTrace());
			e.getStackTrace();
		} finally {
			close(cs);
			close(rs);
		}
		
		return null;
	}
	
	public int updateFlashCardById(FlashCard fc) {
		Statement stmt = null;
		int lines_affected = -1;

		try (Connection conn = ConnectionUtil.getConnection();) {			
			String sql = "    UPDATE flash_cards" + 
					" set FC_QUESTION = '" + fc.getQuestion() + "', FC_ANSWER = '" + fc.getAnswer() + 
					"' where FC_ID = " + fc.getId();
			stmt = conn.createStatement();
			lines_affected = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return lines_affected;
	}
	
	public int deleteFlashCardById(Integer id) {
		PreparedStatement ps = null;
		int lines_affected = -1;

		try (Connection conn = ConnectionUtil.getConnection();) {
			String sql = "DELETE FROM Flash_Cards " + 
					"WHERE FC_ID = ?";
			ps = conn.prepareStatement(sql);

			// Setting vars takes 2 args
			// The first refers to which question mark we are replacing.
			// From left to right, each qustion mark is assigned a value sequentially from 1
			// up.
			// The above select statement has one ?, labeled as 1
			ps.setInt(1, id);
			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}

		return lines_affected;
	}
}
