package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import static com.revature.util.CloseStreams.close;
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
		return null;
	};
}
