package com.revature.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.lang.reflect.*;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;

import javax.crypto.*;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.model.Model;
import com.revature.model.Properties;
import com.revature.model.account.*;
import com.revature.model.request.*;
import com.revature.throwable.*;

import org.apache.commons.io.FileUtils;

class BankingDriverTest {
	BufferedReader br;
	FileReader fr;
	byte[] bytes;
	private static boolean executed = false;
	private PrintStream out;
	private PrintStream err;
	static File inFile, outFile, errFile;
	static File ivFile = new File(Properties.IVFILE);
	static File serFile = new File(Properties.FILENAME);
	static Map<String, String[]> tests = new Hashtable<String, String[]>();
	static String[] exts = { ".out", ".err", ".tst", ".terr" };
	static PrintStream os;
	static PrintStream es;
	String[] files;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// save old streams
		os = System.out;
		es = System.err;

		// deactivate console appender
		Logger.getRootLogger().removeAppender("stdout");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	// work around to handle reflection once. JUnit does not provide support for one
	// time
	// set up methods after declaration
	@BeforeEach
	synchronized void getTestMethods() {
		files = new String[exts.length];
		if (executed)
			return;
		executed = true;

		// use reflection to build test directories/files
		List<Method> methods = Arrays.asList(BankingDriverTest.class.getMethods());
		for (Method m : methods) {
			if (m.getAnnotation(Test.class) != null) {
				for (int i = 0; i < exts.length; i++) {
					String s = "tests/" + m.getName() + "/test" + exts[i];
					File file = new File(s);
					file.getParentFile().mkdirs();
					if (file.exists() && (s.contains(".out") || s.contains(".err")))
						file.delete();
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					files[i] = s;
				}

				tests.put(m.getName(), files);
			}
		}
	}

	@BeforeEach
	public void resetSingleton() {
		Field instance;
		try {
			instance = Model.class.getDeclaredField("m");
			instance.setAccessible(true);
			instance.set(null, null);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
		}
	}

	@BeforeEach
	void setUp() throws Exception {
		// remove serialized file and init vector file
		System.setOut(os);
		System.setErr(es);
		if (ivFile.exists())
			ivFile.delete();
		if (serFile.exists())
			serFile.delete();
	}

	@AfterEach
	void tearDown() throws Exception {
		System.setOut(os);
		System.setErr(es);
		if (out != null) {
			out.flush();
			out.close();
			err.flush();
			err.close();
		}
		if (br != null) {
			br.close();
			fr.close();
		}

		if (ivFile.exists())
			ivFile.delete();
		if (serFile.exists())
			serFile.delete();
	}

