package bowsercastle_view;

import bowsercastle_model.User;

public class KingUI extends UI {

	@Override
	public void printOptions(User king) {
		System.out.println("(0) Deposit coins" +
				"\n(1) Withdraw coins" +
				"\n(2) Level up" +
				"\n(3) Verify a new member" +
				"\n(4) Promote a member to admin" +
				"\n(5) View all members on file" +
				"\n(6) Lock/Unlock a user's account" + 
				"\n(7) View and approve or deny a loan application" + 
				"\n(8) Apply for a loan" +
				"\n(x) Log Out.");
	}
}
