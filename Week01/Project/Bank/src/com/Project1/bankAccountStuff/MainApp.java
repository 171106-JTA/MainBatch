package com.Project1.bankAccountStuff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class MainApp {
	private static final String databaseFile = "database.txt"; //File containing database
	private HashMap<String, User> db;
	private User currentUser;
	
	//Status values for user accounts
	private static final int status_approvalPending = 0;
	private static final int status_active = 1;
	private static final int status_locked = 2;
	//Used the tutorials listed below for the ObjectInputStream and ObjectOutputStream
	//http://www.studytonight.com/java/serialization-and-deserialization.php
	//http://www.tutorialspoint.com/java/io/objectinputstream_readobject.htm
	//Date Accessed: 11/10/2017
	ObjectInputStream  ois;
	ObjectOutputStream oos;
	
	/*
	 * To Do's:
	 * Add a Username field to the User class. Propogate this change through all relevant 
	 * functions in Main
	 * For login() function, allow user to enter 'q' or something to go back
	 * Disable 'q' as a valid password
	 * Convert if-else chains to switch statements (To practice working with switch statements)
	 */
	
	public static void main(String[] args) {		
		MainApp mp = new MainApp();
		mp.db = new HashMap<>();
		boolean exit = false;
		
		//Read database from the file
		try {
			mp.readDatabase(mp.databaseFile);
		} catch (IOException e1) {
			//To Do: Expand exception handling?  
			e1.printStackTrace();
		} //To Do: Any finally statement?
		
		//Print at the start of the program
		System.out.println("Welcome");
		
		//Loop over the Main Menu
		while(!exit) {
			String userChoice = mp.mainMenu();
			//To Do: Refactor the control statements to a separate function
			//(i.e. No control statements in main
			if (userChoice.equals("1")) {
				boolean loggedIn = mp.login(); 
				
				//If successfully logged in, check the user's status
				if(loggedIn) {
					int status = mp.currentUser.getStatus();
					if(status==status_approvalPending) {
						System.out.println("Account Approval Pending");
					} else if (status==status_locked) {
						System.out.println("Account Locked");
					} else if (status==status_active) {
						System.out.println("Successfully Logged In!");
					}
					else {
						//To Do: Put throw statement here with custom error message
						System.out.println("FATAL ERROR #1: Should NEVER See This!!!!");
						System.exit(1);
					}
				} else {
					System.out.println("NOT Logged In");
				}
			} else if (userChoice.equals("2")) {
				mp.createNewClientAccount();
			} else if (userChoice.equals("3")) {
				System.out.println("Closing Program");
				exit = true;
			} else if (userChoice.equals("42")) { //Testing user creation
				String firstName = "Evan";
				String lastName = "West";
				String middleInitial = "A"; 
				String ssn = "0"; 
				String password = "0";
				int permissions = 0; 
				int status = 0; 
				int accountAmount = 0;
				User default_user_1 = new User(firstName, lastName, middleInitial, ssn, 
						password, permissions, status, accountAmount);
				
				firstName = "A";
				lastName = "A";
				middleInitial = "A"; 
				ssn = "1"; 
				password = "1";
				permissions = 0; 
				status = 1; 
				accountAmount = 0;
				User default_user_2 = new User(firstName, lastName, middleInitial, ssn, 
						password, permissions, status, accountAmount);
				
				firstName = "B";
				lastName = "B";
				middleInitial = "B"; 
				ssn = "2"; 
				password = "2";
				permissions = 0; 
				status = 2; 
				accountAmount = 0;
				User default_user_3 = new User(firstName, lastName, middleInitial, ssn, 
						password, permissions, status, accountAmount);
				
				mp.db.put(default_user_1.getSsn(), default_user_1);
				mp.db.put(default_user_2.getSsn(), default_user_2);
				mp.db.put(default_user_3.getSsn(), default_user_3);
				
				System.out.println(mp.db);
			} else {
				//Add counter and exit after 5 incorrect attempts
				System.out.println("Not an option");
			}			
		}
		
		//Save the database to the 'database.txt' file. Erase whatever is in the file currently
		//To Do: Might be able to abstract this try/catch statement with the one at readDatabase() call
		try {
			mp.saveDb();
		} catch (IOException e) {
			e.printStackTrace();
			//To Do: Return message with catch statement???
		} //To Do: Any finally statement? 
	}
	
	/**
	 * Read serialized HashMap object from file and store in the 'db' member object.
	 * @param fileName			Name of file containing serialized HashMap database
	 * @throws IOException		Throws this exception if input stream cannot be closed (in finally statement)
	 */
	private void readDatabase(String fileName) throws IOException {		
		try {
			//Read in File
			ois = new ObjectInputStream (new FileInputStream(databaseFile));
			
			/*
			 * To Do: There is a warning generated here because casting the 'Object' returned from 
			 * readObject to a HashMap. See the link below for a possible fix
			 * https://stackoverflow.com/questions/262367/type-safety-unchecked-cast
			 */
			this.db = (HashMap<String,User>)ois.readObject();
			System.out.println("The Database!!!!: " + this.db);
		} catch (ClassNotFoundException e) {
			//To Do: Fill in 
			e.printStackTrace();
		} finally {
			//Close input stream
			if(ois!=null) {
				ois.close();
			}
		}
	}
	
	/**
	 * Displays initial menu
	 * @return Contains user's choice from the initial menu
	 */
	private String mainMenu() {
		System.out.println("_______________________________");
		System.out.println("1) Login");
		System.out.println("2) Create New Account");
		System.out.println("3) Exit");
		System.out.print("Choice: ");
		
		Scanner aScanner = new Scanner(System.in);
		String userInput = aScanner.nextLine();
		//Build input validation later
		
		return userInput;
//		StringTokenizer st = new StringTokenizer(s);
	}
	
	/**
	 * Get username and password from user and compare against the database
	 * @return True - successful login. False - Too many login attempts, end program
	 */
	private boolean login() { 
		Scanner aScanner = new Scanner(System.in);
		String snn; 
		String password; 
		boolean loggedIn = false;
		
		System.out.println("Social Security Number: "); //re-enter password at some point
		String ssn = aScanner.nextLine();
		
		if(this.db.containsKey(ssn)) {
			boolean correctPassword = false; 	//Control's execution of password loop
			final int maxPasswordLoop = 5; 			//Maximum number of password retries
			
			//Password loop
			for(int i = 0; i < maxPasswordLoop && !correctPassword; i ++)
			{
				System.out.println("Password: "); //re-entered password at some point
				password = aScanner.nextLine();
				if(password.equals(this.db.get(ssn).getPassword())) {
					//Stop the loop
					correctPassword = true;
					
					//Signal that the user is logged in
					loggedIn = true;
					
					//Fetch the current user's information from the database
					this.currentUser = new User(this.db.get(ssn));
				}
				else {
					System.out.println("Incorrect password");
				}
			}
			
			//Print message is user exceeded the number of password tries
			if(!correctPassword) {
				System.out.println("Too many incorrect tries\n");
			}
		} else
		{
			System.out.println("No user by that name\n\n");
		}
				
		return loggedIn;
	}
	
	/**
	 * Allows user to create a new client account
	 */
	private void createNewClientAccount() {
		//Get username and password
		//Check username against existing usernames
		//If unique, add information 
		Scanner aScanner = new Scanner(System.in);
		
		//Variables to hold user's input
		//to do: Convert ssn to 9 digits, eventually
		//to do: Convert middleInitial to char
		String firstName, lastName, middleInitial, password, ssn; 
		
		System.out.println("First Name: ");
		firstName = aScanner.nextLine();
		
		System.out.println("Last Name: ");
		lastName = aScanner.nextLine();
		
		System.out.println("Middle Initial: ");
		middleInitial = aScanner.nextLine();
		
		System.out.println("Social Security Number: "); //re-enter password at some point
		ssn = aScanner.nextLine();
		
		System.out.println("Password: "); //re-entered password at some point
		password = aScanner.nextLine();
		
		User newUser = new User(firstName, lastName, middleInitial, ssn, password);
		
		System.out.println(newUser.toString());
		
		//To Do: Duplicate check. And test case for this duplicate check
		//To Do: Run the ssn key through a Hash function so that it's not stored in the db as a key
		//To Do: Run the object serialization through a ?hash function? 
		this.db.put(newUser.getSsn(), newUser);
		
		System.out.println(db);
	}
	
	/**
	 * Get user's response to menus
	 * @return Returns user's choice
	 */
	private String getUserInput(String message) {
		System.out.println(message);
		Scanner aScanner = new Scanner(System.in);
		return aScanner.nextLine();
	}
	
	/**
	 * Display login menu, get user's choice, and validate user's choice
	 * @return Returns user's choice
	 */
	private int LoginMenu() {
		return 0;
	}
	
	/**
	 * Display menu for Admins, get Admin's choice, and validate admin's choice
	 */
	private void AdminMenu() {
		
	}
	
	/**
	 * Display menu for Client's, get Clien'ts choice, and validate Client's choice
	 */
	private void ClientMenu() {
		
	}
	
	////////////////////////////////////////////////////////
	//Admin Functionality
	////////////////////////////////////////////////////////
	/**
	 * Lock the given client's account
	 * @param clientUsername		Username of client account to lock
	 */
	public void lockClientAccount(String clientUsername) {
		
	}
	
	/**
	 * Unlock the given client's account
	 * @param clientUsername		Username of the client account to unlock
	 */
	public void unlockClientAccount(String clientUsername) {
		
	}
	
	/**
	 * Approve the given client's account
	 * @param clientUsername		Username of client account to approve
	 */
	public void approveClientAccount(String clientUsername) {
		
	}
	
	/**
	 * Allows admins to promote clients to admin status
	 */
	public void promoteClientToAdmin() {
		
	}
	
	////////////////////////////////////////////////////////
	//Client Functionality
	////////////////////////////////////////////////////////
	/**
	 * Add given amount to the Client's account
	 * @param depositAmount			Amount to be deposited into Client's account
	 */
	public void deposit(double depositAmount) {
		
	}
	
	/**
	 * Subtract given amount from the Client's account
	 * @param withdrawAmount		Amount to be withdrawn from Clien'ts account
	 */
	public void withdraw(double withdrawAmount) {
		
	}	
	
	public void saveDb() throws IOException{
		//To Do: Don't pass in database! use  this.db to access!!!!!
		try {
			oos = new ObjectOutputStream(new FileOutputStream(databaseFile));
			oos.writeObject(this.db);
			oos.close();
		} catch (IOException e) {
			//To Do: Return message with catch statement???
			e.printStackTrace();
		} finally {
			if(oos!=null) {
				oos.close();
			}
		}
	}
}
