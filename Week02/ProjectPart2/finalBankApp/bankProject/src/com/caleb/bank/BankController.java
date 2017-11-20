package com.caleb.bank;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import java.util.List;


/**
 * The bankController class acts as the liaison between the bankView class and the bankModel class.
 * This class utilizes the bankView class as the UI and uses it for user input. Once the bankView returns the user input
 * the controller uses this input and passes it to the bankModel class. The bankModel then handles the needed computation
 * and returns it back to the controller. At this point, the controller calls the bankView class passing it the new updated
 * information. The aforementioned cycle continues until a user logs out. This class also handles both serialization and deserialization of the
 * bank model. 
 * @author calebschumake 
 *
 */

public class BankController {

	private String[] userInformation;
	private final int email = 0; 

	private BankView bankView;
	protected BankModel bankModel;
	private SMSNotificationServer notificationServer; 
	private static final Logger logger = Logger.getLogger("GLOBAL"); 

	/* boolean array is used to verify that a customer has logged in successfully and if they have admin privileges */ 
	private boolean[] loginAndAdmin = new boolean[3];
	private final int SUCCESSFULL_LOGIN = 1;
	private final int ADMINISTATOR_PRIVILEGE = 2;

	/**
	 * This constructor creates the bankView and bankModel objects which the BankController utilizes.
	 */
	BankController() {
		/* add default admin */
		bankView = new BankView();
		bankModel = new BankModel();
		notificationServer = new SMSNotificationServer(); 
	}
	
	
	
	/**
	 * This method is used to determine whether a user is new or not. Also, it determines if the user is an admin. This method 
	 * sets the loginAndAdmin array which is used to determine if the used was found in the model and if they have admin
	 * privileges
	 */
	public void getAndVerifyCredentials() {

		if (bankView.isCustomerNew()) {

			userInformation = bankView.getSignUpInfo();
			boolean isOnPendingList = bankModel.isOnPendingList(userInformation);
			boolean isOnActiveList = bankModel.isOnActiveList(userInformation);
			System.out.println("Value of isOnAvticeList: " + isOnActiveList);
			
			/* if customer is not on pending and not on the active list then place them on the pending list */ 
			if (!(isOnPendingList) && !(isOnActiveList)) {

				bankModel.placeUserOnPendingApprovalList(userInformation);
				logger.trace("User has been placed on pending approval list");
				bankView.showUserIsNowOnWaitingListMessage(); 

			} else if (isOnPendingList) {

				bankView.displayPendingApprovalMessage(userInformation);

			} else if (isOnActiveList) {

				bankView.displayAlreadyActiveMessage(userInformation);

			}

		} else {
			
			/* Call bankView object to return user login info  */
			userInformation = bankView.getLoginInfo(); 
			
			/* If the customer has admin privilege attempt to sign them in */ 
			if (userInformation[ADMINISTATOR_PRIVILEGE].equalsIgnoreCase("yes")) {

				Admin temp;
				temp = bankModel.signInAdmin(userInformation);

				if (temp != null) {

					loginAndAdmin[SUCCESSFULL_LOGIN] = true;
					loginAndAdmin[ADMINISTATOR_PRIVILEGE] = true;

				} else {

					bankView.showIncorrectLoginCredentialsMessage();
					loginAndAdmin[SUCCESSFULL_LOGIN] = false;
					loginAndAdmin[ADMINISTATOR_PRIVILEGE] = false;

				}

			} else {
				
				/* attempt to sign them in as a user */ 
				User temp;
				temp = bankModel.getUser(userInformation[email], true);
				
				/* check if user is already on the pending list */ 
				if(bankModel.isOnPendingList(userInformation)) {
					
					bankView.showUserIsOnWaitingListMessage();
					temp = null; 
					
				} else if(temp ==  null) { /* if user is not in our system */ 
					
					bankView.showIncorrectLoginCredentialsMessage(); 
					
				}

				if (temp != null) { 

					if (temp.isBlocked()) { /* Check to see if user account has been blocked by an admin */ 

						bankView.showBlockedUserMessage(temp);

					} else {

						loginAndAdmin[SUCCESSFULL_LOGIN] = true;
						loginAndAdmin[ADMINISTATOR_PRIVILEGE] = false;

					}

				}
				
			}

		}

	}
	
