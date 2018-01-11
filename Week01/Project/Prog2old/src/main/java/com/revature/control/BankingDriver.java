package com.revature.control;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;

import javax.crypto.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.model.*;
import com.revature.model.Properties;
import com.revature.model.account.UserAccount;
import com.revature.model.request.Request;
import com.revature.throwable.*;
import com.revature.util.JDBC;
import com.revature.view.Console;

/**
 * @author Vaeth
 *
 */

public class BankingDriver {
	private static String user = null;
	private static Model m;
	private Thread setup = new NetworkSetupThread();
	private BufferedReader br;
	private Socket client;
	private ServerSocket server;
	// private static final String NO_LINE_STR = "No line found";

	/**
	 * Constructor of the main class takes user input and interfaces on the Model
	 * for the core logic.
	 * 
	 * @param in
	 *            an input stream to read from
	 * @param out
	 *            an output stream to write to
	 * @param err
	 *            the error output stream
	 */
	public BankingDriver(InputStream in, PrintStream out, PrintStream err) {
		int portID = Properties.PORT_ID;

		// start db networking thread
		setup.start();
		
		// set stdin, stdout, and stderr
		System.setOut(out);
		System.setErr(err);
		br = new BufferedReader(new InputStreamReader(in));

		// hook shutdown to serialize
//		Runtime.getRuntime().addShutdownHook(new ExitThread());

		// Create a new server connection
//		while (true) {
//			try {
//				client = new Socket();
//				client.connect(server.getLocalSocketAddress(), Properties.TIMEOUT);
//			} catch(SocketTimeoutException e) {
//				Console.print(System.err, e);
//			} catch (IOException e) {
//				Console.print(System.err, e);
//			}
//		}

		// try to deserialize object
		// set model to new instance

		// try {
		// m = Model.getInstance();
		// } catch (FatalError e) {
		// Console.print(System.err, e);
		// return;
		// }
	}

	public void initialize() {
		// run til stops, give program chance to cleanup
		while (true) {
			try {
				startup();
			} catch (QuitException e) {
				m.cleanup();
				return;
			} catch (NumberFormatException | InvalidInputException e) {
				Console.print(System.err, e);
			}
		}
	}

	/**
	 * Opens the console prompt to users to login, register, or quit
	 */

