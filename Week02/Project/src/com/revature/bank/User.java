package com.revature.bank;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	protected String _name, 
		_pass; // will be hashed
	protected String _state; // the state of the User
	private Accounts _accounts;
	/**
	 * The active state. NOTE: Use these constants to change state of User.
	 */
	public static final String ACTIVE = "active",
			/**
			 * The logged state. NOTE: Use these constants to change state of User. 
			 */
			LOCKED = "locked",
			/**
			 * The flagged state. NOTE: Use these constants to change state of User.
			 */
			FLAGGED = "flagged",
			/**
			 * The banned state. NOTE: Use these constants to change state of User.
			 */
			BANNED = "banned";
	public static Map<String, String> statesMap;	// make this singleton?

	/**
	 * Constructor
	 * 
	 * Creates a new User with no initial accounts and an initial state of User.LOCKED
	 * This User must be "unlocked" in order to be able to take any actions. 
	 * @param name : the username
	 * @param pass : the passcode this User is supposed to have
	 */
	public User(String name, String pass) {
		this(name, pass, User.LOCKED);
	}
	
	/**
	 * Creates a new User with no initial accounts
	 * @param name : the username
	 * @param pass : the passcode this User is supposed to have
	 * @param state: this User's default state
	 */
	public User(String name, String pass, String state)
	{
		this(name, pass, new Accounts(), state);
	}
	
	/**
	 * Creates a new User
	 * @param name : the username
	 * @param pass : the passcode this User is supposed to have
	 * @param accounts : the Accounts this User has ownership of 
	 * @param state: this User's default state
	 */
	public User(String name, String pass, Accounts accounts, String state)
	{
		// initialize the variables with the parameters...
		_name = name;
		_pass = User.hashString(pass);
		// ...now, let's instantiate everything else...
		_accounts = accounts;
		// ideally, we would have initialized state, but let's pass on that for now.
		_state = state;		
	}
	
	public User(User other)
	{
		this(other._name, other._pass, other._accounts, other._state);
		// copy _pass,_accounts
		_pass = other._pass;
		_accounts = other._accounts;
	}

	/**
	 * 
	 * @return the username of the User
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Changing the username of the User
	 * @param _name
	 */
	public void setName(String _name) {
		this._name = _name;
	}

	/**
	 * 
	 * @return the hashed password of the user
	 * 
	 */
	public String getPass() {
		return _pass;
	}

	/**
	 * Sets the passcode of this User. 
	 * NOTE: Passcode will be hashed!
	 * @param _pass
	 */
	public void setPass(String pass) {
		this._pass = hashString(pass);
//		this._pass = pass;
		
	}
	
	/**
	 * Sets the state of this User. 
	 * @param newState : the new state of this User. 
	 * NOTE: this function is only available to Users and Admins 
	 */
	protected void setState(String newState)
	{
		_state = newState;
	}
	
	/**
	 * 
	 * @return the state of this User. 
	 * 
	 * Users are, as of the time of this JavaDoc, in one of three states:
	 * • active,
	 * • locked, or
	 * • banned
	 * 
	 */
	public String getState() { return _state; }
	
	/**
	 * 
	 * @return The message that is to display upon successful user login. May be the entire next screen.
	 */
	public String getLoginMessage() 
	{
		switch (_state)
		{
			case User.ACTIVE:
				return String.format("Welcome %s!\n", _name);
			case User.LOCKED:
				return "Your account has been locked. An admin must unlock it before you may proceed\n";
			case User.FLAGGED:
				return "Your account has been flagged. It is pending admin inspection";
			case User.BANNED:
				return "You have been banned from this service\n";
		}
		return "";
	}
	
	/**
	 * @return the accounts this User has access to  
	 */
	public Accounts getAccounts() { return _accounts; }
	
	/**
	 * Updates the list of accounts this User has access to
	 * @param newAccounts : Accounts
	 */
	public void setAccounts(Accounts newAccounts)
	{
		_accounts = newAccounts;
	}
	
	/**
	 * creates new Account for this user and appends it to their list of Accounts, iff this User is active
	 * @return the zero-balance account this just created, or null if this User is not active
	 */
	// TODO: unit test this in the case of locked, banned, or flagged account
	public Account createAccount()
	{ 
		System.out.printf("Trying to create new Account on this. _state == %s\n", _state);
		if (_state.equals(User.ACTIVE))
		{
			Account newAccount = new Account();
			_accounts.push(newAccount);
			return newAccount;
		}
		return null;
	}
	
	/**
	 * Hashes Strings using SHA-256 algorithm.
	 * @param str : the unhashed plaintext string
	 * @return hashed String
	 * NOTE: This string will not map str to itself, ever, even if str is already hashed.
	 */
	public static String hashString(String str)
	{
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = digest.digest(str.getBytes("utf8"));
			return Base64.encode(hashedBytes);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	// probably unnecessary
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_accounts == null) ? 0 : _accounts.hashCode());
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		result = prime * result + ((_pass == null) ? 0 : _pass.hashCode());
		result = prime * result + ((_state == null) ? 0 : _state.hashCode());
		return result;
	}

	// also probably unnecessary
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (_accounts == null) {
			if (other._accounts != null)
				return false;
		} else if (!_accounts.equals(other._accounts))
			return false;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		if (_pass == null) {
			if (other._pass != null)
				return false;
		} else if (!_pass.equals(other._pass))
			return false;
		if (_state == null) {
			if (other._state != null)
				return false;
		} else if (!_state.equals(other._state))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [_name=" + _name + ", _pass=" + _pass + ", _state=" + _state + ", _accounts=" + _accounts + "]";
	}
	
	/**
	 * Sets requirements on specified passwords
	 * @param pass : the password to enforce requirements on
	 * @return whether or not password meets requirements
	 */
	public static boolean meetsRequirements(String pass)
	{
		// trim whitespace from either end of password
		pass = pass.trim();
		// for right now, the only requirement is that passwords cannot be blank
		if (pass.equals("")) return false;
		// TODO: implement some more password requirements
		return true;
	}
	
}
