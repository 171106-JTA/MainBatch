package com.revature.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.beans.ReimbursementEvent;
import com.revature.utilities.CloserUtility;
import com.revature.utilities.DataConnectionUtility;

public abstract class ReimbursementEventsDAO {
	public static ArrayList<ReimbursementEvent> getReimbursementEvents() throws IOException, SQLException {
		ArrayList<ReimbursementEvent> reimbursementevents;
		ReimbursementEvent reimbursementevent;
		DataConnectionUtility dataconnection;
		ResultSet resultset;
		reimbursementevents = new ArrayList<ReimbursementEvent>();
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		resultset = dataconnection.requestQuery("SELECT * FROM Facilities;");
		while(resultset.next()) {
			reimbursementevent = new ReimbursementEvent();
			reimbursementevent.setEventid(resultset.getInt("event_id"));
			reimbursementevent.setEventtype(resultset.getString("event_type"));
			reimbursementevent.setReimbursementpercent(resultset.getFloat("reimbursement_percent"));
			reimbursementevents.add(reimbursementevent);
		}
		CloserUtility.close(resultset);
		return reimbursementevents;
	}
}
