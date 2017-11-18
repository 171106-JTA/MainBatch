package bowsercastle_test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bowsercastle_model.Role;
import bowsercastle_model.User;
import bowsercastle_model.UserComparator;
import bowsercastle_model.BowserStorage;

public class UserStorageTest {
	
	private BowserStorage st;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		st = BowserStorage.getBowserStorage();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test the serialization and unserialization of objects works.
	 */
	@Test
	public void testSerialization() {
		
		Queue<User> q1 = new PriorityQueue<>(new UserComparator());
		Queue<User> q2 = new PriorityQueue<>(new UserComparator());
		q1.add(new User("bob", 123, Role.MEMBER));
		q2.add(new User("alex", 456, Role.UNVERIFIED));
		
		
	}	
}