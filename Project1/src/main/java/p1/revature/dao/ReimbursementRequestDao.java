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
import p1.revature.beans.Employee;
import p1.revature.beans.ReimbursableEvent;
import p1.revature.beans.ReimbursementRequest;

public class ReimbursementRequestDao {
	public List<ReimbursementRequest> getAllReimbursementRequests()
	{
		Statement stmt = null;
		ResultSet rs   = null;

		List<ReimbursementRequest> requests = new LinkedList<>();
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_reimbursement_requests";
			stmt = conn.createStatement();
			rs   = stmt.executeQuery(sql);
			while (rs.next())
			{
				requests.add(new ReimbursementRequest(rs.getInt("RR_ID"),
						rs.getInt("REQUESTER_ID"),
						rs.getDouble("RR_AMOUNT"),
						rs.getString("RR_STATUS"),
						rs.getInt("RR_RE")));
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

		return requests; 
	}
	
	public ReimbursementRequest getRequestByEventID(int id)
	{
		PreparedStatement ps    = null;
		ResultSet rs            = null;

		ReimbursementRequest rr = null;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_reimbursement_requests WHERE rr_re = ?";
			ps = conn.prepareStatement(sql);
			// set parameters here
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
			{
				rr = new ReimbursementRequest(rs.getInt(1),
						rs.getInt("REQUESTER_ID"),
						rs.getDouble("RR_AMOUNT"), 
						rs.getString("RR_STATUS"), 
						rs.getInt("RR_RE"));
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

		return rr;
	}
	
	public List<ReimbursementRequest> getEventsByRequester(Employee emp)
	{
		PreparedStatement ps = null;
		ResultSet rs         = null;

		List<ReimbursementRequest> requests = new LinkedList<>();
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_rr_view WHERE employee_id = ? OR employee_email = ?";
			ps = conn.prepareStatement(sql);
			// set parameters here
			ps.setInt(1, emp.getEmployeeID());
			ps.setString(2, emp.getEmployeeEmail());
			rs = ps.executeQuery();
			while (rs.next())
			{
				requests.add(new ReimbursementRequest(rs.getInt(1), 
						rs.getInt("EMPLOYEE_ID"),
						rs.getDouble("RR_AMOUNT"), 
						rs.getString("RR_STATUS"), 
						rs.getInt("EVENT_ID")));
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

		return requests;
	}
	
	public List<ReimbursementRequest> getEventsByRequester(Employee emp, boolean doJoin)
	{
		if (!doJoin) return getEventsByRequester(emp);
		PreparedStatement ps = null;
		ResultSet rs         = null;

		List<ReimbursementRequest> requests = new LinkedList<>();
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_rr_view INNER JOIN p1_re_view USING (event_id) "
					+ "WHERE employee_id = ? OR employee_email = ?";
			ps = conn.prepareStatement(sql);
			// set parameters here
			ps.setInt(1, emp.getEmployeeID());
			ps.setString(2, emp.getEmployeeEmail());
			rs = ps.executeQuery();
			while (rs.next())
			{
				requests.add(new ReimbursementRequest(rs.getInt(1), 
						rs.getInt("REQUESTER_ID"),
						rs.getDouble("RR_AMOUNT"), 
						rs.getString("RR_STATUS"), 
						rs.getInt("EVENT_ID")
						
					)
				);
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

		return requests;
	}
	
	public int insertReimbursementRequest(ReimbursementRequest rr)
	{
		return insertReimbursementRequest(rr, false);
	}
	
	public int insertReimbursementRequest(ReimbursementRequest rr, boolean returnNewID)
	{
		PreparedStatement ps = null;
		int returnValue      = 0;
		Statement currValStmt= null;
		ResultSet currValRS  = null;

		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "INSERT INTO p1_reimbursement_requests(requester_id, rr_amount, rr_status, rr_re)"
					+ " VALUES (?,?,?,?)";
			String newIDSQL = "SELECT p1_rr_seq.CURRVAL FROM dual";
			ps = conn.prepareStatement(sql);
			/* TODO: get requester id from the requester member of rr (or a DAO method call that uses it)
				if rr.getRequesterID() returns null*/
			ps.setInt(1, rr.getRequesterID());
			ps.setDouble(2, rr.getAmount());
			ps.setString(3, rr.getStatus());
			ReimbursableEvent re = rr.getReimbursableEvent();
			int eventID = (rr.getReimbursableEventID() == Bean.NULL) ? re.getEventID() : rr.getReimbursableEventID();
			// if eventID doesn't already exist in the p1_reimbursable_events table in the database
			ReimbursableEventDao reDao = new ReimbursableEventDao();
			if ((rr.getReimbursableEvent() != null) && (reDao.getReimbursableEventByID(eventID) == null) )
			{
				// write the ReimbursableEvent to the database
				eventID = reDao.insertReimbursableEvent(re, true);
				rr.setReimbursableEventID(eventID);
			}
			ps.setInt(4, eventID);
			// set parameters here
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
