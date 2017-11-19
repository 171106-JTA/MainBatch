package com.revature.project2.bean;

import java.util.List;
import java.util.Scanner;

import com.revature.project2.daoUtil.DaoImpl;

public class Menus {
	public void userMenu(String name){
		boolean go = true;
		Scanner scan = new Scanner(System.in);
		String s;
		double money;
		DaoImpl dao = new DaoImpl();
		double debt, deposit,loan;
		
		
		while(go){
			debt = dao.getDebt(name);
			deposit = dao.getDeposit(name);
			loan = dao.getLoan(name);
			
			System.out.println("Deposit: " + deposit);
			System.out.println("Amount Due: " + debt);
			if(loan > 0)System.out.println("Loan is pending");
			System.out.println("0: Logout");
			System.out.println("1: Deposit");
			System.out.println("2: Withdraw");
			System.out.println("3: Apply Loan");
			System.out.println("4: Make Loan Payment");
			s = scan.nextLine();
			switch(s){
			case "0":
				go = false;
				break;
				
			case "1":
				System.out.println("How much would you like to deposit?");
				money = scan.nextDouble();
				if(money < 0)
					System.out.println("Invalid input!");
				else
					dao.makeDeposit(name, money);
				break;
				
			case "2":
				System.out.println("How much would you like to deposit?");
				money = scan.nextDouble();
				if(money < 0 || money > deposit)
					System.out.println("Invalid input!");
				else
					dao.makeDeposit(name, money);
				break;
				
			case "3":
				System.out.println("How much would you like to deposit?");
				money = scan.nextDouble();
				if(money < 0)
					System.out.println("Invalid input!");
				else
					System.out.println("coming soon");//dao.makeDeposit(name, money);
				break;
				
			case "4":
				System.out.println("How much would you like to deposit?");
				money = scan.nextDouble();
				if(money < 0 || debt == 0 || money > debt)
					System.out.println("Invalid input!");
				else
					dao.makePayment(name, money);
				break;
				
			}//end switch
		}//end while
	}
	
	
	public void adminMenu(String name){
		boolean go = true;
		Scanner scan = new Scanner(System.in);
		String s;
		List<String> users;
		DaoImpl dao = new DaoImpl();
		//double debt, deposit,loan;
		
		
		while(go){
			
			System.out.println("0: Logout");
			System.out.println("1: Approve Account");
			System.out.println("2: Block Account");
			System.out.println("3: Unblock Account");
			System.out.println("4: Promote Account");
			s = scan.nextLine();
			switch(s){
			case "0":
				go = false;
				break;
				
			case "1":
				users = dao.getPendingUsers();
				for(String user : users)
					System.out.println(user);
				System.out.println("Who do you want to approve?");
				s = scan.nextLine();
				dao.approveAccount(s);
				break;
				
			case "2":
				users = dao.getUnblockedUsers();
				for(String user : users)
					System.out.println(user);
				System.out.println("Who do you want to block?");
				s = scan.nextLine();
				dao.blockUser(s);
				break;
				
			case "3":
				users = dao.getBlockedUsers();
				for(String user : users)
					System.out.println(user);
				System.out.println("Who do you want to unblock?");
				s = scan.nextLine();
				dao.unblockUser(s);
				break;
				
			case "4":
				users = dao.getNormalUsers();
				for(String user : users)
					System.out.println(user);
				System.out.println("Who do you want to promote?");
				s = scan.nextLine();
				dao.makeAdmin(s);
				break;
				
			}//end switch
		}//end while
	}
	
	public void login(){
		boolean go = true;
		Scanner scan = new Scanner(System.in);
		String s, username, password;
		DaoImpl dao = new DaoImpl();
		
		while(go){
			System.out.println("0: Exit");
			System.out.println("1: Login User");
			System.out.println("2: Login Admin");
			System.out.println("3: Create User");
			//System.out.println("4: Promote Account");
			s = scan.nextLine();
			
			switch(s){
			case "0":
				go= false;
				break;
				
			case "1":
				System.out.println("Give username:");
				username = scan.nextLine();
				System.out.println("Give pasword:");
				password = scan.nextLine();
				if(dao.verify(username, password))
					userMenu(username);
				else
					System.out.println("access denied");
				break;
				
			case "2":
				System.out.println("Give username:");
				username = scan.nextLine();
				System.out.println("Give pasword:");
				password = scan.nextLine();
				if(dao.verifyAdmin(username, password)){
					if(dao.verify(username, password))
						adminMenu(username);
					else
						System.out.println("access denied");
					
				}
				
			case "3":
				System.out.println("Give username:");
				username = scan.nextLine();
				System.out.println("Give pasword:");
				password = scan.nextLine();
				dao.createUser(username, password);
				break;
				
			}
		}
	}
}
