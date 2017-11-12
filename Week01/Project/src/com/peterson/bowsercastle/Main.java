package com.peterson.bowsercastle;

/**
 * Driver class for Bowser's castle.
 * @author Alex Peterson
 */
public final class Main {

	private Main() {
		//this class cannot be instantiated
	}

	public static void main(final String[] args) {
		final BowserCastle bc = new BowserCastle();
		bc.enterCastle();
	}
}