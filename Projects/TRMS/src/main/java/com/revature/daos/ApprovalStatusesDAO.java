package com.revature.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.beans.ApprovalStatus;
import com.revature.utilities.CloserUtility;
import com.revature.utilities.DataConnectionUtility;

public abstract class ApprovalStatusesDAO {
	public static ArrayList<ApprovalStatus> getApprovalStatuses() throws IOException, SQLException {
		ArrayList<ApprovalStatus> approvalstatuses;
		ApprovalStatus approvalstatus;
		DataConnectionUtility dataconnection;
		ResultSet resultset;
		approvalstatuses = new ArrayList<ApprovalStatus>();
		dataconnection = DataConnectionUtility.getDataConnection();
		while(!dataconnection.initializeConnection()) {
		}
		resultset = dataconnection.requestQuery("SELECT * FROM ApprovalStatuses;");
		while(resultset.next()) {
			approvalstatus = new ApprovalStatus();
			approvalstatus.setStatusid(resultset.getInt("status_id"));
			approvalstatus.setStatus(resultset.getString("status"));
			approvalstatus.setHandlerid(resultset.getInt("handler_id"));
			approvalstatuses.add(approvalstatus);
		}
		CloserUtility.close(resultset);
		return approvalstatuses;
	}
}
