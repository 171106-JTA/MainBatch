package com.revature.DAO;

import static com.revature.util.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.revature.beans.FlashCard;
import com.revature.util.ConnectionUtil;

public class FlashCardDaoImpl implements FlashCardDao {
	private Statement stmt = null;

	@Override
	public void createFlashCard(FlashCard fc) {
		/*
		 * Try-With-Resources
		 * In a try block, you cna include any streams as a parameter, and Java will manage it for you and close it 
		 * automatically without you having to worry about it. Any class that implements autoclosable can do this. 
		 */
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "INSERT INTO flash_cards(fc_question, fc_answer)" + 
						 "VALUES('" + fc.getQuestion() + "', '" + fc.getAnswer() + "')";
			stmt = conn.createStatement();
			int effected = stmt.executeUpdate(sql);
			
			System.out.println(effected + " Rows effected");
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(stmt);
		}
	}

	@Override
	public FlashCard selectFlashCardById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		FlashCard fc = null;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM flash_cards WHERE fc_id = ?";
			ps = conn.prepareStatement(sql);
			// setting your variables takes two arguments
			// The first refers to which question mark we are replacing
			// From left to right, each question mark is assigned a value from 1 and above, sequentially.
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				fc = new FlashCard(
//					rs.getInt(1),	// get data from column 1
					rs.getString("fc_question"),	// you can get either by column # or by name
					rs.getString(3)
				);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return fc;
	}

	@Override
	public void createFlashCardSP(FlashCard fc) {
		CallableStatement cs = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String q = fc.getQuestion(),
					a = fc.getAnswer();
			String sql = "{call insert_fc_procedure(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, q);
			cs.setString(2, a);
			cs.executeQuery();
			System.out.println("Successful procedure call");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(cs);
		}
	}
	
	// TODO: Fix this broken code.
	public FlashCard getAnswerByQuestion(FlashCard fc) { 
		CallableStatement cs = null;
		ResultSet rs = null;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "{call get_answer(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, fc.getQuestion());
			cs.setString(2, fc.getAnswer());
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.executeQuery();
			
			System.out.printf("cs.getString(1) == %s", cs.getString(1));
			System.out.printf("cs.getString(2) == %s", cs.getString(2));
			
			fc = new FlashCard("blach", cs.getString(2));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(cs);
			close(rs);
			
		}

		return fc;
	}

	@Override
	public List<FlashCard> getAllFlashCards() {
		// the list being returned
		List<FlashCard> flashCards = new LinkedList<>();
		// the query
		String sql = "SELECT * FROM flash_cards";
		// the variables for retrieving from the database
		Statement stmt = null;
		ResultSet rs= null;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			// we use just a plain old Statement to interact with the DB
			stmt = conn.createStatement();
			// now we execute the query and get result set
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				// we add a new FlashCard to flashCards, using the 1-based ResultSet
				flashCards.add(new FlashCard(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			close(rs);
		}
		
		return flashCards;
	}

	@Override
	// should return the amount of rows affected
	public int updateFlashCardById(FlashCard fc) {
		PreparedStatement ps;
		String sql_setQuestion = "UPDATE flash_card SET fc_question = ? WHERE fc_id = ?",
				sql_setAnswer = "UPDATE flash_card SET fc_answer = ? WHERE fc_id = ?";
		int rowsEffected = 0;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			// we make two updates here...
			// ... first to the answer ... 
			ps = conn.prepareStatement(sql_setAnswer);
			// updating the question first
			// setting of parameters is 1-based!
			ps.setString(1, fc.getQuestion());
			ps.setInt(2, fc.getId());
			rowsEffected = ps.executeUpdate(sql_setAnswer);
			// ... now to the question ...
			ps = conn.prepareStatement(sql_setQuestion);
			ps.setString(1,  fc.getAnswer());
			ps.setInt(2,  fc.getId());
			rowsEffected = ps.executeUpdate(sql_setAnswer);
			System.out.printf("%d Rows effected", rowsEffected);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{

		}
		
		return rowsEffected;
	}

	@Override
	// should return the amount of rows affected
	public int deleteFlashCardById(FlashCard fc) {
		PreparedStatement ps;
		String sql = "DELETE FROM flash_card WHERE fc_id = ?";
		int rowsEffected = 0;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			ps = conn.prepareStatement(sql);
			// everything is 1-based in SQL and its interfaces
			ps.setInt(1, fc.getId());
			rowsEffected = ps.executeUpdate();
			System.out.printf("%d rows effected", rowsEffected);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		finally
		{

		}
		return rowsEffected;
	}
}
