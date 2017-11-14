package com.peterson.bowsercastle_view;

import com.peterson.bowsercastle_model.User;

public class AdminUI extends UI {

	@Override
	public void printOptions(final User king) {
		System.out.println("(0) Deposit coins" +
				"\n(1) Withdraw coins" +
				"\n(2) Level up" +
				"\n(3) Attempt to kill king" + king.getName() +
				"\n(4) Verify a new member" +
				"\n(5) Promote a member to admin" +
				"\n(6) View all members on file" +
				"\n(7) Lock/Unlock a user's account" + 
				"\n(8) View and approve or deny a loan application" + 
				"\n(9) Apply for a loan" +
				"\n(x) Log Out");
	}
}