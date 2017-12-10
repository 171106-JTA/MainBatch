package revature.trms.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import revature.trms.bean.Message;
import revature.trms.bean.Request;
import revature.trms.bean.User;
import revature.trms.connectionUtil.ConnectorUtil;

public class DaoImpl implements DaoInterface{
	

	public void create_user(String firstname, String lastname, String emp_id, String password, String email, String address, int ssn, int phone){
		CallableStatement cs = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			cs = conn.prepareCall("{call new_employee(?,?,?,?,?,?,?,?)}");
			cs.setString(1, firstname);
			cs.setString(2, lastname);
			cs.setString(3, emp_id);
			cs.setString(4, password);
			cs.setString(5, email);
			cs.setString(6, address);
			cs.setInt(7, ssn);
			cs.setInt(8, phone);
			cs.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(cs);
		}
	}//create a new user by inputing id, password, first name, last name
	
	
	
	public void make_reimbursement_request(String emp_id, double money){
		CallableStatement cs = null;
		try(Connection conn = ConnectorUtil.getConnected()){
			cs = conn.prepareCall("{call make_request(?,?)}");
			cs.setString(1, emp_id);
			cs.setDouble(2, money);
			cs.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(cs);
		}
	}//user applies for a reimbursement request
	
	
	
	public double get_score(int req_id){
		PreparedStatement cs = null;
		ResultSet rs = null;
		double score = 0;
		try(Connection conn = ConnectorUtil.getConnected()){
			cs = conn.prepareCall("select grade1, grade2 from req_status where req_id = ?");
			cs.setInt(1, req_id);
			rs = cs.executeQuery();
			if(rs.next())
				score = (rs.getDouble(1) + rs.getDouble(2))/2;
			return score;
		}catch(SQLException e){
			return 0;
		}finally{
			if(cs != null)
				ConnectorUtil.close(cs);
			if(rs != null)
				ConnectorUtil.close(rs);
		}
	}//returns the total score from grading
	
	
	
	public boolean isApproved(int req_id){
		PreparedStatement ps = null;
		ResultSet rs = null;
		int status = 0;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("select pending from req_status where req_id = ?");
			ps.setInt(1, req_id);
			rs = ps.executeQuery();
			
			if(rs.next())
				status = rs.getInt(1);
			if(status == 2)
				return true;
			else
				return false;
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			ConnectorUtil.close(ps);
			ConnectorUtil.close(rs);
		}
		
	}//tells if the reimbursement is approved or not
	
	
	public void verify(int req_id){
		CallableStatement cs = null;
		double score = 0;
		try(Connection conn = ConnectorUtil.getConnected()){
			score = get_score(req_id);
			if(score > 60)
				cs = conn.prepareCall("{call approve(?)}");
			else
				cs = conn.prepareCall("{call deny(?)}");
			cs.setInt(1, req_id);
			cs.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(cs != null)
				ConnectorUtil.close(cs);
		}
	}//sets approved column to 0 for denied, 1 for pending, and 2 for approved
	
	
	
	public void getStatus(String emp_id){
		PreparedStatement ps = null;
		ResultSet rs = null;
		double granted = 0;
		double left = 0;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("select reimb_avail from emp_amount where emp_id = ?");
			ps.setString(1, emp_id);
			rs = ps.executeQuery();
			if(rs.next())
				left = rs.getDouble(1);
			granted = 1000 - left;
			System.out.println("Amount Granted: " + granted);
			System.out.println("Amount Available: " + left);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(ps);
			ConnectorUtil.close(rs);
		}
	}//get user's reimbursement granted and reimbursement remaining.
	
	
	
	public boolean login_emp(String emp_id, String password){
		PreparedStatement ps = null;
		ResultSet rs = null;
		String realEmp_id = "";
		String realPassword = "";
		int authority = 0;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("select emp_id, pass, authority from employees where emp_id = ? and pass = ?");
			ps.setString(1, emp_id);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next()){
				realEmp_id = rs.getString(1);
				realPassword = rs.getString(2);
				authority = rs.getInt(3);
			}
			
			if(realEmp_id.equals(emp_id) && realPassword.equals(password) && authority == 1)
				return true;
			else
				return false;
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			if(ps != null)
				ConnectorUtil.close(ps);
			if(rs != null)
				ConnectorUtil.close(rs);
		}
	}//employee login
	
	
	
	public boolean login_sv(String emp_id, String password){
		PreparedStatement ps = null;
		ResultSet rs = null;
		String realEmp_id = "";
		String realPassword = "";
		int authority = 0;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("select emp_id, pass, authority from employees where emp_id = ? and pass = ?");
			ps.setString(1, emp_id);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next()){
				realEmp_id = rs.getString(1);
				realPassword = rs.getString(2);
				authority = rs.getInt(3);
			}
			
			if(realEmp_id.equals(emp_id) && realPassword.equals(password) && authority == 2)
				return true;
			else
				return false;
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			ConnectorUtil.close(ps);
			ConnectorUtil.close(rs);
		}
	}//super visor login
	
	
	public boolean login_dh(String emp_id, String password){
		PreparedStatement ps = null;
		ResultSet rs = null;
		String realEmp_id = "";
		String realPassword = "";
		int authority = 0;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("select emp_id, pass, authority from employees where emp_id = ? and pass = ?");
			ps.setString(1, emp_id);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next()){
				realEmp_id = rs.getString(1);
				realPassword = rs.getString(2);
				authority = rs.getInt(3);
			}
			
			if(realEmp_id.equals(emp_id) && realPassword.equals(password) && authority == 3)
				return true;
			else
				return false;
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			ConnectorUtil.close(ps);
			ConnectorUtil.close(rs);
		}
	}//dept head login
	
	
	
	public boolean login_bc(String emp_id, String password){
		PreparedStatement ps = null;
		ResultSet rs = null;
		String realEmp_id = "";
		String realPassword = "";
		int authority = 0;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("select emp_id, pass, authority from employees where emp_id = ? and pass = ?");
			ps.setString(1, emp_id);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next()){
				realEmp_id = rs.getString(1);
				realPassword = rs.getString(2);
				authority = rs.getInt(3);
			}
			
			if(realEmp_id.equals(emp_id) && realPassword.equals(password) && authority == 4)
				return true;
			else
				return false;
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			ConnectorUtil.close(ps);
			ConnectorUtil.close(rs);
		}
	}//benco login
	
	public void insert_info(String emp_id, int ssn, String phone, String address, String email){
		PreparedStatement ps = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("update person set ssn = ?, phone = ?, address = ?, email = ? where emp_id = ?");
			ps.setInt(1, ssn);
			ps.setString(2, phone);
			ps.setString(3, address);
			ps.setString(4, email);
			ps.setString(5, emp_id);
			
			ps.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(ps);
		}
	}
	
	public ArrayList<Request> getRequests(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Request> requests = new ArrayList<Request>();
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("select a.emp_id, a.req_id, a.money, b.pending, b.grade1, b.grade2, a.req_date from request a "
					+ "inner join req_status b "
					+ "on a.req_id = b.req_id");
			rs = ps.executeQuery();
			while(rs.next()){
				requests.add(new Request(rs.getString(1), rs.getInt(2), rs.getDouble(3), rs.getInt(4), rs.getFloat(5), rs.getFloat(6), rs.getDate(7)));
			}//end while
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(ps);
			ConnectorUtil.close(rs);
		}
		return requests;
	}
	
	public User getUser(String emp_id){
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("select a.emp_id, a.pass, b.firstname, b.lastname, b.email, b.address, b.ssn, b.phone from employees a "
					+ "inner join person b "
					+ "on a.emp_id = b.emp_id and b.emp_id = ?");
			ps.setString(1, emp_id);
			rs = ps.executeQuery();
			if(rs.next()){
				User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8));
				return user;
			}
		}catch(SQLException e){
			e.printStackTrace();
			
		}finally{
			if(ps != null)
				ConnectorUtil.close(ps);
			if(rs != null)
				ConnectorUtil.close(rs);
		}
		return null;
	}
	
	public double getAvail(String emp_id){
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("select reimb_avail from emp_amount where emp_id = ?");
			ps.setString(1, emp_id);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getDouble(1);
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(ps);
			ConnectorUtil.close(rs);
		}
		
		return 0;
	}
	

	public void gradeSV(int req_id, double grade){
		PreparedStatement ps = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("update req_status set grade1 = ? where req_id = ?");
			ps.setDouble(1, grade);
			ps.setInt(2, req_id);
			ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(ps);
		}
	}
	
	public void gradeDH(int req_id, double grade){
PreparedStatement ps = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("update req_status set grade2 = ? where req_id = ?");
			ps.setDouble(1, grade);
			ps.setInt(2, req_id);
			ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(ps);
		}
	}
	
	public void sendMessage(String sender, String receiver, String message){
		CallableStatement cs= null;
		try(Connection conn = ConnectorUtil.getConnected()){
			cs= conn.prepareCall("{call send_message(?,?,?)}");
			cs.setString(1, sender);
			cs.setString(2, receiver);
			cs.setString(3, message);
			
			cs.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(cs);
		}
	}
	
	public ArrayList<Message> getMessages(){
		PreparedStatement ps =null;
		ResultSet rs = null;
		String sender, receiver, message;
		Date date;
		ArrayList<Message> messages= new ArrayList<Message>();
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("select * from messages order by datesent");
			rs = ps.executeQuery();
			while(rs.next()){
				date = rs.getDate(1);
				sender  = rs.getString(2);
				receiver = rs.getString(3);
				message = rs.getString(4);
				
				messages.add(new Message(sender, receiver, message, date));
			}
			return messages;
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void approve(int req_id){
		CallableStatement cs = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			cs = conn.prepareCall("{call approve(?)}");
			cs.setInt(1, req_id);
			cs.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(cs);
		}
	}
	
	public void deny(int req_id){
		CallableStatement cs = null;
		
		try(Connection conn = ConnectorUtil.getConnected()){
			cs = conn.prepareCall("{call deny(?)}");
			cs.setInt(1, req_id);
			cs.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(cs);
		}
	}
	
	public ArrayList<String> getHeads(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> heads =  new ArrayList<String>();
		try(Connection conn = ConnectorUtil.getConnected()){
			ps = conn.prepareStatement("select emp_id from employees where authority != 1");
			rs = ps.executeQuery();
			while(rs.next()){
				heads.add(rs.getString(1));
			}
			return heads;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectorUtil.close(ps);
			ConnectorUtil.close(rs);
		}
		
		return null;
	}


}
