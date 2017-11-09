package bank.service;

import bank.accounts.*;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

enum UserOption {
	LOG_IN, CREATE, QUIT
}

public class Bank {
	private UserOption userChoice = UserOption.LOG_IN;

	public UserOption getUserChoice() {
		return userChoice;
	}

	public void setUserChoice(UserOption userChoice) {
		this.userChoice = userChoice;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Account currentUser = new Account();
		Scanner sc = new Scanner(System.in);
		Bank myBank = new Bank();
		while(myBank.getUserChoice() != UserOption.QUIT) {
			System.out.println("Up to here works now setting user choice to Quit.");
			myBank.setUserChoice(UserOption.QUIT);
		
		}
		//
		//
		// userChoice = sc.nextInt();
		// while (userChoice != 1 && userChoice != 2 && userChoice != 3) {
		// System.out.println("Please Enter one of the numbers for listed choices");
		// userChoice = sc.nextInt();
		// }
		// sc.nextLine();
		// while (userChoice != 3) {
		// if (userChoice == 1) {
		// System.out.println("Existing User Login");
		// System.out.print("Username: ");
		// String userName = sc.nextLine();
		// sc.nextLine();
		// System.out.print("Password: ");
		// String password = sc.nextLine();
		// sc.nextLine();
		//
		// } else if (userChoice == 2) {
		// System.out.println("Create User Account ver 0.1");
		// System.out.print("Username: ");
		// String userName = sc.nextLine();
		// System.out.print("Password: ");
		// String password = sc.nextLine();
		// System.out.print("First Name: ");
		// String firstName = sc.nextLine();
		// System.out.print("Last Name: ");
		// String lastName = sc.nextLine();
		// System.out.print("PIN : ");
		// int pinNumber = sc.nextInt();
		// User newUser = new User(userName, password, firstName, lastName, pinNumber);
		// System.out.println("New user added, new user information:" + newUser);
		//
		// FileOutputStream fos = new FileOutputStream("ser/" + newUser.getUsername() +
		// ".ser");
		// try {
		//
		// ObjectOutputStream out = new ObjectOutputStream(fos);
		// out.writeObject(newUser);
		// out.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// if (fos != null) {
		// fos.close();
		// }
		// }
		// }
		// }
		// printMainMenu();
		// sc.close();
		// }
	}
}