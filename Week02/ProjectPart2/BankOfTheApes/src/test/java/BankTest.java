import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bankoftheapes.dao.QueryUtil;
import com.bankoftheapes.ui.Deposit;
import com.bankoftheapes.user.BankAccount;

public class BankTest {
	private QueryUtil qu;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		qu = new QueryUtil();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void userExistsTest() {
		assertEquals(qu.userExists("admin"), true);
	}
	
	@Test
	public void getUserInfoTest() {
		assertNotNull(qu.getUserInfo("admin"));
		assertNull(qu.getUserInfo("hello"));
	}

	@Test
	public void testDeposit() {
		BankAccount ba = new BankAccount(0);
		String user = "admin";
		
		Deposit.Screen(ba, user, qu);
		/*
		 * User input = 5
		 * account balance = 0
		 * result should be 5
		 */
		assertEquals(5.0, ba.getAmount(), 0D);
	}
}
