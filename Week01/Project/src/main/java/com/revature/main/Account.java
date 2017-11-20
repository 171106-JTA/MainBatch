package com.revature.main;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.InputMismatchException;


import com.revature.util.ConnectionUtil;

/**
 * Provides basic account functionality e.g. withdrawals, deposits, transfers
 */
public class Account {
	public static PreparedStatement ps = null;
	public static PreparedStatement ds = null;
	public static ResultSet rs = null;

	public static void displayBalance(String usrnm, String pswrd) {
		Integer uid, rid, accountStat;
		Double dolAmt, balance, rBalance;
		String input;
		DecimalFormat df = new DecimalFormat("0.00");
		boolean isAdmin = false, loggedOut = false;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM bank_users WHERE username = '" + usrnm + "' AND userpassword = '" + pswrd + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			uid = rs.getInt("current_user_id");
			balance = rs.getDouble("balance");
			isAdmin = Boolean.parseBoolean(rs.getString("admin_status"));
			 
			

			while (!loggedOut) {
				System.out.println("\nYour current karma balance is: $" + df.format(balance));
				System.out.println("\n> withdraw  > deposit  > transfer  > viewHistory  > logout  > delete" + (isAdmin ? "  > admin" : ""));
				input = Driver.scanner.next();

				switch (input) {
				case "withdrawal":
					System.out.println("\nHow much do you want to withdraw?");

					while (!Driver.scanner.hasNextDouble()) {
						System.out.println("Please input a valid number: ");
						Driver.scanner.next();
					} // InputMismatchException custom message

					dolAmt = Driver.scanner.nextDouble();

					if (dolAmt > balance) {
						System.out.println("\nInsufficient funds; please choose another transaction:");
						continue;
					} else {
						String updateBalance = "UPDATE bank_users SET balance = " + (balance -= dolAmt) + "WHERE user_id = " + uid;
						String pushTransaction = "INSERT INTO transactions (current_user_id, trans_type, trans_amount) VALUES (" + uid + ",'withdrawal'," + dolAmt +")";
						ps = conn.prepareStatement(updateBalance);	ps.execute();
						ps = conn.prepareStatement(pushTransaction);	ps.execute();
						continue;
					}

					// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

				case "deposit":
					System.out.println("\nHow much do you want to deposit?");

					while (!Driver.scanner.hasNextDouble()) {
						System.out.println("Please input a valid number: ");
						Driver.scanner.next();
					}

					dolAmt = Driver.scanner.nextDouble();
					String updateBalance = "UPDATE bank_users SET balance = " + (balance += dolAmt) + "WHERE user_id = " + uid;
					String pushTransaction = "INSERT INTO transactions (current_user_id, trans_type, trans_amount) VALUES (" + uid + ",'deposit'," + dolAmt + ")";
					ps = conn.prepareStatement(updateBalance);	ps.execute();
					ps = conn.prepareStatement(pushTransaction);	ps.execute();
					continue;

				// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

				case "transfer":
					System.out.println("Existing Users:\n");
					System.out.println("\nflag key:\t0=unapproved 1=approved 2=blocked 3=inactive");
					String printAll = "SELECT username, account_status FROM bank_users";
					ps = conn.prepareStatement(printAll);
					rs = ps.executeQuery();
					while(rs.next()) {
						rs.toString();
					}
					
					System.out.println("\nInput recipient and amount: ");
					try {
						String recipient = Driver.scanner.next();
						dolAmt = Driver.scanner.nextDouble();

						String recipientRecord = "SELECT * FROM bank_users WHERE username = '" + recipient + "'";
						ps = conn.prepareStatement(recipientRecord);
						rs = ps.executeQuery();
						rid = rs.getInt("recipient_id");
						rBalance = rs.getDouble("balance");
						accountStat = rs.getInt("account_status");
						
						if (accountStat != 1) 		System.out.println("Account is not available for transfer.");
						else if (uid == rid) 		System.out.println("Cannot transfer to own account.");
						else if (dolAmt < balance) {
							String transferto = "UPDATE bank_users SET balance =" + (balance -= dolAmt) + "WHERE user_id = " + uid;
							String transferfrom = "UPDATE bank_users SET balance =" + (rBalance += dolAmt) + "WHERE user_id = " + rid;
							pushTransaction = "INSERT INTO transactions VALUES (" + uid + ",'transfer'," + dolAmt + "," + rid + ")";
							ps = conn.prepareStatement(transferto);		ps.execute();
							ps = conn.prepareStatement(transferfrom);		ps.execute();
							ps = conn.prepareStatement(pushTransaction);	ps.execute();
							System.out.println("K$" + dolAmt + " was successfully transfered to: " + recipient);
						} else 						System.out.println("Insufficient funds.");
					} catch (InputMismatchException e) {
						System.out.println("Please submit transfer in proper format.");
						// LogUtil.log.warn("input not in proper format", e);
						continue;
					}
					continue;

				// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
					
				case "viewHistory":
					String viewTransactions = "SELECT * FROM transactions WHERE current_user_id = " + uid;
					rs = ps.executeQuery(viewTransactions); //if works, can update rest
					
					while(rs.next()) {
						System.out.println(rs.toString());
					}
					continue;
				// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

				case "admin":
					if (isAdmin)
						Admin.displayFunctions(usrnm, pswrd);
					else
						System.out.println("\nNice try, non-admin Σ( ￣□￣||).");
					continue;

				// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

				case "logout":
					loggedOut = true;
					System.out.println("\nHave a good day!");
					return; // back to driver, finally still closes resources

				// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

				case "delete":
					if (balance != 0) {
						System.out.println("\nAll funds must be withdrawn or transferred before account can be deleted.");
					} else if (isAdmin) {
						System.out.println(
								"Are you sure? Another admin must approve your deletion, and your account will be disabled. (yes/no)");
						if (Driver.scanner.next().equals("yes")) {
							System.out.println("\nYour account has been marked for deletion and is now inactive.");
							String deleteAcct = "UPDATE bank_users SET account_status = 3 WHERE user_id = " + uid;
							ps = conn.prepareStatement(deleteAcct);	ps.execute();
							return;
						} else
							System.out.println("\nAccount has NOT been marked for deletion.");
						continue;
					} else {
						System.out.println("\nYour account has been marked for deletion and is now inactive.");
						String deleteAcct = "UPDATE bank_users SET account_status = 3 WHERE user_id = " + uid; 
						ps = conn.prepareStatement(deleteAcct);	ps.execute();
						return;
					}
					continue;

				// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

				default:
					if (input.equals("\n"))
						continue; // incorrect withdrawal amt ends up here
					System.out.println("\nChoose a valid prompt:");
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
