package revature.trms.bean;

import java.sql.Date;

public class Request {
	private String emp_id;
	double money;
	int pending, req_id;
	float grade1, grade2;
	Date req_date;
	
	public Request(String emp_id, int req_id, double money, int pending, float grade1, float grade2, Date req_date) {
		this.emp_id = emp_id;
		this.req_id = req_id;
		this.money = money;
		this.pending = pending;
		this.grade1 = grade1;
		this.grade2 = grade2;
		this.req_date = req_date;
	}
	
	//getters

	public String getEmp_id() {
		return emp_id;
	}

	public int getReq_id() {
		return req_id;
	}

	public double getMoney() {
		return money;
	}

	public int getPending() {
		return pending;
	}

	public float getGrade1() {
		return grade1;
	}

	public float getGrade2() {
		return grade2;
	}

	public Date getReq_date() {
		return req_date;
	}

	//setters
	
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public void setReq_id(int req_id) {
		this.req_id = req_id;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public void setGrade1(float grade1) {
		this.grade1 = grade1;
	}

	public void setGrade2(float grade2) {
		this.grade2 = grade2;
	}

	public void setReq_date(Date req_date) {
		this.req_date = req_date;
	}
	
}
