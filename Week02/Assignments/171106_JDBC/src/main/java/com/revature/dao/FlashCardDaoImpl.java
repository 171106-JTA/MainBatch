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
	
	public void createFlashCard(FlashCard fc){
		/*
		 * Try-With-Resources
		 * In a try block, you can include any streams as a parameter and
		 * Java will manage it for you and close it automatically without you having
		 * to worry about it. Any class that implements autocloseable can do this.
		 */
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "INSERT INTO flash_cards(fc_question,fc_answer)" +
						 "VALUES('" + fc.getQuestion() + "', '" + fc.getAnswer() + "')";
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			
			System.out.println(affected + " Rows affected");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
	};
	
	public FlashCard selectFlashCardById(Integer id){
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		FlashCard fc = null;
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM flash_cards WHERE fc_id = ?";
			ps = conn.prepareStatement(sql);
			//Setting your variables takes two arguments:
			//The first refers to which question mark we are replacing
			//From left to right, each question mark is assigned a value from 1 and
			//above, sequentially.
			ps.setInt(1, id);
			rs = ps.executeQuery();
						
			while(rs.next()){
				fc = new FlashCard( 
						rs.getString("fc_question"), //NOTE: you can pull either by column # or name.
						rs.getString(3) //Grab data from column 3
						);
				fc.setId(rs.getInt(1)); //Grab data from column 1
			}	
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		return fc;
	};
	
	public void createFlashCardSP(FlashCard fc){
		CallableStatement cs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String q = fc.getQuestion();
			String a = fc.getAnswer();
			String sql = "{call insert_fc_procedure(?,?)}";//Syntax is different for storedProcedures
			cs = conn.prepareCall(sql);
			cs.setString(1, q);
			cs.setString(2, a);
			cs.executeQuery();
			System.out.println("Successful procedure call.");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(cs);
		}
	}
	
	public FlashCard getAnswerByQuestion(FlashCard fc){
		CallableStatement cs = null;

		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "{call get_answer(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, fc.getQuestion());
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.executeQuery();
			
			fc = new FlashCard("blah", cs.getString(2));
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(cs);
		}
		
		
		return fc;
	}
	
	@Override
	public List<FlashCard> getAllFlashCards() {
		List<FlashCard> fcList = new ArrayList<FlashCard>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * " +
						 "FROM flash_cards ";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()){
				int acctNum = rs.getInt("fc_id");
				String fc_question = rs.getString("fc_question");
				String fc_answer = rs.getString("fc_answer");
				fcList.add(new FlashCard(fc_question, fc_answer));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
		
		return fcList;
	}
	
	@Override
	public int updateFlashCardById(FlashCard fc) {
		int result = 0;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "UPDATE flash_cards " +
						 "SET fc_id = 100 " +
					     "WHERE fc_id = " + fc.getId();
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
			System.out.println(result + " Rows affected");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
		
		return result;
	}
	
	@Override
	public int deleteFlashCardById(Integer id) {
		int result = 0;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "DELETE FROM flash_cards " +
						 "WHERE fc_id = " + id;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
			System.out.println(result + " Rows affected");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
		return result;
	}
}
