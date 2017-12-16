package p1.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.revature.util.ConnectionUtil;

import p1.revature.beans.EventGradeCriteria;

public class EventGradeCriteriaDao {
	public List<EventGradeCriteria> getAllCriteria()
	{
		Statement stmt = null;
		ResultSet rs   = null;
		
		List<EventGradeCriteria> allRubric = new LinkedList<>();

		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_event_grade_criteria";
			stmt = conn.createStatement();
			rs   = stmt.executeQuery(sql);
			while (rs.next())
			{
				allRubric.add(new EventGradeCriteria(rs.getInt(1), 
						rs.getInt("EVENT_ID"), 
						rs.getString("GS_LETTER_GRADE"),
						rs.getDouble("GS_PERCENTAGE_ROW")));
			}
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			close(stmt);
			close(rs);
		}

		return allRubric;
	}
	
	public List<EventGradeCriteria> getRubricByEventID(int eventID)
	{
		PreparedStatement ps = null;
		ResultSet rs         = null;

		List<EventGradeCriteria> rubric = new LinkedList<>();
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_event_grade_criteria WHERE event_id = ?";
			ps = conn.prepareStatement(sql);
			// set parameters here
			ps.setInt(1, eventID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				rubric.add(new EventGradeCriteria(rs.getInt(1), 
						rs.getInt("EVENT_ID"), 
						rs.getString("GS_LETTER_GRADE"),
						rs.getDouble("GS_PERCENTAGE_ROW")));
			}
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			close(ps);
			close(rs);
		}

		return rubric;
	}
	
	public int insertEventGradeCriteria(EventGradeCriteria criteria)
	{
		return insertEventGradeCriteria(criteria, false);
	}
	
	public int insertEventGradeCriteria(EventGradeCriteria criteria, boolean returnNewID)
	{
		PreparedStatement ps = null;
		int returnValue      = 0;
		Statement currValStmt= null;
		ResultSet currValRS  = null;

		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "INSERT INTO p1_event_grade_criteria(event_id, gs_letter_grade, gs_percentage_low) "
					+ "VALUES (?,?,?)";
			String newIDSQL = "SELECT p1_gs_seq.CURRVAL FROM dual";
			ps = conn.prepareStatement(sql);
			// set parameters here
			ps.setInt(1, criteria.getEventID());
			ps.setString(2, criteria.getLetterGrade());
			ps.setDouble(3, criteria.getCutoffPercentage());
			returnValue = ps.executeUpdate();
			if (returnNewID)
			{
				currValStmt  = conn.createStatement();
				currValRS = currValStmt.executeQuery(newIDSQL);
				if (currValRS.next())
				{
					returnValue = currValRS.getInt(1);
				}
			}
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			close(ps);
			close(currValStmt);
			close(currValRS);
		}

		return returnValue;
	}
	
	public int insertEventGradeCriteria(List<EventGradeCriteria> criteriaList)
	{
		int x = 0; 
		for(EventGradeCriteria c : criteriaList)
		{
			x += insertEventGradeCriteria(c);
		}
		return x;
	}
	
}
