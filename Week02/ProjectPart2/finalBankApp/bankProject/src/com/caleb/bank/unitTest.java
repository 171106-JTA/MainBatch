package com.caleb.bank;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class unitTest {
	
	BankModel bankModel = new BankModel(); 
	BankView bankView = new BankView(); 
	BankController bankController = new BankController(); 
	SMSNotificationServer notificationServer; 
	
	@Test  
	void testSignUp() {
		String[] userInfo = {"caleb@gmail.com", "password", "caleb schumake", "7348836255", "no"}; 
		String[] userInput = bankView.getSignUpInfo(); 
		assertEquals(userInfo[0], userInput[0]); 
		assertEquals(userInfo[1], userInput[1]);
		assertEquals(userInfo[2], userInput[2]);
		assertEquals(userInfo[3], userInput[3]);
		assertEquals(userInfo[4], userInput[4]);
	}
	
	@Test
	void testGetLogInInfo() {
		String[] userInfo = {"caleb", "123", "no"}; 
		String[] user = bankView.getLoginInfo(); 
		assertEquals(userInfo[0], user[0]); 
		assertEquals(userInfo[1], user[1]); 
		assertEquals(userInfo[2], user[2]); 
	}
	
	@Test
	void testIsCustomerNew() {
		assertEquals(true, bankView.isCustomerNew()); 
		assertEquals(false, bankView.isCustomerNew()); 
	}

	@Test
	void testShowAdminScreen() {
		assertEquals(1, bankView.showAdminScreen("caleb")); 
	}
	
	
	/* Testing serialization class */ 
//	@Test
//	void testSerialization() {
//		// adding default admin
//		
//		bankController.bankModel.addDefaultAdmin(); // add default admin "caleb"
//		bankController.saveInformation(); // serialize the data
//		bankController.loadUserData(); // reload model 
//		assertEquals("caleb", bankController.bankModel.getDefaultAdmin().getName()); 
//		
//	}
	
	
	/* Testing user functionality */ 
	void testWithDrawAndDeposit() {
		String[] test = {"caleb", "caleb", "caleb", "caleb", "caleb"}; 
		bankController.bankModel.placeUserOnPendingApprovalList(test);
		bankController.bankModel.approveAccount("caleb", notificationServer);
		bankController.bankModel.depositFunds(100, "caleb");
		assertEquals(true, bankController.bankModel.withDrawFunds(0, "caleb")); 
	}
	
	/*test admin functionality */
	@Test
	void testBlockAndUnblockUser() {
		String[] test = {"caleb", "caleb", "caleb", "caleb", "caleb"}; 
		bankController.bankModel.placeUserOnPendingApprovalList(test);
		bankController.bankModel.approveAccount("caleb", notificationServer);
		assertEquals(true, bankController.bankModel.blockUserAccount("caleb", notificationServer)); 
		assertEquals(true, bankController.bankModel.unblockUser("caleb", notificationServer)); 
		
	}
	
	@Test
	void testPromoteUser() {
		String[] test = {"mike", "mike", "mike", "mike", "mike"}; 
		bankController.bankModel.placeUserOnPendingApprovalList(test);
		bankController.bankModel.approveAccount("mike", notificationServer);
		assertEquals(true, bankController.bankModel.promoteUserToAdmin("mike", notificationServer)); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
