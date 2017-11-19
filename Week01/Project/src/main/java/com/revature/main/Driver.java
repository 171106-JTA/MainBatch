package com.revature.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.log.LogUtil;
import com.revature.util.ConnectionUtil;
import static com.revature.util.CloseStreams.close;


/**
 * open/close connection to: 
 * 1. login/confirm user existence 
 * 2. create new user in db
 */
public class Driver {
	public static Scanner scanner = new Scanner(System.in);
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;
	
	public static void main(String[] args) throws Exception {


		boolean running = true;
		System.out.println("Welcome to the National Bank of Second Chances; this is your third~!");
		while (running) {
			System.out.println("\n> login  > create  > quit");
			String input = scanner.next();

			switch (input) {
			case "login":
				
				try (Connection conn = ConnectionUtil.getConnection()) {
					String sql = "SELECT * FROM bank_users WHERE username=? AND userpassword=?";

					System.out.print("> username: ");
					String username = scanner.next();
					System.out.print("> password: ");
					String password = scanner.next();

					ps = conn.prepareStatement(sql);
					ps.setString(1, username);
					ps.setString(2, password);

					rs = ps.executeQuery();
					if (rs.next()) { 			//false when rs is empty and user dne; if true, should only return one record
						Integer active = Integer.parseInt(rs.getString("account_status"));
						if 		(active == 1)	Account.displayBalance();
						else if (active == 0)	System.out.println("Account has not yet been activated. Try again later.");
						else if (active == 2)	System.out.println("Account has been blocked; wait for reactivation.");
						else if (active == 3)	System.out.println("Account has been marked for deletion and is no longer active.");

						continue;
					}
					System.out.println( "\nWrong credentials provided, or account does not yet exist. To create one, type 'create'.");
					continue;
				} catch (SQLException e) {
					e.printStackTrace();
					//logging?
				} finally {
					close(ps);
					close(rs);
				}

			// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

			case "create":
				
				String desiredUsrnm = null;
				String desiredPswrd = null;
				
				try (Connection conn = ConnectionUtil.getConnection();) {
					System.out.println("\nChoose a username:");
					boolean isAvailable = false;
					
					while (!isAvailable) {
						desiredUsrnm = scanner.next();
						String checkExists = "SELECT username FROM bank_users WHERE username = '" + desiredUsrnm + "'";
	
						ps = conn.prepareStatement(checkExists);
						rs = ps.executeQuery();
						if (rs.next()) System.out.println("Username already taken; pick another: ");
						else isAvailable = true;
					}
					
					System.out.println("\nChoose a password: ");
					desiredPswrd = scanner.next();
						
					String sql = "INSERT INTO bank_users (username, userpassword, balance, account_status, admin_status"
							+ " VALUES(?, ?, 0, 0, 'false')"; 
					ps = conn.prepareStatement(sql);
					ps.setString(1, desiredUsrnm);
					ps.setString(2, desiredPswrd);
					rs = ps.executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
					//logging?
				} finally {
					close(ps);
					close(rs);
				}
				System.out.println("\nThanks for creating a new account; it's under review and will be approved shortly. Attempt logging in then.");
				continue;
				
			

			// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

			case "quit":
				break;

			default:
				System.out.println("\nPlease input a valid prompt: ");
				continue;
			}
			running = false;
		}
	}
}