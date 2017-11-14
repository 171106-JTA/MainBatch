package com.peterson.bowsercastle_controller;

import com.peterson.bowsercastle_model.BowserCastle;

/**
 * Driver class for Bowser's castle.
 * @author Alex Peterson
 */
public final class BowserCastleMain {
	
	private BowserCastleMain() {
		//this class cannot be instantiated
	}

	public static void main(final String[] args) {
		final BowserCastle bc = new BowserCastle();
		bc.enterCastle(); //run the program
	}
}