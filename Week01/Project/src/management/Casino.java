/*
 * This mini project is an account manager for the user 
 * accounts of patrons at the Revature Casino. If the 
 * password is known, user accounts can be created an accessed.
 * The administrator of the Revature Casino can lock, unlock, and
 * promote users
 */
package management;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Casino.
 */
public class Casino {

	static ObjectOutputStream oos;
	static ObjectInputStream ois;
	static ArrayList<User> gamblers = new ArrayList<>();
	static Admin administrator = new Admin("1", "2", 10, 999000);
	static Scanner scan = new Scanner(System.in);

	static boolean access = true;

	static int adminA = 0;
	final static Logger logger = Logger.getLogger(Casino.class);


	/**
	 * The main method. runs the Revature Casino console application
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		logger.trace("starting up");
		load();

		if (Casino.gamblers.isEmpty()) {
			Casino.gamblers.add(administrator);
		}
		//Casino.gamblers.clear();
		save(gamblers);
		System.out.println(gamblers);
		// System.out.println(Admin.displayAdmin(administrator));

		System.out.println("Uses secret knock on old alleyway door* ");
		System.out.println();
		System.out.println(" \nBouncer: Whats the password...");

		String answer = scan.next();
		if (answer.equals("new")) {
			newUser();
		} else if (answer.equals("returning")) {
			returningUser();
		}
		// else if (answer.equals("admin")) {
		// middleMenu();
		// }
		else {
			System.out.println("I dont know what you're talking about");
			return;
		}

		accessAdminMenu();

		access = true;
		if (access == true) {
			for (int i = 1; i < gamblers.size(); i++) {
				if ((gamblers.get(i).getBalance() > 1000)) {
					System.out.println("\nsits down at desk as the phone rings*");
					System.out.println("Secretary: Mr.Boss? Here is the paperwork for today\n");
					System.out.println();
					System.out.println("Secretary: " + (gamblers.get(i).getUsername() + " wants a promotion"));
					System.out.println("Secretary: his current balance is " + (gamblers.get(i)).getBalance());
					System.out.println("hmm...*");
					adminMenu(gamblers.get(i));
				} else {
					access = false;
				}
			}
			if (access == false) {
				System.out.println("Exiting application");
			}
		} else {
			access = false;
			System.out.println("Im gonna go home and eat dinner with my wife for once");
			System.out.println("Exiting Application");

		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Save. This method writes the list of gamblers to the file where the Casino
	 * information is stored.
	 * 
	 * @param gamblers
	 *            the list of gamblers which contains all of the current users and
	 *            the administrator
	 */
	public static void save(ArrayList<User> gamblers) {
		try {
			oos = new ObjectOutputStream(new FileOutputStream("CasinoInfo.txt"));
			oos.writeObject(gamblers);
			// System.out.println(gamblers);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (!oos.equals(null)) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Load. this method reads from the file containing Casino information
	 */
	public static void load() {
		try {
			ois = new ObjectInputStream(new FileInputStream("CasinoInfo.txt"));
			readList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Read list. loads the file information into the list of current gamblers
	 */
	public static void readList() {
		try {
			gamblers = ((ArrayList<User>) ois.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * New user. This method creates a new user account. - by asking for a username
	 * and password - making sure that username isnt already taken - and calling the
	 * administrator to approve the account - once approved the new user can then
	 * access the user menu
	 */
	static void newUser() {
		load();
		System.out.println("Bouncer: Whats your codename...");
		String username = scan.next();

		System.out.println("Bouncer: ...and your password? ");
		String password = scan.next();
		User person = new User(username, password, 0, 0);

		load();

		if ((match(person) == (true))) {
			System.out.println("Bouncer: that information is taken man");
		} else {
			System.out.println("Bouncer: Wait right here... gotta check with the boss\n\n");
			adminA = 0;
			adminApproval(administrator);

			if (adminA == 2) {
				gamblers.add(person);
				save(gamblers);
				System.out.println(gamblers.get(gamblers.size() - 1));
				load();
				System.out.println("\nwalks over to the front desk*");
				System.out.println("\nTeller: What can I help you with today?\n");
				userMenu(person);
			}
		}
	}

	/**
	 * Returning user. This method is accessed when an already approved user returns
	 * to the Revature Casino - user information is verified - if correct, access is
	 * granted to the user menu
	 */
	private static void returningUser() {
		load();
		System.out.println("\nBouncer: you've been here before?... You don't look familiar. Whats your code name?");
		String username = scan.next();
		System.out.println("Bouncer: ...and your password...");
		String password = scan.next();

		User person = new User(username, password, 0, 0);
		// System.out.println(gamblers);

		if (match(person) == (true)) {
			System.out.println("\nBouncer: You're good to go!");
			System.out.println("\nwalks over to the front desk*");
			System.out.println("\nTeller: What can I help you with today?\n");
			userMenu(person);
		} else {
			System.out.println("\nBouncer: Something doesnt add up, scram!");
		}
	}

	/**
	 * Access admin menu. once the user is finished with the user menu the
	 * administrator can then choose to access his menu where he can lock users, etc
	 * or he can exit the application
	 * 
	 * @return true, if successful
	 */
	public static boolean accessAdminMenu() {
		// if (adminApproval(administrator) == 2) {
		System.out.println("\nBoss Actions");
		System.out.println("\na) go to office*");
		System.out.println("\nb) go home*");

		String a = scan.next();
		if (a.equals("a")) {
			access = true;
			return access;
			// bring up admin emnu

		} else {
			access = false;
		}
		// }
		return access;

	}

	/**
	 * Admin menu. the admin menu contains all of the options available to the
	 * administrator. Choosing an option will run the next method.
	 * 
	 * the admin options for user x will be shown and if the admin chooses not to
	 * perform any actions of user x (by pressing e), the options for the next user
	 * will be shown.
	 * 
	 * @param user
	 *            the user
	 */
	public static void adminMenu(User user) {

		/*
		 * approval process of new patrons isn't used here because the Revature Casino
		 * likes to ensure that every patron has equal opportunities of gambling aka
		 * take advantage of the inexperience of new patrons.
		 * 
		 * The immediate approval process featured at the Revature Casino accounts for a
		 * significant amount of all profits.
		 */

		System.out.println("a) promote");
		System.out.println("b) lock user account");
		System.out.println("c) unlock user account");
		System.out.println("d) back");
		System.out.println("e) next");

		// System.out.println("b) Grant Loan");
		String choice = scan.next();

		if (choice.equals("a")) {
			increaseRank(user);
		} else if (choice.equals("b")) {
			lockUserAccount(user);
		} else if (choice.equals("c")) {
			unlockUserAccount(user);
		} else if (choice.equals("d")) {
			System.out.println();
			adminMenu(user);
		} else if (choice.equals("e")) {
			System.out.println("...ah I don't feel like it");
			return;
		} else {
			System.out.println("please choose a valid command");
			adminMenu(user);
		}
	}

	/**
	 * Increase rank. This method gives the administrator the option of increasing a
	 * current user's rank if their balance is above 1000
	 * 
	 * @param user
	 *            the user
	 */
	private static void increaseRank(User user) {
		// System.out.println("Secretary: Sir, " + user.getUsername() + " wants a job at
		// the Casino");
		// System.out.println("Mr.Boss: What are his credentials...");

		// while (findUserSave(user).getRank() == 0) {

		if (findUserSave(user).getBalance() > 1000) {
			System.out.println("\nMr.Boss: give him a broom, lets see what he can do");
			findUserSave(user).setRank(user.rank + 1);
			// user.setRank((user.rank) + 1);
			save(gamblers);
		} else {
			System.out.println("..oh wait. I think he's a little too broke to help me run a casino");
			System.out.println("\nMr.Boss: Time is money. If he doesnt have the money... I dont have the time");
			System.out.println("\nMr.Boss: NEXT");
		}
		// }
	}

	/**
	 * Lock user account. this method will lock the user account by reducing his
	 * rank to -1. If this option is choosen for a user who's balance is over 0, the
	 * bouncer will remind the administrator(Boss) that the user is in good standing
	 * 
	 * @param user
	 *            the user
	 */
	private static void lockUserAccount(User user) {
		load();
		if (findUserSave(user).getBalance() < 0) {
			findUserSave(user).setRank(findUserSave(user).rank = -1);
			System.out.println("Bouncer: User account locked");
			System.out.println("You better scram!!!");
			save(gamblers);
		} else {
			System.out.println("Bouncer: I just checked him Boss, he's good");
			adminMenu(user);
		}
	}

	/**
	 * Unlock user account. this method unlocks a user account if the account is
	 * already locked
	 * 
	 * @param user
	 *            the user
	 */
	private static void unlockUserAccount(User user) {
		load();
		if (findUserSave(user).getRank() < 0) {
			findUserSave(user).setRank(findUserSave(user).rank + 1);
			System.out.println("User account unlocked*");
			save(gamblers);
			userMenu(user);
		} else {
			System.out.println("user account already unlocked*");
			adminMenu(user);
		}
	}

	/**
	 * User locked. this method simply checks the current rank of the user
	 * 
	 * @param user
	 *            the user
	 * @return the int
	 */
	public static int userLocked(User user) {
		if (findUserSave(user).getRank() >= 0) {
			return 1;
		}
		return 2;
	}

	/**
	 * User menu. This method contains the options accessible to the user once
	 * inside the casino. Users can view their balance, deposit chips, and withdraw
	 * chips. If a user account is locked he/she may not despoit or withdraw and
	 * chips. If the user types in anything other than an option the user exits the
	 * menu
	 * 
	 * @param user
	 *            the user
	 */
	public static void userMenu(User user) {
		load();
		System.out.println();

		System.out.println("a) Display your Balance(in chips)");
		System.out.println("b) Desposit Chips");
		System.out.println("c) Withdraw Chips from your Reserves");

		String choice = scan.next();

		if (choice.equals("a")) {
			displayBalance(user);
			save(gamblers);
			userMenu(user);
		} else if (choice.equals("b")) {
			depositChips(user);
			save(gamblers);
			userMenu(user);
		} else if (choice.equals("c")) {
			if (userLocked(user) == 1) {
				withdrawChips(user);
				save(gamblers);
			} else {
				System.out.println("User account is locked");
			}
		} else {
			System.out.println("Nevermind, Im gonna go home and warm up some hot pockets.");
			// middleMenu();
		}
	}

	public static void middleMenu() {
		String a = scan.nextLine();
		if ((administrator.getUsername()).equals(a)) {
			String b = scan.nextLine();
			if ((administrator.getPassword()).equals(b)) {
				adminMenu(administrator);
			}
		}

		for (int i = 1; i < gamblers.size(); i++) {
			if ((gamblers.get(i).getBalance() > 1000)) {
				System.out.println("\nsits down at desk as the phone rings*");
				System.out.println("Secretary: Mr.Boss? Here is the paperwork for today\n");
				System.out.println();
				System.out.println("Secretary: " + (gamblers.get(i).getUsername() + " wants a promotion"));
				System.out.println("Secretary: his current balance is " + (gamblers.get(i)).getBalance());
				System.out.println("hmm...*");
				adminMenu(gamblers.get(i));
			}
		}

	}

	/**
	 * Find user save. This method finds the patron in the list of patrons who's
	 * username matches the parameter's username
	 * 
	 * @param user
	 *            the user
	 * @return the user
	 */
	public static User findUserSave(User user) {
		for (User u : Casino.gamblers) {
			if (u.userName.equals(user.userName)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * Display balance. this method simply displays the balance of the user
	 * parameter
	 * 
	 * @param user
	 *            the user
	 */
	private static void displayBalance(User user) {
		load();
		System.out.println("\nchooses display balance*");
		System.out.println("\nBalance: " + findUserSave(user).getBalance());
	}

	/**
	 * Deposit chips. this method adds chips into the user's account
	 * 
	 * @param user
	 *            the user
	 */
	private static void depositChips(User user) {
		load();
		System.out.println("\nTeller: chooses deposit*");
		System.out.println("\nHow many chips would you like to deposit sir?...");
		try {
			int a = scan.nextInt();
			findUserSave(user).setBalance(findUserSave(user).balance + a);
			logger.trace("deposit of: " + a + "from user: " + user.userName);

		} catch (InputMismatchException e) {
			// e.printStackTrace();
			return;
		}
		User savedU = findUserSave(user);
		save(gamblers);

		System.out.println("Balance: " + savedU.getBalance());
	}

	/**
	 * Withdraw chips. this method withdraws chips from the user account
	 * 
	 * @param user
	 *            the user
	 */
	private static void withdrawChips(User user) {
		load();
		System.out.println("\nchoose withdraw chips*");
		System.out.println("\nTeller: How many chips would you like to withdraw sir?...");
		if (scan.hasNextInt()) {
			int a = scan.nextInt();

			if (a > findUserSave(user).balance) {
				System.out.println("Teller: pushes red button*");
				System.out.println("\n\nTeller: ...ah Boss, we got a problem");
				System.out.println("Bouncer: come with me sir");
				findUserSave(user).setBalance(findUserSave(user).balance - a);
				logger.trace("withdrawal of" + a + "from user: " + user.userName);

				// System.out.println(User.displayUser(findUserSave(user)));
				save(gamblers);
				adminMenu(user);
			} else {
				findUserSave(user).setBalance(findUserSave(user).balance - a);
				User savedU = findUserSave(user);

				save(gamblers);
				System.out.println("Balance: " + savedU.getBalance());
				System.out.println("says thanks and walks away*");
				System.out.println("Teller: See you soon");
				System.out.println("\nwhat was that?");
				System.out.println("Teller: Good luck sir");
				System.out.println("squints*...");
				System.out.println("walks over to poker table*");
			}
		} else {
			System.out.println("\nTeller: ...what am I supposed to do with this?");
		}
	}

	/**
	 * Match. this method takes the parameter and looks through the list of patrons
	 * for a user that has the same username and password
	 * 
	 * @param user
	 *            the user
	 * @return true, if successful
	 */
	public static boolean match(User user) {
		String a = user.getUsername();
		String b = user.getPassword();

		for (int i = 0; i < gamblers.size(); i++) {
			// System.out.println(gamblers);
			if (a.equals(gamblers.get(i).getUsername()) && b.equals(gamblers.get(i).getPassword())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Admin approval. this method is used to approve user accounts. The admin must
	 * sign in to approve a user. If the incorrect info is given the bouncer
	 * identifies the intruder. If the correct info is given, the admin can then
	 * approve a user
	 * 
	 * @param admin
	 *            the admin
	 * @return the int
	 */
	public static int adminApproval(Admin admin) {
		if (adminA < 1) {
			System.out.println("Hey Boss, we got this new guy out front... Says he knows Bobbert ");
			scan.nextLine();
			String a = scan.nextLine().trim();
			// System.out.println("\n" + Admin.displayAdmin(administrator));

			load();
			if ((admin.getUsername()).equals(a)) {
				String b = scan.nextLine();
				if ((admin.getPassword()).equals(b)) {
					adminA = 1;
					System.out.println("\nDo you know him");
					approved();
					adminA = 2;
					return adminA;
				}
			}
		} else {
			System.out.println("Wait... how did you get in here?! You're not the boss!");
			adminA = 1;
		}
		return adminA;
	}

	/**
	 * Approved. if the administrator types in yes, true is returned which approves
	 * the user in the above method otherwise the user isn't approved
	 * 
	 * @return true, if successful
	 */
	public static boolean approved() {
		String a = scan.next();
		if (a.equals("yes")) {
			System.out.println("\n\n\n\nLooks like you're good to go, enjoy the Revature Casino!!!");
			return true;
		} else {
			System.out.println("Bouncer: Boss says he doesn't know anyone by that name");
		}
		return false;
	}

}
