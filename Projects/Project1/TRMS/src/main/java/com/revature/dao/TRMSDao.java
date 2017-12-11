package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.revature.util.ConnectionUtil;
/**
 * Access database for TRMS servlets
 * @author Xavier Garibay
 *
 */
public class TRMSDao {
	/**
	 * Get list of all users' login information
	 * @return ArrayList<String[]> - users' login info
	 */
	public ArrayList<String[]> getLogins() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		ArrayList<String[]> logins= new ArrayList<>();
		String[] log= new String[3];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM login";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				log= new String[3];
				log[0]=rs.getString(1);//emp_id
				log[1]=rs.getString(2);//username
				log[2]=rs.getString(3);//password
				
				logins.add(log);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return logins;
	}
	
	/**
	 * Check if user is supervisor to any other employee
	 * @param id - user id
	 * @return boolean - whether the user is the supervisor of an employee
	 */
	public boolean checkSuperior(int id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		ArrayList<Integer> logins= new ArrayList<>();
		int supID= -1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT sup_id FROM employee where sup_id=?";//find employees who have user as supervisor
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				supID=rs.getInt(1);
				if(supID==id) {
					logins.add(supID);
					break;
				}
			}
			
			if(logins.size()!=0)//if at least one employee has user as supervisor
					return true;
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return false;//if not supervisor to anyone
	}

	/**
	 * Get Id of employee with a given username
	 * @param username - username to be searched for
	 * @return int- employee id
	 */
	public int getId(String username) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int id= -1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT emp_id FROM login where emp_username=?";//get id of login with given username
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				id=rs.getInt(1);//get id
			}			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return id;
	}
	
	/**
	 * Get id of user who made reimbursement application
	 * @param app_id - application id
	 * @return int - employee id of creator
	 */
	public int getEmpIdofApp(int app_id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int id= -1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT emp_id FROM repay_app where app_id=?";//get employee id of app #
			ps = conn.prepareStatement(sql);
			ps.setInt(1, app_id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				id=rs.getInt(1);//get employee id
			}			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return id;
	}
	
	/**
	 * Get position status of employee
	 * @param id - employee id
	 * @return int[] - benco and chair status of employee
	 */
	public int[] isBencoOrChair(int id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int[] status= new int[2];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT benco, chair FROM emp_status where emp_id=?";//get benco and chair status of employee
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				status[0]=rs.getInt(1);//benco
				status[1]=rs.getInt(2);//chair
			}			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return status;
	}
	
	/**
	 * Get employee name and money info from id
	 * @param id - employee id
	 * @return String[] - name and money info
	 */
	public String[] getEmpInfo(int id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		String[] info= new String[4];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT fname, lastname, repay_awarded, repay_pending FROM employee where emp_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				info[0]=rs.getString(1);//fname
				info[1]=rs.getString(2);//lname
				info[2]=rs.getString(3);//repay_awarded
				info[3]=rs.getString(4);//repay_pending
			}			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return info;
	}
	
	/**
	 * Add application to database, filling all necessary tables and updating repayment pending
	 * @param request - web page info
	 * @param id - user info
	 * @param reqDate - date of event
	 */
	public void addApplication(HttpServletRequest request, int id, String reqDate) {
		int x=0;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String s= new String();
		ArrayList<String> users= new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection();){
			//repay_app - list of applications and creators
			String sql ="INSERT INTO repay_app (emp_id) values('" + id +"')";
			ps = conn.prepareStatement(sql);
			x=ps.executeUpdate(sql);
			//app status - approval status
			sql="INSERT INTO app_status (urgent, sup_apr, chair_apr, benco_apr, passed, approved, completed, recent_date)"
					+" values(0,0,0,0,0,0,0,0,?)";//default values except for date
			ps= conn.prepareStatement(sql);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(1,date);
			x=ps.executeUpdate();
			//app info - event information
			sql="INSERT INTO app_info (ev_date, ev_descr, ev_time, ev_cost, ev_type_id, ev_grade_id)"
					+ "values(?,?,?,?,?,?)";
			ps= conn.prepareStatement(sql);
			int[] datePart= new int[3];
			String dates=request.getParameter("eventDate");
			String[] stringDate=reqDate.split("-");
			for(int i=0;i<3;i++)
				datePart[i]=Integer.parseInt(stringDate[i]);
			Date eventDate= new Date(datePart[0],datePart[1],datePart[2]);
			ps.setDate(1,eventDate);
			ps.setString(2,request.getParameter("eventName"));
			ps.setString(3,request.getParameter("eventTime"));
			ps.setString(4,request.getParameter("cost"));
			ps.setString(5,request.getParameter("eventType"));
			ps.setString(6,request.getParameter("gradeType"));
			int cost=Integer.parseInt(request.getParameter("cost"));
			x=ps.executeUpdate();
			//get user's current repay_pending
			sql="Select repay_pending from employee where emp_id=?";
			ps= conn.prepareStatement(sql);
			ps.setInt(1,id);
			rs=ps.executeQuery();
			int pending=0;
			while(rs.next())
			{
				pending=rs.getInt(1);
			}
			//update user's pending repayment including new application
			sql="update employee set repay_pending=? where emp_id=?";
			ps= conn.prepareStatement(sql);
			ps.setInt(1, pending+cost);
			ps.setInt(2,id);
			ps.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
	}
	
	/**
	 * Same as above, but creates a new grade format and assigns it to the application
	 * @param request - web page info
	 * @param id - user info
	 * @param reqDate - date of event
	 * @param passGrade - passing grade of new format
	 * @param presentReq - whether or not format requires presentation
	 */
	public void addApplication(HttpServletRequest request, int id, String reqDate, String passGrade, boolean presentReq)
	{
		int x=0;
		int present=-1;
		if(presentReq)
			present=1;
		else
			present=0;		
		PreparedStatement ps =null;
		ResultSet rs = null;
		String s= new String();
		ArrayList<String> users= new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection();){
			//create grade format
			String sql ="INSERT INTO grade_format (pass_grade, presentation) values(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, passGrade);
			ps.setInt(2, present);
			x=ps.executeUpdate(sql);
			
			//app repay
			sql ="INSERT INTO repay_app (emp_id) values('" + id +"')";
			ps = conn.prepareStatement(sql);
			x=ps.executeUpdate(sql);
			
			//app status
			sql="INSERT INTO app_status (urgent, sup_apr, chair_apr, benco_apr, passed, approved, completed, recent_date)"
					+" values(0,0,0,0,0,0,0,0,?)";
			ps= conn.prepareStatement(sql);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(1,date);
			x=ps.executeUpdate();
			
			//get new grade_type
			int grade_id=0;
			sql="select ev_grade_id from grade_format where pass_grade=?";
			ps= conn.prepareStatement(sql);
			ps.setString(1, passGrade);
			rs=ps.executeQuery();
			while(rs.next())
			{
				grade_id=rs.getInt(1);
			}
			//app info
			sql="INSERT INTO app_info (ev_date, ev_descr, ev_time, ev_cost, ev_type_id, ev_grade_id)"
					+ "values(?,?,?,?,?,?)";
			ps= conn.prepareStatement(sql);
			int[] datePart= new int[3];
			String dates=request.getParameter("eventDate");
			String[] stringDate=reqDate.split("-");
			for(int i=0;i<3;i++)
				datePart[i]=Integer.parseInt(stringDate[i]);
			Date eventDate= new Date(datePart[0],datePart[1],datePart[2]);
			ps.setDate(1,eventDate);
			ps.setString(2,request.getParameter("eventName"));
			ps.setString(3,request.getParameter("eventTime"));
			ps.setString(4,request.getParameter("cost"));
			ps.setString(5,request.getParameter("eventType"));
			ps.setInt(6,grade_id);//new grade format id
			int cost=Integer.parseInt(request.getParameter("cost"));
			x=ps.executeUpdate();
			
			//Get and update pending repayment
			sql="Select repay_pending from employee where emp_id=?";
			ps= conn.prepareStatement(sql);
			ps.setInt(1,id);
			rs=ps.executeQuery();
			int pending=0;
			while(rs.next())
			{
				pending=rs.getInt(1);
			}
			sql="update employee set repay_pending=? where emp_id=?";
			ps= conn.prepareStatement(sql);
			ps.setInt(1, pending+cost);
			ps.setInt(2,id);
			ps.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		
	}
	
	/**
	 * Get approval status of all applications
	 * @return ArrayList<int[]> approval statuses
	 */
	public ArrayList<int[]> getAppsStatus() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		ArrayList<int[]> logins= new ArrayList<>();
		int[] log= new int[4];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM app_status"; //get all application statuses
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				log= new int[4];
				log[0]=rs.getInt(1);//app id
				log[1]=rs.getInt(3);//supervisor appr
				log[2]=rs.getInt(4);//chair appr
				log[3]=rs.getInt(5);//benco appr
				
				logins.add(log);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return logins;
	}
	
	/**
	 * Get approval status of specific application
	 * @param id - application status
	 * @return int[] approval status of application
	 */
	public int[] getAppStatus(int id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int[] log= new int[4];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM app_status where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				log[0]=rs.getInt(1);//app id
				log[1]=rs.getInt(3);//supervisor appr
				log[2]=rs.getInt(4);//chair appr
				log[3]=rs.getInt(5);//benco appr
				
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return log;
	}
	
	/**
	 * Get status information of all applications with most recent date info
	 * @return ArrayList<String[]> status information of applications
	 */
	public ArrayList<String[]> getAppStatusWithDate() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		ArrayList<String[]> logs= new ArrayList<>();
		String[] log= new String[5];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM app_status";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				log=new String[5];
				log[0]=rs.getString(1);//app id
				log[1]=rs.getString(3);//supervisor appr
				log[2]=rs.getString(4);//chair appr
				log[3]=rs.getString(5);//benco appr
				log[4]=rs.getString(10);//date	
				logs.add(log);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return logs;
	}
	
	/**
	 * Get department of employee from employee id
	 * @param id - employee id
	 * @return int - department
	 */
	public int getDepartment(int id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int dept= -1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT department FROM employee where emp_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				dept=rs.getInt(1);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return dept;
	}
	
	/**
	 * Get pending repayment of employee from id
	 * @param id - employee id
	 * @return int - pending repayment of employee
	 */
	public int getPending(int id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int dept= -1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT repay_pending FROM employee where emp_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				dept=rs.getInt(1);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return dept;
	}

	/**
	 * Get supervisor of employee
	 * @param emp_id - employee id 
	 * @return - supervisor's employee id
	 */
	public int getAppSup(int emp_id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int sup_id= -1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT sup_id FROM employee where emp_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, emp_id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				sup_id=rs.getInt(1);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return sup_id;		
	}
	
	/**
	 * Get department of employee who created application
	 * @param app_id - application id
	 * @return int - departent of employee
	 */
	public int getAppDept(int app_id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int dept= -1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT department FROM app_sup where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, app_id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				dept=rs.getInt(1);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return dept;		
	}

	/**
	 * Get application info including; including employee and event info
	 * @param app_id - application id
	 * @return String[] - application info
	 */
	public String[] getAppInfo(int app_id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		String[] info= new String[9];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM approve_app where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, app_id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				info[0]=rs.getString(1);//app_id
				info[1]=rs.getString(2);//emp_id
				info[2]=rs.getString(3);//fname
				info[3]=rs.getString(4);//lastname
				info[4]=rs.getString(5);//ev_descr
				info[5]=rs.getString(6);//ev_cost
				info[6]=rs.getString(7);//ev_type_id
				info[7]=rs.getString(8);//repay_awarded
				info[8]=rs.getString(9);//repay_pending
			}			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return info;
		
	}

	/**
	 * Get application info; including event and approval info
	 * @param app_id - application id
	 * @return String[] - application information
	 */
	public String[] getAppUserInfo(int app_id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		String[] info= new String[11];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();)
		{
			String sql ="SELECT * FROM user_app where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, app_id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				info[0]=rs.getString(1);//app_id
				info[1]=rs.getString(2);//ev_descr
				info[2]=rs.getString(3);//ev_date
				info[3]=rs.getString(4);//ev_cost
				info[4]=rs.getString(5);//ev_type
				info[5]=rs.getString(6);//sup_apr
				info[6]=rs.getString(7);//chair_apr
				info[7]=rs.getString(8);//benco_apr
				info[8]=rs.getString(9);//passed
				info[9]=rs.getString(10);//recent_date
				info[10]=rs.getString(11);//info_hold	
			}			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
			close(rs);
		}
		return info;
		
	}
	
	/**
	 * Mark application as approved by supervisor and update most recent date to current date
	 * @param approved_id - application id
	 */
	public void supApprove(int approved_id) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set sup_apr=1, recent_date=? where app_id=?";
			ps = conn.prepareStatement(sql);
			Date date = new Date(Calendar.getInstance().getTime().getTime());// get current date
			ps.setDate(1,date);
			ps.setInt(2, approved_id);
			int x= ps.executeUpdate();				
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {//close resources
			close(ps);
		}
		
	}

	/**
	 * Mark application as approved by department chair
	 * @param approved_id - application id
	 */
	public void chairApprove(int approved_id) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set chair_apr=1, recent_date=? where app_id=?";
			ps = conn.prepareStatement(sql);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(1,date);
			ps.setInt(2, approved_id);
			int x= ps.executeUpdate();				
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}
		
	}

	/**
	 * Mark application as approved by BenCo
	 * @param approved_id - application id
	 */
	public void bencoApprove(int approved_id, String reason) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set benco_apr=1, recent_date=? where app_id=?";
			ps = conn.prepareStatement(sql);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(1,date);
			ps.setInt(2, approved_id);
			int x= ps.executeUpdate();
			sql ="insert into benco_approved values(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,approved_id);
			ps.setString(2, reason);
			x= ps.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}
	}
	
	
	/**
	 * Mark application as rejected by superior
	 * @param approved_id - application id
	 */
	public void supReject(int reject_id, String reason) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set sup_apr=-1, chair_apr=-1, benco_apr=-1, approved=-1, completed=-1, recent_date=? where app_id=?";
			ps = conn.prepareStatement(sql);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(1,date);
			ps.setInt(2, reject_id);
			int x= ps.executeUpdate();	
			sql="insert into app_rejected values(?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, reject_id);
			ps.setString(2, reason);
			x= ps.executeUpdate();				
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}
	}

	/**
	 * Mark application as rejected by department chair
	 * @param approved_id - application id
	 */
	public void chairReject(int reject_id, String reason) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set chair_apr=-1, benco_apr=-1, approved=-1, completed=-1, recent_date=? where app_id=?";
			ps = conn.prepareStatement(sql);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(1,date);
			ps.setInt(2, reject_id);
			int x= ps.executeUpdate();
			sql="insert into app_rejected values(?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,reject_id);
			ps.setString(2, reason);
			x= ps.executeUpdate();	
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}
		
	}

	/**
	 * Mark application as rejected by Benco
	 * @param approved_id - application id
	 */
	public void bencoReject(int reject_id, String reason) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set benco_apr=-1, approved=-1, completed=-1, recent_date=? where app_id=?";
			ps = conn.prepareStatement(sql);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(1,date);
			ps.setInt(2, reject_id);
			int x= ps.executeUpdate();
			sql="insert into app_rejected values(?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,reject_id);
			ps.setString(2, reason);
			x= ps.executeUpdate();	
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}
		
	}

	/**
	 * Reset all employee's repayment awarded to 0
	 */
	public void resetAward() {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update employee set repay_awarded=0";
			ps = conn.prepareStatement(sql);
			int x= ps.executeUpdate();	
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}
		
	}

	/**
	 * Get a list of all applications made by a specific user
	 * @param emp_id - employee id
	 * @return ArrayList<Integer> - list of application id's
	 */
	public ArrayList<Integer> getUserApps(int emp_id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		ArrayList<Integer> apps= new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT app_id FROM repay_app where emp_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, emp_id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				int app_id=rs.getInt(1);//application id
				apps.add(app_id);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return apps;
	}

	/**
	 * Get file information of all files attached to a specific application
	 * @param appID - application id
	 * @return ArrayList<String[]> - information of files
	 */
	public ArrayList<String[]> getAppFiles(int appID) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		ArrayList<String[]> files= new ArrayList<>();
		String[] file= new String[3];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT file_id,file_name, file_description FROM app_files where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, appID);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				file=new String[3];
				file[0]=rs.getString(1);//file id
				file[1]=rs.getString(2);//file name
				file[2]=rs.getString(3);//file description
				files.add(file);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return files;
	}

	/**
	 * Get list of all file id's 
	 * @return ArrayList<Integer> - list of file id's
	 */
	public ArrayList<Integer> getFileIds() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		ArrayList<Integer> files= new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT file_id FROM app_files";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				int id=rs.getInt(1);
				files.add(id);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return files;
	}

	/**
	 * mark application as having a grade or presentation input
	 * @param id - application id
	 * @param file_id - file id
	 */
	public void markGrade(int id, int file_id) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_pass set grade_file_id=?, grade_input_mark=1 where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,file_id);
			ps.setInt(2, id);
			int x= ps.executeUpdate();				
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}
	}

	/**
	 * Pay user based on cost and event type, adjusting repayment pending and awarded
	 * @param emp_id - employee id
	 * @param app_id - application id
	 */
	public void payUser(int emp_id, int app_id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int awarded=0;
		int pending=0;
		int repay=0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			//Get money information of employee
			String sql ="SELECT repay_awarded, repay_pending FROM employee where emp_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, emp_id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				awarded=rs.getInt(1);
				pending=rs.getInt(2);
			}
			//get event information of application
			sql ="SELECT ev_cost, ev_type_id FROM app_info where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, app_id);
			rs= ps.executeQuery();
			while(rs.next())
			{//adjust repay based on type
				int cost=rs.getInt(1);
				int type=rs.getInt(2);
				if(repay>pending)
				{
					pending=0;
				}
				else
				{
					pending-=repay;//subtract full cost from pending
				}
				if(type==1)
					repay=(int)(cost*.8);
				else if(type==2)
					repay=(int)(cost*.6);
				else if(type==2)
					repay=(int)(cost*.6);
				else if(type==3)
					repay=(int)(cost*.75);
				else if(type==5)
					repay=(int)(cost*.9);
				else if(type==6)
					repay=(int)(cost*.3);
			}
			awarded+=repay;//add adjusted repayment to awarded repayments of employee
			//update employee information
			sql ="UPDATE employee set repay_awarded=?, repay_pending=? where emp_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, awarded);
			ps.setInt(2, pending);
			ps.setInt(3, emp_id);
			ps.executeUpdate();
			//mark application as completed
			sql ="UPDATE app_status set completed=1 where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, app_id);
			ps.executeUpdate();	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		
	}

	/**
	 * Get application information of all apps; including employee, grade, approval information
	 * @return ArrayList<String[]>  - all applications' information
	 */
	public ArrayList<String[]> getGrades() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		ArrayList<String[]> apps= new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM grade_approve";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				String[] app= new String[10];
				app[0]=rs.getString(1);//app_id
				app[1]=rs.getString(2);//fname
				app[2]=rs.getString(3);//lastname
				app[3]=rs.getString(4);//filename
				app[4]=rs.getString(5);//pass_grade
				app[5]=rs.getString(6);//presentation
				app[6]=rs.getString(7);//passed
				app[7]=rs.getString(8);//approved
				app[8]=rs.getString(9);//sup_id
				app[9]=rs.getString(10);//completed
				apps.add(app);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return apps;
	}
	
	/**
	 * Get information of a specific application; including employee, grade and approval info
	 * @param app_id - application id
	 * @return String[] - application info
	 */
	public String[] getGrade(int app_id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		String[] app= new String[9];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM grade_approve where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, app_id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				app[0]=rs.getString(1);//app_id
				app[1]=rs.getString(2);//fname
				app[2]=rs.getString(3);//lastname employee in
				app[3]=rs.getString(4);//filename 
				app[4]=rs.getString(5);//pass_grade
				app[5]=rs.getString(6);//presentation
				app[6]=rs.getString(7);//passed
				app[7]=rs.getString(8);//approved
				app[8]=rs.getString(9);//sup_id
				
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return app;
	}

	/**
	 * Mark application as having a passing grade
	 * @param app_id - application id
	 */
	public void BencoGradeApprove(int app_id) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set passed=1, recent_date=? where app_id=?";
			ps = conn.prepareStatement(sql);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(1, date);
			ps.setInt(2,app_id);
			int x= ps.executeUpdate();				
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}	
	}
	
	/**
	 * Mark application as having an appropriate presentation
	 * @param app_id - application id
	 */
	public void SupGradeApprove(int app_id) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set approved=1, recent_date=? where app_id=?";
			ps = conn.prepareStatement(sql);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(1, date);
			ps.setInt(2,app_id);
			int x= ps.executeUpdate();				
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}	
	}

	/**
	 * Get a specific application's approval information
	 * @param app_id - application id
	 * @return
	 */
	public int[] CompleteInfo(int app_id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int[] app= new int[5];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT * FROM complete_info where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, app_id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				app[0]=rs.getInt(1);//app_id
				app[1]=rs.getInt(2);//passed
				app[2]=rs.getInt(3);//approved
				app[3]=rs.getInt(4);//presentation
				app[4]=rs.getInt(5);//completed
				
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return app;
	}

	/**
	 * Update cost of an application and pending repayment of creator
	 * @param app_id - application id
	 * @param newCost - cost to update to
	 * @param pending - adjusted pending repayment of employee
	 */
	public void updateCost(int app_id, int newCost, int pending) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_info set ev_cost=? where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newCost);
			ps.setInt(2,app_id);
			int x= ps.executeUpdate();	
			int emp_id=getEmpIdofApp(app_id);
			sql ="update employee set repay_pending=? where emp_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pending);
			ps.setInt(2,emp_id);
			x= ps.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}	
		
	}

	/**
	 * Get email of the supervisor of a BenCo
	 * @return String - email
	 */
	public String getBencoSupEmail() {
		PreparedStatement ps =null;
		ResultSet rs = null;
		ArrayList<int[]> users=new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();)
		{
			//get benco status of all employees
			String sql ="select emp_id, benco from employee";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next())
			{
				int[] user= new int[2];
				user[0]=rs.getInt(1);
				user[1]=rs.getInt(2);
				users.add(user);
			}
			int benco=0;
			for(int[] user: users)//find benco employee id
			{
				if(user[1]==1)
				{
					benco=user[0];
				}
			}
			//get benco's supervisor's id
			sql ="select sup_id from employee where emp_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, benco);
			rs= ps.executeQuery();
			int sup=0;
			while(rs.next())
			{
				sup=rs.getInt(1);
			}
			//get email of supervisor
			sql ="select email from employee where emp_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sup);
			rs= ps.executeQuery();
			String email="";
			while(rs.next())
			{
				email=rs.getString(1);
			}
			return email;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}
		return "";
	}

	/**
	 * Mark application as being on hold
	 * @param app_id - application id
	 */
	public void infoHold(int app_id) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set info_hold=1 where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, app_id);
			int x= ps.executeUpdate();	
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}	
		
	}
}

