package com.revature.main;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.ConnectionUtil;

/**
 * Defines and implements administrative funcitonality, e.g. account access,
 * deletion.
 */
public class Admin {
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;

	public static void displayFunctions(String usrnm, String pswrd) {
		String input;
		boolean wandWielding = true;
		System.out.print("\nHello Admin.");

		try (Connection conn = ConnectionUtil.getConnection()) {
			while (wandWielding) {
				System.out.println("\n> viewAll  > approve  > block  > grantAdmin  > revokeAdmin  > delete  > return ");
				input = Driver.scanner.next();

				switch (input) {
				case "viewAll":
					System.out.println("\nflag key:\t0=unapproved 1=approved 2=blocked 3=inactive");
					String view = "SELECT user_id, username, account_status, admin_status FROM bank_users";
					ps = conn.prepareStatement(view);
					rs = ps.executeQuery();
					
					while(rs.next())		rs.toString();
					continue;

				case "approve":
					String acct = Driver.scanner.next();
					String approve = "UPDATE bank_users SET account_status = 1 WHERE username = '" + acct + "'";
					ps = conn.prepareStatement(approve);
					if(ps.execute())		System.out.println(acct + " has been approved.");
					else 				Driver.log.warn("User approval incomplete.");
					
					continue;

				case "block":
					acct = Driver.scanner.next();
					String block = "UPDATE bank_users SET account_status = 2 WHERE username = '" + acct + "'";
					ps = conn.prepareStatement(block);
					if(ps.execute())		System.out.println(acct + " has been blocked.");
					else 				Driver.log.warn("User block incomplete.");
					
					continue;

				case "grantAdmin":
					acct = Driver.scanner.next();
					String grant = "UPDATE bank_users SET admin_status = 'true' WHERE username = '" + acct + "'";
					
					ps = conn.prepareStatement(grant);
					if(ps.execute())		System.out.println(acct + " has been made an admin.");
					else 				Driver.log.warn("User grant incomplete.");
					
					continue;

				case "revokeAdmin":
					acct = Driver.scanner.next();
					String revoke = "UPDATE bank_users SET admin_status = 'false' WHERE username = '" + acct + "'";
					
					ps = conn.prepareStatement(revoke);
					if(ps.execute())		System.out.println(acct + " is no longer an admin.");
					else 				Driver.log.warn("User revoke incomplete.");
			
					continue;

				case "delete":
					acct = Driver.scanner.next();
					String checkForDelete = "SELECT account_status FROM bank_users WHERE username = '" + acct + "'";
					String delete = "DELETE FROM bank_users WHERE username = '" + acct + "'";
					
					ps = conn.prepareStatement(checkForDelete);
					rs = ps.executeQuery();
					if(rs.getInt("account_status") != 3) System.out.println("Cannot delete user without request.");
					else {
						ps = conn.prepareStatement(delete);
						if(ps.execute())		System.out.println(acct + " is no longer an admin.");
						else 				Driver.log.warn("User revoke incomplete.");						
					}
					
					continue;

				case "return":
					return; // returns to basic account functions

				default:
					System.out.println("\nPlease choose a valid admin command.");
					continue;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Driver.log.trace("Dang.");
		} finally {
			close(ps);
			close(rs);
		}
	}
}
