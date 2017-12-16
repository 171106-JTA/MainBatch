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

import p1.revature.beans.Bean;
import p1.revature.beans.Location;
import p1.revature.beans.ReimbursableEvent;

public class ReimbursableEventDao {
	public List<ReimbursableEvent> getAllReimbursableEvents()
	{
		Statement stmt = null;
		ResultSet rs   = null;
		
		List<ReimbursableEvent> reimbursableEvents = new LinkedList<>();

		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_reimbursable_events";
			stmt = conn.createStatement();
			rs   = stmt.executeQuery(sql);
			while (rs.next())
			{
				reimbursableEvents.add(new ReimbursableEvent(rs.getInt("EVENT_ID"),
					rs.getInt("RE_RET"), 
					rs.getDate("RE_DATE_START"),
					rs.getDate("RE_DATE_END"),
					rs.getInt("RE_LOCATION"),
					rs.getString("RE_DESCRIPTION"),
					rs.getDouble("RE_COST"),
					rs.getBoolean("RE_IS_SATISFACTORY"),
					rs.getBoolean("RE_PRESENTATION_NEEDED"),
					rs.getBoolean("RE_PRESENTATION_PASSING"),
					rs.getDouble("RE_NUMERIC_GRADE")));
				
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

		return reimbursableEvents;
	}
	
	public ReimbursableEvent getReimbursableEventByID(int id)
	{
		PreparedStatement ps = null;
		ResultSet rs         = null;
		
		ReimbursableEvent re = null;

		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_reimbursable_events WHERE event_id = ?";
			ps = conn.prepareStatement(sql);
			// set parameters here
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
			{
				re = new ReimbursableEvent(rs.getInt("EVENT_ID"),
						rs.getInt("RE_RET"), 
						rs.getDate("RE_DATE_START"),
						rs.getDate("RE_DATE_END"),
						rs.getInt("RE_LOCATION"),
						rs.getString("RE_DESCRIPTION"),
						rs.getDouble("RE_COST"),
						rs.getBoolean("RE_IS_SATISFACTORY"),
						rs.getBoolean("RE_PRESENTATION_NEEDED"),
						rs.getBoolean("RE_PRESENTATION_PASSING"),
						rs.getDouble("RE_NUMERIC_GRADE"));
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

		return re;
	}
	
	public int insertReimbursableEvent(ReimbursableEvent event)
	{
		return insertReimbursableEvent(event, false);
	}
	
	public int insertReimbursableEvent(ReimbursableEvent event, boolean returnNewID)
	{
		PreparedStatement ps = null;
		Statement currValStmt= null;
		ResultSet currValRS  = null;
		int returnValue      = 0;
		// check for LocationID in event
		int locationID = event.getLocationID();
		// if it's null
		Location location = event.getLocation();
		if (locationID == Bean.NULL)
		{
			// get it from the Location member of event
			locationID = location.getLocationID();
		}
		// if the locationID doesn't point to a location record in the data store, we add it and overwrite locationID 
		//	with its primary key
		LocationDao locDao = new LocationDao();
		Location loc = locDao.getLocationByID(locationID);
		if (loc == null)
		{
			locationID = locDao.insertLocation(location, true);
		}
		else 
		{
			locationID = loc.getLocationID();
		}
		// TODO: establish criteria for delegating this insertion task to other DAO methods
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "INSERT INTO p1_reimbursable_events(re_ret, re_date_start, re_date_end, re_location, "
					+ "re_description, re_cost, re_is_satisfactory, re_presentation_needed, "
					+ "re_numeric_grade, re_presentation_passing) VALUES (?,?,?,?,?,?,?,?,?,?)";
			String newIDSQL = "SELECT p1_re_seq.CURRVAL FROM dual";
			ps = conn.prepareStatement(sql);
			// set parameters here
			ps.setInt(1, (event.getTypeID() == Bean.NULL) ? 
					new ReimbursableExpenseTypeDao().getTypeByName(event.getType()).getId() :
					event.getTypeID());
			ps.setDate(2, event.getStartDate());
			ps.setDate(3, event.getEndDate());
			ps.setInt(4, locationID);
			ps.setString(5, event.getDescription());
			ps.setDouble(6, event.getCost());
			ps.setBoolean(7, event.isSatisfactory());
			ps.setBoolean(8, event.isPresentationNeeded());
			ps.setDouble(9, event.getNumericGrade());
			ps.setBoolean(10, event.isPresentationPassing());
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
}
