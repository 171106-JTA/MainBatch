package com.peterson.bowsercastle_test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.peterson.bowsercastle_model.Role;
import com.peterson.bowsercastle_model.User;
import com.peterson.bowsercastle_model.UserComparator;
import com.peterson.bowsercastle_model.UserStorage;

public class UserStorageTest {
	
	private UserStorage st;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		st = new UserStorage();
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
		
		try {
			String filename = "SerializeTest.txt";
			
			st.serializeUsers(filename, q1); //serialize list1

			q2.addAll(st.grabUsers(filename)); //list2 will be list1 unserialized
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue("List1 should be equal to list2", q1.equals(q2)); //both lists should be equal
	}	
}