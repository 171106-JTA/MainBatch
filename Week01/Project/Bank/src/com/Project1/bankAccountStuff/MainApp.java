package com.Project1.bankAccountStuff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class MainApp {
	
	/**
	 * Contains all user information
	 */
	private static final String databaseFile = "database.txt"; //File containing database
	private HashMap<String, User> db;
	private BufferedWriter bw;
	private static BufferedReader br;  
//	private BufferedWriter bw;
	
	public static void main(String[] args) {		
		MainApp mp = new MainApp();
		mp.db = new HashMap<>();
		boolean exit = false;
		
		while(!exit) {
			String userChoice = mp.initialMenu();
			
			if (userChoice.equals("1")) {
				//Call login
			} else if (userChoice.equals("2")) {
				mp.createNewClientAccount();
			} else if (userChoice.equals("3")) {
				System.out.println("Closing Program");
				exit = true;
			} else if (userChoice.equals("42")) { //Testing user creation
				String firstName = "Evan";
				String lastName = "West";
				String middleInitial = "A"; 
				String ssn = "000000000"; 
				String password = "password";
				User default_user_1 = new User(firstName, lastName, middleInitial, ssn, password);
				
				firstName = "A";
				lastName = "A";
				middleInitial = "A"; 
				ssn = "111111111"; 
				password = "A";
				User default_user_2 = new User(firstName, lastName, middleInitial, ssn, password);
				
				firstName = "B";
				lastName = "B";
				middleInitial = "B"; 
				ssn = "222222222"; 
				password = "B";
				User default_user_3 = new User(firstName, lastName, middleInitial, ssn, password);
				
				mp.db.put(default_user_1.getSsn(), default_user_1);
				mp.db.put(default_user_2.getSsn(), default_user_2);
				mp.db.put(default_user_3.getSsn(), default_user_3);
				
				System.out.println(mp.db);
			} else {
				//Add counter and exit after 5 incorrect attempts
				System.out.println("Not an option");
			}			
		}
		//Save the database stuff for later
//		try {
//			readDatabase(databaseFile);
//		} catch(IOException ioe){
//			ioe.getStackTrace();
//		}
		//Might be able to abstract this try/catch statement
		
		
//		try {
//			mp.saveDb(mp.db);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * Read serialized 'User' objects from file and store in the 'db' member object.
	 * @param fileName 		Name of file containing serialized User objects
	 */
	private static void readDatabase(String fileName) throws IOException {
		//Read in all user info stored in file
		//Store in a hash table. Key = pair of username and password. value = user object
		//Return hash table?
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(br!=null) {
				br.close();
			}
		}
	}
	
	/**
	 * Displays initial menu
	 * @return Contains user's choice from the initial menu
	 */
	private String initialMenu() {
		System.out.println("Welcome");
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
		//Get user's username and password
		//Check against allusers
		//Allow user to re-enter username and password 5 times. 
		//If successful, return true
		//If unsuccessful, return false (end program)
		//Note: probably use try/catch statements for this
		
		return false; //Place holder
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
	private int getUserResponse() {
		return 0;
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
	
	public void saveDb(HashMap<String, User> db) throws IOException{
		try {
			String str = "Hello There!!!";
//			bw = new BufferedWriter(new FileWriter(databaseFile));
//			bw.write(str);
//			bw.write(db.toString());
			
			FileOutputStream foo = new FileOutputStream(databaseFile);
			ObjectOutputStream oos = new ObjectOutputStream(foo);
			
			oos.writeObject(db);
			oos.close();
			foo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(bw!=null) {
				bw.close();
			}
		}
	}
}
