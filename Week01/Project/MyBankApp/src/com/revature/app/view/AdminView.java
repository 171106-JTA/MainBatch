package com.revature.app.view;

import com.revature.app.Menu;
import com.revature.app.MyBank;

public class AdminView implements View {
	@Override
	public void run() {
		String input = "";
		
		Menu.printUser(MyBank.data);
		
		//while (input.toLowerCase().equals("exit")) {
			
		//}		
	}
}
