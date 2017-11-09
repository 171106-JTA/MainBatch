package com.peterson.bowsercastle;

/**
 * 
 * @author Alex Peterson
 *
 */
public final class Main {

	private Main() {
		//this class cannot be instantiated
	}

	public static void main(String[] args) {
		final BowserCastle bowserCastle = new BowserCastle();
		bowserCastle.enterCastle();
	}
}