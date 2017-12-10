package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.bean.ReimbursementForm;
import com.revature.util.ConnectionUtil;

public class TrmsDaoImplement implements TrmsDao {

	@Override
	public String login(String username, String password) {
		String permission = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Login WHERE username=? AND password=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				username = rs.getString("username");
				password = rs.getString("password");
				permission = rs.getString("permission");
			}
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return permission;
	}

	public void insertNewReimbursementForm(ReimbursementForm newForm) {
		CallableStatement cs1 = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "{call newReimbursementForm(?, ?, ?, " + "?, ?, ?, " + "?, ?, ?, " + "?, ?, ?, " + "?, ?)}";
			String username = newForm.getUsername();
			String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };
			String DateAndTime = newForm.getDay() + "-" + months[Integer.parseInt(newForm.getMonth()) - 1] + "-"
					+ newForm.getYear() + " " + newForm.getHour() + ":" + newForm.getMinute();
			String SubmitDateAndTime = null;
			
			
			cs1 = conn.prepareCall(sql);
			cs1.setString(1, username);
			cs1.setString(2, newForm.getStreet());
			cs1.setString(3, newForm.getCity());
			cs1.setString(4, newForm.getState());
			cs1.setString(5, newForm.getZip());
			cs1.setString(6, "-1"); // Ignore apt numbers for now.
			cs1.setString(7, DateAndTime);
			cs1.setString(8, SubmitDateAndTime);
			cs1.setString(9, newForm.getDescription());
			cs1.setString(10, newForm.getCost());
			cs1.setString(11, newForm.getGradingFormat());
			cs1.setString(12, newForm.getEventType());
			cs1.setString(13, null);
			cs1.setInt(14, 70);

			int rows_changed = cs1.executeUpdate();
			if (rows_changed == 0) {
				throw new SQLException("Address Insertion Failed!");
			}
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			e.printStackTrace();
		} finally {
			if (cs1 != null) {
				try {
					cs1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<ReimbursementForm> getUserForms(String username) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReimbursementForm> Forms = new ArrayList<ReimbursementForm>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			// String sql = "{call getUserForms(?)}";\
			String sql = "SELECT * FROM ReimbursementForm WHERE username = ?";
			ps = conn.prepareCall(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				//Get and use the form's username (rather than the existing 'username' variable from the session)
				String form_username = rs.getString("username"); 
				String DateAndTime = rs.getString("eventDateAndTime");
				String[] splitDateAndTime = DateAndTime.split("-| |:");
				System.out.println(splitDateAndTime[0]);

				String year = splitDateAndTime[0];
				String month = splitDateAndTime[1];
				String day = splitDateAndTime[2];
				String hour = splitDateAndTime[3];
				String minute = splitDateAndTime[4];
				String description = rs.getString("eventDescription");
				String cost = rs.getString("eventCost");
				String gradingFormat = getGradingFormat(rs.getInt("gradingFormatID"));
				String eventType = getEventType(rs.getInt("eventTypeID"));

				List<String> address = getAddress(rs.getInt("eventLocation"));

				String street = address.get(0);
				String city = address.get(1);
				String state = address.get(2);
				String zip = address.get(3);

				Forms.add(new ReimbursementForm(form_username, year, month, day, hour, minute, description, cost,
						gradingFormat, eventType, street, city, state, zip));
				System.out.println(Forms.get(Forms.size() - 1));
			}
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return Forms;
	}

	public List<ReimbursementForm> getFormsByApprovalStatus(int form_status, String permission) {
		System.out.println("Inside dao: getFormsByApprovalStatus");

		String approval_column_name = null;
		if (permission.equals("department_head")) {
			approval_column_name = "approval_by_department_head";
		} else if (permission.equals("direct_supervisor")) {
			approval_column_name = "approval_by_direct_supervisor";
		} else if (permission.equals("benco")) {
			approval_column_name = "approval_by_benco";
		}
		System.out.println("approval column name: " + approval_column_name);

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReimbursementForm> Forms = new ArrayList<ReimbursementForm>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			// String sql = "{call getUserForms(?)}";\
			String sql = "SELECT * FROM ReimbursementForm WHERE status = ? AND " 
					+ approval_column_name + " = ?";
			ps = conn.prepareCall(sql);
			ps.setInt(1, form_status);
			ps.setInt(2, 0); //Forms that the Department Head has approved yet
			rs = ps.executeQuery();
			while (rs.next()) {
				int reimbursementID = rs.getInt("reimbursementID");
				String form_username = rs.getString("username");
				
				//Event Date and Time
				String DateAndTime = rs.getString("eventDateAndTime");
				String[] splitDateAndTime = DateAndTime.split("-| |:");
				System.out.println(splitDateAndTime[0]);
				String year = splitDateAndTime[0];
				String month = splitDateAndTime[1];
				String day = splitDateAndTime[2];
				String hour = splitDateAndTime[3];
				String minute = splitDateAndTime[4];
				
				//Submission Date and Time
//				String submitDataAndTime = rs.getString("submissionDateAndTime");
//				String splitDateAndTime = DateAndTime.split("-| |:");
				String submitYear = null;
				String submitMonth = null;
				String submitDay = null;
				String submitHour = null;
				String submitMinute = null;
				
				String description = rs.getString("eventDescription");
				String cost = rs.getString("eventCost");
				String gradingFormat = getGradingFormat(rs.getInt("gradingFormatID"));
				String eventType = getEventType(rs.getInt("eventTypeID"));

				List<String> address = getAddress(rs.getInt("eventLocation"));

				String street = address.get(0);
				String city = address.get(1);
				String state = address.get(2);
				String zip = address.get(3);
				
				String workRelatedJustification = rs.getString("work_related_justification");
				int passingGrade = rs.getInt("passing_grade");
				int status = rs.getInt("status");
				int exceedsFundsFlag = rs.getInt("exceeds_funds");
				String reason_for_excess_funds = rs.getString("reason_for_excess_funds");
				String reason_for_denial = rs.getString("reason_for_denial");
				int approvalByDirectSupervisor = rs.getInt("approval_by_direct_supervisor");
				int approvalByDepartmentHead = rs.getInt("approval_by_department_head");
				int approvalByBenCo = rs.getInt("approval_by_benco");
				int urgency = rs.getInt("urgency");
				
//				Forms.add(new ReimbursementForm(form_username, year, month, day, hour, minute, description, cost,
//						gradingFormat, eventType, street, city, state, zip));
				
				Forms.add(new ReimbursementForm(reimbursementID, form_username, year, month, day, hour,
						minute, submitYear, submitMonth, submitDay, submitHour,
						submitMinute, description, cost, gradingFormat, eventType, street,
						city, state, zip, workRelatedJustification, passingGrade, status,
						exceedsFundsFlag, reason_for_excess_funds, reason_for_denial,
						approvalByDirectSupervisor, approvalByDepartmentHead, approvalByBenCo, urgency));
				System.out.println(Forms.get(Forms.size() - 1));
			}
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return Forms;
	}

	public List<String> getAddress(int addressID) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<String> address = new ArrayList<String>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Address WHERE addressID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, addressID);
			rs = ps.executeQuery();
			if (rs.next()) {
				address.add(rs.getString("numberAndStreet"));
				address.add(rs.getString("city"));
				address.add(rs.getString("state"));
				address.add(rs.getString("zip"));
			}
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return address;
	}

	public String getGradingFormat(int gradingFormatID) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String gradingFormat = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT gradingFormatName FROM gradingFormats WHERE gradingFormatID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gradingFormatID);
			rs = ps.executeQuery();
			if (rs.next()) {
				gradingFormat = rs.getString("gradingFormatName");
			}
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return gradingFormat;
	}

	public String getEventType(int eventTypeID) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String eventType = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT eventTypeName FROM eventTypes WHERE eventTypeID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, eventTypeID);
			rs = ps.executeQuery();
			if (rs.next()) {
				eventType = rs.getString("eventTypeName");
			}
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return eventType;
	}
	
	public void updateFormApproval(
			int reimbursementID, 
			int directSupervisorApproval,
			int departmentHeadApproval,
			int benCoApproval) {
		PreparedStatement ps = null;
		int rows_updated = -1;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ReimbursementForm "
					+ "SET approval_by_direct_supervisor= ?, "
					+ "approval_by_department_head= ?, "
					+ "approval_by_benco= ?"
					+ "WHERE reimbursementID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, directSupervisorApproval);
			ps.setInt(2, departmentHeadApproval);
			ps.setInt(3, benCoApproval);
			ps.setInt(4, reimbursementID);
			
			rows_updated = ps.executeUpdate();
			if (rows_updated == 0) {
				throw new SQLException("Reimbursement Form Update Failed!");
			}
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void denyReimbursement(int reimbursementID, String deny_msg){
		PreparedStatement ps = null;
		int rows_updated = -1;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ReimbursementForm "
					+ "SET reason_for_denial = ?, "
					+ "status = -1"  //Set the status to Denied
					+ "WHERE reimbursementID = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, deny_msg);
			ps.setInt(2, reimbursementID);
			
			rows_updated = ps.executeUpdate();
			if (rows_updated == 0) {
				throw new SQLException("Reimbursement Form Update Failed!");
			}
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

