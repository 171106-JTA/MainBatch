package com.revature.bank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;
/**
 * User Interface of the banking application. Pulls a list of users from a serialized file
 * Allows user to create an account or login to an existing one. Once logged in the user can deposit into or withdraw from their
 * account. If the user is marked as an admin, they can promote other users to admin, review account creation requests and lock or
 * unlock accounts.
 * @author Xavier Garibay
 * @since 11/10/2017
 *
 */
public class UI {
	protected Scanner read;
	private ObjectInputStream ois;
	public ArrayList<User> users;
	public String fileName;
	final static Logger logger= Logger.getLogger(UI.class);
	
	public UI(){
		read=new Scanner(System.in);
		users=new ArrayList<>();
		fileName="userRecord.ser";
	}

	/**
	 * Checks if user record exists. If a record is found fills arraylist from serialized file otherwise fills with preset users.
	 * Then moves app to running state.
	 * @param none
	 */
	@SuppressWarnings("unchecked")//to allow casting to ArrayList<Users>
	public void setUp() {
		try {//attempt to read in record of users
			ois= new ObjectInputStream(new FileInputStream("userRecord.ser"));
			users=(ArrayList<User>)ois.readObject();			
		}
		catch(FileNotFoundException e) {
			;//skip if not found, will happen on first execution so is expected to occur
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {//close the input stream
			if(ois!=null)
				try {
					ois.close();
				} catch (IOException e){
					e.printStackTrace();
				}
		}
		if(users.size()==0)//if nothing was added to user list use a preset list. Will happen first time
		{
			logger.info("Users defaulted");
			users= start();
		}
		System.out.println("Welcome to the Java Online Banking tool. ");
		
		run();//run application
		//user is exiting application
		writeRecord(fileName);//save changes made to file before exiting
		System.out.println("\n"
				+ "Goodbye");
	}

	/**
	 * Used when user is not signed in.
	 * Allows user to login or create an account
	 *@param none 
	 */
	protected void run() {
		System.out.println("\n=====================================");
		System.out.println("Enter 1 to login \nEnter 2 to create a new account \nEnter 0 to exit");
		System.out.println("=====================================");
		String input= read.nextLine();
		if(input.equals("1"))
			login();
		else if(input.equals("2"))
			createAccount();
		else if(input.equals("0"))
		{	
			 //allow go back to last function to exit
		}
		else{
			System.out.println("Improper input. Please input one of the options provided.");
			run();
		}
	}
	
	/**
	 * Checks if user can sign into an existing account. Asks for username and checks if it exists then checks if input
	 * password matches the found username's password. If passed moves onto account management. Closes app if user
	 * inputs three wrong passwords.
	 * @param none
	 */
	protected void login()
	{
		boolean found=false;
		User passCheck= new User("","");
		System.out.println("\nPlease enter your username:");
		String uName= read.nextLine();
		for(User u: users)
		{
			if(u.getUsername().equals(uName))//find a match to the username and make a copy of the user
			{
				found=true;
				passCheck=u;
				break;
			}
		}
		if(found==true)//if match found
		{
			int passCount=0;
			while(passCount<3)
			{
				System.out.println("Please enter your password:");
				String pWord = read.nextLine();
				passCount++;
				if(pWord.equals(passCheck.getPassword()))//check password against found user
				{
					if(passCheck.isApproved()==true && passCheck.getLock()==false)
					{
						System.out.println("\n****************************************");
						System.out.println("Welcome " + passCheck.getUsername() +"\nYour account balance is: " + passCheck.getBalance());
						System.out.println("******************************************");
						manageAccount(passCheck);
						break;
					}
					else //if has account but is not approved yet
					{
						if(!passCheck.isApproved())
							System.out.println("Your account has not been approved yet. An administrator will see to it shortly. Goodbye.");
						else
							System.out.println("Your account has been locked by an administrator. Please contact your bank directly"
									+ " for further details. Goodbye.");
						break;	
					}
				}
				else//if password wrong
				{
					System.out.println("Your password is not recognized. Please try again:");
				}
			}
			if(passCount==3)
			{
				System.out.println("You have failed to log in too many times. Farewell");
			}
		}
		else//if no user with username is found
		{
			System.out.println("No user with that name exists. Would you like to try again? Y/N");
			String input= "";
			while(!(input.equals("Y")||input.equals("y")||input.equals("N")||input.equals("n")))//loop until valid input
			{
				input=read.nextLine();
				if(input.equals("Y")||input.equals("y"))
				{
					login();
				}
				else if(input.equals("N")||input.equals("n"))
				{
					run();
				}
				else
				{
					System.out.println("Would you like to try again? Please input Y or N");
				}
			}
		}
	}
	
	/**
	 * Allows user to create an account by inputting a username and password. Usernames cannot be duplicated. Records creation of
	 * new user and exits afterwards.
	 * @param none
	 */
	protected void createAccount()
	{
		boolean approve=true;
		System.out.println("\nPlease enter your desired username: ");
		String uName=read.nextLine();
		for(User u: users)//check to ensure desired username is not already used
		{
			if(u.getUsername().equals(uName))//No duplicate user names
			{
				System.out.println("That username is not available please try again.");
				createAccount();
				approve=false;//mark as not the function call to make a new user
				break;
			}
		}
		if(approve==true)//if username is approved
		{
			System.out.println("Please enter your desired password: ");
			String pWord= read.nextLine();
			User newUser = new User(uName, pWord);
			users.add(newUser);//add new user to the list of users
			System.out.println("Your acount is under review. We look forward to your business.");
			logger.info(uName +" applied for an account");
		}
	}
	
	/**
	 * Allows user to deposit, withdraw from their account, apply for a loan or pay back a loan.
	 * If user is an admin allows the user to promote other users to admin, review account applications or review loan applications
	 * @param u - The current user's account. Used to access balance and check admin status
	 */
	protected void manageAccount(User u) {
		boolean cont=true;
		boolean relog=false;
		while(cont==true)
		{
			String input= "";
			//Display user options based on admin status
			System.out.println("***********************************************");
			System.out.println("Enter 1 to deposit money into your account\n"
						+ "Enter 2 to withdraw money from your account\n"
						+ "Enter 3 to apply for a loan\n"
						+ "Enter 4 to pay back a loan");
			if(u.isAdmin()==true)
			{
				System.out.println("Enter 5 to review account creation requests\n"
								+ "Enter 6 to promote a user to admin\n"
								+ "Enter 7 to lock or unlock a user acoount\n"
								+ "Enter 8 to review loan requests");			
			}
			System.out.println("To loggout press 0");
			System.out.println("***********************************************");
			//Get user input and act according to above prompt
			input = read.nextLine();
			if(input.equals("1"))//deposit
			{
				boolean numCont=false;
				int money=0;
				System.out.println("Please enter the amount to be deposited: ");
				while(numCont==false)//check input is a number and loop until it is
				{
					try 
					{
						numCont=true;
						money= Integer.parseInt(read.nextLine());
					}
					catch(NumberFormatException e)
					{
						System.out.println("Invalid input. Please enter a number.");
						numCont=false;
					}
				}
				u.deposit(money);//deposit accepted input
				logger.info("Deposit. " + u.getUsername()+ "'s balance is now " + u.getBalance());
				System.out.println("Your new balance is: $" + u.getBalance());
			}
			else if(input.equals("2"))//withdraw
			{	
				boolean numCont=false;
				int money=0;
				System.out.println("Please enter the amount to be withdrawn: ");
				while(numCont==false)//loop until number input
				{
					try 
					{
						numCont=true;
						money= Integer.parseInt(read.nextLine());
					}
					catch(NumberFormatException e)
					{
						System.out.println("Invalid input. Please enter a number.");
						numCont=false;
					}
				}
				u.withdraw(money);//withdraw accepted input
				logger.info("Withdraw. " + u.getUsername()+ "'s balance is now " + u.getBalance());
				System.out.println("Your new balance is: $" + u.getBalance());
			}
			else if(input.equals("3"))//apply for loan
			{	
				boolean numCont=false;
				int money=0;
				if(u.getLoan()==0)//check if already has loan or already applied
				{
					System.out.println("Please enter the amount to be loaned: ");
					while(numCont==false)//loop until number input
					{
						try 
						{
							numCont=true;
							money= Integer.parseInt(read.nextLine());
						}
						catch(NumberFormatException e)
						{
							System.out.println("Invalid input. Please enter a number.");
							numCont=false;
						}
					}
					u.setLoan(-1*money);//mark loan as not accepted
					logger.info("Loan Application. " + u.getUsername()+ "'s potential loan is " + (-1*u.getLoan()));
					System.out.println("Your loan application for $" +money+ " is being processed");
				}
				else
				{
					System.out.println("You cannot apply for a loan unless you do not have a loan and do not have an application under review");
				}
			}
			else if(input.equals("4"))//pay back
			{	
				payBack(u);
			}
			else if(u.isAdmin()==true && (input.equals("5") || input.equals("6")|| input.equals("7")|| input.equals("8")))//accept 3, 4 or 5 if admin
			{
				if(input.equals("5"))//Review account creation requests
				{
					reviewRequest();
				}
				else if(input.equals("6"))//Promote a user to admin
				{
					promoteUser();
				}
				else if(input.equals("7"))//lock or unlock
				{//Lock or unlock a users account
					lockOrNot();
				}
				else//8 and admin loan applications
				{
					loanRequest(u);
				}
			}
			else if (input.equals("0"))//Logout
			{
				relog=true;//mark to not ask for continue, would be asked if user exited otherwise
				cont=false;
				run();
			}
			else {
				System.out.println("Input invalid. Please input one of the options given.");
			}
			if(relog==false)
			{//Check if user would like to take another action
				System.out.println("Would you like to continue? Y/N");
				input="";
				while(!(input.equals("Y")||input.equals("y")||input.equals("N")||input.equals("n")))
				{
					input=read.nextLine();
					if(input.equals("Y")||input.equals("y"))
					{
						cont=true;
					}
					else if(input.equals("N")||input.equals("n"))
					{
						cont=false;
					}
				}
			}
		}
	}
	
	/**
	 * Allows user to look at all unapproved accounts and gives them the option to approve or reject the request or simply
	 * move on without changing the account's status
	 * @param none
	 */
	protected void reviewRequest() {
		ArrayList<User> rejects= new ArrayList<>();//used to mark the users to be removed
		for(User u: users)
		{
			if(u.isApproved()==false)//only show users whose accounts have not been approved yet
			{
				System.out.println(u.getUsername() + " has applied for an accout.\nEnter 1 to approve \nEnter 2 to reject"
						+"\nEnter 3 to move on;");
				String input= "";
				while(!(input.equals("1")||input.equals("2")||input.equals("3")))//loop until valid input
				{
					input=read.nextLine();
					if(input.equals("1"))//approve request
					{
						u.Approve();
						logger.info(u.getUsername() + "'s account has been approved");
						System.out.println(u.getUsername() + "'s account has been approved");
					}
					else if(input.equals("2"))//reject request
					{
						logger.info(u.getUsername() + "'s account has been rejected");
						System.out.println(u.getUsername() + "'s account has been rejected");
						rejects.add(u);						
					}
					else if(input.equals("3"))//move on without changing the accounts status
					{
						;
					}
					else 
					{
						System.out.println("Invalid input. Please enter one of the options given.");
						input=read.nextLine();
					}
				}
			}
		}
		for(User u:rejects)//remove all rejected requests from users
			users.remove(u);
		System.out.println("All requests have been reviewed");
		
	}
	/**
	 * Allows user to input the username of another user which can then be made into an admin
	 * @param none
	 */
	protected void promoteUser()
	{
		System.out.println("Please enter the username of the user you would like to promote to administrator: ");
		String input= read.nextLine();
		boolean found=false;//used to tell if username input exists in users
		for(User u: users)
		{
			if(u.getUsername().equals(input))//if username is found in users mark user as admin
			{
				found=true;
				u.makeAdmin();
				System.out.println(u.getUsername() + " is now an administrator.");
				logger.info(u.getUsername()+ " was promoted to admin");
				break;
			}
		}
		if(found==false)//if username does not exist ask if try again
		{
			System.out.println("Username not found. Would you like to try again? Y/N");
			input="";
			while(!(input.equals("Y")||input.equals("y")||input.equals("N")||input.equals("n")))//loop until valid input
			{
				input=read.nextLine();
				if(input.equals("Y")||input.equals("y"))
				{
					promoteUser();
				}
				else if(input.equals("N")||input.equals("n"))
				{
					System.out.println("You will now be taken to the previous menu");
				}
				else
				{
					System.out.println("Would you like to try again? Please input Y or N");					
				}
			}
		}
	}
	
	/**
	 * Asks for user input of a username and, if found in users, reports current lock status and asks if the user would
	 * like to change the lock status, doing so if prompted.
	 * @param none
	 */
	protected void lockOrNot()
	{
		System.out.println("Please input the username of the account you desire:");
		String input= read.nextLine();
		boolean found=false;
		for(User u: users)
		{
			if(u.getUsername().equals(input))//if input is found in users
			{
				found=true;
				//display current status
				System.out.print(u.getUsername() +"'s acount is currently ");
				if(u.getLock()==false)
					System.out.print("unlocked.\nWould you like to lock the account? Y/N");
				else
					System.out.print("locked.\nWould you like to unlock the account? Y/N");
				input="";
				
				while(!(input.equals("Y")||input.equals("y")||input.equals("N")||input.equals("n")))//loop until valid input
				{
					input=read.nextLine();
					if(input.equals("Y")||input.equals("y"))//switch account's lock status
					{
						u.switchLock();
						if(u.getLock())
							logger.info(u.getUsername() + "'s account has been locked");
						else
							logger.info(u.getUsername() +"'s account has been unlocked");
					}
					else if(input.equals("N")||input.equals("n"))//returns without changing the status of the account
					{
						System.out.println("The account's status will remain the same");
					}
					else
					{
						System.out.println("Would you like to try again? Please input Y or N");					
					}
				}
				break;
			}
		}
		if(found==false)//if input not found in users
		{
			System.out.println("Username not found. Would you like to try again? Y/N");
			input="";
			while(!(input.equals("Y")||input.equals("y")||input.equals("N")||input.equals("n")))//loop until valid input
			{
				input=read.nextLine();
				if(input.equals("Y")||input.equals("y"))
				{
					lockOrNot();
				}
				else if(input.equals("N")||input.equals("n"))
				{
					System.out.println("You will now be brought to the previous menu.");
				}
				else
				{
					System.out.println("Would you like to try again? Please input Y or N");					
				}
			}
		}
	}
	
	/**
	 * Serializes ArrayList of users to userRecord.ser, creating initial record or overwriting the previous record.
	 * @param none
	 */
	protected void writeRecord(String fileName) {
		try {
			ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(fileName));
			oos.writeObject(users);//save list of users to separate file to be read in on startup
			logger.info("User file has been updated and app closed\n");
			if(oos!=null)
				oos.close();

		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			read.close();
		}
	}
	
