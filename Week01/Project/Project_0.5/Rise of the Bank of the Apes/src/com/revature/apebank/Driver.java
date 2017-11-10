package com.revature.apebank;

import com.revature.data.ProcessData;
import com.revature.ui.Splash;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int option = Splash.Screen();
		
		BankOfTheApes bota = BankOfTheApes.getBank();
		
		bota.displayOperationScreen(option);
		
		ProcessData.serialize(bota.getUsers());
	}

}
