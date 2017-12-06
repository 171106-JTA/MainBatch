package com.revature.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.TRMSDao;

public class Service {
	private static TRMSDao dao= new TRMSDao();
	public static String[] checkLoginstring(String u, String p) {
		ArrayList<String[]> users= dao.getLogins();
		for(String[] s: users)
		{
			System.out.println("session: " +u + " || " + p);
			System.out.println(s[1] + " || " + s[2]);
			if(u.equals(s[1]))
			{
				if(p.equals(s[2]))
				{
					return s;
				}
			}
		}
		return null;
	}
	
	public static boolean checkLogin(String u, String p) {
		ArrayList<String[]> users= dao.getLogins();
		for(String[] s: users)
		{
			System.out.println("session: " +u + " || " + p);
			System.out.println(s[1] + " || " + s[2]);
			if(u.equals(s[1]))
			{
				if(p.equals(s[2]))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean canApprove(String username) {
		int id= dao.getId(username);
		int supStatus= dao.checkSuperior(id);
		int[] bencoStatus= dao.isBencoOrChair(id);
		if(supStatus==1||bencoStatus[0]==1||bencoStatus[1]==1)
			return true;
		else
			return false;
	}
	
	public static boolean canApprove(int id) {
		int supStatus= dao.checkSuperior(id);
		int[] bencoStatus= dao.isBencoOrChair(id);
		if(supStatus==1||bencoStatus[0]==1||bencoStatus[1]==1)
			return true;
		else
			return false;
	}
	
	public static void getUserInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		int id= (int)session.getAttribute("id");
		String[] empInfo = dao.getEmpInfo(id);
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		if (empInfo[0] != null) {
			String myXml = "<root><user><fname>"+empInfo[0]+"</fname><lname>"+empInfo[1]+"</lname><repay_awarded>"+empInfo[2]+"</repay_awarded><repay_pending>"+empInfo[3]+"</repay_pending></user></root>";
			out.println(myXml);
		}
		else{
			out.println("<root></root>");
		}
	}

	public static boolean approvalSetup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session= request.getSession();
		int id= (int)session.getAttribute("id");
		int[] position= dao.isBencoOrChair(id);
		int superior= dao.checkSuperior(id);
		ArrayList<Integer> apps= new ArrayList<>();
		if(position[0]==1)//if benco
		{
			System.out.println("BENCO");
			apps=bencoSetup();
		}
		else if(position[1]==1)
		{
			System.out.println("Chair");
			int dept=dao.getDepartment(id);
			apps=chairSetup(dept, id);
		}
		else if(superior==1)
		{
			System.out.println("Superior but not chair");
			apps=supSetup(id);
		}
		else {
			return false;
		}
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		if(apps.size()!=0)			
		{
			String myXml = "<root>";
			
			for (int app : apps) {
				String[] appInfo= dao.getAppInfo(app);
				System.out.println("appInfo: "+appInfo);
				myXml += "<application><app_id>" + appInfo[0] + "</app_id>" + "<emp_id>" + appInfo[1] + "</emp_id>"
						+ "<fname>" + appInfo[2] + "</fname>" + "<lastname>" + appInfo[3] + "</lastname>"
						+ "<ev_descr>" + appInfo[4] + "</ev_descr>"+ "<ev_cost>" + appInfo[5] + "</ev_cost>"
						+ "<ev_type_id>" + appInfo[6] + "</ev_type_id>" + "<repay_awarded>" + appInfo[7] + "</repay_awarded>"
						+ "<repay_pending>"+appInfo[8]+"</repay_pending></application>";
			}
			myXml += "</root>";
			out.println(myXml);
		}
		else {
			System.out.println("hello");
			out.println("<root></root>");
		}
		
		return true;		
	}
	
	//doesn't have found problem
	public static ArrayList<Integer> bencoSetup() {
		ArrayList<int[]> apps= dao.getAppsStatus();
		ArrayList<int[]> remove= new ArrayList<>();
		for(int[] app: apps)
		{
			if(!(app[3]==0 && app[2]==1))//check if not benco approved and if it is chair approved
			{
				remove.add(app);
			}
		}	
		for(int[] app: remove)
			apps.remove(app);
		ArrayList<Integer> bencoApps= new ArrayList<>();
		for(int[] app: apps)
			bencoApps.add(app[0]);
		return bencoApps;
	}
	
	//fixed found problem
	public static ArrayList<Integer> chairSetup(int dept, int id) {
		ArrayList<int[]> apps= dao.getAppsStatus();
		ArrayList<int[]> remove= new ArrayList<>();
		System.out.println("Start count= " + apps.size());
		for(int[] app: apps)
		{
			System.out.println("chair check (pass =0)  =" + app[2] );
			if(app[2]!=0)//check if approved by chair don't check sup in case chair is sup
			{
				remove.add(app);
			}
		}
		
		for(int[] app: remove)
			apps.remove(app);
		remove= new ArrayList<>();
		System.out.println("After filter those approved by chair: " + apps.size() );
		for(int[] app:apps)
		{
			int emp_id=dao.getEmpIdofApp(app[0]);			
			int app_dept=dao.getAppDept(emp_id);
			System.out.println("department compare " + app_dept + " " + dept );
			if(app_dept!=dept)//check if application is in department
				remove.add(app);
		}
		for(int[] app: remove)
			apps.remove(app);
		
		ArrayList<Integer> chairApps= new ArrayList<>();
		for(int[] app: apps)
			chairApps.add(app[0]);
		System.out.println("Final: " + chairApps.size() );
		ArrayList<Integer> supCheck=supSetup(id);
		for(int app: supCheck)
		{
			boolean check=false;
			for(int chairApp: chairApps)
			{
				if(chairApp==app)
				{
					check=true;
					break;
				}
			}
			if(check==false)
				chairApps.add(app);
		}
		return chairApps;
	}
	
	//works
	public static ArrayList<Integer> supSetup(int id) {
		ArrayList<int[]> apps= dao.getAppsStatus();
		ArrayList<int[]> remove= new ArrayList<>();
		for(int[] app: apps)
		{
			
			if(app[1]!=0)//check if has not approved by supervisor
			{
				remove.add(app);
			}
		}
		for(int[] app: remove)
			apps.remove(app);
		System.out.println(apps.size());
		remove= new ArrayList<>();
		for(int[] app:apps)
		{
			int emp_id=dao.getEmpIdofApp(app[0]);
			int sup=dao.getAppSup(emp_id);
			System.out.println("emp_id"+emp_id);
			System.out.println("Sup Check (user app): " + id + "  " + sup);
			if(sup!=id)//check if user is supervisor
				remove.add(app);
		}
		for(int[] app: remove)
			apps.remove(app);
		System.out.println(apps.size());
		ArrayList<Integer> supApps= new ArrayList<>();
		for(int[] app: apps)
			supApps.add(app[0]);
		System.out.println(supApps.size());
		return supApps;
	}

	public static void approveApp(int approved_id, int user_id) {
		int emp_id=dao.getEmpIdofApp(approved_id);//emp_id of app
		int[] app_status= dao.getAppStatus(approved_id);//approval status of app
		int[] user_status=dao.isBencoOrChair(user_id);//user position check
		if(user_status[0]==1)//if benco
		{
			int sup_id=dao.getAppSup(emp_id);
			if(app_status[1]==0)
				dao.supApprove(approved_id);
			else
				dao.bencoApprove(approved_id);
		}
		else if(user_status[1]==1)//if user is chair
		{
			if(app_status[1]==0)//if user not supervisor approved
			{
				if(dao.getDepartment(emp_id)==dao.getDepartment(user_id))//if user in department
				{
					dao.chairApprove(approved_id);
				}
				else//if not supervisor approved and not in department
				{
					dao.supApprove(approved_id);
				}
			}
			else
			{
				dao.chairApprove(approved_id);
			}
		}
		else//not benco or chair and thus sup
		{
			dao.supApprove(approved_id);
		}
	}

	public static void rejectApp(int reject_id, int user_id, String reason) {
		int emp_id=dao.getEmpIdofApp(reject_id);//emp_id of app
		int[] app_status= dao.getAppStatus(reject_id);//approval status of app
		int[] user_status=dao.isBencoOrChair(user_id);//user position check
		if(user_status[0]==1)//if benco
		{
			int sup_id=dao.getAppSup(emp_id);
			if(app_status[1]==0)
				dao.supReject(reject_id, reason);
			else
				dao.bencoReject(reject_id, reason);
		}
		else if(user_status[1]==1)//if user is chair
		{
			if(app_status[1]==0)//if user not supervisor approved
			{
				if(dao.getDepartment(emp_id)==dao.getDepartment(user_id))//if user in department
				{
					dao.chairReject(reject_id, reason);
				}
				else//if not supervisor approved and not in department
				{
					dao.supReject(reject_id, reason);
				}
			}
			else
			{
				dao.chairReject(reject_id, reason);
			}
		}
		else//not benco or chair and thus sup
		{
			dao.supReject(reject_id, reason);
		}
	}

	public static void AutoApprove() {
		Date newest=new Date(2010,12,20);
		
		Date curDate = new Date(Calendar.getInstance().getTime().getTime()); 
		ArrayList<String[]> apps_status=dao.getAppStatusWithDate();
		for(String[] app:apps_status)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Date oldDate;
			try {
				String[] getDate=app[4].split(" ");
				System.out.println(getDate[0]);
				oldDate = sdf.parse(app[4]);
				if(getDateDiff(oldDate,newest)>0)
				{
					newest=oldDate;
				}
				if(app[1].equals("0") && getDateDiff(oldDate,curDate)>7)
				{
					dao.supApprove(Integer.parseInt(app[0]));
				}
				else if(app[2].equals("0") && getDateDiff(oldDate,curDate)>7)
				{
					dao.chairApprove(Integer.parseInt(app[0]));
				}
				else if(app[3].equals("0") && getDateDiff(oldDate,curDate)>7)
				{
					//EMAIL BENCO SUP **CHECKLIST**
				}
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		//Get newest year and current year if different, reset awarded
		String newestDate=newest.toString();
		String[] splitNew=newestDate.split("-");
		String splitCur=curDate.toString().split("-")[0];
		if(!splitCur.equals(splitNew[0]))
		{
			dao.resetAward();
		}
	}
	
	public static long getDateDiff(Date date1, Date date2) {
	    long difference = date2.getTime() - date1.getTime();
        difference=difference/1000/60/60/24;//millisecods to days
	    return difference;
	}

	public static void yearCheck() {
		// TODO Auto-generated method stub
		
	}
}
