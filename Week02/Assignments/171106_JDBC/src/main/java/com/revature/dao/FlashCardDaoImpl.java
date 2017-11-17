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

public class FlashCardDaoImpl implements FlashCardDao{
	private Statement stmt = null;
	@Override
	public void createFlashCard(FlashCard fc) {
				// Try With Resources
				//In a try block, you cant include anyu streams as a parameter and java
				//will manage and close it for you automatically without you havaing
				//to worry about it. Any class that implements autocloseable can do this.
				
				try(Connection conn = ConnectionUtil.getConnection()){
					String sql = "INSERT INTO flash_cards(fc_question, fc_answer)" +
							"VALUES('" + fc.getQuestion() + "', '" + fc.getAnswer() + "')";  
					stmt = conn.createStatement();
					int affected = stmt.executeUpdate(sql);
					System.out.println(affected + " Rows Affected");
				}catch(SQLException e) {
					e.printStackTrace();
				}finally {
					close(stmt);
				}
		
	}
	public FlashCardDaoImpl() {
		
	}
	@Override
	public FlashCard selectFlashCardById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultsSects hold query data
		FlashCard fc = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM flash_cards WHERE fc_id = ?";
			ps = conn.prepareStatement(sql);
			//Setting your variables takes two arguments:
			//The first refers to which question mark we are replacing
			//From left to right, each question mark is assigned a value from 1 and 
			//above, sequentially
			ps.setInt(1, id); //SQL is 1 indexed
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				fc = new FlashCard(
						rs.getString("fc_question"),
						rs.getString(3) //Grab data from column 3
						);
				fc.setId(rs.getInt(1));//Grab data from column1
				// NOTE: you can pull either by column number or name"
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(ps);
			close(rs);
		}
		return fc;
	}
	
	@Override
	public void createFlashCardSP(FlashCard fc) {
		CallableStatement cs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String q = fc.getQuestion();
			String a = fc.getAnswer();
			String sql = "{call insert_fc_procedure(?, ?)}";
			
			cs = conn.prepareCall(sql);
			cs.setString(1, q);
			cs.setString(2, a);
			cs.executeQuery();
			System.out.println("Success");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(cs);
		}
		
	}
	
	public FlashCard getAnswerByQuestion(FlashCard fc) {
		CallableStatement cs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "{call get_answer(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, fc.getQuestion());
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.executeQuery();
			fc = new FlashCard("blah", cs.getString(2));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(cs);
		}
		return fc;
	}
	
	/**
	 * This method returns all the flash cards in the database as a list.
	 * It utilizes a statement to query the database. The returned resultset is put
	 * into a while loop to extract all the entries into a list
	 * 
	 * @return List<FlashCard> - List of flash cards
	 */
	@Override
	public List<FlashCard> getAllFlashCards() {
		ResultSet rs = null;
		List<FlashCard> fclist = new ArrayList<>();
		FlashCard fc = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM flash_cards";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				fc = new FlashCard(rs.getString(2), rs.getString(3));
				fc.setId(rs.getInt(1));
				fclist.add(fc);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
		}
		return fclist;
	}
	
	/**
	 * This method utilizes a callable procedure on the database. 
	 * It updates the question and answer section of the flashcard.
	 * 
	 * @param fc - a flashcard object
	 * 
	 * @return the number of rows affected by the update
	 */
	@Override
	public int updateFlashCardById(FlashCard fc) {
		CallableStatement cs = null;
		int affected = -1;
		if(fc == null) {
			System.out.println("Empty FlashCard");
			return 0;
		}
		
		Integer fc_id = fc.getId();
		String fc_question = fc.getQuestion();
		String fc_answer = fc.getAnswer();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "{call update_flashcard(?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, fc_id);
			cs.setString(2, fc_question);
			cs.setString(3, fc_answer);
			affected = cs.executeUpdate();
			System.out.println(affected + " row was changed");
		}catch(SQLException e) {
			
		}finally {
			close(cs);
		}
		return affected;
	}
	
	/**
	 * This methods deletes flashcards by id
	 * A prepared statement is executed to delete the record
	 * 
	 * @param id - the id of the flashcard
	 * 
	 * @return the affected number of rows
	 */
	@Override
	public int deleteFlashCardById(Integer id) {
		PreparedStatement ps = null;
		int affect = -1;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM flash_cards WHERE fc_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id); //SQL is 1 indexed
			affect = ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(ps);
		}
		return affect;
	}
}
