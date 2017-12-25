package com.revature.bankdb;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;
/**
 * User Interface of the banking application. Pulls all data from a database
 * Allows user to create an account or login to an existing one. Once logged in the user can deposit into or withdraw from their
 * account. If the user is marked as an admin, they can promote other users to admin, review account creation requests and lock or
 * unlock accounts.
 * @author Xavier Garibay
 * @since 11/19/2017
 *
 */
public class UI {
	public Scanner read;
	public UserDAO dao;
	final static public Logger logger= Logger.getLogger(UI.class);
	
	/**
	 * Constructor setting up scanner for user input and UserDAO for database access
	 */
	public UI(){
		read=new Scanner(System.in);
		dao= new UserDAO();
	}


	/**
	 * Starts application, looping until user exits. Used when user is not signed into an account.
	 * Allows user to login or create an account
	 *@param none 
	 */
	public void run() {
		System.out.println("Welcome to the Java Online Banking tool. ");
		String input=" ";
		while(!input.equals("0"))
		{	
			System.out.println("\n=====================================");
			System.out.println("Enter 1 to login \nEnter 2 to create a new account \nEnter 0 to exit");
			System.out.println("=====================================");
			input= read.nextLine();
			if(input.equals("1"))
				login();
			else if(input.equals("2"))
				createAccount();
			else if(input.equals("0"))
			{	
				 //allow application to exit
			}
			else{
				System.out.println("Improper input. Please input one of the options provided.");
			}
		}
		read.close();
		System.out.println("Goodbye");
	}
	
