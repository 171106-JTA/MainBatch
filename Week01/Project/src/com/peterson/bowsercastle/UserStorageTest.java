package com.peterson.bowsercastle;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		
		List<User> list1 = new LinkedList<>();
		List<User> list2 = new LinkedList<>();
		list1.add(new User("bob", 123, Role.MEMBER));
		list1.add(new User("alex", 456, Role.UNVERIFIED));
		
		try {
			String filename = "SerializeTest.txt";
			
			st.serializeUsers(filename, list1); //serialize list1

			list2.addAll(st.grabUsers(filename)); //list2 will be list1 unserialized
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue("List1 should be equal to list2", list1.equals(list2)); //both lists should be equal
	}	
}