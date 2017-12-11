package com.revature.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.revature.dao.TRMSDao;
/**
 * Inbetween layer that connects the servlets and dao
 * @author Xavier Garibay
 *
 */
public class Service {
	private static TRMSDao dao= new TRMSDao();//dao to be used throughout
	final static private Logger logger= Logger.getLogger(Service.class);//logs important actions
	
	
	/**
	 * Check if username and password match a pair in the database
	 * @param u - input username
	 * @param p - input password
	 * @return boolean - whether or not a match was found
	 */
	public static boolean checkLogin(String u, String p) {
		ArrayList<String[]> users= dao.getLogins();//get all username and passwords
		for(String[] s: users)
		{
			if(u.equals(s[1]))//if username matches
			{
				if(p.equals(s[2]))//if password matches
				{
					logger.info(u + " has logged in to their account");
					return true;//successful login
				}
			}
		}
		return false;//no match found
	}
	
	/**
	 * Check if user can approve applications in any way
	 * @param username - username
	 * @return boolean - whether or not user can approve
	 */
	public static boolean canApprove(String username) {
		int id= dao.getId(username);//get employee id
		boolean supStatus= dao.checkSuperior(id);//check if anyone's superior
		int[] bencoStatus= dao.isBencoOrChair(id);//get benco and chair status
		if(supStatus||bencoStatus[0]==1||bencoStatus[1]==1)//if supervisor, benco or chair
			return true;
		else
			return false;
	}
	
	/**
	 * Same as above except based on employee id
	 * @param id - employee id
	 * @return whether or not user can approve
	 */
	public static boolean canApprove(int id) {
		boolean supStatus= dao.checkSuperior(id);
		int[] bencoStatus= dao.isBencoOrChair(id);
		if(supStatus||bencoStatus[0]==1||bencoStatus[1]==1)
			return true;
		else
			return false;
	}
	
	/**
	 * Gets the name and money information of current user
	 * @param request
	 * @param response
	 * @throws IOException
	 */
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

	/**
	 * Get information of applications which can be approved by current user and converting to xml
	 * @param request
	 * @param response
	 * @return boolean - check to ensure user has authority to approve
	 * @throws IOException
	 */
	public static boolean approvalSetup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session= request.getSession();
		int id= (int)session.getAttribute("id");
		
		int[] position= dao.isBencoOrChair(id);
		boolean superior= dao.checkSuperior(id);
		ArrayList<Integer> apps= new ArrayList<>();
		if(position[0]==1)//if benco
		{
			apps=bencoSetup();
		}
		else if(position[1]==1)//if chair
		{
			int dept=dao.getDepartment(id);
			apps=chairSetup(dept, id);
		}
		else if(superior)//if supervisor
		{
			apps=supSetup(id);
		}
		else {//if not eligible to approve
			return false;
		}
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		if(apps.size()!=0)//if at least one application can be approved			
		{
			String myXml = "<root>";
			
			for (int app : apps) {
				String[] appInfo= dao.getAppInfo(app);//get application information to populate xml
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
			out.println("<root></root>");
		}
		
		return true;		
	}
	
