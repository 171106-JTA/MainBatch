package com.revature.p1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.log4j.Logger;

/**
 * @author Grantley Morrison
 *
 */
// Only one person can be logged in at a time.
// After a login, sets currUser to the last logged in user
// *Serialization
// Input Checking
// File Exceptions
// JUnit Testing
// Liberal commenting

public class UI {
	public static ObjectOutputStream oos;
	public static ObjectInputStream ois;
	static Admin admin = new Admin("Adminstrator", "admin", "default");
	final static Logger logger = Logger.getLogger(UI.class);

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Welcome to Grantley's Bank!");
		UI ui = new UI();
		ui.users = ui.read(); // make things for exceptions
		if (ui.users.isEmpty()) {
			ui.users.add(admin);
		}
		//ui.users.set(0, admin);
		admin.approve(admin);
		ui.save(ui.users);
		int select;
		// System.out.println(ui.users.toString());
		while (true) {
			ui.scan = new Scanner(System.in);
			System.out.println("\nPlease select an option below!");
			System.out.println("\n========================================");
			System.out.println("1. Login\n2. Create Account\n3. Withdraw\n4. Deposit\n5. Balance\n6. Log Out\n7. Exit");
			System.out.println("========================================");
			try {
				select = ui.scan.nextInt();
			} catch (InputMismatchException e) {
				logger.warn("Exception occured: " + e.getMessage());
				select = 0;
			}
			switch (select) {
			case 1:
				if (ui.currUser == null) {
					ui.loginAcc();
					break;
				} else {
					// Better error message...
					System.err.println("Login failed. Another User is Logged in.");
					break;
				}

			case 2:
				// SHouldn't be able to create an account if logged in
				ui.createAcc();
				ui.save(ui.users);
				break;

			case 3:			
				if (ui.currUser != null) {
					try {
						System.out.print("$");
						double x = ui.scan.nextDouble();
						ui.currUser.withdraw(x);
						logger.trace(ui.currUser.toString());
						ui.save(ui.users);
						break;
					} catch (InputMismatchException e) {
						logger.warn("Exception occured: " + e.getMessage());
						System.err.println("Invalid input. Please try again");
						break;
					}
				} else {
					System.err.println("You are not currently logged in. Please Login.");
					break;
				}
				
			case 4:
				if (ui.currUser != null) {
					try {
						System.out.print("$");
						double x = ui.scan.nextDouble();
						ui.currUser.deposit(x);
						logger.trace(ui.currUser.toString());
						ui.save(ui.users);
						break;
					} catch (InputMismatchException e) {
						logger.warn("Exception occured: " + e.getMessage());
						System.err.println("Invalid input. Please try again");
						break;
					}

				} else {
					System.err.println("You are not currently logged in. Please Login.");
					break;
				}
			case 5:
				if (ui.currUser != null) {
					System.out.printf("$%.2f", ui.currUser.getBal());
					break;
				} else {
					System.err.println("You are not currently logged in. Please Login.");
					break;
				}

			case 6:
				if (ui.currUser != null) {
					ui.currUser = null;
					System.out.println("Succesfully logged out!");
					break;
				} else {
					System.out.println("No user is logged in.");
					break;
				}

			case 7:
				// Statements
				System.out.println("Have a nice day!");
				ui.scan.close();
				ui.save(ui.users);
				return; // optional
			case 8:
				if (ui.currUser == null) {
					System.err.println("Invalid selection.");
					break;
				}
				if (ui.currUser.isAdmin()) {
					if (ui.users.size() == 1) {
						System.out.println("User list is empty.");
						break;
					} else {
						int i;
						System.out.println();
						for (User x : ui.users) {
							System.out.println(ui.users.indexOf(x) + ": " + x.toString());
						}
						System.out.println("Select a user to modify.");
						try {
							i = ui.scan.nextInt();
							if ((i >= ui.users.size()) || (i < 0)) {
								System.err.println("Invalid selection. Please try again.");
								break;
							}
						} catch (InputMismatchException e) {
							logger.warn("Exception occured: " + e.getMessage());
							System.err.println("Invalid input. Please try again.");
							break;
						}
						System.out.println("Would you like to approve/revoke access or promote an account? (a/r/p)");
						ui.scan.nextLine();
						String choice = ui.scan.nextLine().toLowerCase();
						// Select Approve/Revoke/Promote
						if (choice.equals("a")) {
							ui.users.get(i).setApproved(true);
							System.out.println(
									ui.users.get(i).getUser() + " Approval Status: " + ui.users.get(i).isApproved());
							ui.save(ui.users);
							break;
						} else if (choice.equals("r")) {
							ui.users.get(i).setApproved(false);
							System.out.println(
									ui.users.get(i).getUser() + " Approval Status: " + ui.users.get(i).isApproved());
							ui.save(ui.users);
							break;
						} else if (choice.equals("p")) {
							ui.users.set(i, ((Admin) ui.currUser).promote(ui.users.get(i)));
							ui.users.get(i).setApproved(true);
							System.out
									.println(ui.users.get(i).getUser() + " Admin Status: " + ui.users.get(i).isAdmin());
							System.out.println(
									ui.users.get(i).getUser() + " Approval Status: " + ui.users.get(i).isApproved());
							ui.save(ui.users);
							break;
						}

						else {
							System.err.println("Invalid selection.");
							break;
						}
					}
				} else {
					// Error message doesn't need to be so detailed
					System.err.println("Invalid selection.");
					break;
				}
			default: // Optional
				System.err.println("Invalid selection.");
				break;
			}
		}

	}

	String login;
	String name;
	User currUser;
	transient String pass;
	ArrayList<User> users = new ArrayList<>();
	Scanner scan;

	// Create a new object from passed in from user input
	// Check to see if username is taken and handle that

	/**
	 * Creates the acc.
	 */
	private void createAcc() {
		System.out.println("Please input your desired username.");
		scan.nextLine();
		login = scan.nextLine().trim();
		for (User x : users) {
			// System.out.println(x.getUser()); //test
			if (login.toLowerCase().equals(x.getUser().toLowerCase())) {
				System.err.println("Username already taken.\nPlease try again with a new username.");
				return;
			}
		}
		System.out.println("Please input your name.");
		name = scan.nextLine().trim();
		System.out.println("Please input your password.");
		pass = scan.nextLine().trim();
		User c = new User(name, login, pass);
		// adds user to users list
		users.add(c);
		System.out.println(c.getName() + " has been succesfully added!");
		System.out.println("You'll be able to use your account after the approval process.");
	}

	/**
	 * Login acc.
	 *
	 * @return true, if successful
	 */
	private boolean loginAcc() {
		System.out.println("Please input your username.");
		scan.nextLine();
		login = scan.nextLine();
		for (User x : users) {
			// System.out.println(x.getUser()); //Test to see if users is populated
			if (login.equals(x.getUser())) {
				if (signIn(login, x)) {
					System.out.println("Login successful!");
					currUser = x;
					return true;
				} else {
					System.err.println("Login failed. Wrong password.");
					return false;
				}
			}
		}
		System.err.println("Login failed. Username does not exist.");
		return false;
	}

	/**
	 * Read users from users.ser.
	 *
	 * @return the array list of users
	 * @throws IOException Signals that an I/O exception has occurred
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<User> read() throws IOException {
		try {
			ois = new ObjectInputStream(new FileInputStream("users.ser"));
			users = (ArrayList<User>) ois.readObject();
			// System.out.println(users.toString());
			return users;
		} catch (IOException | ClassNotFoundException e) {
			logger.warn("Exception occured: " + e.getMessage());
			// System.out.println(user1);
			return users;

		} finally {
			if (ois != null) {
				ois.close();
			}
		}
	}

	
	/**
	 * Saves the current list of users to file.
	 *
	 * @param current list of users
	 * @throws IOException 
	 */
	private void save(ArrayList<User> users) throws IOException {
		try {
			oos = new ObjectOutputStream(new FileOutputStream("users.ser"));
			oos.writeObject(users);
			// System.out.println(users);
		} catch (IOException e) {
			logger.warn("Exception occured: " + e.getMessage());
			e.printStackTrace();
			
		} finally {
			if (oos != null) {
				oos.close();
			}
			
		}
	}
	


	/**
	 * Sign in.
	 *
	 * @param login - the username
	 * @param x - the user attempting to be logged into
	 * @return true, if successful
	 */
	private boolean signIn(String login, User x) {
		if (x != null) {
			System.out.println("Please input your password.");
			String pass = scan.nextLine().trim();
			//System.out.println(x.getPass());
			return (x.getPass().equals(pass));
		}
		return false;
	}

}
