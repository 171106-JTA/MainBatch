package com.Project1.bankAccountStuff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class MainApp {
	
	/**
	 * Contains all user information
	 */
	private static final String databaseFile = "database.txt"; //File containing database
	private Hashtable<String, User> db;
	private static BufferedReader br;  
//	private BufferedWriter bw;
	
	public static void main(String[] args) {
		System.out.println("Just Saying Something");
		
		try {
			readDatabase(databaseFile);
		} catch(IOException ioe){
			ioe.getStackTrace();
		}
		
	}
	
	/**
	 * Read serialized 'User' objects from file and store in the 'db' object
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
	}
	
	/**
	 * Displays initial menu
	 */
	private void displayLoginMenu() {
		
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
}
