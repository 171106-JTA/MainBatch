/**
 * Defines and implements administrative funcitonality, e.g. account access,
 * deletion.
 */
public class Admin {
	/**
	 * Displays options and (while) loops to provide continuous (switch-case) access
	 * to them.
	 */
	public static void displayFunctions() {
		String input;
		boolean wandWielding = true;
		System.out.print("\nHello Admin.");

		while (wandWielding) {
			System.out.println(
					"\nWhat would you like to do? \n> viewAll  > approve  > block  > grantAdmin  > revokeAdmin  > delete  > return ");
			input = Driver.scanner.next();

			switch (input) {
			case "viewAll":
				System.out.println("\nflag key:\t0=unapproved 1=approved 2=blocked 3=inactive");
				Driver.userList.sort(null); // sort userlist by status
				for (User all : Driver.userList) {
					System.out.println(all.toString());
				}
				continue;

			case "approve":
				approveAccount(User.returnExisting(Driver.scanner.next())); // passes string → user object → method
				continue;

			case "block":
				blockAccount(User.returnExisting(Driver.scanner.next()));
				continue;

			case "grantAdmin":
				makeAdmin(User.returnExisting(Driver.scanner.next()));
				continue;

			case "revokeAdmin":
				revokeAdmin(User.returnExisting(Driver.scanner.next()));
				continue;

			case "delete":
				User xx = User.returnExisting(Driver.scanner.next());
				if (xx.getStatusFlag() == 3)
					Driver.userList.remove(xx);
				else
					System.out.println("Cannot delete user without request.");
				continue;

			case "return":
				return; // returns to basic account functions

			default:
				System.out.println("\nPlease choose a valid admin command.");
				continue;
			}
		}

	}

	/**
	 * Takes User object and sets status flag to approved.
	 *
	 * @param u
	 */
	private static void approveAccount(User u) {
		if (!u.getUserID().equals(" ")) {
			u.setStatusFlag(1);
			System.out.println(u.getUserID() + " has been approved.");
		} else
			System.out.println("\nCannot approve a nonexistent account."); // returnExisting() returns blank usr if id
																			// not found
	}

	/**
	 * Takes User object and sets status flag to blocked.
	 *
	 * @param u
	 */
	private static void blockAccount(User u) {
		if ((!u.getUserID().equals(" ")) && (u.getStatusFlag() == 1)) {
			u.setStatusFlag(2);
			System.out.println("\n" + u.getUserID() + " has been blocked.");
		} else
			System.out.println("\nCannot block a nonexistent/unapproved account.");
	}

	/**
	 * Takes User object and sets admin flag to granted.
	 *
	 * @param u
	 */
	private static void makeAdmin(User u) {
		if ((!u.getUserID().equals(" ")) && (u.getStatusFlag() == 1)) {
			u.setAdmin(true);
			System.out.println("\n" + u.getUserID() + " has been made an admin.");
		} else
			System.out.println("\nCannot grant admin status to a nonexistent/unactivated account.");
	}

	/**
	 * Takes User object and sets admin flag to revoked.
	 *
	 * @param u
	 */
	private static void revokeAdmin(User u) {
		if ((!u.getUserID().equals(" ")) && (u.getStatusFlag() == 1)) {
			u.setAdmin(false);
			System.out.println("\n" + u.getUserID() + " is no longer an admin.");
		} else
			System.out.println("\nCannot revoke admin status from a nonexistent/unactivated account.");
	}

}
