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
import p1.revature.beans.State;

public class LocationDao {
	
	/**
	 * @return every Location in the locations table
	 */
	public List<Location> getAllLocations()
	{
		Statement stmt = null;
		ResultSet rs   = null;
		List<Location> locations = new LinkedList<>();
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_locations";
			stmt = conn.createStatement();
			rs   = stmt.executeQuery(sql);
			while (rs.next()) {
				locations.add(new Location(rs.getInt("LOCATION_ID"),
						rs.getString("LOCATION_CITY"),
						rs.getString("LOCATION_STATE_ABBR"),
						rs.getString("LOCATION_ADDRESS"),
						rs.getInt("LOCATION_ZIP"),
						rs.getInt("LOCATION_SUITE")));
				
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
		return locations;
	}
	
	/**
	 * Retrieves a Location by its id in the table.
	 * @param id : the id of the location to retrieve
	 * @return the Location having that id
	 */
	public Location getLocationByID(int id)
	{
		PreparedStatement ps = null;
		ResultSet         rs = null;
		Location location    = null;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_locations WHERE location_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) 
			{
				location = new Location(rs.getInt("LOCATION_ID"),
						rs.getString("LOCATION_CITY"),
						rs.getString("LOCATION_STATE_ABBR"),
						rs.getString("LOCATION_ADDRESS"),
						rs.getInt("LOCATION_ZIP"),
						rs.getInt("LOCATION_SUITE"));
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
		return location;	
	}
	
	/**
	 * Retrieves Location specified by street address, city, and state
	 * @param location : the location that has the specified street address, city, and state, if it exists
	 * @return location or null if it doesn't exist in the data store
	 */
	public Location getLocationByAddressCityState(Location location)
	{
		PreparedStatement ps = null;
		ResultSet rs         = null;
		
		Location loc = null;

		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_locations WHERE "
					+ "location_city = ? AND location_state_abbr = ? AND location_address = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, location.getCity());
			ps.setString(2, location.getStateAbbreviation());
			ps.setString(3, location.getStreetAddress());
			rs = ps.executeQuery();
			
			if (rs.next())
			{
				loc = new Location(rs.getInt("LOCATION_ID"),
						rs.getString("LOCATION_CITY"),
						rs.getString("LOCATION_STATE_ABBR"),
						rs.getString("LOCATION_ADDRESS"),
						rs.getInt("LOCATION_ZIP"),
						rs.getInt("LOCATION_SUITE"));
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

		return loc;
	}
	
	/**
	 * Does a plain ol insert into the locations table. Uses all fields of location.
	 * @param location : the Location to insert into the table 
	 * @return the number of rows affected
	 */
	public int insertLocation(Location location)
	{
		return insertLocation(location, false);
	}
	
	/**
	 * Does a plain ol insert into the locations table. Uses all fields of location. Also, this method may return 
	 * the id of that newly inserted row
	 * @param location : the Location to insert into the table 
	 * @param returnNewID : whether or not to return the id of the newly inserted row
	 * @return the number of rows affected or the id of the newly inserted row
	 */
	public int insertLocation(Location location, boolean returnNewID)
	{
		PreparedStatement ps = null;
		Statement currValStmt= null;
		ResultSet currValRS  = null;
		int returnValue      = 0,
				locationID   = location.getLocationID(); 
		// if locationID is null then we use the id-free DAO method
		if (locationID == Bean.NULL) return insertLocationNoID(location, returnNewID);
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "INSERT INTO p1_locations VALUES (?,?,?,?,?)";
			String newIDSQL = "SELECT p1_locations_seq.CURRVAL FROM dual";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, locationID);
			ps.setString(2, location.getCity());
			ps.setString(3, location.getStateAbbreviation());
			ps.setString(4, location.getStreetAddress());
			ps.setInt(5, location.getZipCode());
			ps.setInt(6, location.getSuiteNumber());
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
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(ps);
			close(currValStmt);
			close(currValRS);
		}
		return returnValue;	
	}

	public int insertLocationNoID(Location location)
	{
		return insertLocation(location, false);
	}
	
	/** 
	 * Inserts everything but the location id of the location into the table
	 * @param location : the Location to insert
	 * @return the number of rows affected
	 */
	public int insertLocationNoID(Location location, boolean returnNewID) {
		PreparedStatement ps = null;
		Statement currValStmt= null;
		ResultSet currValRS  = null;
		int returnValue      = 0,
				suiteNumber  = location.getSuiteNumber(); 
		// if suiteNumber is null then we use the suite-free DAO method
		if (suiteNumber == Bean.NULL) return insertLocationNoSuite(location, returnNewID);
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "INSERT INTO p1_locations(location_city, location_state_abbr, location_street_address, "
					+ "location_zip, location_suite) VALUES (?,?,?,?,?)";
			String newIDSQL = "SELECT p1_locations_seq.CURRVAL FROM dual";
			ps = conn.prepareStatement(sql);
			ps.setString(1, location.getCity());
			ps.setString(2, location.getStateAbbreviation());
			ps.setString(3, location.getStreetAddress());
			ps.setInt(4, location.getZipCode());
			ps.setInt(5, location.getSuiteNumber());
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
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(ps);
			close(currValStmt);
			close(currValRS);
		}
		return returnValue;	
	}

	public int insertLocationNoSuite(Location location) {
		return insertLocation(location, false);
	}
	
	/**
	 * Inserts location by city, state abbreviation, street address, and location zip
	 * @param location
	 * @return the rows affected
	 */
	public int insertLocationNoSuite(Location location, boolean returnNewID) {
		PreparedStatement ps = null;
		Statement currValStmt= null;
		ResultSet currValRS  = null;
		int returnValue      = 0;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql   = "INSERT INTO p1_locations(location_city, location_state_abbr, location_address, "
					+ "location_zip) VALUES (?,?,?,?)",
				newIDSQL = "SELECT p1_locations_seq.CURRVAL FROM dual";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, location.getCity());
			ps.setString(2, location.getStateAbbreviation());
			ps.setString(3, location.getStreetAddress());
			ps.setInt(4, location.getZipCode());
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
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(ps);
			close(currValStmt);
			close(currValRS);
		}
		return returnValue;
	}
	
	/**
	 * gets all the location states
	 * @return a list of states
	 */
	public List<State> getAllStates()
	{
		Statement stmt = null;
		ResultSet rs   = null;

		List<State> states = new LinkedList<State>();
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_states";
			stmt = conn.createStatement();
			rs   = stmt.executeQuery(sql);
			while (rs.next())
			{
				states.add(new State(rs.getString("STATE_ABBR"),
						rs.getString("STATE_NAME")));
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

		return states;
	}
	
	public State getState(State lookup)
	{
		PreparedStatement ps = null;
		ResultSet rs         = null;
		
		State state          = null;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM p1_states WHERE state_abbr = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, lookup.getStateAbbreviation());
			rs = ps.executeQuery();
			if (rs.next())
			{
				state = new State(rs.getString(1), rs.getString(2));
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

		return state;
	}
}