	/**
	 * Get applications not approved by benco but approved by chair
	 * @return ArrayList<Integer> - list of application id's 
	 */
	private static ArrayList<Integer> bencoSetup() {
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
	
	/**
	 * Get applications approvable by the chair of a specific department, also getting their direct underling's applications
	 * @param dept - user department
	 * @param id - user's employee id
	 * @return ArrayList<Integer> - list of application id's
	 */
	private static ArrayList<Integer> chairSetup(int dept, int id) {
		ArrayList<int[]> apps= dao.getAppsStatus();//get all applications information
		ArrayList<int[]> remove= new ArrayList<>();
		for(int[] app: apps)
		{
			if(app[2]!=0)//check if approved by chair, don't check sup in case chair is sup
			{
				remove.add(app);
			}
		}
		
		for(int[] app: remove)
			apps.remove(app);
		remove= new ArrayList<>();
		for(int[] app:apps)//for all applications left, check if in user's department
		{
			int emp_id=dao.getEmpIdofApp(app[0]);//get employee id of creator
			int app_dept=dao.getDepartment(emp_id);//get department of creator
			if(app_dept!=dept)//check if application is in department
				remove.add(app);
		}
		for(int[] app: remove)
			apps.remove(app);
		ArrayList<Integer> chairApps= new ArrayList<>();
		for(int[] app: apps)
			chairApps.add(app[0]);//Applications approvable by chair
		ArrayList<Integer> supCheck=supSetup(id);//get applications where user is direct supervisor
		for(int app: supCheck)//for each application where user is supervisor
		{
			boolean check=false;
			for(int chairApp: chairApps)//check if application is also in chair approvable applications
			{
				if(chairApp==app)
				{
					check=true;
					break;
				}
			}
			if(check==false)//add applications that are in supApps but not chairApps to chairApps
				chairApps.add(app);
		}
		return chairApps;
	}
	
	/**
	 * Get application id's of applications approvable by user as direct supervisor
	 * @param id - user's employee id
	 * @return ArrayList<Integer> - list of application id's
	 */
	private static ArrayList<Integer> supSetup(int id) {
		ArrayList<int[]> apps= dao.getAppsStatus();//get all applications
		ArrayList<int[]> remove= new ArrayList<>();
		for(int[] app: apps)
		{
			
			if(app[1]!=0)//check if each has not approved by supervisor
			{
				remove.add(app);
			}
		}
		for(int[] app: remove)
			apps.remove(app);
		remove= new ArrayList<>();
		for(int[] app:apps)//check if each has user as direct supervisor
		{
			int emp_id=dao.getEmpIdofApp(app[0]);
			int sup=dao.getAppSup(emp_id);
			if(sup!=id)//check if user is supervisor
				remove.add(app);
		}
		for(int[] app: remove)
			apps.remove(app);
		ArrayList<Integer> supApps= new ArrayList<>();
		for(int[] app: apps)
			supApps.add(app[0]);//app id's
		return supApps;
	}

	/**
	 * Approve application in a manner based on user status and application approval status
	 * @param approved_id - application id
	 * @param user_id - user id
	 * @param reason - for benco approval as money can be administered
	 */
	public static void approveApp(int approved_id, int user_id, String reason) {
		int emp_id=dao.getEmpIdofApp(approved_id);//emp_id of app
		int[] app_status= dao.getAppStatus(approved_id);//approval status of app
		int[] user_status=dao.isBencoOrChair(user_id);//user position check
		if(user_status[0]==1)//if benco
		{
			int sup_id=dao.getAppSup(emp_id);
			if(app_status[1]==0) {//if application not approved by supervisor
				dao.supApprove(approved_id);//approve as supervisor
				logger.info("App #" + approved_id+ " has been approved by its superior");
			}
			else {//if application approved by supervisor
				boolean check=checkRepay(approved_id);//check if repayment would exceed $1000 limit and require a reason
				if(check==true)//if within limits no reason needed
				{
					dao.bencoApprove(approved_id, "Within limit");//approve as benco
					logger.info("App #" + approved_id+ " has been approved by a BenCo");
				}
				else//if exceeds limits
				{
					if(reason!="")//if reason is provided
					{
						dao.bencoApprove(approved_id, reason);//approve as benco
						logger.info("App #" + approved_id+ " has been approved by a BenCo for more than limit");
					}
					//don't approve if no reason given
				}
				checkComplete(approved_id);//check if repayment should occur
			}
		}
		else if(user_status[1]==1)//if user is chair
		{
			if(app_status[1]==0)//if application not supervisor approved, potential skip of supervisor approval
			{
				if(dao.getDepartment(emp_id)==dao.getDepartment(user_id))//if user in department
				{
					dao.chairApprove(approved_id);//approve as chair
					logger.info("App #" + approved_id+ " has been approved by a chair");
				}
				else//if not supervisor approved and not in department
				{
					dao.supApprove(approved_id);//approve as supervisor
					logger.info("App #" + approved_id+ " has been approved by its superior");
				}
			}
			else//if supervisor approved
			{
				dao.chairApprove(approved_id);//approve as chair
				logger.info("App #" + approved_id+ " has been approved by a chair");
			}
		}
		else//not benco or chair and thus sup
		{
			dao.supApprove(approved_id);//approve as supervisor
			logger.info("App #" + approved_id+ " has been approved by its superior");
		}
	}

	/**
	 * Check if repayment would be over $1000 limit
	 * @param approved_id - application id
	 * @return boolean - whether or not limit is exceeded
	 */
	private static boolean checkRepay(int approved_id) {
		String[] info= dao.getAppInfo(approved_id);
		int cost=Integer.parseInt(info[5]);
		int type=Integer.parseInt(info[6]);
		int awarded=Integer.parseInt(info[7]);
		if(type==1)//adjust cost based on event type
			cost=(int)(cost*.8);
		else if(type==2)
			cost=(int)(cost*.6);
		else if(type==3)
			cost=(int)(cost*.75);
		else if(type==5)
			cost=(int)(cost*.9);
		else if(type==6)
			cost=(int)(cost*.3);
		int limit=1000-awarded;
		if(cost>limit)
			return false;
		else
			return true;
	}

	/**
	 * Reject application based on user status and application approval status
	 * @param reject_id - application id
	 * @param user_id - user employee id
	 * @param reason - reason to reject
	 */
	public static void rejectApp(int reject_id, int user_id, String reason) {
		int emp_id=dao.getEmpIdofApp(reject_id);//emp_id of app
		int[] app_status= dao.getAppStatus(reject_id);//approval status of app
		int[] user_status=dao.isBencoOrChair(user_id);//user position check
		if(user_status[0]==1)//if benco
		{
			if(app_status[1]==0){//if not approved by benco
				dao.bencoReject(reject_id, reason);//reject as benco
				logger.info("App #" + reject_id+ " has been recected by a BenCo");
			}
			else {
				dao.supReject(reject_id, reason);//reject as supervisor 
				logger.info("App #" + reject_id+ " has been recected by its supervisor");
			}
		}
		else if(user_status[1]==1)//if user is chair
		{
			if(app_status[1]==0)//if application not supervisor approved
			{
				if(dao.getDepartment(emp_id)==dao.getDepartment(user_id))//if user in department
				{
					dao.chairReject(reject_id, reason);//reject as chair
					logger.info("App #" + reject_id+ " has been recected by a chair");
				}
				else//if not supervisor approved and not in department
				{
					dao.supReject(reject_id, reason);//reject as supervisor
					logger.info("App #" + reject_id+ " has been recected by its superior");
				}
			}
			else//supervisor approved
			{
				dao.chairReject(reject_id, reason);//reject as chair
				logger.info("App #" + reject_id+ " has been recected by a chair");
			}
		}
		else//not benco or chair and thus sup
		{
			dao.supReject(reject_id, reason);//reject as supervisor
		}
	}

	/**
	 * Auto-approve any applications one level if they have not been reacted to within a week, at BenCo level email BenCo's supervisor.
	 * If current year is different than most recent year in database reset repayment awarded for all users
	 */
	public static void AutoApprove() {
		Date newest=new Date(2010,12,20);//temp date
		
		Date curDate = new Date(Calendar.getInstance().getTime().getTime());//get current date 
		ArrayList<String[]> apps_status=dao.getAppStatusWithDate();//get all applications statuses
		for(String[] app:apps_status)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//SQL date format
			
			Date oldDate;
			try {
				String[] getDate=app[4].split(" ");//split date from database to take out time
				oldDate = sdf.parse(app[4]);//set application date as date object
				if(getDateDiff(oldDate,newest)>0)//find most recent date in database
				{
					newest=oldDate;
				}
				if(app[1].equals("0") && getDateDiff(oldDate,curDate)>7)//if date is older than a weak and not approved by supervisor 
				{
					dao.supApprove(Integer.parseInt(app[0]));//approve application as supervisor
					logger.info("App #" + Integer.parseInt(app[0])+ " has been approved by its superior automatically");
				}
				else if(app[2].equals("0") && getDateDiff(oldDate,curDate)>7)//if date is older than a weak and not approved by chair
				{
					dao.chairApprove(Integer.parseInt(app[0]));//approve application as chair
					logger.info("App #" + Integer.parseInt(app[0])+ " has been approved by a chair automatically");
				}
				else if(app[3].equals("0") && getDateDiff(oldDate,curDate)>7)//if date is older than a weak and not approved by BenCo
				{
					emailBencoSup();//email supervisor of a benco
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
			logger.info("Awarded money has been reset due to year change");
		}
	}
	
	private static void emailBencoSup(){
		String to = dao.getBencoSupEmail();

	      // Sender's email ID needs to be mentioned
	      String from = "trms@gmail.com";

	      // Assuming you are sending email from localhost
	      String host = "localhost";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("BenCo Failure");

	         // Now set the actual message
	         message.setText("The current Benefit Coordinator has failed to respond to a waiting reimbursement request");

	         // Send message
	         Transport.send(message);
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
		
	}

	/**
	 * Get the difference between two dates in terms of days
	 * @param date1 - older date
	 * @param date2 - newer date
	 * @return long - difference between date in days
	 */
	private static long getDateDiff(Date date1, Date date2) {
	    long difference = date2.getTime() - date1.getTime();
        difference=difference/1000/60/60/24;//millisecods to days
	    return difference;
	}

	/**
	 * Output all applications made by user in the form of xml
	 * @param response
	 * @param id - user id
	 */
	public static void getUserApps(HttpServletResponse response, int id) {
		ArrayList<Integer> apps= dao.getUserApps(id);//get id's of all apps made by the user
		response.setContentType("text/xml");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(apps.size()!=0)			
			{
				String myXml = "<root>";
				
				for (int app : apps) {
					String[] appInfo= dao.getAppUserInfo(app);//get information of app to populate xml
					String evDate=appInfo[2].split(" ")[0];
					String rcDate=appInfo[9].split(" ")[0];
					myXml += "<application><app_id>" + appInfo[0] + "</app_id>" + "<ev_descr>" + appInfo[1] + "</ev_descr>"
							+ "<ev_date>" + evDate + "</ev_date>" + "<ev_cost>" + appInfo[3] + "</ev_cost>"
							+ "<ev_type>" + appInfo[4] + "</ev_type>"+ "<sup_apr>" + appInfo[5] + "</sup_apr>"
							+ "<chair_apr>" + appInfo[6] + "</chair_apr>" + "<benco_apr>" + appInfo[7] + "</benco_apr>"
							+ "<passed>"+appInfo[8]+"</passed><recent_date>"+ rcDate +"</recent_date><info_hold>"+ appInfo[10] +"</info_hold></application>";
				}
				myXml += "</root>";
				out.println(myXml);
			}
			else {
				out.println("<root></root>");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Output file information of user's files in the form of an xml
	 * @param request
	 * @param response
	 */
	public static void appFileSetup(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session= request.getSession();
		int id= (int)session.getAttribute("id");
		ArrayList<Integer> appIDs= dao.getUserApps(id);//get all app id's of users apps
		response.setContentType("text/xml");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(appIDs.size()!=0)			
			{
				String myXml = "<root>";			
				for (int appID : appIDs) {//for each app id
					ArrayList<String[]> files=dao.getAppFiles(appID);//get all files attached to app id
					for(String[] file:files)
					{
						myXml += "<file><id>" + file[0] + "</id><name>" + file[1] + "</name><desc>" + file[2] + "</desc></file>";
					}
				}
				myXml += "</root>";
				out.println(myXml);
			}
			else {
				out.println("<root></root>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Output files approvable as grade or presentation as an xml string. Approval based on user and account approval information
	 * @param request
	 * @param response
	 */
	public static void GradeSetup(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session= request.getSession();
		int id= (int)session.getAttribute("id");
		int[] status= dao.isBencoOrChair(id);
		ArrayList<String[]> apps=dao.getGrades();
		ArrayList<String[]> accept= new ArrayList<>();
		if(status[0]==1)//user is benco
		{
			for(String[] app: apps)
			{
				if(app[6].equals("0"))//not passed
				{
					accept.add(app);
				}
				else if(Integer.parseInt(app[8])==id && app[7].equals("0") && app[6].equals("1") && app[9].equals("0"))
				{//if benco is sup, the app is passed, not approved and not completed
					accept.add(app);
				}
			}
		}
		else {//not benco
			for(String[] app: apps)
			{
				if(app[6].equals("1") && app[7].equals("0") && app[9].equals("0"))//check if passed but not approved or completed 
				{
					accept.add(app);
				}
			}
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			if(accept.size()!=0)			
			{
				String myXml = "<root>";			
				for (String[] app : accept) {//for each app id
					myXml += "<grade><id>" + app[0] + "</id><fname>" + app[1] + "</fname><lname>" + app[2] + "</lname><filename>"
							+ app[3]+"</filename><passgrade>"+app[4]+"</passgrade><presentation>"+app[5]+"</presentation><passed>"
							+app[6]+ "</passed><approved>"+app[7]+"</approved></grade>";
				}
				myXml += "</root>";
				out.println(myXml);
			}
			else {
				out.println("<root></root>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Approve file as a passing grade or valid presentation of an application based on user and account info
	 * @param request
	 * @param response
	 */
	public static void GradeApprove(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session= request.getSession();
		int id= (int)session.getAttribute("id");
		int[] status= dao.isBencoOrChair(id);
		String[] app=dao.getGrade(Integer.parseInt(request.getParameter("activeID")));
		int app_id=Integer.parseInt(app[0]);
		ArrayList<String[]> accept= new ArrayList<>();
		if(status[0]==1)//user is benco
		{
			if(app[6].equals("0"))//not passed
			{
				dao.BencoGradeApprove(app_id);//mark as passing
				logger.info("App #" + app_id + " has been been marked as passing");
			}
			else if(Integer.parseInt(app[8])==id && app[7].equals("0") && app[6].equals("1") && app[5].equals("1"))
			{//check if user is supervisor, if application is passed but not approved, and requires a presentation
				dao.SupGradeApprove(app_id);//mark as having valid presentation
				logger.info("App #" + app_id + " has been been marked as having presented");
			}
		}
		else {// user is not benco
			if(app[6].equals("1") && app[7].equals("0") && app[5].equals("1"))//check if passed but not approved and needs presentation 
			{
				dao.SupGradeApprove(app_id);//mark as having a valid presentation
				logger.info("App #" + app_id + " has been been marked as having presented");
			}
		}
		checkComplete(app_id);//Check if repayment is necessary
	}

	/**
	 * Check if repayment is necessary, doing so if necessary and marking application as completed
	 * @param app_id - application id
	 */
	private static void checkComplete(int app_id) {
		int[] completeCheck= dao.CompleteInfo(app_id);
		if(completeCheck[4]==0)//if not completed or on hold
		{
			if(completeCheck[3]==1)//if needs presentation approval
			{
				if(completeCheck[2]==1)//if approved
				{
					int emp_id=dao.getEmpIdofApp(app_id);
					dao.payUser(emp_id, app_id);//pay user and mark as completed
					logger.info("App #" + app_id+ " is completed");
				}
			}
			else//if does not need presentation approval
			{
				if(completeCheck[1]==1)//if passed
				{
					int emp_id=dao.getEmpIdofApp(app_id);
					dao.payUser(emp_id, app_id);//pay user and mark as completed
					logger.info("App #" + app_id+ " is completed");
				}
			}
		}
		
	}

	/**
	 * Adjust cost of application based on input and event type, adjusting requestor's pending repayment appropriately
	 * @param request
	 * @param response
	 */
	public static void newCost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session= request.getSession();
		int id= (int)session.getAttribute("id");
		int[] benco=dao.isBencoOrChair(id);
		if(benco[0]==1)//only if user is benco
		{
			int app_id= Integer.parseInt(request.getParameter("activeID"));
			String[] app_info=dao.getAppInfo(app_id);
			int newPay=Integer.parseInt(request.getParameter("newCost"));
			int newCost=0;
			int type=Integer.parseInt(app_info[6]);//event type
			//adjust payment based on event type to put in new cost
			if(type==1)
				newCost=(int)(newPay/.8);
			else if(type==2)
				newCost=(int)(newPay/.6);
			else if(type==3)
				newCost=(int)(newPay/.75);
			else if(type==4)
				newCost=newPay;
			else if(type==5)
				newCost=(int)(newPay/.9);
			else if(type==6)
				newCost=(int)(newPay/.3);
			//get current information
			int cost=Integer.parseInt(app_info[5]);		
			int pending=Integer.parseInt(app_info[8]);
			//adjust pending
			pending-=cost;
			pending+=newCost;
			//update employee to changes
			dao.updateCost(app_id, newCost, pending);
			logger.info("App #" + app_id+ " has been updated. Repaymen now $"+newPay);
		}
		
	}

	/**
	 * Mark application as requiring more files from an approver
	 * @param request
	 * @param response
	 */
	public static void infoHold(HttpServletRequest request, HttpServletResponse response) {
		int app_id=Integer.parseInt(request.getParameter("activeID"));
		dao.infoHold(app_id);
		logger.info("App #" + app_id+ " has been put on hold until file input");
		
		
	}

	public static void specificAppSetup(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session= request.getSession();
		int id= (int)session.getAttribute("application");
		ArrayList<String[]> files= dao.getAppFiles(id);//get all files attached to app
		response.setContentType("text/xml");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(files.size()!=0)			
			{
				String myXml = "<root>";			
				for(String[] file:files)
				{
					myXml += "<file><id>" + file[0] + "</id><name>" + file[1] + "</name><desc>" + file[2] + "</desc></file>";
				}
				myXml += "</root>";
				out.println(myXml);
			}
			else {
				out.println("<root></root>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
