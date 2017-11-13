package package1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

//Console application class
public class Driver {

	public static void main(String[] args) {

		// Read in existing users
		ArrayList<User> users = getUsers();

		// Check if an admin exists.
		// If one does not exist then create a default admin and add the admin to the
		// list
		checkForAnAdmin(users);

		// Navigating method for the User
		drive(users);
	}

	// Navigation method for the User. Functions with the use of
	// nested do-while loops with switch statements and through a Scanner object
	public static void drive(ArrayList<User> users) {
		Scanner input = new Scanner(System.in); // Create Scanner object

		// Selection variables
		int doSelection = -1; // int variable to be used for switch statement
		String doSelectionString; // String variable to read in nextLine() from the Scanner,
									// this String is parsed into an int

		// do-while loop to allow the user to navigate between Login, Create Account,
		// and Exiting the program
		do {
			try {
				System.out.println("==================Login Screen==================");
				System.out.println("Select an option: \n");
				System.out.println("1 - Login\t 2 - Create Account \t 0 - Exit");

				doSelectionString = input.nextLine();
				doSelection = Integer.parseInt(doSelectionString);

				switch (doSelection) {
				case 0:
					return;
				case 1:
					doLogin(users, input);
					break;
				case 2:
					doCreateUser(users, input);
					break;
				default:
					System.out.println("Invalid input! Try again.");
					break;

				}

			} catch (NumberFormatException ex) {
				System.out.println("\nPlease enter a valid input!\n");
			}

		} while (true);

	}

