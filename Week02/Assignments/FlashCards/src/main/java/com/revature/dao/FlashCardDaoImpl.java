package com.revature.dao;

import static com.revature.factory.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.revature.connection.FlashCard;
import com.revature.factory.ConnectionUtil;

public class FlashCardDaoImpl implements FlashCardDao {
	Statement stat = null;
	
	public void createFlashCard(FlashCard card) {
		 /*
		  * try-with-resources
		  * can incluce any stream as a parameter and closes automatically if fail (must implement autocloseable
		  */
		try(Connection con = ConnectionUtil.getConnection();) {
			String query = "insert into flash_cards(fc_question,fc_answer)" +
							"values('" + card.getQuestion() + "','" + card.getAnswer() +"')";
			stat = con.createStatement();
			stat.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stat);
		}

	}

	public FlashCard findFlashCard(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		FlashCard fc = null;
		try(Connection con = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM flash_cards WHERE fc_id = ?";
			ps = con.prepareStatement(sql);
			
			// Set values with ?, requires two arguments 
			// Where first arg represents question make index starting at 1
			// second value is the value to replace 
			ps.setInt(1, id);;
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				fc = new FlashCard(
							rs.getString("fc_question"),
							rs.getString("fc_answer")
						);
				// first column
				fc.setId(rs.getInt(1));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		
		return fc;
	}
	
	
	public void createFlashCardSP(FlashCard fc) {
		CallableStatement cs = null;
		
		try(Connection conn = ConnectionUtil.getConnection();) {
			String q = fc.getQuestion();
			String a = fc.getAnswer();
			String sql = "{call insert_fc_procedure(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, q);;
			cs.setString(2, a);
			cs.executeQuery();
			System.out.println("ok");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
		}
	}

	public FlashCard getFlashCardByQuestion(FlashCard question) {
		// Out params + values stored in Callable Statement 
		CallableStatement cs = null;
		FlashCard fc = null;
		ResultSet rs;
		
		try(Connection conn = ConnectionUtil.getConnection();) {
			String sql = "{call get_answer(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, question.getQuestion());
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.executeQuery();
			System.out.println("ok");
			fc = new FlashCard(question.getQuestion(), cs.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
		}
		
		return fc;
	}
	
	
	
	public List<FlashCard> getAllFlashCards() {
		List<FlashCard> deck = new LinkedList<FlashCard>();
		Statement stat = null;
		ResultSet res = null;
		
		try(Connection con = ConnectionUtil.getConnection();) {
			String sql = "SELECT * FROM FLASH_CARDS";
			FlashCard fc;
			
			
			stat = con.createStatement();
			res = stat.executeQuery(sql);
			
			while (res.next() ) {
				fc = new FlashCard(res.getString("fc_question"), res.getString("fc_answer"));
				fc.setId(res.getInt("fc_id"));
				deck.add(fc);
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stat);
			close(res);
		}
		
		return deck;
	}

	public int updateFlashCardById(FlashCard fc) {
		Integer updated = 0;
		PreparedStatement ps = null;
		ResultSet res = null;
		
		try(Connection con = ConnectionUtil.getConnection();) {
			String sql = "UPDATE FLASH_CARDS SET fc_question=?,fc_answer=? WHERE fc_id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1,fc.getQuestion());	
			ps.setString(2,fc.getAnswer());	
			ps.setInt(3,fc.getId());			
			
			updated = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
		
		return updated;
	}

	public int deleteFlashCardById(FlashCard fc) {
		Integer updated = 0;
		PreparedStatement ps = null;
		ResultSet res = null;
		
		try(Connection con = ConnectionUtil.getConnection();) {
			String sql = "DELETE FROM FLASH_CARDS WHERE fc_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1,fc.getId());			
			
			updated = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
		
		return updated;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
