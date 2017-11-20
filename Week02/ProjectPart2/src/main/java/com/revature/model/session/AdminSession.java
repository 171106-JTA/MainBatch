package com.revature.model.session;

import java.util.List;
import java.util.Scanner;

import com.revature.dao.jBankDAO;
import com.revature.dao.jBankDAOImpl;
import com.revature.model.account.User;
import com.revature.model.account.UserLevel;

public class AdminSession {
	private boolean isAdminNow;
	private int adminChoice;
	private Scanner sc = new Scanner(System.in);
	// constant for admin menu
	final private int PROMOTE = 1;
	final private int LOCK = 2;
	final private int APPROVE = 3;
	final private int BANK = 4;

	public void getSession() {
		setAdminNow(true);
		do {
			adminMenu();
			while (getAdminChoice() < PROMOTE || getAdminChoice() > BANK) {
				System.out.println("Please select number from one of the choice and press /'Enter/'");
				adminMenu();
			}
			

			switch (getAdminChoice()) {
			case PROMOTE:
				promoteUserPrompt();
				break;
			case LOCK:
				lockUserPrompt();
				break;
			case APPROVE:
				approveUserPrompt();
				break;
			case BANK:
				setAdminNow(false);
				break;
			}
		} while (isAdminNow());

	}

	private void approveUserPrompt() {
		System.out.println("List of users with pending approval:");
		jBankDAO dao = new jBankDAOImpl();
		List<User> myUsers = dao.getAllUser();
		for(User user : myUsers) {
			if(user.isApproved() == false) {
				System.out.println("> username:" + user.getUsername());
			}
		}
		String userInput;
		System.out.println("> Any user you want to approve? (Type 'quit' to exit):");
		userInput = sc.nextLine().toLowerCase();
		if(userInput.equals("quit")) {
			return;
		}
		dao.approveUser(userInput);
		
	}

	private void lockUserPrompt() {
		System.out.println("> Which user would you like to lock/unlock?");
		String luName = sc.nextLine();
		jBankDAO dao = new jBankDAOImpl();

		User promotee = dao.grabUser(luName);
		while (promotee == null) {
			System.out.println("> Which user that \"EXIST\" would you like to lock/unlock?");
			luName = sc.nextLine();
			promotee = dao.grabUser(luName);
		}
		if (promotee.getUserLevel().equals(UserLevel.ADMIN)) {
			System.out.println("Can't touch other admin");
			return;
		}
		if (promotee.isLocked())
			System.out.println("User is currently locked");
		else
			System.out.println("User is not locked");
		System.out.println("> Enter lock or unlock");
		String userInput = sc.nextLine().toLowerCase();
		if (userInput.equals("lock") && promotee.isLocked() == true) {
			System.out.println("It's already locked");
			return;
		} else if ((userInput.equals("unlock") && promotee.isLocked() == false)) {
			System.out.println("It's already unlocked");
			return;
		} else {
			if (userInput.equals("lock")) {
				dao.lockUser(promotee, true);
			}
			else {
				dao.lockUser(promotee, false);
			}
		}
	}

	private void promoteUserPrompt() {

		System.out.println("> Which user would you like to promote/demote?");
		String prName = sc.nextLine();
		jBankDAO dao = new jBankDAOImpl();

		User promotee = dao.grabUser(prName);
		while (promotee == null) {
			System.out.println("> Which user that \"EXIST\" would you like to promote/demote?");
			prName = sc.nextLine();
			promotee = dao.grabUser(prName);
		}

		if (promotee.getUserLevel().equals(UserLevel.ADMIN)) {
			System.out.println("Can't touch other admin");
			return;
		}
		System.out.println("> This user's level is currently: " + promotee.getUserLevel());
		System.out.println("> Enter promotion title: (BASIC,VIP,ADMIN)");
		String userInput = sc.nextLine().toUpperCase();
		System.out.println(dao.promoteUser(promotee, userInput) + " user has been updated");

	}

	/**
	 * Prints Admin menu listings and takes input for selection.
	 */
	private void adminMenu() {
		System.out.println("> Now logged in as admin.");
		System.out.println("> What would you like to do?");
		System.out.println("1> PROMOTE/DEMOTE USER");
		System.out.println("2> LOCK/UNLOCK USER OUT");
		System.out.println("3> APPROVE USER");
		System.out.println("4> GO TO BANKING MENU");
		System.out.print("> ");
		setAdminChoice(sc.nextInt());
		sc.nextLine();
	}

	public boolean isAdminNow() {
		return isAdminNow;
	}

	public void setAdminNow(boolean isAdminNow) {
		this.isAdminNow = isAdminNow;
	}

	public int getAdminChoice() {
		return adminChoice;
	}

	public void setAdminChoice(int adminChoice) {
		this.adminChoice = adminChoice;
	}
}
