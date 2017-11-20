package com.revature.main;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
//import java.util.ArrayList;
//import java.util.List;

import com.revature.util.ConnectionUtil;

/**
 * Provides basic account functionality e.g. withdrawals, deposits, transfers
 */
public class Account {
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;
	static Integer uid, rid, accountStat;
	static Double dolAmt, balance, rBalance;

	public static void displayBalance(String usrnm, String pswrd) {
		String input;
		DecimalFormat df = new DecimalFormat("0.00");
		boolean isAdmin = false, loggedOut = false;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM bank_users WHERE username = '" + usrnm + "' AND userpassword = '" + pswrd + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				uid = rs.getInt("user_id");
				balance = rs.getDouble("balance");
				isAdmin = Boolean.parseBoolean(rs.getString("admin_status"));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Driver.log.trace("Dang.");
		} finally {
			close(ps);
			close(rs);
		}

		while (!loggedOut) {
			System.out.println("\nYour current karma balance is: $" + df.format(balance));
			System.out.println("\n> withdraw  > deposit  > transfer  > viewHistory  > logout  > delete"
					+ (isAdmin ? "  > admin" : ""));
			input = Driver.scanner.next();

			switch (input) {
			case "withdraw":
				try (Connection conn = ConnectionUtil.getConnection()) {
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
						String updateBalance = "UPDATE bank_users SET balance = " + (balance -= dolAmt)
								+ "WHERE user_id = " + uid;
						String pushTransaction = "INSERT INTO transactions (current_uid, trans_type, trans_amount) VALUES ("
								+ uid + ",'withdrawal'," + dolAmt + ")";
						ps = conn.prepareStatement(updateBalance);
						ps.execute();
						ps = conn.prepareStatement(pushTransaction);
						ps.execute();
						continue;
					}
				} catch (SQLException e) {
					e.printStackTrace();
					Driver.log.trace("Dang.");
					continue;
				} finally {
					close(ps);
					close(rs);
				}
				// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

			case "deposit":
				try (Connection conn = ConnectionUtil.getConnection()) {
					System.out.println("\nHow much do you want to deposit?");

					while (!Driver.scanner.hasNextDouble()) {
						System.out.println("Please input a valid number: ");
						Driver.scanner.next();
					}

					dolAmt = Driver.scanner.nextDouble();
					String updateBalance = "UPDATE bank_users SET balance = " + (balance += dolAmt) + "WHERE user_id = "
							+ uid;
					String pushTransaction = "INSERT INTO transactions (current_uid, trans_type, trans_amount) VALUES ("
							+ uid + ",'deposit'," + dolAmt + ")";

					ps = conn.prepareStatement(updateBalance);
					ps.execute();
					ps = conn.prepareStatement(pushTransaction);
					ps.execute();
					continue;
				} catch (SQLException e) {
					e.printStackTrace();
					Driver.log.trace("Dang.");
					continue;
				} finally {
					close(ps);
					close(rs);
				}
				// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

			case "transfer":
				try (Connection conn = ConnectionUtil.getConnection()) {
					System.out.println("\nExisting Users:\n");
					System.out.println("flag key:\t0=unapproved 1=approved 2=blocked 3=inactive");
					String printAll = "SELECT username, account_status, admin_status FROM bank_users";
					
					ps = conn.prepareStatement(printAll);
					rs = ps.executeQuery();
					User u = null;
					while (rs.next()) {
						u = new User(rs.getString("username"), rs.getInt("account_status"), Boolean.parseBoolean(rs.getString("admin_status")));
						System.out.println(u.toString());
					}

					System.out.println("\nInput recipient and amount: ");
					try {
						String recipient = Driver.scanner.next();
						dolAmt = Driver.scanner.nextDouble();

						String recipientRecord = "SELECT * FROM bank_users WHERE username = '" + recipient + "'";
						ps = conn.prepareStatement(recipientRecord);
						rs = ps.executeQuery();
						
						if(rs.next()) {
							rid = rs.getInt("user_id");
							rBalance = rs.getDouble("balance");
							accountStat = rs.getInt("account_status");
							if (accountStat != 1)
								System.out.println("Account is not available for transfer.");
							else if (uid == rid)
								System.out.println("Cannot transfer to own account.");
							else if (dolAmt < balance) {
								String transferto = "UPDATE bank_users SET balance =" + (balance -= dolAmt)
										+ "WHERE user_id = " + uid;
								String transferfrom = "UPDATE bank_users SET balance =" + (rBalance += dolAmt)
										+ "WHERE user_id = " + rid;
								String pushTransaction = "INSERT INTO transactions VALUES (" + uid + ",'transfer'," + dolAmt + ","
										+ rid + ")";
								ps = conn.prepareStatement(transferto);
								rs = ps.executeQuery();
								ps = conn.prepareStatement(transferfrom);
								rs = ps.executeQuery();
								ps = conn.prepareStatement(pushTransaction);
								rs = ps.executeQuery();
								System.out.println("K$" + dolAmt + " was successfully transfered to: " + recipient);
							} else
								System.out.println("Insufficient funds.");							
						}
					} catch (InputMismatchException e) {
						System.out.println("Please submit transfer in proper format.");
						// LogUtil.log.warn("input not in proper format", e);
						continue;
					}
					continue;
				} catch (SQLException e) {
					e.printStackTrace();
//					Driver.log.trace("Cannot transfer to account that dne.");
					System.out.println("Transfer failed.");
					continue;
				} finally {
					close(ps);
					close(rs);
				}
				// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

			case "viewHistory":
//				List<String> history = new ArrayList<String>();
				Transaction t = null;
				try (Connection conn = ConnectionUtil.getConnection()) {
					String viewTransactions = "SELECT * FROM transactions WHERE current_uid = " + uid;
					ps = conn.prepareStatement(viewTransactions);
					rs = ps.executeQuery(); 

					while (rs.next()) {
						t = new Transaction(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4));
						System.out.println(t.toString());						
					}
					continue;
				} catch (SQLException e) {
					e.printStackTrace();
					Driver.log.trace("Dang.");
					continue;
				} finally {
					close(ps);
					close(rs);
				}
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
				try (Connection conn = ConnectionUtil.getConnection()) {
					if (balance != 0) {
						System.out
								.println("\nAll funds must be withdrawn or transferred before account can be deleted.");
					} else if (isAdmin) {
						System.out.println(
								"Are you sure? Another admin must approve your deletion, and your account will be disabled. (yes/no)");
						if (Driver.scanner.next().equals("yes")) {
							System.out.println("\nYour account has been marked for deletion and is now inactive.");
							String deleteAcct = "UPDATE bank_users SET account_status = 3 WHERE user_id = " + uid;
							ps = conn.prepareStatement(deleteAcct);
							ps.execute();
							return;
						} else
							System.out.println("\nAccount has NOT been marked for deletion.");
						continue;
					} else {
						System.out.println("\nYour account has been marked for deletion and is now inactive.");
						String deleteAcct = "UPDATE bank_users SET account_status = 3 WHERE user_id = " + uid;
						ps = conn.prepareStatement(deleteAcct);
						ps.execute();
						return;
					}
					continue;
				} catch (SQLException e) {
					e.printStackTrace();
					Driver.log.trace("Unable to delete.");
					continue;
				} finally {
					close(ps);
					close(rs);
				}
				// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

			default:
				if (input.equals("\n"))
					continue; // incorrect withdrawal amt ends up here
				System.out.println("\nChoose a valid prompt:");
				continue;
			}
		}
	}
}
