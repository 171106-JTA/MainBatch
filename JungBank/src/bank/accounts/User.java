package bank.accounts;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4590648012139772907L;
	
	private static int uid = 0;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	
	
}
