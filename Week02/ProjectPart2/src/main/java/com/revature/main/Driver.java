package com.revature.main;

import java.util.Scanner;

public class Driver {
	private static boolean BankOn;
	public static Scanner sc = new Scanner(System.in);
	
	// Static constants to eliminate magic numbers;
	static {
		final int CREATE = 1;
		final int LOG_IN = 2;
		final int QUIT = 3;
	}

	public static void main(String... args) {
		Driver bankSession = new Driver();
		setBankOn(true);
		bankSession.welcomePrompt();
	}

	public void welcomePrompt() {
		System.out.println("----------------------------------------------------------------");
		System.out.println("> Welcome to jBank, How can we assist you today?\n");
		System.out.println("Please enter the number that corresponds to your needs.");
		System.out.println("1) CREATE AN ACCOUNT");
		System.out.println("2) LOG_IN");
		System.out.println("3) QUIT");
		sc.nextLine();
	}

	public static boolean isBankOn() {
		return BankOn;
	}

	public static void setBankOn(boolean bankOn) {
		BankOn = bankOn;
	}

}
