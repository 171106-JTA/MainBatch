package com.revature.apebank;

import com.revature.data.ProcessData;
import com.revature.ui.UserInterface;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int option = UserInterface.splashScreen();
		
		BankOfTheApes bota = BankOfTheApes.getBank();
		
		bota.displayOperationScreen(option);
		
		ProcessData.serialize(bota.getUsers());
	}

}
