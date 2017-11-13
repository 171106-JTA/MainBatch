package com.revature.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * To unit-test this console app is to test operations on the data, since all given operations in a serious app are 
 * data-affecting ones
 *
 */
public class ApplicationTest {
	DataStore _data;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Testing operations on the DataStore of this Application");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// fetch some dummy data to work with
		_data = Application.dummyStore();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUserPassHash() {
		// create a user with name of "SampleUser" and set password
		String pass = "SamplePass";
		User newUser = new User("SampleUser", pass);
		// the password of newUser should be hashed version of password we gave it
		assertNotEquals(pass, newUser.getPass());
	}
	
	@Test
	public void testDefaultAdminInAdmins()
	{
		// initialize a new list of Admins
		Admins admins = new Admins();
		// test should pass if admins already contains a DefaultAdmin
		assertEquals(DefaultAdmin.getDefaultAdmin(), admins.getFirst());
	}
	
	
	@Test
	public void testUserAccountsEmpty() 
	{
		// create User
		User user = new User("Sample", "User");
		// test should pass if user has no accounts yet
		assertTrue(user.getAccounts().isEmpty());
	}
	
	@Test
	public void testUserDefaultState()
	{
		// create User using two-arg constructor
		User user = new User("Test", "User");
		// test should pass iff that User is locked by default
		assertEquals(User.LOCKED, user.getState());
	}
	
	// now testing operations on the DataStore, in a state-independent way
	@Test
	public void testAdminsPresent()
	{
		// DataStore should contain admins
		assertFalse(_data.getAdmins().isEmpty());
	}
	
	@Test
	public void testActivateUser()
	{
		// try to find a single locked user 
		boolean anyLockedUsers = !_data.getLockedUsers().isEmpty();
		// if there are none
		User user;
		if (!anyLockedUsers)
		{
			// let's add one 
			user = new User("random", "pass0x436fde");
			_data.getLockedUsers().add(user);
		}
		// otherwise
		else
		{
			// let's get the first one
			user = _data.getLockedUsers().getFirst();
		}
		// have an Admin unlock the user
		_data.getAdmins().getFirst().activate(user);
		// user should be marked active, be in _data.getActiveUsers() now, and no longer be in _data.getLockedUsers()
		assertEquals(User.ACTIVE, user.getState());
		assertFalse(_data.getLockedUsers().contains(user));
		assertTrue(_data.getActiveUsers().contains(user));
	}
	
	@Test
	public void testWithdrawal()
	{
		User user;
		double defaultBalance = 50;
		// get an active User from the data store, or create (and push to the data store) one if you can't
		if (!_data.getActiveUsers().isEmpty())
		{
			user = _data.getActiveUsers().getFirst();
		}
		else
		{
			user = new User("JustTryingToWIthdraw", "some money", User.ACTIVE);
			_data.getActiveUsers().add(user);
		}
		// if user doesn't have an account, or if that account has a zero-balance
		double bal;
		if ((user.getAccounts().isEmpty()) || (user.getAccounts().getFirst().getBalance() == 0))
		{
			// create an account for them, with defaultBalance
			user.createAccount().setBalance(50);
		}
		// get the Account and its initial balance
		Account firstAccount = user.getAccounts().getFirst();
		bal = firstAccount.getBalance();
		// withdraw a random amount of money less than the balance, making sure that amount is greater than zero
		double withdrawAmount = 0;
		while (withdrawAmount == 0)
			withdrawAmount = new Random().nextDouble() * bal;
		firstAccount.withdraw(withdrawAmount);
		assertTrue(user.getAccounts().getFirst().getBalance() > 0);
	}
	
	@Test
	public void testDeposit()
	{
		// same as above, except this time, we're depositing
		User user;
		// get an active User from the data store, or create (and push to the data store) one if you can't
		if (!_data.getActiveUsers().isEmpty())
		{
			user = _data.getActiveUsers().getFirst();
		}
		else
		{
			user = new User("JustTryingToDposit", "some money", User.ACTIVE);
			_data.getActiveUsers().add(user);
		}
		// if user doesn't have an account
		double bal;
		Account account;
		if (user.getAccounts().isEmpty())
		{
			// we create one for the user
			account = user.createAccount();
		}
		// otherwise, we simply grab the first one
		else
		{
			account = user.getAccounts().getFirst();
		}
		// next, let's fetch the initial balance
		bal = account.getBalance();
		// deposit a random amount of money, not exceeding $200
		account.deposit(new Random().nextDouble() * 199 + 1);
		// test passes iff balance of this Account changed, to the User
		assertTrue(user.getAccounts().getFirst().getBalance() > bal);
	}
	
	@Test
	public void testLockUser()
	{
		// the reverse of the test above
		// try to find a single active user 
		boolean anyActiveUsers = !_data.getActiveUsers().isEmpty();
		// if there are none
		User user;
		if (!anyActiveUsers)
		{
			// let's add one 
			user = new User("random", "pass0x436fde");
			_data.getActiveUsers().add(user);
		}
		// otherwise
		else
		{
			// let's get the first one
			user = _data.getActiveUsers().getFirst();
		}
		// have an Admin unlock the user
		_data.getAdmins().getFirst().lock(user);
		// user should be marked active, be in _data.getActiveUsers() now, and no longer be in _data.getLockedUsers()
		assertEquals(User.LOCKED, user.getState());
		assertFalse(_data.getActiveUsers().contains(user));
		assertTrue(_data.getLockedUsers().contains(user));
	}

	@Test
	public void testBanUser()
	{
		// similar logic as the above two methods, except we test the capability to make an active user and a locked user both banned users
		// try to find a single locked user and a single active user 
		boolean anyLockedUsers = !_data.getLockedUsers().isEmpty(),
				anyActiveUsers = !_data.getActiveUsers().isEmpty();
		User lockedUser, activeUser;
		// if there are no locked users
		if (!anyLockedUsers)
		{
			// let's add one 
			lockedUser = new User("wasLocked", "pass0x436fde");
			_data.getLockedUsers().add(lockedUser);
		}
		// otherwise
		else
		{
			// let's get the first one
			lockedUser = _data.getLockedUsers().getFirst();
		}
		// if there are no active users
		if (!anyActiveUsers)
		{
			// let's add one
			activeUser = new User("wasActive", "banMeNow");
			_data.getActiveUsers().add(activeUser);
		}
		// otherwise
		else
		{
			// let's get the first one
			activeUser = _data.getActiveUsers().getFirst();
		}
		// have an Admin ban both
		_data.getAdmins().getFirst().ban(activeUser);
		_data.getAdmins().getFirst().ban(lockedUser);
		// activeUser,lockedUser should be in the banned list and have a state of banned
		assertEquals(User.BANNED, activeUser.getState());
		assertEquals(User.BANNED, lockedUser.getState());

		assertTrue(_data.getBannedUsers().contains(activeUser));
		assertTrue(_data.getBannedUsers().contains(lockedUser));
		// activeUser should no longer be marked active in any way
		assertFalse(_data.getActiveUsers().contains(activeUser));
		// lockedUser should no longer be marked locked in any way
		assertFalse(_data.getLockedUsers().contains(lockedUser));
	}
}