	private void startup() throws QuitException, InvalidInputException {
		int select = -1;
		String inStr;
		while (true) {
			Console.printPage(com.revature.model.Properties.PAGE1);
			try {
				inStr = br.readLine();
				if (inStr != null) {
					switch ((select = Integer.parseInt(Character.toString(inStr.charAt(0))))) {
					case 0:
						login(false);
						break;
					case 1:
						login(true);
						break;
					case 2:
						register();
						break;
					case 3:
						throw new QuitException();
					default:
						throw new InvalidInputException(select, Properties.PAGE1.length);
					}
				}
			} catch (LoginException | IOException e) {
				Console.print(System.err, e);
			} catch (LogoutException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Prompts user login
	 * 
	 * @param admin:
	 *            flag for admin login
	 * @throws IOException
	 */
	private void login(boolean admin) throws LoginException, LogoutException, QuitException, IOException {
		boolean login = false;
		String pass;
		System.out.println(com.revature.model.Properties.USER_REQ);
		user = br.readLine();
		System.out.println(com.revature.model.Properties.PASS_REQ);
		pass = br.readLine();
		if (checkAuth(user, pass, admin))
			login = attemptLogin(user, admin);

		while (login) {
			if (admin)
				adminSession();
			else
				userSession();
		}

	}

	/**
	 * Prompts user to attempt registration
	 * 
	 * @throws IOException
	 */
	private void register() throws IOException {
		String pass;
		int age, ssn;
		System.out.println(com.revature.model.Properties.USER_REQ);
		user = br.readLine();
		System.out.println(com.revature.model.Properties.PASS_REQ);
		pass = br.readLine();
		System.out.println(com.revature.model.Properties.AGE_REQ);
		age = Integer.parseInt(br.readLine().replaceAll(" ", ""));
		System.out.println(com.revature.model.Properties.SSN_REQ);
		ssn = Integer.parseInt(br.readLine().replaceAll("[\\s-]", ""));
		if (attemptRegistration(user, pass, age, ssn))
			throw new PendingAccountException(user);
	}

	/**
	 * Auxillary method to call server Model for authentication
	 */
	private boolean checkAuth(String user, String pass, boolean admin) throws InvalidLoginException {
		if (m.checkAuth(user, pass, admin))
			return true;
		throw new InvalidLoginException(user, pass);
	}

	/**
	 * Attempts login from checksums open session to Model if successful
	 * 
	 * @param user:
	 *            the username key to login
	 * @param admin:
	 *            a flag for admin login
	 */
	private boolean attemptLogin(String user, boolean admin) throws LoginException {
		int code;

		if (!admin) {
			code = m.getUserStatus(user);
			switch (code) {
			case com.revature.model.Properties.ACCT_STAT_LOCKED:
				throw new LockedAccountException(user);
			case com.revature.model.Properties.ACCT_STAT_PENDING:
				throw new PendingAccountException(user);
			case com.revature.model.Properties.ACCT_STAT_REJECTED:
				throw new RejectedAccountException(user);
			case com.revature.model.Properties.ACCT_STAT_GOOD:
				break;
			}
		}

		if (!m.beginSession(user))
			throw new UsernameInSessionException(user);
		return true;
	}

	/**
	 * Attempts registration following checksums
	 * 
	 * @param user:
	 *            the username
	 * @param pass:
	 *            the password
	 * @param age:
	 *            the user's age
	 * @param ssn:
	 *            the user's social securty number
	 */
	private boolean attemptRegistration(String user, String pass, int age, int ssn) {
		try {
			if (m.attemptRegistration(user, pass, age, ssn))
				return true;
		} catch (RegistrationException e) {
			Console.print(System.err, e);
		}
		return false;
	}

	/**
	 * Prompt the collection of user to a series of options
	 */
	private void userSession() throws QuitException, LogoutException {
		UserAccount ua;
		String inStr;
		ua = m.getUser(user);
		Console.printFinances(ua.getBalance(), ua.getInterest(), ua.getLimit());
		while (true) {
			try {
				System.out.println(com.revature.model.Properties.QUERY_STR);
				Console.printPage(com.revature.model.Properties.PAGE2_USER);
				inStr = br.readLine();
				if (inStr != null) {
					switch (Integer.parseInt(Character.toString(inStr.charAt(0)))) {
					case 0:
						if (ua == null)
							throw new NoSuchUserException(user);
						if (ua.getStatus() == com.revature.model.Properties.ACCT_STAT_LOCKED)
							throw new LockedAccountException(user);
						requestLoan();
						break;
					case 1:
						if (ua == null)
							throw new NoSuchUserException(user);
						if (ua.getStatus() == com.revature.model.Properties.ACCT_STAT_LOCKED)
							throw new LockedAccountException(user);
						checkDeposit();
						break;
					case 2:
						if (ua == null)
							throw new NoSuchUserException(user);
						if (ua.getStatus() == com.revature.model.Properties.ACCT_STAT_LOCKED)
							throw new LockedAccountException(user);
						withdraw();
						break;
					case 3:
						requestRollback();
						break;
					case 4:
						closeUser();
						break;
					case 5:
						logout();
						break;
					case 6:
						throw new QuitException();
					}
				}
			} catch (NumberFormatException | DebtException | OutstandingLoanException | IOException e) {
				Console.print(System.err, e);
			}
		}
	}

	/**
	 * Request a transaction rollback from the admins
	 * 
	 * @throws IOException
	 */
	private void requestRollback() throws IOException {
		String inStr;
		Console.printMap(m.getTransactions(user));
		Console.print(Properties.ROLLBACK_QUERY_STR, Level.OFF_INT);
		inStr = br.readLine();
		if (inStr != null) {
			try {
				for (String s : inStr.split(" ")) {
					if (m.sendRequest(user, Integer.parseInt(s)))
						Console.print(Properties.REQ_SUCC, Level.INFO_INT);
				}
			} catch (InvalidInputException | BankingException e) {
				Console.print(System.err, e);
			}
		}
	}

	/**
	 * Execute a withdraw transaction
	 * 
	 * @throws IOException
	 */
	private void withdraw() throws IOException {
		Object o;
		String inStr;
		Console.print(Properties.AMOUNT_QUERY_STR, Level.OFF_INT);
		inStr = br.readLine();
		if (inStr == null)
			return;
		double amount = Double.parseDouble(inStr);
		String msg;
		Console.print(Properties.MSG_QUERY_STR, Level.OFF_INT);
		msg = br.readLine();
		if ((o = m.processTransaction(user, -amount, msg, false)) != null) {
			Console.print(Properties.TRANS_SUCC + ": " + o, Level.INFO_INT);
			Console.printFinances(m.getUser(user).getBalance(), m.getUser(user).getInterest(),
					m.getUser(user).getLimit());
		}
	}

	/**
	 * Prompt user for deposit destination
	 * 
	 * @throws IOException
	 */
	private void checkDeposit() throws IOException {
		String inStr;
		while (true) {
			try {
				System.out.println(com.revature.model.Properties.QUERY_STR);
				Console.printPage(com.revature.model.Properties.PAGE3_DEPO);
				inStr = br.readLine();
				if (inStr == null)
					return;
				switch (Integer.parseInt(Character.toString(inStr.charAt(0)))) {
				case 0:
					deposit(-1);
					break;
				case 1:
					auxillaryDeposit();
					break;
				case 2:
					return;
				case 3:
					throw new QuitException();
				}
			} catch (InvalidInputException | NumberFormatException | IndexOutOfBoundsException e) {
				Console.print(System.err, e);
			}
		}
	}

	/**
	 * Auxillary method for paying to a loan instead of account
	 * 
	 * @throws IOException
	 */
	private void auxillaryDeposit() throws InvalidInputException, IOException {
		int val;
		String inStr;
		Console.printMap(m.getLoans(user));
		Console.print(Properties.LOANS_QUERY_STR, Level.OFF_INT);
		inStr = br.readLine();
		if (inStr == null)
			return;
		val = Integer.parseInt(inStr.split(" ")[0]);
		deposit(val);
	}

	/**
	 * Execute a deposit transaction
	 * 
	 * @throws IOException
	 */
	private void deposit(int id) throws IOException {
		Object o;
		String inStr;
		Console.print(Properties.AMOUNT_QUERY_STR, Level.OFF_INT);
		inStr = br.readLine();
		if (inStr == null)
			return;
		double amount = Double.parseDouble(inStr);
		String msg;
		Console.print(Properties.MSG_QUERY_STR, Level.OFF_INT);
		msg = br.readLine();
		if (msg == null)
			return;
		if (id < 0) {
			if ((o = m.processTransaction(user, amount, msg, false)) != null) {
				Console.print(Properties.TRANS_SUCC + " " + o, Level.INFO_INT);
				Console.printFinances(m.getUser(user).getBalance(), m.getUser(user).getInterest(),
						m.getUser(user).getLimit());
			}
		} else if ((o = m.processLoan(user, id, amount, msg, false)) != null) {
			Console.print(Properties.LOAN_SUCC + " " + o, Level.INFO_INT);
			Console.printFinances(m.getUser(user).getBalance(), m.getUser(user).getInterest(),
					m.getUser(user).getLimit());
		}
	}

	/**
	 * Request a loan for approval by admins
	 * 
	 * @throws IOException
	 */
	private void requestLoan() throws IOException {
		String inStr;
		Console.print(Properties.AMOUNT_QUERY_STR, Level.OFF_INT);
		inStr = br.readLine();
		if (inStr == null)
			return;
		double amount = Double.parseDouble(inStr);
		if (m.sendRequest(user, amount))
			Console.print(Properties.REQ_SUCC, Level.INFO_INT);
	}

	/**
	 * Prompt admins for a list of options
	 */
	private void adminSession() throws QuitException, LogoutException {
		String inStr;
		while (true) {
			try {
				System.out.println(com.revature.model.Properties.QUERY_STR);
				Console.printPage(com.revature.model.Properties.PAGE2_ADMIN);
				inStr = br.readLine();
				if (inStr != null) {
					switch (Integer.parseInt(Character.toString(inStr.charAt(0)))) {
					case 0:
						lockUsers();
						break;
					case 1:
						unlockUsers();
						break;
					case 2:
						viewReqs();
						break;
					case 3:
						restoreUsers();
						break;
					case 4:
						promoteUsers();
						break;
					case 5:
						closeUsers();
						break;
					case 6:
						Console.printMap(m.getUsers(-1));
						break;
					case 7:
						logout();
						break;
					case 8:
						throw new QuitException();

					}
				}
			} catch (NumberFormatException | InvalidInputException | BankingException | IOException e) {
				Console.print(System.err, e);
			}
		}
	}

	/**
	 * End session and logout
	 * 
	 * @throws IOException
	 */
	private void logout() throws InvalidInputException, LogoutException, IOException {
		String inStr;
		System.out.println(com.revature.model.Properties.CONFIRM_LOGOUT);
		inStr = br.readLine();
		if (inStr.length() == 0)
			throw new InvalidInputException();
		switch (inStr.charAt(0)) {
		case 'y':
			m.endSession(user);
			throw new LogoutException();
		case 'n':
			return;
		default:
			throw new InvalidInputException();
		}
	}

	/**
	 * Close account
	 * 
	 * @throws IOException
	 */
	private void closeUser() throws InvalidInputException, LogoutException, IOException {
		String inStr;
		Map<?, ?> map = (Map<?, ?>) m.queryUsers(Properties.PROT_GET, null);
		UserAccount ua = (UserAccount) map.get(user);
		try {
			if (ua == null)
				throw new NoSuchUserException(user);
			if (ua.getStatus() != Properties.ACCT_STAT_GOOD)
				throw new LockedAccountException(user);
		} catch (NoSuchUserException | LockedAccountException e) {
			Console.print(System.err, e);
			throw new LogoutException();
		}
		if (ua.getBalance() < 0) {
			throw new DebtException();
		}
		if (!ua.getLoans().isEmpty()) {
			throw new OutstandingLoanException();
		}

		System.out.println(com.revature.model.Properties.CONFIRM_CLOSE);
		inStr = br.readLine();
		if (inStr.length() == 0)
			throw new InvalidInputException();
		switch (inStr.charAt(0)) {
		case 'y':
			m.endSession(user);
			m.queryUsers(com.revature.model.Properties.PROT_REMOVE, user);
			throw new LogoutException();
		case 'n':
			return;
		default:
			throw new InvalidInputException();
		}
	}

	/**
	 * View user requests pending admin action
	 * 
	 * @throws IOException
	 */
	private void viewReqs() throws QuitException, IOException {
		Request r;
		String inStr;
		int select;
		int[] size = { 0 };
		while (true) {
			r = m.getRequest(size, user);
			if (r == null)
				break;
			Console.print(r, Level.OFF_INT);
			Console.print(Properties.COUNT + size[0], Level.OFF_INT);
			Console.printPage(com.revature.model.Properties.PAGE3_REQS);
			inStr = br.readLine();
			if (inStr != null) {
				switch (select = Integer.parseInt(Character.toString(inStr.charAt(0)))) {
				case 0:
					m.processRequest(r, com.revature.model.Properties.PROT_APPROVE, user);
					break;
				case 1:
					m.processRequest(r, com.revature.model.Properties.PROT_REJECT, user);
					break;
				case 2:
					m.processRequest(r, Properties.PROT_CANCEL, user);
					return;
				case 3:
					m.processRequest(r, Properties.PROT_CANCEL, user);
					throw new QuitException();
				default:
					m.processRequest(r, Properties.PROT_CANCEL, user);
					throw new InvalidInputException(select, com.revature.model.Properties.PAGE3_REQS.length - 1);
				}
			}
		}
	}

	/**
	 * Lock user account from logging inStr
	 * 
	 * @throws IOException
	 */
	private void lockUsers() throws EmptySetException, IOException {
		String inStr;
		Map<?, ?> map = m.getUsers(Properties.PROT_UNLOCK);
		Console.printMap(map);
		if (map.isEmpty())
			throw new EmptySetException();
		Console.print(Properties.LOCK_QUERY_STR, Level.OFF_INT);
		inStr = br.readLine();
		if (inStr != null) {
			for (String s : inStr.split(" ")) {
				try {
					m.tryLock(s);
				} catch (NumberFormatException | NoSuchUserException | DuplicateAdminException
						| RestrictedAdminException e) {
					Console.print(System.err, e);
				}
			}
		}
	}

	/**
	 * Unlock user accounts for logging inStr
	 * 
	 * @throws IOException
	 */
	private void unlockUsers() throws EmptySetException, IOException {
		Map<?, ?> map = m.getUsers(Properties.PROT_LOCK);
		String inStr;
		Console.printMap(map);
		Console.print(Properties.UNLOCK_QUERY_STR, Level.OFF_INT);
		if (map.isEmpty())
			throw new EmptySetException();
		inStr = br.readLine();
		if (inStr != null) {
			for (String s : inStr.split(" ")) {
				try {
					m.tryUnlock(s, user);
				} catch (NumberFormatException | NoSuchUserException | IllegalAdminException e) {
					Console.print(System.err, e);
				}
			}
		}
	}

	/**
	 * Restore units from the rejected list
	 * 
	 * @throws IOException
	 */
	private void restoreUsers() throws EmptySetException, IOException {
		Map<?, ?> map;
		String inStr;
		map = m.getUsers(Properties.PROT_REJECT);
		Console.printMap(map);
		if (map.isEmpty())
			throw new EmptySetException();
		Console.print(Properties.RESTORE_QUERY_STR, Level.OFF_INT);
		inStr = br.readLine();
		if (inStr != null) {
			for (String s : inStr.split(" ")) {
				try {
					m.tryRestore(s);
				} catch (NumberFormatException | NoSuchUserException | DuplicateAdminException
						| RestrictedAdminException e) {
					Console.print(System.err, e);
				}
			}
		}

	}

	/**
	 * Promote users to admin
	 * 
	 * @throws IOException
	 */
	private void promoteUsers() throws EmptySetException, IOException {
		Map<?, ?> map = m.getUsers(Properties.PROT_PROMOTE);
		Console.printMap(map);
		String inStr;
		if (map.isEmpty())
			throw new EmptySetException();
		Console.print(Properties.PROMOTE_QUERY_STR, Level.OFF_INT);
		inStr = br.readLine();
		if (inStr != null) {
			for (String s : inStr.split(" ")) {
				try {
					m.tryPromote(s);
				} catch (NumberFormatException | NoSuchUserException | DuplicateAdminException e) {
					Console.print(System.err, e);
				}
			}
		}
	}

	/**
	 * Close user accounts
	 * 
	 * @throws IOException
	 */
	private void closeUsers() throws EmptySetException, CloseAdminException, IOException {
		Map<?, ?> map;
		String inStr;
		map = m.getUsers(Properties.PROT_CLOSE);
		Console.printMap(map);
		if (map.isEmpty())
			throw new EmptySetException();
		Console.print(Properties.CLOSE_QUERY_STR, Level.OFF_INT);
		inStr = br.readLine();
		if (inStr != null) {
			for (String s : inStr.split(" ")) {
				m.tryClose(s);
			}
		}
	}

	/**
	 * ExitThread: The exit thread runs a shutdown hook to serialize the program
	 * before JVM shutdown. This guarantees serialization in the event of an
	 * uncaught exception or error
	 */
	private class ExitThread extends Thread {
		boolean access = true;

		public void run() {
			synchronized (this) {
				if (!access) {
					return;
				}
				access = false;
			}

			try {
				m.serialize();
			} catch (InvalidKeyException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException
					| IllegalBlockSizeException | InvalidParameterSpecException | IOException
					| InvalidAlgorithmParameterException e) {
				Throwable t = new SerializationError(e);
				Console.print(System.err, t, Level.FATAL_INT);
			} finally {
				Console.print(Properties.QUIT, Level.INFO_INT);
			}
		}
	}

	public static void main(String[] args) {
		if (args.length > 0) {
			if (args[0].contains("-debug"))
				com.revature.model.Properties.DEBUG = true;
		}

		Logger.getRootLogger().removeAppender("stdout");

		// default to stdin for user input
		new BankingDriver(System.in, System.out, System.err);
	}
	
	// start JDBC/Connection pool on separate thread to avoid main thread hanging forever
	private class NetworkSetupThread extends Thread {
		
		public void run() {
			JDBC.getinstance();
		}
	}
}
