package com.trms.dao;

import static com.trms.util.CloseStreams.close;
import static com.trms.util.ConnectionUtil.getConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.trms.obj.ContactInformation;
import com.trms.obj.Employee;
import com.trms.obj.PrivilegesSummary;
import com.trms.obj.ReimbRequest;
import com.trms.util.ConnectionUtil;

public class DaoImpl implements Dao {

	public boolean loginIdAvailable(String loginUserId) {
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			Class.forName ("oracle.jdbc.OracleDriver");
			Connection conn = getConnection();
			System.out.println("Connection successfully established");
			String sql = "SELECT loginUserId from Employee where loginUserId = ?";
			sql = "select * from employee"; 
			ps = conn.prepareStatement(sql); 
			rs = ps.executeQuery();
			return (rs.getRow() > 0); 
//			ps = conn.prepareStatement(sql); 
//			ps.setString(1, loginUserId);
//			rs = ps.executeQuery();
//			return (rs.getRow() > 0);
		} 
		catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs); 
			close(ps); 
		}



		return true;//return true on a failure, to bar requesting user from progressing
	}

	@Override
	public boolean emailAvailable(String email) {
		ResultSet rs = null;
		PreparedStatement ps = null;

		try(Connection conn = getConnection()) {
			String sql = "SELECT email FROM Employee E join ContactInformation C "
					+ "on E.contactInformationKey = c.Contact_Index where email = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setString(1, email);
			rs = ps.executeQuery();
			return rs.next(); 			

		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs); 
			close(ps); 
		}

		return true; //return true on a failure, to bar requesting user from progressing
	}

	public boolean insertEmployee(Employee e) {
		CallableStatement cs = null;
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		boolean result = false; 
		ContactInformation c = e.getContactInfo();

		try(Connection conn = getConnection()) {
			String sql = "call Create_Employee_Full(?, ?, ?, ?,"
					+ "?, ?, ?,"
					+ "?, ?, ?, ?, ?, ?)";
			cs = conn.prepareCall(sql); 
			System.out.println(e);
			
			cs.setString(1, e.getFirstName());
			cs.setString(2, e.getLastName());
			cs.setString(3, e.getLoginUserId());
			cs.setString(4, e.getLoginPassword());
			cs.setString(5, e.getDepartment());
			cs.setString(6, e.getPosition());
			cs.setString(7, e.getSupervisor());
			
			cs.setString(8, c.getEmail());
			cs.setString(9, c.getPhone());
			cs.setString(10, c.getStreetAddr());
			cs.setString(11, c.getCity());
			cs.setString(12, c.getState());
			cs.setString(13, c.getZipCode());
			
			cs.execute();		
			
			
			sql = "select * from Employee E join ContactInformation C on E.contactInformationKey = C.Contact_Index "
					+ "where loginUserId = ?"; 
			ps = conn.prepareStatement(sql); 
			ps.setString(1, e.getLoginUserId());
			rs = ps.executeQuery();
			while(rs.next()) {
				result = true; 
				if(!(e.getFirstName().equals(rs.getString("firstName")))) {
					System.out.println("Failed on firstName");
					result = false; 
				}
				if(!(e.getLastName().equals(rs.getString("lastName")))) {
					System.out.println("Failed on lastName");
					result = false; 
				}
				if(!(e.getLoginUserId().equals(rs.getString("loginUserId")))) {
					System.out.println("Failed on loginUserId");
					result = false; 
				}
				if(!(e.getLoginPassword().equals(rs.getString("loginPassword")))) {
					System.out.println("Failed on loginPassword");
					result = false; 
				}
				if(rs.getInt("deptId") == 0) {
					System.out.println("Failed on department");
					result = false; 
				}
				if(rs.getInt("positionId") == 0) {
					System.out.println("Failed on position");
					result = false; 
				}
				if(rs.getInt("supervisorEmpId") == 0) {
					System.out.println("Failed on supervisor");
					result = false; 
				}
				if(!(c.getEmail().equals(rs.getString("email")))) {
					System.out.println("Failed on email");
					result = false; 
				}
				if(!(c.getPhone().equals(rs.getString("phoneNumber")))) {
					System.out.println("Failed on phone");
					result = false; 
				}
				if(!(c.getStreetAddr().equals(rs.getString("streetAddress")))) {
					System.out.println("Failed on streetAddress");
					result = false; 
				}
				if(!(c.getCity().equals(rs.getString("city")))) {
					System.out.println("Failed on city");
					result = false; 
				}
				if(!(c.getState().equals(rs.getString("state")))) {
					System.out.println("Failed on state");
					result = false; 
				}
				if(!(c.getZipCode().equals(rs.getString("zipCode")))) {
					System.out.println("Failed on zipCode");
					result = false; 
				}
				
					
			}
			

		}catch(SQLException s) {
			s.printStackTrace();
		}
		finally {
			close(cs); 
			close(rs); 
			close(ps); 
		}		
		return result; 
 
	}

	
	public boolean verifyCredentials(String loginUserId, String loginPassword) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			Class.forName ("oracle.jdbc.OracleDriver");
			String sql = "SELECT * from Employee where loginUserId = ? and loginPassword = ?"; 
			ps = conn.prepareStatement(sql);
			ps.setString(1,  loginUserId);
			ps.setString(2, loginPassword);
			rs = ps.executeQuery();
			return rs.next();
		}
		catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			close(ps); 
			close(rs); 
		}
		
		return false; 
	}
	
	@SuppressWarnings("deprecation")
	public void submitReimbursementRequestForm(ReimbRequest trr) {
		PreparedStatement ps = null; 
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			ps = conn.prepareStatement("insert into TuitionReimbursmentRequest("
					+ "requesterloginid, eventName, eventDate, eventAddress, eventType, eventDescription,"
					+ "unmodifiedCost, justification, workHoursMissing"
					+ ") "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, trr.getReqesterLoginId());
			ps.setString(2, trr.getEventName());
			ps.setTimestamp(3, trr.getDateAndTime());
			ps.setString(4, trr.getLocation());
			ps.setString(5, trr.getEventType());
			ps.setString(6, trr.getDescription());
			ps.setDouble(7, trr.getUnmodifiedCost());
			ps.setString(8, trr.getJustification());
			ps.setString(9, trr.getWorkHoursMissing());
			rs = ps.executeQuery();			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(ps); 
			close(rs); 
		}
		
	}
	
	public List<ReimbRequest> getRequests(String loginUserId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<ReimbRequest> requests = new ArrayList<ReimbRequest>(); 
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM TuitionReimbursmentRequest where requesterLoginId = ?";
			ps = conn.prepareStatement(sql); 
			ps.setString(1, loginUserId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ReimbRequest trr = new ReimbRequest(
					rs.getInt("trackingnumber"),
					rs.getString("requesterLoginId"),
					rs.getString("eventName"),
					rs.getTimestamp("eventDate"),
					rs.getString("eventAddress"),
					rs.getString("eventType"),
					rs.getString("eventDescription"),
					rs.getDouble("unmodifiedCost"),
					rs.getString("gradingFormat"),
					rs.getString("justification"),
					rs.getString("workHoursMissing"),
					rs.getInt("supervisorApproved") == 1,
					rs.getInt("deptHeadApproved") == 1,
					rs.getInt("benCoApproved") == 1);
				requests.add(trr); 					
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs); 
		}
		return requests; 
	}
	

	public PrivilegesSummary getPrivilegesByUserId(String loginUserId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PrivilegesSummary priv; 
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select firstName, lastName, loginUserId, supervisorEmpId, "
					+ "name as department, Positions.title as position, departmentHeadId "
					+ "from Employee natural join Department natural join Positions "
					+ "where loginUserId = ?"; 
					
			ps = conn.prepareStatement(sql); 
			ps.setString(1, loginUserId);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				priv = new PrivilegesSummary(
					rs.getString("firstName"),
					rs.getString("lastName"),
					rs.getInt("supervisorEmpId") == 0,
					(rs.getString("departmentHeadId") != null) && (rs.getString("departmentHeadId").equals(loginUserId)),
					rs.getString("department"),
					rs.getString("position").equals("Benefits Coordinator")
				);
				return priv; 
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs); 
		}
		
		
		return null; 
	}
	

	public int approveOrDeny(int trackingNumber, String approvalType, int value) {
		PreparedStatement ps = null;
		int rslt = 0; 
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update tuitionreimbursmentrequest set tuitionreimbursmentrequest.benCoApproved = 1 where trackingNumber = 5";
			ps = conn.prepareStatement(sql); 
//			ps.setString(1, approvalType);
//			ps.setInt(2, value);
//			ps.setInt(3, trackingNumber);
			System.out.println("update tuitionreimbursmentrequest set " + approvalType + " = " + value + " where trackingNumber = " + trackingNumber);
			rslt = ps.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(ps);
		}
		
		return rslt; 
	}


}
