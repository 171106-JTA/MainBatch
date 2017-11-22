package com.revature.bank;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class UserMenu implements Menu {
	private static final int[] MENU_SELECTIONS = { 1, 2, 3 };
	private static final String[] MENU_SELECTIONS_TEXT = {
		"Withdraw money",
		"Deposit money",
		"Exit"
	};
	protected static final String[] EXIT_KEYWORDS = {
		"exit", "terminate"	
	};
	private static final int NO_ACCOUNT = -12,
		NO_ACCOUNT_SELECTED = -1;
	
	protected Map<Integer, String> options;
	protected User user;
	protected Scanner scanner;
	
	public UserMenu(User u)
	{
		// invoke constructor with default menu selections and selectionsText
		this(u, MENU_SELECTIONS, MENU_SELECTIONS_TEXT);
	}
	
	public UserMenu(User u, int[] selections, String[] selectionsText) throws IllegalArgumentException
	{
		user = u;
		// initialize the Scanner
		scanner = new Scanner(System.in);
		// write selections and selectionsText to options
		options = new LinkedHashMap<Integer, String>();
		// guarantee that selections and selectionsText are the same length
		if (selections.length != selectionsText.length) 
			throw new IllegalArgumentException(String.format(
				"selections,selectionsText not of same length.\n\nselections.length == %d\nselectionsText.length == %d",
				selections.length,
				selectionsText.length));
		// add in all these options, and search for the exit/terminate option
		boolean foundExitOption = false;
		for(int i = 0; i < selections.length; i++) {
			// writing to options
			options.put(selections[i], selectionsText[i]);
			// Searching for an exit/terminate option (via those keywords in the selection text name 
			if (!foundExitOption)
			{
				for (String exitKeyword : EXIT_KEYWORDS)
				{
					if (selectionsText[i].toLowerCase().contains(exitKeyword)) foundExitOption = true; 
				}
			}
		}
		// if the exit option hasn't been found...
		if (!foundExitOption)
		{
			// let's add in an "Exit" (or "Terminate") option
			options.put(selections[selections.length - 1] + 1, "Exit");
		}
		// if the user doesn't have an Account, silently create one
		if ((user.getAccounts() == null) || (user.getAccounts().isEmpty())) user.getAccounts().add(new Account());
	}
	
	@Override
	public void display() {
		// if user is not active
		if (!user.getState().equals(User.ACTIVE))
		{
			System.out.printf("Your account is %s\n", user.getState());
			return;
		}
		// Here's where we display the list to the user
		System.out.println("Please enter : \n");
		for (Integer i : options.keySet())
		{
			System.out.printf("%d for %s\n", i, options.get(i));
		}
	}

	@Override
	public void handleInput() {
		// For now, this should only show if user is active
		if (!user.getState().equals(User.ACTIVE)) return;
		// This is going to keep going until user decides to exit
		boolean keepGoing = true;
		while (keepGoing)
		{
			try
			{
				// get input from user
				int userChoice = Integer.parseInt(scanner.nextLine());
				// get the text for that option they chose, in lowercase
				String userChoiceText = options.get(userChoice).toLowerCase();
				// if that text contains 'withdraw', bring up withdraw prompt for them.
				if (userChoiceText.contains("withdraw"))
				{
					withdrawPrompt();
				}
				// if that text contains 'deposit', bring up deposit prompt for them. 
				else if (userChoiceText.contains("deposit"))
				{
					depositPrompt();
				}
				// if that text contains any of the EXIT_KEYWORDS, we exit this loop
				else 
				{
					for (String stop : EXIT_KEYWORDS)
						if (userChoiceText.contains(stop)) keepGoing = false;
				}
				// if user has decided to keep going
				if (keepGoing)
				{
					// display this menu again
					display();
				}
			}
			catch (NumberFormatException | NullPointerException e)
			{
				System.out.println("You have entered an invalid selection. Please try again!");
			}
		}
		System.out.println("Thank you for using the Revature banking app! Goodbye!");
	}

	/**
	 * gets the account the user is to do transactions on
	 */
	private int fetchAcctNumberFromUser()
	{
		// display Accounts the user has access to 
		Accounts userAccounts = user.getAccounts();
		// if there are no accounts for this user to choose from, return sentry value for that
		if (userAccounts.size() == 0) return NO_ACCOUNT;
		// if there's just one Account, return its number
		if (userAccounts.size() == 1) {
			System.out.printf("Bal: $%.2f\n", userAccounts.get(0).getBalance());
			return userAccounts.get(0).getNumber();
		}
		// display the Accounts to the end user
		System.out.println("We have the following accounts for you:\n\n");
		for (Account a : userAccounts)
		{
			System.out.printf("\t%d with balance %.2f\nWould you like to use it?",
				a.getNumber(),
				a.getBalance());
			if (userEnteredYes()) return a.getNumber();
		}
		return NO_ACCOUNT_SELECTED; 
	}
	
	/**
	 * displays, and handles input on, withdraw prompt
	 */
	private void withdrawPrompt() {
		boolean keepGoing = false;
		// print title
		System.out.println("Withdrawal prompt");
		System.out.println(Application.LINE_SEPARATOR + "\n");
		// have user select an Account to withdraw from
		int acctNumber = fetchAcctNumberFromUser();
		if (acctNumber == NO_ACCOUNT_SELECTED)
		{
			System.out.println("You have not made a selection. Terminating withdrawal mode...");
			return;
		}
		// ensure there's actually money to withdraw
		double balance = user.getAccounts().getAccountByID(acctNumber).getBalance(); 
		if (balance == 0)
		{
			System.out.println("There's no money to withdraw. Exiting withdrawal mode...");
			return;
		}
		// we allow a withdrawal on that Account one or more times.
		do
		{
			System.out.printf("You have an account balance of $%.2f", balance);
			System.out.print("Enter the amount you would like to withdraw: $");
			String userInput = scanner.nextLine();
			double withdrawal = Double.parseDouble(userInput);
			// attempt withdrawal, display balance, and ask them if they would like to continue withdrawing
			try
			{
				// attempt withdrawal, but using their list of Accounts to facilitate serialization process
				user.getAccounts().getAccountByID(acctNumber).withdraw(withdrawal);
				// display balance and ask the user if they would like to keep withdrawing
				System.out.printf("Withdrawal complete! Your new balance is: $%.2f. Would you like to keep withdrawing?",
					user.getAccounts().getAccountByID(acctNumber).getBalance());
			}
			catch (IllegalArgumentException e)
			{
				System.out.println(e.getMessage());
			}
			finally
			{
				keepGoing = userEnteredYes();
			}
		}
		while (keepGoing);
	}
	
	/**
	 * Displays, and handles input on, deposit prompt
	 */
	private void depositPrompt()
	{
		boolean keepGoing = false;
		// print title 
		System.out.println("Deposit prompt");
		System.out.println(Application.LINE_SEPARATOR + "\n");
		// have user select an Account to withdraw from
		int acctNumber = fetchAcctNumberFromUser();
		if (acctNumber == NO_ACCOUNT_SELECTED)
		{
			System.out.println("You have not made a selection. Terminating withdrawal mode...");
			return;
		}
		do 
		{
			// prompt user for amount to deposit into the account
			System.out.print("Please enter the amount you wish to deposit: $");
			String userInput = scanner.nextLine();
			Double deposit = Double.parseDouble(userInput);
			try {
				// attempt deposit
				user.getAccounts().getAccountByID(acctNumber).deposit(deposit);
				// inform user that deposit was successful and ask them if they wish to keep depositing
				System.out.printf("Deposit successful! The new balance on this account is $%.2f . Make another deposit? "
					, user.getAccounts().getAccountByID(acctNumber).getBalance());
			} 
			// if they're trying to deposit so much money that they break the numeric stability of addition
			catch (IllegalArgumentException e) {
				// inform them
				System.out.println(e.getMessage());
			}
			finally
			{
				keepGoing = userEnteredYes();
			}
		}
		while (keepGoing);
	}
	
	/**
	 * silently listens for a "y", a "Y", or a string that contains some form of "yes", from the user
	 * @return whether or not user has entered said string
	 */
	protected boolean userEnteredYes()
	{
		return userEnteredYes("");
	}
	
	/**
	 * "silently" listens for user input
	 * @param messageToUser : the message to tell the end user, if any. If no message should be displayed, set this to empty string
	 * @return : Whether or not the user entered a string that contains some form of "yes", or if the string is some form of "y"
	 */
	protected boolean userEnteredYes(String messageToUser)
	{
		if (!messageToUser.isEmpty()) System.out.println(messageToUser);
		// get next line in input stream, to lower case
		String userInput = scanner.nextLine().trim().toLowerCase();
		// return whether or not that line contains the word "yes"
		return userInput.contains("yes")
				// && (userInput.indexOf("yes") == 0)
				|| (userInput.equals("y"));
	}
	
}
