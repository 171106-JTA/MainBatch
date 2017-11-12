package com.revature.apebank;

import com.revature.data.Logging;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BankOfTheApes bota = BankOfTheApes.getBank();
		
		bota.setUp();
	}

}
