package com.revature.model.session;

import java.text.NumberFormat;
import java.util.Scanner;

import com.revature.dao.jBankDAO;
import com.revature.dao.jBankDAOImpl;
import com.revature.model.account.User;
import com.revature.model.account.UserLevel;
import com.revature.model.transaction.TransType;
import com.revature.model.transaction.Transaction;

public class UserSession {
	// constant for user banking menu
	final public int STATEMENT = 1;
	final public int DEPOSIT = 2;
	final public int WITHDRAW = 3;
	final public int LOAN = 4;
	final public int QUIT = 5;
	private Scanner sc = new Scanner(System.in);
	private int BankChoice;
	private User currentUser;
	
	public UserSession() {
		super();
	}
	public UserSession(User user) {
		setCurrentUser(user);
	}
	
	public void getSession() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		System.out.println("You currently have " + formatter.format(currentUser.getBalance()) + " in you Account.");
		if(currentUser.getUserLevel().equals(UserLevel.VIP)) {
			System.out.println("Oh you beautiful person!");
		}
		do {
			bankmenu();
			while (getBankChoice() < STATEMENT || getBankChoice() > QUIT) {
				System.out.println("Please select number from one of the choice and press /'Enter/'");
				bankmenu();
			}
			switch(getBankChoice()) {
			case STATEMENT:
				getStmt();
				break;
			case DEPOSIT:
				makeDeposit();
				break;
			case WITHDRAW:
				makeWithdraw();
				break;
			case LOAN:
				applyLoad();
				break;
			case QUIT:
				return;
			}
		} while(true); //infinite, only way to quit is by selecting quit from menu
	}
	private void getStmt() {
		System.out.println("Here are your statements");
		jBankDAO dao = new jBankDAOImpl();
		for(Transaction trn : dao.getTransactionsByUID(currentUser)) {
			System.out.println(trn);
		}
		
	}
	private void applyLoad() {
		System.out.println("Coming soon feature on the app.");
		System.out.println("Please call 855-100-1500 to talk to our representative or");
		System.out.println("visit https://www.jbank.io for more information.\nPress Enter to go back.\n\n");
		System.out.println("Download our app on Play Store https://play.google.com/store/apps/dev?id=5700313618786177705");
		sc.nextLine();
	}
	
	private void makeDeposit() {
		double amount;
		jBankDAO dao = new jBankDAOImpl();
		System.out.print("Enter amount of dollars to deposit: $");
		amount = sc.nextDouble();
		dao.createTransaction(TransType.DEPOSIT, amount, currentUser);
		dao.balanceUpdate(TransType.DEPOSIT, amount, currentUser);
		System.out.println("Deposit of $" + amount + " has been made");
		
	}
	private void makeWithdraw() {
		double amount;
		jBankDAO dao = new jBankDAOImpl();
		System.out.print("Enter amount of dollars to Withdraw: $");
		amount = sc.nextDouble();
		if(currentUser.getBalance()<amount) {
			System.out.println("Cannot withdraw more than your balance.");
			return;
		}
		dao.createTransaction(TransType.WITHDRAW, amount, currentUser);
		dao.balanceUpdate(TransType.WITHDRAW, amount, currentUser);
		System.out.println("Withdraw of $" + amount + " has been made");
		
	}
	
	public void bankmenu() {
			System.out.println("> What would you like to do?");
			System.out.println("1> SEE STATEMENT");
			System.out.println("2> DEPOSIT");
			System.out.println("3> WITHDRAW");
			System.out.println("4> LOAN");
			System.out.println("5> QUIT");
			System.out.print("> ");
			setBankChoice(sc.nextInt());
			sc.nextLine();
	}


	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public int getBankChoice() {
		return BankChoice;
	}

	public void setBankChoice(int bankChoice) {
		BankChoice = bankChoice;
	}
}
