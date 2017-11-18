package bowsercastle_view;

import bowsercastle_model.User;

public class MemberUI extends UI {

	@Override
	public void printOptions(final User king ) {
		System.out.println("(0) Deposit coins" +
				"\n(1) Withdraw coins" +
				"\n(2) Level up" + 
				"\n(3) Attempt to kill king" + king.getName() +
				"\n(4) Apply for a loan" +
				"\n(x) Log Out.");
	}
}