	/**
	 * Checks if user can sign into an existing account. Asks for username and checks if it exists then checks if input
	 * password matches the found username's password. If passed moves onto account management. Closes app if user
	 * inputs three wrong passwords.
	 * @param none
	 */
	public void login()
	{
		String[] dbInfo= new String[3]; 
		boolean found=false;
		System.out.println("\nPlease enter your username:");
		String uName= read.nextLine();
		ArrayList<String> users= dao.getAllUsername();
		for(String u: users)
		{
			if(u.equals(uName))//find a match to the username and make a copy of the user
			{
				found=true;
				dbInfo=dao.getLogin(uName);//save username's password and id for later
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
				if(pWord.equals(dbInfo[2]))//check password against found user
				{
					int[] statusInfo = new int[4];
					statusInfo= dao.getStatus(Integer.parseInt(dbInfo[0]));
					if(statusInfo[3]==1 && statusInfo[2]==0)//if approved and not locked
					{
						int[] moneyInfo= new int[4];
						moneyInfo= dao.getMoney(Integer.parseInt(dbInfo[0]));//get balance
						System.out.println("\n****************************************");
						System.out.println("Welcome " + dbInfo[1] +"\nYour account balance is: " + moneyInfo[1]);
						System.out.println("******************************************");
						manageAccount(statusInfo[0], statusInfo[1]);
						break;
					}
					else //if has account but is not approved yet or is locked
					{
						if(statusInfo[3]==0)//not approved
							System.out.println("Your account has not been approved yet. An administrator will see to it shortly. Goodbye.");
						else//locked
							System.out.println("Your account has been locked by an administrator. Please contact your bank directly"
									+ " for further details.");
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
					;//allow to go back to run loop
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
	public void createAccount()
	{
		boolean approve=true;
		System.out.println("\nPlease enter your desired username: ");
		ArrayList<String> users= dao.getAllUsername();
		String uName=read.nextLine();
		for(String u: users)//check to ensure desired username is not already used
		{
			if(u.equals(uName))//if username already used
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
			dao.addUser(uName, pWord);//add new user to the list of users
			System.out.println("Your acount is under review. We look forward to your business.");
			logger.info(uName +" applied for an account");
		}
	}
	
	/**
	 * Allows user to deposit, withdraw from their account, apply for a loan or pay back a loan.
	 * If user is an admin allows the user to promote other users to admin, review account applications or review loan applications
	 * @param u - The current user's account. Used to access balance and check admin status
	 */
	public void manageAccount(int id, int admin) {
		String[] loginInfo= new String[3];
		int[] statusInfo= new int[4];
		int[] moneyInfo= new int[3];
		moneyInfo=dao.getMoney(id);
		loginInfo= dao.getLogin(id);
		statusInfo= dao.getStatus(id);
		boolean cont=true;
		while(cont==true)
		{
			String input= "";
			//Display user options based on admin status
			System.out.println("***********************************************");
			System.out.println("Enter 1 to deposit money into your account\n"
						+ "Enter 2 to withdraw money from your account\n"
						+ "Enter 3 to apply for a loan\n"
						+ "Enter 4 to pay back a loan");
			if(admin==1)
			{
				System.out.println("Enter 5 to review account creation requests\n"
								+ "Enter 6 to promote a user to admin\n"
								+ "Enter 7 to lock or unlock a user acoount\n"
								+ "Enter 8 to review loan requests");			
			}
			System.out.println("To log out press 0");
			System.out.println("***********************************************");
			//Get user input and act according to above prompt
			input = read.nextLine();
			if(input.equals("1"))//deposit
			{
				moneyInfo[1]=deposit(moneyInfo[1]);//deposit accepted input
				logger.info("Deposit. " + loginInfo[1] + "'s balance is now " + moneyInfo[1]);
				System.out.println("Your new balance is: $" + moneyInfo[1]);
			}
			else if(input.equals("2"))//withdraw
			{	

				moneyInfo[1]=withdraw(moneyInfo[1]);//withdraw accepted input
				logger.info("Withdraw. " + loginInfo[1] + "'s balance is now " + moneyInfo[1]);
				System.out.println("Your new balance is: $" + moneyInfo[1]);
			}
			else if(input.equals("3"))//apply for loan
			{	
				moneyInfo[2]=applyLoan(moneyInfo[2]);
				logger.info("Loan Application attempt. " + loginInfo[1] + "'s loan status is " + moneyInfo[2]);
				if(moneyInfo[2]<0)
					System.out.println("Your loan application for $" +(-1*moneyInfo[2])+ " is being processed");
				else
					System.out.println("You currently have a loan of $"+ moneyInfo[2]);
			}
			else if(input.equals("4"))//pay back
			{	
				moneyInfo=payBack(moneyInfo);
			}
			else if(admin==1 && (input.equals("5") || input.equals("6")|| input.equals("7")|| input.equals("8")))//accept 3, 4 or 5 if admin
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
					loanRequest(id);
				}
			}
			else if (input.equals("0"))//Logout
			{
				dao.logout(statusInfo, moneyInfo);
				cont=false;
			}
			else {
				System.out.println("Input invalid. Please input one of the options given.");
			}
			if(!input.equals("0"))//logout without asking
			{//Check if user would like to take another action
				System.out.println("Would you like to continue on this account? Y/N");
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
						dao.logout(statusInfo, moneyInfo);
					}
				}
			}
		}
	}
	

	/**
	 * Allows user to deposit money into their account.
	 * @param balance - current balance
	 * @return new balance
	 */
	public int deposit(int balance) {

		System.out.println("Please enter the amount to be deposited: ");
		int money=numInput();
		if(money>=0)//if positive or 0 allow deposit
		{
			balance+=money;	
		}
		else
			System.out.println("Invalid input. You can only deposit a positive number");
		return balance;
	}
	
	/**
	 * Allows user to withdraw money from their account checking to ensure there is enough
	 * @param balance - current balance
	 * @return new balance
	 */
	public int withdraw(int balance)
	{
		System.out.println("Please enter the amount to be withdrawn: ");
		int money=numInput();
		if(money>=0)//ensure positive or zero input
		{
			if(money<=balance)
			{
				balance-=money;
			}
			else//if account does not have enough funds
			{
				System.out.println("Your account does not have enough funds \nPlease deposit more funds or withdraw a smaller amount");
			}
		}
		else
			System.out.println("Invalid input. You can only withdraw a positive number");
		return balance;
	}
	
	/**
	 * Allows user to request a loan, checks if they currently have a loan or have one waiting aproval before allowing input
	 * @param curLoan - Current loan status
	 * @return new loan status
	 */
	public int applyLoan(int curLoan)
	{
		if(curLoan==0)//check if already has loan or already applied
		{
			System.out.println("Please enter the amount to be loaned: ");
			int money=numInput();

			return (-1*money);//mark as unapproved with negative
		}
		else
		{
			System.out.println("You cannot apply for a loan unless you do not have a loan and do not have an application under review");
		}
		return curLoan;
	}
	
	
	
	/**
	 * Allows user to look at all unapproved accounts and gives them the option to approve or reject the request or simply
	 * move on without changing the account's status
	 * @param none
	 */
	public void reviewRequest() {
		ArrayList<String> users= dao.getAllUsername();
		for(String u: users)
		{
			String[] id=dao.getLogin(u);
			int[] status=dao.getStatus(Integer.parseInt(id[0]));
			if(status[3]==0)//only show users whose accounts have not been approved yet
			{
				System.out.println(u + " has applied for an accout.\nEnter 1 to approve \nEnter 2 to reject"
						+"\nEnter 3 to move on;");
				String input= "";
				while(!(input.equals("1")||input.equals("2")||input.equals("3")))//loop until valid input
				{
					input=read.nextLine();
					if(input.equals("1"))//approve request
					{
						status[3]=1;//mark to approve
						logger.info(u + "'s account has been approved");
						System.out.println(u + "'s account has been approved\n");
					}
					else if(input.equals("2"))//reject request
					{
						logger.info(u + "'s account has been rejected");
						System.out.println(u + "'s account has been rejected\n");
						status[3]=-1;//mark to remove						
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
				if(status[3]==-1)//remove all rejected requests from users
				{
					dao.remove(status[0]);
				}
				else if(status[3]==1)//update all approved users
				{
					dao.approve(status[0]);
				}
			}
		}
		System.out.println("All requests have been reviewed");
		
	}
	/**
	 * Allows user to input the username of another user which can then be made into an admin
	 * @param none
	 */
	public void promoteUser()
	{
		System.out.println("Please enter the username of the user you would like to promote to administrator: ");
		String input= read.nextLine();
		boolean found=false;//used to tell if username input exists in users
		ArrayList<String> users= dao.getAllUsername();
		for(String u: users)
		{
			if(u.equals(input))//if username is found in users, change user to admin
			{
				found=true;
				String[] loginInfo=dao.getLogin(u);
				dao.promote(Integer.parseInt(loginInfo[0]));
				System.out.println(u + " is now an administrator.");
				logger.info(u + " was promoted to admin");
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
	public void lockOrNot()
	{
		boolean found=false;
		System.out.println("Please input the username of the account you desire:");
		String input= read.nextLine();
		ArrayList<String> users= dao.getAllUsername();
		for(String u: users)
		{
			if(u.equals(input))//if input is found in users
			{
				found=true;
				String[] loginInfo=dao.getLogin(u);
				int[] statusInfo=dao.getStatus(Integer.parseInt(loginInfo[0]));
				//display current status
				System.out.print(u +"'s acount is currently ");
				if(statusInfo[2]==0)
					System.out.print("unlocked.\nWould you like to lock the account? Y/N");
				else
					System.out.print("locked.\nWould you like to unlock the account? Y/N");
				input="";
				
				while(!(input.equals("Y")||input.equals("y")||input.equals("N")||input.equals("n")))//loop until valid input
				{
					input=read.nextLine();
					if(input.equals("Y")||input.equals("y"))//switch account's lock status
					{
						dao.switchLock(statusInfo[0], statusInfo[2]);
						if(statusInfo[2]==0)
							logger.info(u + "'s account has been locked");
						else
							logger.info(u +"'s account has been unlocked");
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
	 *Review requests for loans and allow user to accept or reject the request as well as move on. User cannot approve own request 
	 * @param x - Used so that user cannot approve own loan request
	 */
	public void loanRequest(int curId) {
		ArrayList<String> users= dao.getAllUsername();
		for(String u: users)
		{
			boolean update=false;
			String[] loginInfo=dao.getLogin(u);
			int[] loanInfo=dao.getMoney(Integer.parseInt(loginInfo[0]));
			if(loanInfo[2]<0 && loanInfo[0]!=curId)//only show users whose accounts have not been approved yet
			{
				System.out.println(u + " has applied for a loan of $" + (-1*loanInfo[2]) +".\nEnter 1 to approve "
						+"\nEnter 2 to reject\nEnter 3 to move on;");
				String input= "";
				while(!(input.equals("1")||input.equals("2")||input.equals("3")))//loop until valid input
				{
					input=read.nextLine();
					if(input.equals("1"))//approve request
					{
						loanInfo[2]*=-1;//mark as actual
						loanInfo[1]+=loanInfo[2];//add funds to account
						logger.info(u + "'s loan has been approved");
						System.out.println(u + "'s loan has been approved");
						update=true;
					}
					else if(input.equals("2"))//reject request
					{
						logger.info(u + "'s loan has been rejected");
						System.out.println(u + "'s loan has been rejected");
						loanInfo[2]=0;//allow new application
						update=true;
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
				if(update==true)//if an update to account occurred, update balance and loan of account in database 
					dao.loanUpdate(loanInfo);
			}
		}
		System.out.println("All requests have been reviewed");	
	}
	/**
	 * Allow user to pay back loan. Checks value payed against loan and account balance and detracts from loan and account accordingly
	 * @param u - User account to be accessed
	 */
	public int[] payBack(int[] curMoney)
	{
		if(curMoney[2]>0)//if has a loan
		{
			System.out.println("Your current loan is $" +curMoney[2]);
			System.out.println("Please enter the amount to be payed back: ");
			int money=numInput();
			if(curMoney[1]>=money)//if has enough money in account
			{
				if(money>curMoney[2])//if paying back too much
				{
					curMoney[1]+=(money-curMoney[2]);
					curMoney[1]-=money;
					curMoney[2]=0;
				}
				else//if paying back full amount or less
				{
					curMoney[1]-=money;
					curMoney[2]=curMoney[2]-money;
				}
				System.out.println("Your loan balance is now $" + curMoney[2] + "\nYour account balance is now $" + curMoney[1]);
				logger.info("Loan payment. Loan balance is now "  + curMoney[2]);
				logger.info("Account balance is now " + curMoney[1]);
			}
			else
			{
				System.out.println("You do not currently have the funds to pay. Please deposit more funds to your account.");
			}
		}
		else
			System.out.println("You do not currently have a loan to pay back.");
		return curMoney;
	}
	
	/**
	 * Loop until a valid number input is put in and then return that input
	 * @return number input
	 */
	public int numInput()
	{
		int money =0;
		boolean cont=false;
		while(cont==false)//loop until number input
		{
			try 
			{
				cont=true;
				money= Integer.parseInt(read.nextLine());
			}
			catch(NumberFormatException e)//if not a number, no exception just loop
			{
				System.out.println("Invalid input. Please enter a number.");
				cont=false;
			}
		}
		return money;
	}
}
