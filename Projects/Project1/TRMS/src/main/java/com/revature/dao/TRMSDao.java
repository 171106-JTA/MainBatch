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

public class TRMSDao {
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
				log[0]=rs.getString(1);
				log[1]=rs.getString(2);
				log[2]=rs.getString(3);
				
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
	
	public int checkSuperior(int id) {
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
			String sql ="SELECT sup_id FROM employee where sup_id=?";
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
			
			if(logins.size()!=0)
					return 1;
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return 0;
	}

	public int getId(String username) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int id= -1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT emp_id FROM login where emp_username=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				id=rs.getInt(1);
				System.out.println(id);
			}			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return id;
	}
	
	public int getEmpIdofApp(int app_id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		int id= -1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="SELECT emp_id FROM repay_app where app_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, app_id);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				id=rs.getInt(1);
				System.out.println(id);
			}			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return id;
	}
	
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
			String sql ="SELECT benco, chair FROM emp_status where emp_id=?";
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
		finally {
			close(ps);
			close(rs);
		}
		return status;
	}
	
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
		finally {
			close(ps);
			close(rs);
		}
		return info;
	}
	public void addApplication(HttpServletRequest request, int id, String reqDate) {
		int x=0;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String s= new String();
		ArrayList<String> users= new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection();){
			//app repay
			String sql ="INSERT INTO repay_app (emp_id) values('" + id +"')";
			ps = conn.prepareStatement(sql);
			x=ps.executeUpdate(sql);
			//app status
			sql="INSERT INTO app_status (urgent, sup_apr, chair_apr, benco_apr, passed, recent_date)"
					+" values(0,0,0,0,0,?)";//default values
			ps= conn.prepareStatement(sql);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			ps.setDate(1,date);
			x=ps.executeUpdate();
			//app info
			sql="INSERT INTO app_info (ev_date, ev_descr, ev_time, ev_cost, ev_type_id, ev_grade_id)"
					+ "values(?,?,?,?,?,?)";//default values
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
			x=ps.executeUpdate();
			
			//update repay_pending
			int pending=getPending(id);
			pending+=Integer.parseInt(request.getParameter("cost"));
			sql="update employee set repay_pending=? where emp_id=?";
			ps= conn.prepareStatement(sql);
			ps.setInt(1, pending);
			ps.setInt(2, id);
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
	}
	
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
			String sql ="SELECT * FROM app_status";
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
				log[4]=rs.getString(7);//date	
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
	
	public ArrayList<int[]> getApps_Emp() {
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
			String sql ="SELECT * FROM app_status";
			ps = conn.prepareStatement(sql);
			rs= ps.executeQuery();		
			while(rs.next())
			{
				log= new int[3];
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
		finally {
			close(ps);
			close(rs);
		}
		return dept;
	}
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
		finally {
			close(ps);
			close(rs);
		}
		return dept;
	}

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
		finally {
			close(ps);
			close(rs);
		}
		return sup_id;		
	}
	
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
		finally {
			close(ps);
			close(rs);
		}
		return dept;		
	}

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
		finally {
			close(ps);
			close(rs);
		}
		return info;
		
	}

	public String[] getAppUserInfo(int app_id) {
		PreparedStatement ps =null;
		ResultSet rs = null;
		String[] info= new String[10];
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
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
				
			}			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
			close(rs);
		}
		return info;
		
	}
	
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

	public void bencoApprove(int approved_id) {
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
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally {
			close(ps);
		}
	}

	public void supReject(int reject_id, String reason) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set sup_apr=-1, chair_apr=-1, benco_apr=-1, recent_date=? where app_id=?";
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

	public void chairReject(int reject_id, String reason) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set chair_apr=-1, benco_apr=-1, recent_date=? where app_id=?";
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

	public void bencoReject(int reject_id, String reason) {
		PreparedStatement ps =null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql ="update app_status set benco_apr=-1, recent_date=? where app_id=?";
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
				int app_id=rs.getInt(1);
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
				file[0]=rs.getString(1);
				file[1]=rs.getString(2);
				file[2]=rs.getString(3);
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
}

