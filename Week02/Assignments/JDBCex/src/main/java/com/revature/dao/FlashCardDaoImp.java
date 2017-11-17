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

public class FlashCardDaoImp implements FlashCardDao {
	private Statement stmt = null;
	public FlashCard selectFlashCardByID(Integer id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		FlashCard fc= null;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM flash_cards WHERE fc_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();
			
			while(rs.next()) {
				fc=new FlashCard(
						rs.getInt(1),
						rs.getString("fc_question"),
						rs.getString(3));
				fc.setId(rs.getInt(1));
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return fc;
	}
	
	public void createFlashCardSP(FlashCard fc)
	{
		CallableStatement cs=null;
		try(Connection conn= ConnectionUtil.getConnection()){
				String q = fc.getQuestion();
				String a = fc.getAnswer();
				String sql="{call insert_fc_procedure(?,?)}";
				cs= conn.prepareCall(sql);
				cs.setString(1, q);
				cs.setString(2,a);
				cs.executeQuery();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(cs);
		}
	}

	@Override
	public void createFlashCard(FlashCard fc) {
		/*
		 * Try-With-Resources
		 * In a try block, you can include any streams as a parameter and
		 * Java will manage it for you and close it automatically without you having
		 * to worry about it. Any class that implements autocloseable can do this.
		 */
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "INSERT INTO SQLWeek.flash_cards(fc_question,fc_answer)" +
						 "VALUES('" + fc.getQuestion() + "', '" + fc.getAnswer() + "')";
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			
			System.out.println(affected + " Rows affected");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
	}
		
	
	public FlashCard getAnswerByQuestion(FlashCard fc) {
		CallableStatement cs=null;
		try(Connection conn= ConnectionUtil.getConnection())
		{
			String sql="{call get answer(?,?)}";
			cs= conn.prepareCall(sql);
			cs.setString(1, fc.getQuestion());
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.executeQuery();
			fc= new FlashCard(cs.getString(1),cs.getString(2));
		}catch(SQLException e)
		{
			
		}
		finally
		{
			close(cs);
		}
		return fc;
	}

	@Override
	public List<FlashCard> getAllFlashCards() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		List<FlashCard> cards= new ArrayList<FlashCard>();
		FlashCard fc= null;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM flash_cards";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			
			while(rs.next()) {
				fc=new FlashCard(
						rs.getInt(1),
						rs.getString("fc_question"),
						rs.getString(3));
				fc.setId(rs.getInt(1));
				cards.add(fc);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return cards;
	}

	@Override
	public int updateFlashCardById(FlashCard fc) {
		int count=0;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection();){
			//String sql ="UPDATE flash_cards set FC_ANSWER=" +fc.getAnswer()+",FC_QUESTION=" 
			//						+ fc.getQuestion() +" where FC_ID=" + fc.getId()+"";
			String sql="UPDATE flash_cards SET FC_QUESTION=?,FC_ANSWER=? WHERE FC_ID=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,fc.getQuestion());
			ps.setString(2, fc.getAnswer());
			ps.setInt(3, fc.getId());
			rs= ps.executeQuery();
			while(rs.next())
			{
				count++;
			}
			}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return count;
	}

	@Override
	public int deleteFlashCardById(Integer id) {
		int count=0;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="DELETE FROM flash_cards WHERE fc_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();
			
			while(rs.next()) {
				count++;
			}

		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return count;
	}

}
