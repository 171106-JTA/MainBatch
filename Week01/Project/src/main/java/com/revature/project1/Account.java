package com.revature.project1;

import java.text.DecimalFormat;
import java.util.InputMismatchException;

import com.revature.log.LogUtil;

/**
 * Provides basic account functionality
 * e.g. withdrawals, deposits, transfers
 */
public class Account {
	/**
	 * Main transaction prompt that checks if account is 
	 * currently logged in, has admin rights; 
	 * prints current balance; and lists basic options.
	 * 
	 * Local variable input keeps track of console commands.
	 * @param u is user object passed from driver upon successful login
	 */
	public static void displayBalance(User u) {
		String 	input, u2;
		boolean loggedOut = false;
		boolean isAdmin = u.getAdmin();
		double 	d, dolAmt = u.getBalance();
		DecimalFormat df = new DecimalFormat("0.00");

		while(!loggedOut) {
			System.out.println("\nYour current karma balance is: $" + df.format(dolAmt));
			System.out.println("What type of transaction would you like to make? \n> withdrawal  > deposit  > transfer  > viewHistory  > logout  > delete" + (isAdmin ? "  > admin" : ""));
			input = Driver.scanner.next();

			switch(input) {
			case "withdrawal":
				System.out.println("\nHow much do you want to withdraw?");

				while(!Driver.scanner.hasNextDouble()) {
						System.out.println("Please input a valid number: ");
						Driver.scanner.next();
				}//InputMismatchException custom message
				
				d = Driver.scanner.nextDouble();
				
				if (!enoughFunds(u,d)) {
					System.out.println("\nInsufficient funds; please choose another transaction:");
					continue;
				} else {
					u.setBalance(dolAmt -=d );
					continue;
				}
			
			//––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
					
			case "deposit":
				System.out.println("\nHow much do you want to deposit?");

				while(!Driver.scanner.hasNextDouble()) {
						System.out.println("Please input a valid number: ");
						Driver.scanner.next();
				}

				dolAmt += Driver.scanner.nextDouble();
				u.setBalance(dolAmt);
				continue;

			//––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
			
			case "transfer":
				System.out.println("Existing Users:\n");
				System.out.println("\nflag key:\t0=unapproved 1=approved 2=blocked 3=inactive");
				for (User j : Driver.userList) System.out.println(j.toString());
				System.out.println("Please input recipient and amount (name, number):");
				try {
					u2 = Driver.scanner.next(); d = Driver.scanner.nextDouble();
					if (enoughFunds(u, d))	dolAmt -= transfer(u, u2, d);
				} catch (InputMismatchException e) {
					System.out.println("Please submit transfer in proper format."); 	
//					LogUtil.log.warn("input not in proper format", e);
					continue;
				}
				continue;

			//––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
			
			case "admin":
				if(isAdmin) Admin.displayFunctions();
				else System.out.println("\nNice try, non-admin Σ( ￣□￣||).");
				continue;

			//––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
			
			case "logout":
				System.out.println("\nHave a good day!");
				return; // back to driver
			
			//––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
			
			case "delete":
				if(dolAmt != 0) {
					System.out.println("\nAll funds must be withdrawn or transferred before account can be deleted.");
				} else if(isAdmin) {
					System.out.println("Are you sure? Another admin must approve your deletion, and your account will be disabled. (yes/no)");
					if (Driver.scanner.next().equals("yes")) {
						System.out.println("\nYour account has been marked for deletion and is now inactive.");
						u.setStatusFlag(3);
						return;
					}
					else System.out.println("\nAccount has NOT been marked for deletion.");
					continue;
				} else {
					System.out.println("\nYour account has been marked for deletion and is now inactive.");
					u.setStatusFlag(3);
					return;
				}
				continue;

			//––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

			default:
				if(input.equals("\n"))	continue;	//incorrect withdrawal amt ends up here
				System.out.println("\nPlease choos a valid prompt:");
				continue;
			}
		}
	}

	/**
	 * Makes sure
	 * @param u user object has
	 * @param d amount available in their account and
	 * @returns true if they do.
	 * 
	 * Must return true for transfer to go through.
	 */
	private static boolean enoughFunds(User u, double d) {
		if (u.getBalance() < d) return false;
		return true;
	}

	/**
	 * Transfers funds from
	 * @param u to
	 * @param u2 provided desired amount
	 * @param d exists and can be used and
	 * @returns the amount transfered to update the 
	 * current accounts running balance.
	 * 
	 * Prevents users from transferring funds to
	 * inactive accounts, or to themselves.
	 */
	private static double transfer(User u, String u2, double d) {
		User recipient = User.returnExisting(u2);
		
		if (recipient.getStatusFlag()!=1) {
			System.out.println("User's account is not available for transfer, please try again later.");
			return 0;
		} else if(recipient.equals(u)) {
			System.out.println("Cannot transfer to own account;");
			return 0;
		}

		u.setBalance(u.getBalance()-d);
		recipient.setBalance(recipient.getBalance()+d);
		System.out.println("$" + d + " was successfully transfered to: " + recipient.getUserID());
		return d;	
	}
}
