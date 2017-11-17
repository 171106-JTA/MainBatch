package com.revature.bank;

import java.text.NumberFormat;

import com.revature.account.AdminAccount;
import com.revature.account.UserAccount;
import com.revature.account.UserLevel;

public class UserSession extends Bank {
	// constant for admin menu
	// if time, secluded more to admin Session Class
	final public int PROMOTE = 1;
	final public int LOCK = 2;
	final public int UNLOCK = 3;
	final public int BANK = 4;
	// constant for user banking menu
	final public int DEPOSIT = 1;
	final public int WITHDRAW = 2;
	final public int LOANETC = 3;
	final public int QUIT = 4;

	UserAccount currentUser;
	int currentUserIndex;

	UserSession() {
		super();
	}

	/**
	 * Open session.
	 */
	public void openSession() {
		boolean isAdminNow = false;
		System.out.println("Log in to your account. \n\n");
		System.out.print("Username: ");
		setUsername(sc.nextLine());
		System.out.print("Password: ");
		setPassword(sc.nextLine());
		while (!checkLogin(getUsername(), getPassword())) {
			System.out.print("Username: ");
			setUsername(sc.nextLine());
			System.out.print("Password: ");
			setPassword(sc.nextLine());
		}
		System.out.println(getCurrentUser());
		// Calling Admin functions and doing admin things.
		if (currentUser.getUserLevel() == UserLevel.ADMIN) {
			AdminAccount currentAdmin = new AdminAccount(currentUser);
			isAdminNow = true;
			logger.trace("Logging in as admin user");
			AdminSession adminSes = new AdminSession(currentAdmin);
			do {
				adminSes.adminMenu();
				while (adminSes.getChoice() < PROMOTE || adminSes.getChoice() > BANK) {
					System.out.println("Please select number from one of the choice and press /'Enter/'");
					adminSes.adminMenu();
				}
				switch (adminSes.getChoice()) {
				case PROMOTE:
					adminSes.promoteUserPrompt();
					break;
				case LOCK:
					adminSes.lockUserPrompt();
					break;
				case UNLOCK:
					adminSes.unlockUserPrompt();
					break;
				case BANK:
					isAdminNow = false;
				}
			} while (isAdminNow);
		}
		// End of Admining
		do {
			currentUserIndex = findUserIndex(); // to save and find from arraylist
			System.out.println("\n\n\nBanking Menu!\n\n");
			// print current balance in the account
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			System.out.println("You currently have " + formatter.format(currentUser.getBalance()) + " in your account");
			bankingOptions();
			while (getUserChoice() < DEPOSIT || getUserChoice() > QUIT) {
				System.out.println("Please select number from one of the choice and press /'Enter/'");
				bankingOptions();
			}
			switch (getUserChoice()) {
			case DEPOSIT:
				makeDeposit(currentUserIndex);
				break;
			case WITHDRAW:
				makeWithdrawl(currentUserIndex);
				break;
			case LOANETC:
				otherMenus();
				break;
			case QUIT:
				quit();
			}
		} while (bankOn);

	}

	/**
	 * Find user index.
	 *
	 * @return the int
	 */
	private int findUserIndex() {
		for (int i = 0; i < userAccounts.size(); i++) {
			if (userAccounts.get(i).getUsername().equals(currentUser.getUsername())) {
				return i; //userAccounts[i]
			}
		}
		logger.error("How did you even get this far?");
		// unlikely to reach here since user had to log in with username from ArrayList
		return 0;

	}

	/**
	 * Other menus.
	 */
	private void otherMenus() {
		// TODO Auto-generated method stub
		System.out.println("Please vist your nearest bank to speak to a loan advisor or"
				+ "\nContact us at our website jungbank.com");

	}

	/**
	 * subtract amount from the user at given index .
	 *
	 * @param index
	 */
	private void makeWithdrawl(int index) {
		double withdrawlAmt;
		System.out.println("Enter amount to withdraw: $");
		withdrawlAmt = sc.nextDouble();
		sc.nextLine();
		if (currentUser.getBalance() < withdrawlAmt) {
			System.out.println("Not enough money to withdraw.");
			logger.error("Amount failed to withdraw");
		} else {
			currentUser.setBalance(currentUser.getBalance() - withdrawlAmt);
			userAccounts.set(index, currentUser);
			updateUserList();
			logger.info("$" + withdrawlAmt + " has been withdrawn from " + currentUser.getFirstName());
		}

	}

	/**
	 * add amount to the user at given index.
	 *
	 * @param index
	 */
	private void makeDeposit(int index) {
		double depositamt;
		System.out.println("Enter amount to deposit: $");
		depositamt = sc.nextDouble();
		sc.nextLine();
		currentUser.setBalance(currentUser.getBalance() + depositamt);
		userAccounts.set(index, currentUser);
		updateUserList();
		logger.info("$" + depositamt + " has been deposited" + " to " + currentUser.getFirstName());
	}

	/**
	 * Banking options.
	 */
	private void bankingOptions() {
		System.out.println("1) DEPOSIT");
		System.out.println("2) WITHDRAW");
		System.out.println("3) LOAN AND CREDIT");
		System.out.println("4) QUIT");
		// property of superclass
		setUserChoice(sc.nextInt());
		sc.nextLine();

	}

	/**
	 * Check login.
	 *
	 * @param username
	 *            the username
	 * @param pw
	 *            the password
	 * @return true, if successful
	 */
	public boolean checkLogin(String username, String pw) {
		for (UserAccount users : userAccounts) {
			if (users.getUsername().equals(username) && users.getPassword().equals(pw)) {
				if (users.isLocked() == true) {
					System.out.println("This account is actually locked, contact admin for free it.");
					return false;
				}
				setCurrentUser(users);
				return true;
			}
		}
		System.out.println("Username and/or password is not correct");
		return false;
	}

	public UserAccount getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserAccount currentUser) {
		this.currentUser = currentUser;
	}

}
