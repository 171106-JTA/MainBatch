package com.revature.bank;

import java.util.Scanner;

import com.revature.account.AdminAccount;
import com.revature.account.UserLevel;

public class AdminSession extends UserSession {
	
	private int choice;
	private AdminAccount currentAdmin;

	Scanner sc = new Scanner(System.in);

	/**
	 * To lock out user
	 */
	public void lockUserPrompt() {
		System.out.println("Enter the username of user to be locked:");
		String lockee = sc.nextLine();
		boolean found = false;
		for (int i = 0; i < userAccounts.size(); i++) {
			if (userAccounts.get(i).getUsername().equals(lockee)) {
				userAccounts.get(i).setLocked(true);
				updateUserList();
				logger.info("User " + lockee + " has been Locked ");
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("User " + lockee + " does not exists\n\n\n\n\n");
		}
	}

	/**
	 * To unlock user.
	 */
	public void unlockUserPrompt() {
		System.out.println("Enter the username of user to be unlocked:");
		String lockee = sc.nextLine();
		boolean found = false;
		for (int i = 0; i < userAccounts.size(); i++) {
			if (userAccounts.get(i).getUsername().equals(lockee)) {
				userAccounts.get(i).setLocked(false);
				updateUserList();
				logger.info("User " + lockee + " has been unlocked ");
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("User " + lockee + " does not exists\n\n\n\n\n");
		}
	}

	/**
	 * Promote user prompt.
	 */
	public void promoteUserPrompt() {
		System.out.println("Enter the username of the user to be promoted to admin");
		// if extra time, implement checkUserExist function that returns
		// index int to eliminate repetition.
		String promotee = sc.nextLine();
		boolean found = false;
		for (int i = 0; i < userAccounts.size(); i++) {
			if (userAccounts.get(i).getUsername().equals(promotee)) {
				userAccounts.get(i).setUserLevel(UserLevel.ADMIN);
				updateUserList();
				logger.trace("User has been promoted to Admin");
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("User " + promotee + " does not exists\n\n\n\n\n");
		}

	}

	/**
	 * Prints Admin menu listings and takes input for selection.
	 */
	public void adminMenu() {
		System.out.println("\n\nYou are logged in to admin account\n\n"
				+ "===================================================================================\n\n");
		System.out.println("What would you like to do?");
		System.out.println("1) PROMOTE USER TO ADMIN.");
		System.out.println("2) LOCK USER OUT");
		System.out.println("3) UNLOCK USER");
		System.out.println("4) GO TO NORMAL BANKING MENU");
		setChoice(sc.nextInt());
		sc.nextLine();

	}

	/**
	 * Instantiates a new admin session.
	 *
	 * @param admin the admin
	 */
	// Getters and Setters and constructors
	public AdminSession(AdminAccount admin) {
		super();
		this.currentAdmin = admin;
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	public AdminAccount getCurrentAdmin() {
		return currentAdmin;
	}

	public void setCurrentAdmin(AdminAccount currentAdmin) {
		this.currentAdmin = currentAdmin;
	}

}
