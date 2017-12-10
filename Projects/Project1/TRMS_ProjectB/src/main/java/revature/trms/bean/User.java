package revature.trms.bean;

public class User {
	private String emp_id, pass, fname, lname, email, address;
	private int ssn, phone;
	
	public User(String emp_id, String pass, String fname, String lname, String email, String address, int ssn, int phone) {
		this.emp_id = emp_id;
		this.pass = pass;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.address = address;
		this.ssn = ssn;
		this.phone = phone;
	}
	
	/*
	 * getters
	 */

	public String getEmp_id() {
		return emp_id;
	}

	public String getPass() {
		return pass;
	}

	public String getFname() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	public String getEmail() {
		return email;
	}

	public int getSsn() {
		return ssn;
	}

	public int getPhone() {
		return phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	/*
	 * setters
	 */

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
}
