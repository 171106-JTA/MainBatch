package com.project1.type3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
	
	/**
	 * main method to put pieces together and run the full program
	 * @param args
	 */
	public static void main(String[] args){
		
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		String s, username, password;
		ArrayList<User> accounts = null;
		boolean go = true;
		Scanner scan = new Scanner(System.in);
		UserMenu menu = new UserMenu();
		
		//Attempt to load a list of accounts otherwise create new list
		try{
			ois = new ObjectInputStream(new FileInputStream("accounts.ser"));
			accounts = (ArrayList<User>) ois.readObject();
		}catch(IOException | ClassNotFoundException e){
			System.err.println("Could not load ser file");
			e.printStackTrace();
			
			/*
			 * What I am trying to do here is to have atleast one account and have it be the admin
			 * A "default admin" if there are no accounts. So if someone decides to become a user they
			 * can will be eligible for approval.
			 */
			accounts = new ArrayList<User>();
			User admin = new User("Nasir", "Alauddin");
			admin.makeAdmin();
			admin.makeApproved();
			accounts.add(admin);
		}finally{
			if(ois != null){
				try{
					ois.close();
				}catch(IOException e){
					System.err.println("Could not close input stream");
					e.printStackTrace();
				}
			}
		}
		//end load attempt
		
		while(go){//with the while loop the program does not close until the user decides to exit from menu
			System.out.println("===============");
			System.out.println("***MAIN MENU***");
			System.out.println("===============");
			System.out.println("0: Exit");
			System.out.println("1: Login");
			System.out.println("2: Login as Administrator");
			System.out.println("3: Create an Account");
			s = scan.nextLine();
			
			switch(s){
			case "0":
				go = false;
				break;
				
			case "1":
				System.out.println("Please enter username: ");
				username = scan.nextLine();
				
				System.out.println("Please enter password: ");
				password = scan.nextLine();
				
				for(User account : accounts){
					if(account.getUsername().equals(username) && account.getPassword().equals(password)){
						if(!account.isApproved())
							System.out.println("Your account is currently seeking approval.");
						else if(account.isLocked())
							System.out.println("Your account is currently locked!");
						else
							menu.userAccount(account);
					}
				}//end for
				break;
				
			case "2":
				System.out.println("Please enter username: ");
				username = scan.nextLine();
				
				System.out.println("Please enter password: ");
				password = scan.nextLine();
				
				for(User account : accounts){
					if(account.getUsername().equals(username) && account.getPassword().equals(password) && account.isAdmin()){
						if(!account.isApproved())
							System.out.println("Your account is currently seeking approval.");
						else if(account.isLocked())
							System.out.println("Your account is currently locked!");
						else{
							menu.adminAccount(accounts,username);
							break;
						}
					}
					else
						System.out.println("You do not have administrator access!");
				}//end for
				break;
				
			case "3":
				System.out.println("Please enter username: ");
				username = scan.nextLine();
				
				System.out.println("Please enter password: ");
				password = scan.nextLine();
				
				if(checkDuplicate(accounts, username))
					System.out.println("There is already an account under this name!");
				else{
					accounts.add(new User(username, password));
					System.out.println("Welcome " + username + "! You can log in as soon as you are approved by an administrator.");
				}
				
				break;
			}//end switch
		}//end while
		
		/*
		 * This is to save the current accounts status into to ser file
		 */
		try{
			oos = new ObjectOutputStream(new FileOutputStream("accounts.ser"));
			oos.writeObject(accounts);
		}catch(IOException e){
			System.err.println("Failed to save ser file");
			e.printStackTrace();
		}finally{
			if(oos != null){
				try{
					oos.close();
				}catch(IOException e){
					System.err.println("Could not close output stream");
					e.printStackTrace();
				}
			}
		}
		//end of save to ser try catch
		
	}//end main
	
	/**
	 * method used to check if a username is already taken. If so it will return true
	 * otherwise false
	 * @param accounts
	 * @param username
	 * @return
	 */
	public static boolean checkDuplicate(ArrayList<User> accounts, String username){
		for(User account : accounts)
			if(account.getUsername().equals(username))
				return true;
		return false;
	}

}
