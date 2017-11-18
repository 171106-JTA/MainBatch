package main.java.com.revature.p1;

/**
 * @author Grantley Morrison
 *         The Class Admin.
 */
@SuppressWarnings("serial")
public class Admin extends User {
	
	/**
	 * Instantiates a new admin.
	 *
	 * @param name
	 *           - the name
	 * @param user
	 *           - the user
	 * @param pass
	 *           - the pass
	 */
	public Admin( String name , String user , String pass ) {
		super(name, user, pass);
		super.setAdmin(true);
	}
	
	/**
	 * Approve a user.
	 *
	 * @param c
	 *           - the user to be approved
	 */
	public void approve(User c) {
		c.setApproved(true);
	}
	
	/**
	 * Promote a user.
	 *
	 * @param c
	 *           - the user to be promoted
	 * @return the promoted user
	 */
	public Admin promote(User c) {
		Admin a = new Admin(c.getName(), c.getUser(), c.getPass());
		return a;
	}
	
	/**
	 * Revoke approved status.
	 *
	 * @param c
	 *           - the user to be revoked
	 */
	public void revoke(User c) {
		c.setApproved(false);
	}
}
