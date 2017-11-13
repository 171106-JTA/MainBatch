package com.revature.wrapper;

public class ShortCircuit {

	public static void main(String[] args) {
		/*
		 * operators for AND & OR : & and |
		 * Technically, & and | are called bitwise operators
		 */
		if ((returnFalse()) & (returnTrue()))
		{
			System.out.println("You can't see me");
		}
		System.out.println("========================================");
		if ((returnTrue()) | (returnFalse()))
		{
			System.out.println("You should see me");
		}
		/*
		 * There exists another way to use and and or
		 * That is with short-circuit operators.
		 * Represented as && and ||, respectively.
		 */
		System.out.println("----------------------------------------");
		if ((returnFalse()) && (returnTrue()))
		{
			System.out.println("You can't see me");
		}
		System.out.println("========================================");
		if ((returnTrue()) || (returnFalse()))
		{
			System.out.println("You should see me");
		}
	}

	public static boolean returnTrue() { 
		System.out.println("returned true");
		return true;
	}
	
	public static boolean returnFalse() { 
		System.out.println("returned false");
		return false;
	}
}
