package revature.trms.dao;

import java.util.ArrayList;

import revature.trms.bean.Message;
import revature.trms.bean.Request;
import revature.trms.bean.User;

public interface DaoInterface {
	
	public void create_user(String firstname, String lastname, String emp_id, String password, String email, String address, int ssn, int phone);//create a new user by inputing id, password, first name, last name
	public void make_reimbursement_request(String emp_id, double money);//user applies for a reimbursement request
	public double get_score(int req_id);//returns the total score from grading
	public boolean isApproved(int req_id);//tells if the reimbursement is approved or not
	public void verify(int req_id);//sets approved column to 0 for denied, 1 for pending, and 2 for approved
	public void getStatus(String emp_id);//get user's reimbursement granted and reimbursement remaining.
	public boolean login_emp(String emp_id, String password);//employee login
	public boolean login_sv(String emp_id, String password);//super visor login
	public boolean login_dh(String emp_id, String password);//dept head login
	public boolean login_bc(String emp_id, String password);//benco login
	public void insert_info(String emp_id, int ssn, String phone, String address, String email);//inserts personal information on the employee
	public ArrayList<Request> getRequests();//gets a list of requests
	public User getUser(String emp_id);//gets a employee user
	public double getAvail(String emp_id);//returns the amount available
	
	public void gradeSV(int req_id, double grade);//supervisor inserts grade for reimbursement
	public void gradeDH(int req_id, double grade);//dept head inserts grade for reimbursement
	
	public void sendMessage(String sender, String receiver, String message);
	public ArrayList<Message> getMessages();
	
	public void approve(int req_id);
	public void deny(int req_id);
	public ArrayList<String> getHeads();
}
