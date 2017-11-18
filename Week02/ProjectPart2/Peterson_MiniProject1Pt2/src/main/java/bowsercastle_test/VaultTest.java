package bowsercastle_test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bowsercastle_model.Role;
import bowsercastle_model.User;
import bowsercastle_model.Vault;

public class VaultTest {

	private Vault v;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		v = new Vault();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test deposit and withdraw for a user.
	 */
	@Test
	public void testTransaction() {
		User user = new User("bob", "123".hashCode(), Role.MEMBER);
		v.depositCoins(user, 10000);
		v.depositCoins(user, -4500);
		assertTrue(user.getCoins() == 10000); //the user should now have 10000 coins
		
		v.withdrawCoins(user, 50000);
		assertTrue("bob should still have 10000", user.getCoins() == 10000); //the user should still have 10000 coins
		v.withdrawCoins(user, 5000);
		assertTrue("bob should now have 5000", user.getCoins() == 5000);
	}
}