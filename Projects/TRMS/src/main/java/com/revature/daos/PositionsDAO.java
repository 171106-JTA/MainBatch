package com.revature.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.beans.Position;
import com.revature.utilities.CloserUtility;
import com.revature.utilities.DataConnectionUtility;

public abstract class PositionsDAO {
	public static ArrayList<Position> getPositions() throws IOException, SQLException {
		ArrayList<Position> positions;
		Position position;
		DataConnectionUtility dataconnection;
		ResultSet resultset;
		positions = new ArrayList<Position>();
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		resultset = dataconnection.requestQuery("SELECT * FROM Facilities");
		while(resultset.next()) {
			position = new Position();
			position.setPositionid(resultset.getInt("position_id"));
			position.setPosition(resultset.getString("position"));
			positions.add(position);
		}
		CloserUtility.close(resultset);
		return positions;
	}
}
