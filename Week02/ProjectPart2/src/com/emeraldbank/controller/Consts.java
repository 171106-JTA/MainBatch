package com.emeraldbank.controller;

import java.util.Scanner;

/**
 * Constants class. Contains constants for other classes within this project to use
 * @author sjgillet
 *
 */
public class Consts {

	private static Consts consts;
	
	static final int ANY_SYNTAX = 0; 
	static final int COMMAND_SYNTAX = 1;
	static final int USERNAME_SYNTAX = 2;
	static final int PASSWORD_SYNTAX = 3;
	static final int ACCT_NUM_SYNTAX = 4;
	static final int DBL_2_DECIMALS_SYNTAX = 5; 
	static final int YES_NO_SYNTAX = 6; 
	static final int LEGAL_NAME_SYNTAX = 7; 
	static final int ALPHABETICAL_SYNTAX = 8;
	static final int ALPHANUMERIC_SYNTAX = 9; 
	
	static final int ATTEMPTS_LIMIT = 3; 
	static final int TAMPERING_ATMPTS_ALWD = 3; 
	public static final int ACTIVE = 1; 
	public static final int INACTIVE = 0; 
	public static final int LOCKED = -1; 
	
	public static final int ACCOUNT_NUMBER_LENGTH = 8; 
	public static final int ACCOUNT_NUMBER_REDACT_LENGTH = 4; 
	
	public static final String USERS_SER_PATH = "users.ser"; 
	public static final String ACCTS_SER_PATH = "accts.ser"; 
	
	static final String[] LOGIN_CMD			= {"login", "log in", "l", "log in"}; 
	static final String[] CREATE_USER_CMD 	= {"create user", "create account", "create", "c", "create user account"}; 
	static final String[] EXIT_CMD 			= {"exit", "quit", "e", "q"};
	static final String[] LOGOUT_CMD 		= {"logout", "logo", "lo", "l", "log out", "exit", "quit", "e", "q"}; 
	static final String[] BALANCE_INQ_CMD 	= {"balance", "balance inquiry", "check balance", "balance check", "bal", "b"}; 
	static final String[] DEPOSIT_CMD 		= {"deposit", "deposit funds", "deposit money", "depo", "dep", "d"}; 
	static final String[] WITHDRAW_CMD 		= {"withdraw", "withdrawal", "withdraw funds", "withdraw money", "with", "w"}; 
	static final String[] TRANSFER_CMD 		= {"transfer", "transfer funds", "transfer money", "move funds", "transf", "trans", "tr", "t"}; 
	static final String[] OPEN_ACCT_CMD 	= {"open", "open bank account", "open account", "create bank account", "op", "o"}; 
	static final String[] CLOSE_ACCT_CMD 	= {"close", "close bank account", "close account", "cl", "c" }; 
	static final String[] ACTIVATE_USER_CMD = {"activate user", "validate user", "activate u", "validate u","actu", "au"}; 
	static final String[] ACTIVATE_ACCT_CMD = {"activate account", "validate account", "activate a", "validate a", "acta", "aa"}; 
	static final String[] LOCK_USER_CMD 	= {"lock user", "lock user account", "locku", "lock u", "lu"}; 
	static final String[] LOCK_ACCT_CMD 	= {"lock account", "lock bank account", "locka", "lock a", "la"};
	static final String[] PROMOTE_ADMIN_CMD = {"promote", "promote admin", "promote user", "promote to admin", "p"};  
	static final String[] AFFIRMATIVE_CMD   = {"y", "yes", "affirmative", "absofruitley"};
	static final String[] NEGATIVE_CMD		= {"n", "no", "negative", "nope"}; 
	
	static final String MAIN_PAGE_BANNER = "\n\t .*'^ EMERALD CITY BANK *^.'"; 
	static final String USER_TRANSACTION_PROMPT = "Balance Inquiry | Deposit | Withdraw | Transfer Funds " 
			+ "| Open Account | Close Account\n"; 
	static final String ADMIN_TRANSACTION_PROMPT = USER_TRANSACTION_PROMPT + "Admin Tools: Activate/Unlock User "
			+ "| Activate/Unlock Bank Account | Lock User Account | Lock Bank Account | Promote Administrator\n"; 
	
	public static void initialize() {
		if(consts == null) 
			consts = new Consts();
	}
}