	// Method to create a new User and add the User to the list of Users
	public static void doCreateUser(ArrayList<User> users, Scanner input) {
		System.out.println("Create a username and press enter. ");
		String username = input.nextLine(); // Read in username from Scanner
		username = username.trim(); // trim spaces before and after the username
		if (username.equals("")) { // Exit method if a blank username entered
			System.out.println("New user creation canceled");
			return;
		}
		System.out.println("Create a password: ");
		String password = input.nextLine();
		if (password.equals("")) {
			System.out.println("New user creation canceled");
			return;
		}
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				System.out.println("Username already exists!");
				return;
			}
		}
		
		User user = new User(username, password); // Create a new User
		users.add(user); // Add the User to the list
		setUsers(users); // Rewrite the User list
		System.out.println("User account created!");
	}

	//Display options after logging in. Perform methods as prompted by the user
	public static void doLogin(ArrayList<User> users, Scanner input) {
		User loggedInUser = null;
		int loggedInUserIndex = -1;
		System.out.println("Enter username: ");
		String userName = input.nextLine();
		System.out.println("Enter password: ");
		String password = input.nextLine();
		for (int i = 0; i < users.size(); i++) {
			if (userName.equals(users.get(i).getUsername()) && password.equals(users.get(i).getPassword())) {
				loggedInUser = users.get(i); // Create a copy of the matching object if exists
				loggedInUserIndex = i; // Store index of the matching object
				break;
			}
		}
		//Execute if loggedInUser is still null after going through for loop
		if (loggedInUser == null) {
			System.out.println("Please login!");
			return;
		}
		
		//Execute the following if statement if the user is an admin.
		//the if statement contains, a switch statement with do-while loops for most cases
		if (loggedInUser.isAdmin()) {
			System.out.println("\nCurrent balance: $" + loggedInUser.getBalance());

			int doAdminSelection = -1;
			String doAdminSelectionString;

			do {
				try {
					System.out.println("==================Admin Screen==================");
					System.out.println("Select an option: \n");
					System.out.println("1 - Deposit\t 2 - Withdraw \t 3 - Approve\t "
							+ "4 - Promote\t " + "5 - Lock\t 6 - Unlock\t 0 - Logout");

					doAdminSelectionString = input.nextLine();
					doAdminSelection = Integer.parseInt(doAdminSelectionString);

					switch (doAdminSelection) {
					case 0: // Logout
						return;
					case 1: // Deposit
						double depositAmount = 0;
						String depositAmountString;
						do {
							try {
								System.out.println("Enter deposit amount: ");

								depositAmountString = input.nextLine();
								depositAmount = Double.parseDouble(depositAmountString);

								break;

							} catch (NumberFormatException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							}
						} while (true);

						loggedInUser.deposit(depositAmount);
						users.set(loggedInUserIndex, loggedInUser); // Replace old instance of the user to save updated
																	// balance
						setUsers(users); // Write users list to file

						break;

					case 2: // Withdraw
						double withdrawAmount = 0;
						String withdrawAmountString;
						do {
							try {
								System.out.println("Enter withdraw amount: ");

								withdrawAmountString = input.nextLine();
								withdrawAmount = Double.parseDouble(withdrawAmountString);

								break;

							} catch (NumberFormatException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							}
						} while (true);

						loggedInUser.withdraw(withdrawAmount);
						users.set(loggedInUserIndex, loggedInUser); // Replace old instance of the user to save updated
																	// balance
						setUsers(users); // Write users list to file

						break;
						
					case 3: // Approve
						int userToApproveIndex;
						String userToApproveIndexString;
						User userToApprove;

						do {
							try {
								System.out.println(users);
								System.out.println("\nSelect index of user to approve" + " (Note: first index is 0): ");

								userToApproveIndexString = input.nextLine();
								userToApproveIndex = Integer.parseInt(userToApproveIndexString);

								userToApprove = users.get(userToApproveIndex);

								break;

							} catch (NumberFormatException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							} catch (IndexOutOfBoundsException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							}

						} while (true);

						loggedInUser.approveUser(userToApprove); 		// Approve user
						users.set(userToApproveIndex, userToApprove); 	// Replace old instance of the user to save
																		// updated approved status
						setUsers(users); // Write users list to file

						break;

					case 4: // Promote
						int userToPromoteIndex;
						String userToPromoteIndexString;
						User userToPromote;

						do {
							try {
								System.out.println(users);
								System.out.println("\nSelect index of user to promote" + " (Note: first index is 0): ");

								userToPromoteIndexString = input.nextLine();
								userToPromoteIndex = Integer.parseInt(userToPromoteIndexString);

								userToPromote = users.get(userToPromoteIndex);

								break;

							} catch (NumberFormatException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							} catch (IndexOutOfBoundsException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							}

						} while (true);

						loggedInUser.promoteUser(userToPromote); // Promote user to admin
						users.set(userToPromoteIndex, userToPromote); // Replace old instance of the user to save
																		// updated
																		// admin status
						setUsers(users); // Write users list to file

						break;

					case 5: // Lock
						int userToLockIndex;
						String userToLockIndexString;
						User userToLock;

						do {
							try {
								System.out.println(users);
								System.out.println("\nSelect index of user to lock" + " (Note: first index is 0): ");

								userToLockIndexString = input.nextLine();
								userToLockIndex = Integer.parseInt(userToLockIndexString);

								userToLock = users.get(userToLockIndex);

								break;

							} catch (NumberFormatException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							} catch (IndexOutOfBoundsException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							}
						} while (true);

						loggedInUser.lockUser(userToLock); // Lock user
						users.set(userToLockIndex, userToLock); // Replace old instance of the user to save updated
																// locked status
						setUsers(users); // Write users list to file
						break;

					case 6: // Unlock
						int userToUnlockIndex;
						String userToUnlockIndexString;
						User userToUnlock;

						do {
							try {
								System.out.println(users);
								System.out.println("\nSelect index of user to unlock" + " (Note: first index is 0): ");

								userToUnlockIndexString = input.nextLine();
								userToUnlockIndex = Integer.parseInt(userToUnlockIndexString);

								userToUnlock = users.get(userToUnlockIndex);

								break;

							} catch (NumberFormatException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							} catch (IndexOutOfBoundsException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							}
						} while (true);

						loggedInUser.unlockUser(userToUnlock); 		// Unlock user
						users.set(userToUnlockIndex, userToUnlock); // Replace old instance of the user to save updated
																	// not locked status
						setUsers(users); // Write users list to file
						break;

					default:
						System.out.println("Invalid input! Try again.");
						break;

					}

				} catch (NumberFormatException ex) {
					System.out.println("\nPlease enter a valid input!\n");
				}

			} while (true);

		}

		//Execute the following else statement if the user is not an admin.
		//the if else statement contains a switch statement with do-while loops for most cases
		else {
			System.out.println("\nCurrent balance: $" + loggedInUser.getBalance());

			int doUserSelection = -1;
			String doUserSelectionString;

			do {
				try {
					System.out.println("==================User Screen==================");
					System.out.println("Select an option: \n");
					System.out.println("1 - Deposit\t 2 - Withdraw \t 0 - Logout");

					doUserSelectionString = input.nextLine();
					doUserSelection = Integer.parseInt(doUserSelectionString);

					switch (doUserSelection) {
					case 0: // Logout
						return;
					case 1: // Deposit
						double depositAmount = 0;
						String depositAmountString;
						do {
							try {
								System.out.println("Enter deposit amount: ");

								depositAmountString = input.nextLine();
								depositAmount = Double.parseDouble(depositAmountString);

								break;

							} catch (NumberFormatException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							}
						} while (true);

						loggedInUser.deposit(depositAmount);
						users.set(loggedInUserIndex, loggedInUser); // Replace old instance of the user to save updated
																	// balance
						setUsers(users); // Write users list to file

						break;

					case 2: // Withdraw
						double withdrawAmount = 0;
						String withdrawAmountString;
						do {
							try {
								System.out.println("Enter withdraw amount: ");

								withdrawAmountString = input.nextLine();
								withdrawAmount = Double.parseDouble(withdrawAmountString);

								break;

							} catch (NumberFormatException ex) {
								System.out.println("\nPlease enter a valid input!\n");
							}
						} while (true);

						loggedInUser.withdraw(withdrawAmount);
						users.set(loggedInUserIndex, loggedInUser); // Replace old instance of the user to save updated
																	// balance
						setUsers(users); // Write users list to file

						break;
					default:
						System.out.println("Invalid input! Try again.");
						break;

					}

				} catch (NumberFormatException ex) {
					System.out.println("\nPlease enter a valid input!\n");
				}

			} while (true);

		}
	}

	//Get User list from the file
	public static ArrayList<User> getUsers() {
		ObjectInputStream ois = null;		//Create an ObjectInputStream object

		try {
			ois = new ObjectInputStream(new FileInputStream("Users.txt"));

			ArrayList<User> userList = (ArrayList<User>) ois.readObject();
			return userList;

		} catch (IOException ex) {

		} catch (ClassNotFoundException ex) {

		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
				}
			}
		}
		return new ArrayList<User>();		//Return empty list if one does not already exist
	}

	//Write User list to the file
	public static void setUsers(ArrayList<User> newUsers) {
		ObjectOutputStream oos = null;		//Create an ObjectOutputStream object
		try {
			oos = new ObjectOutputStream(new FileOutputStream("Users.txt"));
			oos.writeObject(newUsers);		//Write the list to the file

		//Catch IOException
		} catch (IOException ex) {

			ex.printStackTrace();
		//Close the output stream
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	// Method to check if an admin exists. Add an admin if one does not
	public static void checkForAnAdmin(ArrayList<User> users) {
		// Check for admin in the list
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).isAdmin()) {
				return;
			}
		}
		// Create an admin and add to list, if admin does not exist
		User admin = new User("admin", "admin");
		admin.setAdmin(true);
		users.add(admin);
		setUsers(users);
	}

}