	/**
	 * Create and return a default list of users 
	 * @return default list of users
	 */
	protected ArrayList<User> start()
	{
		ArrayList<User> startUsers=new ArrayList<User>();
		startUsers=new ArrayList<User>();
		User admin=new User("Xavier.Garibay", "wolf");//admin user
		admin.makeAdmin();
		admin.Approve();
		admin.deposit(2000);
		startUsers.add(admin);
		User user=new User("John.Doe", "deer");//normal user
		user.Approve();
		user.deposit(600);
		startUsers.add(user);
		return startUsers;
	}
	
	/**
	 *Review requests for loans and allow user to accept or reject the request as well as move on. User cannot approve own request 
	 * @param x - Used so that user cannot approve own loan request
	 */
	protected void loanRequest(User x) {
		for(User u: users)
		{
			if(u.getLoan()<0 && u!=x)//only show users whose accounts have not been approved yet
			{
				System.out.println(u.getUsername() + " has applied for a loan of $" + (-1*u.getLoan()) +".\nEnter 1 to approve "
						+"\nEnter 2 to reject\nEnter 3 to move on;");
				String input= "";
				while(!(input.equals("1")||input.equals("2")||input.equals("3")))//loop until valid input
				{
					input=read.nextLine();
					if(input.equals("1"))//approve request
					{
						u.setLoan((u.getLoan()*-1));//mark as actual
						u.deposit((int)u.getLoan());//add funds to account
						logger.info(u.getUsername() + "'s loan has been approved");
						System.out.println(u.getUsername() + "'s loan has been approved");
					}
					else if(input.equals("2"))//reject request
					{
						logger.info(u.getUsername() + "'s loan has been rejected");
						System.out.println(u.getUsername() + "'s loan has been rejected");
						u.setLoan(0);//allow new application
					}
					else if(input.equals("3"))//move on without changing the accounts status
					{
						;
					}
					else 
					{
						System.out.println("Invalid input. Please enter one of the options given.");
						input=read.nextLine();
					}
				}
			}
		}
		System.out.println("All requests have been reviewed");	
	}
	/**
	 * Allow user to pay back loan. Checks value payed against loan and account balance and detracts from loan and account accordingly
	 * @param u - User account to be accessed
	 */
	protected void payBack(User u)
	{
		boolean numCont=false;
		int money=0;
		if(u.getLoan()>0)//if has a loan
		{
			System.out.println("Your current loan is $" +u.getLoan());
			System.out.println("Please enter the amount to be payed back: ");
			while(numCont==false)//loop until number input
			{
				try 
				{
					numCont=true;
					money= Integer.parseInt(read.nextLine());
				}
				catch(NumberFormatException e)
				{
					System.out.println("Invalid input. Please enter a number.");
					numCont=false;
				}
			}
			if(u.getBalance()>=money)//if has enough money in account
			{
				if(money>u.getLoan())//if paying back too much
				{
					u.deposit((int)(money-u.getLoan()));
					u.withdraw(money);
					u.setLoan(0);
				}
				else//if paying back full amount or less
				{
					u.withdraw(money);
					u.setLoan(u.getLoan()-money);
				}
				System.out.println("Your loan balance is now $" + u.getLoan() + "\nYour account balance is now $" + u.getBalance());
				logger.info("Loan payment. Loan balance is now "  + u.getLoan());
				logger.info("Account balance is now " + u.getBalance());
			}
			else
			{
				System.out.println("You do not currently have the funds to pay. Please deposit more funds to your account.");
			}
		}
		else
			System.out.println("You do not currently have a loan to pay back.");
	}
}
