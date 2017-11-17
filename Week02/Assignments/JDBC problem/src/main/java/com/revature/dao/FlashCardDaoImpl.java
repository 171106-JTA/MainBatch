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

public class FlashCardDaoImpl implements FlashCardDao {
	private Statement stmt = null; 

	@Override
	public void createFlashCard(FlashCard fc) {
		/*
		 * Try-With-Resources
		 * In a try block you can include any streams as a parameter and
		 * Java will manage it for you and close it automatically without
		 * you having to worry about it. Any class that implements
		 * autocloseable can do this. 
		 */
		try(Connection conn = ConnectionUtil.getConection()) {

			String sql = "INSERT INTO sjgillet.flash_cards(fc_question, fc_answer)" + 
					"VALUES('" + fc.getQuestion() + "', '" + fc.getAnswer() + "')"; //do NOT put a ';' at the end of the sql statment
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql); 

			System.out.println(affected + " Rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(stmt); 
		}


	}

	@Override
	public FlashCard selectFashCardById(Integer id) {
		PreparedStatement ps = null; 
		ResultSet rs = null; //ResultSets hold query data
		FlashCard fc = null; 

		try(Connection conn = ConnectionUtil.getConection()) {
			String sql = "SELECT * FROM sjgillet.flash_cards WHERE fc_id = ?"; 
			ps = conn.prepareStatement(sql);
			//setting variables here takes 2 args. 1st: which '?' we are replacing, left to right,
			//starting indexed at 1 and incrementing
			//2nd is what to replace it with
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while(rs.next()) {
				fc = new FlashCard(); 
				fc.setId(rs.getInt(1)); //grabs data from column 1, not the first question mark
				fc.setQuestion(rs.getString("fc_question"));//may pull info by column num or col name
				fc.setAnswer(rs.getString("fc_answer"));
			}
			return fc;



		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(ps); 
			close(rs); 
		}
		return null;
	}

	public void createFlashCardSP(FlashCard fc) {
		CallableStatement cs = null;

		try(Connection conn = ConnectionUtil.getConection()) {
			String q = fc.getQuestion(); 
			String a = fc.getAnswer(); 
			String sql = "{call sjgillet.insert_fc_proc(?,?)}";//syntax is diff for stored procedures. use {}
			cs = conn.prepareCall(sql); 
			cs.setString(1, q);
			cs.setString(2, a);
			cs.executeQuery();
			System.out.println("Successful procedure call");


		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs); 
		}
	}

	public FlashCard selectFlashCardByQuestion(String q) {
		FlashCard fc = null; 
		CallableStatement cs = null; 

		try(Connection conn = ConnectionUtil.getConection()) {
			String sql = "{call sjgillet.get_answer(?,?)}";
			cs = conn.prepareCall(sql); 
			cs.setString(1, q);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR); 
			cs.executeQuery(); 
			return new FlashCard(q, cs.getString(2)); //not allowed to touch the IN variables of a proc,
			//getString(2) gets the second variable, the OUT, from get_answer. 


			//return new FlashCard(q, cs.getString(1));

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs); 
		}



		return null;
	}

	@Override
	public List<FlashCard> getAllFlashCards() {
		Statement stmt = null; 
		ResultSet rs = null; 
		List<FlashCard> cards = new ArrayList();
		
		try(Connection conn = ConnectionUtil.getConection()) {
			String sql = "SELECT * FROM sjgillet.flash_cards"; 
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			
			while(rs.next())
				cards.add(new FlashCard(rs.getInt(1),rs.getString("fc_question"),rs.getString("fc_answer")));
			
			return cards; 
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs); 
		}

		return null;
	}

	@Override
	public int updateFlashCardById(FlashCard fc) {
		PreparedStatement ps = null; 
		ResultSet rs = null;
		int rowsAffected = 0; 
		
		try(Connection conn = ConnectionUtil.getConection()) {
			String sql = "UPDATE sjgillet.flash_cards SET fc_question = ? , fc_answer = ? WHERE fc_id = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setString(1, fc.getQuestion());
			ps.setString(2, fc.getAnswer());
			ps.setInt(3, fc.getId());

			rowsAffected = ps.executeUpdate(); 
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs); 

		}
		return rowsAffected;
	}

	@Override
	public int deleteFlashCardById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		int rowsAffected = 0; 
		
		try(Connection conn = ConnectionUtil.getConection()) {
			String sql = "DELETE FROM sjgillet.flash_cards WHERE fc_id = ?"; 
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rowsAffected = ps.getUpdateCount(); 

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs); 
		}
		
		return rowsAffected;
	}



}