	// Get Model instance
	// Serialize Model
	// Get Model instance from deserialization
	// compare objects to verify they're the same
	@Test
	public void serializationVerificationTest() {
		Model m1, m2;

		String dir = new Object() {
		}.getClass().getEnclosingMethod().getName();
		String base = "tests/" + dir + "/test";
		try {
			fr = new FileReader(new File(base + ".in"));
			br = new BufferedReader(fr);
			out = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(base + ".out"), true)));
			err = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(base + ".err"), true)));
			new BankingDriver(br, out, err);
			m1 = Model.getInstance();
			assertFalse(serFile.exists());
			m1.serialize();
			assertTrue(serFile.exists());
			m2 = Model.deserialize();
			assertEquals(m1, m2);
		} catch (DeserializationError | InvalidKeyException | ClassNotFoundException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException | InvalidParameterSpecException
				| InvalidAlgorithmParameterException | IOException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// verify Collections properly handle all operations by calling lower level
	// access operations
	// verifies all maps are got properly
	// verifies all maps add properly
	// verifies all maps remove properly
	// verifies users convert properly
	@Test
	public void expectedCollectionsBehaviorTest() {
		int prot = Properties.PROT_GET;
		int size;
		try {
			Request r = new CancelRequest("user2", 5);
			Map<?, ?> users = (Map<?, ?>) Model.getInstance().queryUsers(prot, null);
			Map<?, ?> lockedAccounts = (Map<?, ?>) Model.getInstance().queryLockedAccounts(prot, null);
			Map<?, ?> unlockedAccounts = (Map<?, ?>) Model.getInstance().queryUnlockedAccounts(prot, null);
			Map<?, ?> admins = (Map<?, ?>) Model.getInstance().queryAdmins(prot, null);
			Map<?, ?> rejected = (Map<?, ?>) Model.getInstance().queryRejectedAccounts(prot, null);
			Map<?, ?> pendingAccounts = (Map<?, ?>) Model.getInstance().queryPendingAccounts(prot, null);
			Queue<?> requests = (Queue<?>) Model.getInstance().queryRequests(prot, null);

			// remove root for testing
			users.remove("root");
			admins.remove("root");

			assertTrue(users.isEmpty());
			assertTrue(lockedAccounts.isEmpty());
			assertTrue(unlockedAccounts.isEmpty());
			assertTrue(admins.isEmpty());
			assertTrue(rejected.isEmpty());
			assertTrue(pendingAccounts.isEmpty());
			assertTrue(requests.isEmpty());

			prot = Properties.PROT_ADD;

			for (int i = 0; i < 6; i++) {
				assertTrue((boolean) Model.getInstance().queryUsers(prot, "user" + (i + 1), BasicUserAccount.class,
						"user1", "pass1", 200.0));
			}
			assertTrue((boolean) Model.getInstance().queryLockedAccounts(prot, "user2"));
			assertTrue((boolean) Model.getInstance().queryUnlockedAccounts(prot, "user3"));
			assertTrue((boolean) Model.getInstance().queryAdmins(prot, "user4", AdminAccount.class, "root", "bobbert1",
					users.get("user1")));
			assertTrue((boolean) Model.getInstance().queryRejectedAccounts(prot, "user5"));
			assertTrue((boolean) Model.getInstance().queryPendingAccounts(prot, "user6"));
			assertTrue((boolean) Model.getInstance().queryRequests(prot, r));
			assertTrue((boolean) Model.getInstance().queryRequests(prot, new AccountRequest("user3")));
			assertTrue((boolean) Model.getInstance().queryRequests(prot, new LoanRequest("user2", 200.0)));

			assertTrue(!users.isEmpty());
			assertTrue(!lockedAccounts.isEmpty());
			assertTrue(!unlockedAccounts.isEmpty());
			assertTrue(!admins.isEmpty());
			assertTrue(!rejected.isEmpty());
			assertTrue(!pendingAccounts.isEmpty());
			assertTrue(!requests.isEmpty());

			prot = Properties.PROT_REMOVE;
			size = users.size();
			assertTrue((boolean) Model.getInstance().queryUsers(prot, "user1"));
			assertTrue((boolean) Model.getInstance().queryLockedAccounts(prot, "user2"));
			assertTrue((boolean) Model.getInstance().queryUnlockedAccounts(prot, "user3"));
			assertTrue((boolean) Model.getInstance().queryAdmins(prot, "user4"));
			assertTrue((boolean) Model.getInstance().queryRejectedAccounts(prot, "user5"));
			assertTrue((boolean) Model.getInstance().queryPendingAccounts(prot, "user6"));

			prot = Properties.PROT_POLL;
			Request r2 = (Request) (Model.getInstance().queryRequests(prot, null, "user1"));
			assertEquals(r, r2);
			assertTrue(Model.getInstance().queryRequests(prot, null, "user3") != null);
			assertTrue(Model.getInstance().queryRequests(prot, null, "user1") != null);

			assertEquals(users.size(), size - 1);
			assertTrue(lockedAccounts.isEmpty());
			assertTrue(unlockedAccounts.isEmpty());
			assertTrue(admins.isEmpty());
			assertTrue(rejected.isEmpty());
			assertTrue(pendingAccounts.isEmpty());
			assertTrue(requests.isEmpty());

			assertTrue((boolean) Model.getInstance().queryUsers(1, "user1", BasicUserAccount.class, "user1", "pass1",
					200.0));

			prot = Properties.PROT_CONVERT;
			size = users.size();
			UserAccount from = (UserAccount) users.get("user1");
			assertTrue(from instanceof BasicUserAccount);

			UserAccount to = (UserAccount) Model.getInstance().queryUsers(prot, "user1", SeniorUserAccount.class);
			assertTrue(to instanceof SeniorUserAccount);
			assertEquals(size, users.size());
			assertTrue(from.equals(to));

			to = (UserAccount) Model.getInstance().queryUsers(prot, "user1", BasicUserAccount.class);
			assertTrue(to instanceof BasicUserAccount);
			assertEquals(size, users.size());
			assertTrue(from.equals(to));

			to = (UserAccount) Model.getInstance().queryUsers(prot, "user1", PremiumUserAccount.class);
			assertTrue(to instanceof PremiumUserAccount);
			assertEquals(size, users.size());
			assertTrue(from.equals(to));

			to = (UserAccount) Model.getInstance().queryUsers(prot, "user1", SeniorUserAccount.class);
			assertTrue(to instanceof SeniorUserAccount);
			assertEquals(size, users.size());
			assertTrue(from.equals(to));

			to = (UserAccount) Model.getInstance().queryUsers(prot, "user1", PremiumUserAccount.class);
			assertTrue(to instanceof PremiumUserAccount);
			assertEquals(size, users.size());
			assertTrue(from.equals(to));

			to = (UserAccount) Model.getInstance().queryUsers(prot, "user1", BasicUserAccount.class);
			assertTrue(to instanceof BasicUserAccount);
			assertEquals(size, users.size());
			assertTrue(from.equals(to));

		} catch (DeserializationError t) {

		}
	}

	// User creates account, admin logs in, rejects account, restores account,
	// locks account, unlocks account, and promotes account, logs off create a
	// second account
	// approve account, add a new request close account from user, check requests
	@Test
	public void expectedUserBehaviorTest() {

		String dir = new Object() {
		}.getClass().getEnclosingMethod().getName();
		String base = "tests/" + dir + "/test";
		String inStr = base + ".in";
		String outStr = base + ".out";
		String errStr = base + ".err";
		String testStr = base + ".tst";
		String testErrStr = base + ".terr";
		try {
			fr = new FileReader(new File(inStr));
			br = new BufferedReader(fr);
			out = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(outStr), true)));
			err = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(errStr), true)));
			System.setOut((PrintStream) out);
			System.setErr((PrintStream) err);
			new BankingDriver(br, out, err);
			out.flush();
			err.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			assertTrue(FileUtils.contentEquals(new File(outStr), new File(testStr)));
			assertTrue(FileUtils.contentEquals(new File(errStr), new File(testErrStr)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// create a user
	// from root create loan and rollback requests
	// accept user and promote, notice root can't see his requests, logout
	// login as new admin, approve root requests, logout
	// user login root,pay loan, verify transactions are there
	// verify withdraw was rolledback and balance is updated
	@Test
	public void expectedRequestBehaviorTest() {
//		Model m = Model.getInstance();
		
	}

	// test unexpected behavior:
	// throw exceptions for empty sets, malformed input, and permission restrictions
	@Test
	public void exceptionalBehaviorTest() {
		String dir = new Object() {
		}.getClass().getEnclosingMethod().getName();
		String base = "tests/" + dir + "/test";
		String inStr = base + ".in";
		String outStr = base + ".out";
		String errStr = base + ".err";
		String testStr = base + ".tst";
		String testErrStr = base + ".terr";
		try {
			fr = new FileReader(new File(inStr));
			br = new BufferedReader(fr);
			out = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(outStr), true)));
			err = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(errStr), true)));
			System.setOut((PrintStream) out);
			System.setErr((PrintStream) err);
			new BankingDriver(br, out, err);
			out.flush();
			err.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			assertTrue(FileUtils.contentEquals(new File(outStr), new File(testStr)));
			assertTrue(FileUtils.contentEquals(new File(errStr), new File(testErrStr)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// test user account restrictions: can not log on when pending, locked, or
	// rejected
	@Test
	public void accountRestrictionsTest() {
		String dir = new Object() {
		}.getClass().getEnclosingMethod().getName();
		String base = "tests/" + dir + "/test";
		String inStr = base + ".in";
		String outStr = base + ".out";
		String errStr = base + ".err";
		String testStr = base + ".tst";
		String testErrStr = base + ".terr";
		try {
			fr = new FileReader(new File(inStr));
			br = new BufferedReader(fr);
			out = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(outStr), true)));
			err = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(errStr), true)));
			System.setOut((PrintStream) out);
			System.setErr((PrintStream) err);
			new BankingDriver(br, out, err);
			out.flush();
			err.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			assertTrue(FileUtils.contentEquals(new File(outStr), new File(testStr)));
			assertTrue(FileUtils.contentEquals(new File(errStr), new File(testErrStr)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}