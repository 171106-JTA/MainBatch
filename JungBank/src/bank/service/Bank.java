package bank.service;

import bank.accounts.*;
import java.util.Scanner;
import java.io.IOException;

public class Bank {
	public static void printMainMenu() {
		System.out.println("Welcome to Jung Bank.");
		System.out.println("Press corresponding number for your needs: ");
		System.out.println("1) Sign in.");
		System.out.println("2) Create new Account.");
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		int userChoice;
		Account currentUser = new Account();
		Scanner sc = new Scanner(System.in);
		printMainMenu();
		userChoice = sc.nextInt();
		while (userChoice != 1 && userChoice != 2) {
			System.out.println("Please Enter one of the numbers for listed choices");
			userChoice = sc.nextInt();
		}
		if (userChoice == 1) {  
			System.out.println("Existing User Login");
			System.out.println("Username: ");
			String userName = sc.nextLine();
			
		} else if (userChoice == 2) {
		}
		sc.close();
	}
}
