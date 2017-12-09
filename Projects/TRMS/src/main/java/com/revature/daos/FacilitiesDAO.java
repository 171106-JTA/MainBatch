package com.revature.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.beans.Facility;
import com.revature.utilities.CloserUtility;
import com.revature.utilities.DataConnectionUtility;



public abstract class FacilitiesDAO {
	public static ArrayList<Facility> getFacilities() throws IOException, SQLException {
		ArrayList<Facility> facilities;
		Facility facility;
		DataConnectionUtility dataconnection;
		ResultSet resultset;
		facilities = new ArrayList<Facility>();
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		resultset = dataconnection.requestQuery("SELECT * FROM Facilities");
		while(resultset.next()) {
			facility = new Facility();
			facility.setFacilityid(resultset.getInt("facility_id"));
			facility.setAddress(resultset.getString("address"));
			facility.setCity(resultset.getString("city"));
			facility.setState(resultset.getString("state"));
			facilities.add(facility);
		}
		CloserUtility.close(resultset);
		return facilities;
	}
}
