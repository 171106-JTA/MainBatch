package bank.service;

import bank.accounts.*;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

enum UserOption {
	HIDDEN, LOG_IN, CREATE, QUIT
}

public class Bank {
	public UserOption userChoice;
	
	public void mainMenu() {
		Scanner sc = new Scanner(System.in);
			
		System.out.println("-----------------------------------");
		System.out.println("|      Welcome to Jung Bank!      |");
		System.out.println("-----------------------------------");
		System.out.println("Select a number from the menu.");
		System.out.println("1) LOG_IN ");
		System.out.println("2) CREATE AN ACCOUNT");
		System.out.println("3) QUIT");
		userChoice = UserOption.values()[sc.nextInt()];
		System.out.println(userChoice);
	}
	public static void main(String[] args) {
		Bank myBank = new Bank();
		myBank.mainMenu();
	}
}