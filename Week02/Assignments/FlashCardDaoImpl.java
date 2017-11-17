package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
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
			
			String sql = "INSERT INTO MahammadRev.flash_cards(fc_question,fc_answer)" +
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
	public FlashCard getAnswerByQuestion(FlashCard fc){
		CallableStatement cs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "{call get_answer(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1,  fc.getQuestion());
			cs.registerOutParameter(2,  java.sql.Types.VARCHAR);
			cs.executeQuery();
			
			fc = new FlashCard(cs.getString(1), cs.getString(2));
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(cs);
		}
		
		return null;
	}
	
/*In Class Assignment
 * Implement 
 * 	public List<FlashCard> getAllFlashCards();
	public int updateFlashCardById(FlashCard fc);
	public int deleteFlashCardById(FlashCard fc);
*/	
	
	public List<FlashCard> getAllFlashCards(){
		CallableStatement cs = null;
		List<FlashCard> result = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "{call get_all_fc()}";
			cs = conn.prepareCall(sql);
			result = (List<FlashCard>) cs.executeQuery();							
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(cs);
		}
			
		return result;
	}
	
	public int deleteFlashCardById(FlashCard fc){
		
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "DELETE FROM MahammadRev.flash_cards" +
						 "WHERE fc_id =('" + fc.getId() +"')";
			stmt = conn.createStatement();
			int deleted = stmt.executeUpdate(sql);
			
			System.out.println(deleted + " Rows deleted");
			
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}finally{
			close(stmt);
		}
		
		return 1;
	}
	
	
	public int updateFlashCardById(FlashCard fc){
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "UPDATE MahammadRev.flash_cards SET fc_question ='" + fc.getQuestion() + "', fc_answer='" +
					"'" + fc.getAnswer() + "' WHERE id = " +  fc.getId();
			stmt = conn.createStatement();
			int updated = stmt.executeUpdate(sql);
			
			System.out.println(updated + " Rows affected");
			
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}finally{
			close(stmt);
		}
		return 1;
	}

	
	
	@Override
	public FlashCard selectFlashCardById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	};
}
