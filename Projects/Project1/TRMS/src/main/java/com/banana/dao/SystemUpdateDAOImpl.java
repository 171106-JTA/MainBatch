package com.banana.dao;

import static com.banana.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.banana.util.ConnectionUtil;

public class SystemUpdateDAOImpl implements SystemUpdateDAO{
	
	@Override
	public boolean updateRequestApproval(int roleId, int rrId, int decision) {
		String sql = null;
		
		if(roleId == 1) {
			sql = "UPDATE Reimbursement_Request SET DS_APPROVAL = ? WHERE RRID = ?";
		}
		else if(roleId == 2) {
			sql = "UPDATE Reimbursement_Request SET DH_APPROVAL = ? WHERE RRID = ?";
		}
		else if(roleId == 3) {
			sql = "UPDATE Reimbursement_Request SET BC_APPROVAL = ? WHERE RRID = ?";
		}
		else {
			return false;
		}
		System.out.println("here");
		PreparedStatement ps = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, decision);
			ps.setInt(2, rrId);
			
			ps.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			close(ps);
		}
		return true;
	}
}
