package com.revature.app.view;

import com.revature.app.Menu;
import com.revature.app.MyBank;

public class CustomerView implements View {

	@Override
	public void run() {
		Menu.printUser(MyBank.data);

	}

}