	protected void finalize() {
		bankView.cleanUp();
	}
	
	
	/**
	 * This method is used to enter the user portal. This method continuously runs until the user logs out
	 */
	public void enterUserPortal() {

		/* if user has successfully logged-in present the portal */
		final int USER_EMAIL = 0;
		
		/* Check to see if login credentials were found and if the user has admin privilege */ 
		if (loginAndAdmin[SUCCESSFULL_LOGIN] && loginAndAdmin[ADMINISTATOR_PRIVILEGE]) {

			boolean userIsLoggedIn = true;

			while (userIsLoggedIn) {
				
				/* This switch statement carrys out the users selections */ 
				switch (bankView.showAdminScreen(bankModel.getAdminName(userInformation[USER_EMAIL]))) {
				case 1: /* Admin wants to block a user */ 
					String userToBlock = bankView.showBlockUserScreen(bankModel.getActiveUsers(),
							userInformation[USER_EMAIL]); /* Admin cannot block his other account */

					if (bankModel.blockUserAccount(userToBlock, notificationServer)) {

						bankView.showUserWasBlockedMessage(); 

					} else {

						bankView.showUserDoesNotExistMessage();

					}
					break;
				case 2: /* approve pending accounts */
					
					/* get the account the Admin wants to approve */ 
					String accountToApprove = bankView.showApproveAccountsScreen(bankModel.getPendingAccounts());

					if (bankModel.canApproveUserAccount(accountToApprove)) {
						/* Model needs to approve user account */
						
						bankModel.approveAccount(accountToApprove, notificationServer);
						bankView.showAccountWasSuccessfullyActivatedMessage(); 

					} else {

						bankView.showUserDoesNotExistMessage();

					}
					break;
				case 3: /* Promote a user to an admin */ 
					/* Get all active accounts that are not already admins */
					List<User> usersWhoAreNotAdmins = bankModel.getUsersWhoAreNotAdmins();

					/* If there are users who are not admins, pass this information to the view */
					if (!usersWhoAreNotAdmins.isEmpty()) {
						
						String accountToPromote = bankView.showPromoteScreen(usersWhoAreNotAdmins);
						
						/* Check to see if user can be promoted */ 
						if (bankModel.promoteUserToAdmin(accountToPromote, notificationServer)) {

							bankView.showUserHasBeenGrantedAdminPrivlegesMessage();

						} else {

							bankView.showUserDoesNotExistMessage();

						}

					} else {

						bankView.thereAreNoUsersToPromoteMessage();

					}
					break;
				case 4: /* Unfreeze user account */
					/* Ask the model to provide all the active users
					 * the view will print ones who have their "isAdmin" property set to false
					 */
					String userToUnblock = bankView.showUnblockUserScreen(bankModel.getActiveUsers());
					
					/* If the user was successfully unblocked show the user has been unblocked message */ 
					if (bankModel.unblockUser(userToUnblock, notificationServer)) {

						bankView.showUserHasBeenUnblockedMessage();

					} else {

						bankView.showNoUserToUnblockMessage();

					}
					break;
				case 5:
					/* User has pressed 5 and decided to call it a day */ 
					userIsLoggedIn = false;
					bankView.showGoodbyeMessage();
					break;
				case 6: /* If user does not type in the correct input this case is hit */ 
					break; 
				}
			}

		} else if (loginAndAdmin[SUCCESSFULL_LOGIN]
				&& (!loginAndAdmin[ADMINISTATOR_PRIVILEGE])) { /* user logged in successfully without admin privilege */

			boolean userIsLoggedIn = true;

			while (userIsLoggedIn) {

				/* Get the user object from the model and pass it to the view */
				switch (bankView.showUserScreen(bankModel.getUser(userInformation[USER_EMAIL], true))) {
				case 1: /* Withdraw funds option */ 
					
					/* get user withdraw amount from the view */ 
					float amountToWithDraw = bankView.showWithdrawScreen();
					
					/* Check if the user can withdraw the specified amount */ 
					if (bankModel.withDrawFunds(amountToWithDraw, userInformation[USER_EMAIL])) {

						bankView.showWithdrawSuccessMessage();

					} else {

						bankView.showInsuffecientFundsMessage();

					}
					break;
				case 2: /* Deposit option */ 
		
					float amountToDeposit = bankView.showDepositScreen();
					bankModel.depositFunds(amountToDeposit, userInformation[USER_EMAIL]);
					bankView.showDepositCompleteMessage();
					break;
				case 3: /* show all previous transactions */
					User user = bankModel.getUser(userInformation[USER_EMAIL],true);
					bankView.showPreviousTransactions(user);
					break;
				case 4:  /* Apply for a loan */ 
					/* if user does not already have a loan and the loan is not too big or too small then sign them up */ 
					
					int amount = bankView.showApplyForLoanScreen();
					if (amount > 0) { /* View returns zero if user presses wrong character */ 
						
						if (bankModel.checkIfUserAlreadyHasLoan(userInformation[USER_EMAIL])) {

							bankView.showUserAlreadyHasLoanMessage();

						} else if (bankModel.checkIfLoanIsTooBig(amount)) {

							bankView.showLoanAmmountIsTooBigMessage();

						} else if (bankModel.checkIfLoanIsToosmall(amount)) {
							
							bankView.showLoanIsTooSmallMessage(); 
							
						} else {
							
							if(bankModel.applyForLoan(userInformation[USER_EMAIL], amount)) {
								
								bankView.showUserHasBeenApprovedForLoanMessage(); 
								
							} else {
								
								bankView.showUserHasBeenDeniedLoanMessage(); 
								
							}
							
						}
						
					} 
					break; 
				case 5: /* make payment on loan*/
				
					float payment = bankView.showMakePaymentOnLoanScreen();
					if (payment > 0) {
						
						if(bankModel.makePaymentOnLoan(userInformation[USER_EMAIL], payment)) {
							
							bankView.showSuccessfullPaymentMessage(); 
							
						} else {
							
							bankView.showPaymentWasNotSuccessfullMessage(); 
							
						}
						
						
					}
					break;  
				case 6:  /* user logs off */ 
					userIsLoggedIn = false;
					bankView.showGoodbyeMessage();
					break; 
					
				default: /* user hits wrong button */ 
					break; 
					
				}
				
			}

		}

	}

}