package BankApp;

/**
 * @author Matthew
 * Class models a Customer Account. All Customer accounts are blocked until
 * an Admin unblocks them.
 * Customers can only access the Customer Dashboard. Customers have a Bank
 * Account associated with their login. Customers can deposit and withdraw 
 * money.
 */
public class Customer extends User{
	
	private static final long serialVersionUID = 1L;
	final boolean isAdmin = false;
	String firstName;
	String lastName; 
	String ssn; 
	Account account;
	boolean isBlocked;
	
	public Customer(String firstName, String lastName, String ssn, String username, String password){
		this.firstName = firstName;
		this.lastName = lastName; 
		this.ssn = ssn; 
		this.username = username;
		this.password = password;
		account = null;
		isBlocked = true;
	}
	
	/**
	 * getting for isBlocked
	 * @return
	 * Returns true if the Customer is blocked, and false otherwise
	 */
	public boolean isBlocked() {
		return isBlocked;
	}

	/**
	 * setter for isBlocked
	 * @param isBlocked
	 * boolean that isBlocked will be set to 
	 */
	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	/**
	 * getter from Account
	 * @return
	 * returns Account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * setter for Account
	 * @param account
	 * Account that this.account will be set to
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * getter
	 * @return
	 * 
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * setter
	 * @return
	 * 
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * getter
	 * @return
	 * 
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * setter
	 * @return
	 * 
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * getter
	 * @return
	 * 
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * setter
	 * @return
	 * 
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * overridden toString
	 */
	@Override
	public String toString() {
		return "Customer [Blocked= " + isBlocked + ", firstName=" + firstName + 
				", lastName=" + lastName + ", username=" + username + ", password="
				+ password + "]";
	}
	
	/**
	 * Overwritten equals
	 */
	public boolean equals(Customer cust) {
		boolean result = false;
		if (this.ssn.equals(cust.ssn)){
			result = true;
		}
		return result;
	}

}
