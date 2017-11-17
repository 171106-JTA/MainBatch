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
			
			String sql = "INSERT INTO bobbert.flash_cards(fc_question,fc_answer)" +
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
	
	public List<FlashCard> getAllFlashCards(){
		List<FlashCard> list = new ArrayList<FlashCard>();
		int fc_id;
		String fc_questions, fc_answers;
		FlashCard fc = null;
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement p = conn.prepareStatement("select * from flash_cards order by fc_id");
			ResultSet r = p.executeQuery();
			while(r.next()){
				fc_id = r.getInt(1);
				fc_questions = r.getString(2);
				fc_answers = r.getString(3);
				fc = new FlashCard(fc_questions, fc_answers);
				fc.setId(fc_id);
				list.add(fc);
			}
			close(p);
			close(r);
		}catch(SQLException e){
			e.printStackTrace();
		}
		list.sort(null);
		return list;
	}
	public int updateFlashCardById(FlashCard fc){
		int x;
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement p = conn.prepareStatement("update flash_cards set fc_question = ?, fc_answer = ? where fc_id = ?");
			p.setString(1, fc.getQuestion());
			p.setString(2, fc.getAnswer());
			p.setInt(3, fc.getId());
			p.execute();
			System.out.println(fc.getId() + " is updated");
			close(p);
			return 1;
		}catch(SQLException e){
			e.printStackTrace();
		}
		System.out.println(fc.getId() + " is not updated");
		return 0;
		
	}
	public int deleteFlashCardById(Integer id){
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement p = conn.prepareStatement("DELETE from flash_cards where fc_id = ?");
			p.setInt(1, id);
			p.execute();
			System.out.println(id + " is deleted");
			close(p);
			return 1;
		}catch(SQLException e){
			e.printStackTrace();
		}
		System.out.println(id + " is not deleted");
		return 0;
	}
}
