package com.revature.daotest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.User;
import com.revature.dao.DAOBusinessObject;

public class DAOBusinessObjectTest {
	User user1;
	User user2;
	User mockedUser;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		user1 = new User(null,1,"billy", "bob");
		user2 = new User(null,1,"brandy", "lill");	
		mockedUser = mock(User.class);
	}

	@Test
	public void mocksShouldNotBeNull() {
		assertNotNull(mockedUser);
	}
	
	@After
	public void tearDown() throws Exception {
		DAOBusinessObject.delete(user1);
		DAOBusinessObject.delete(user2);
	}

	@Test
	public void shouldLoadAllUsers() {
		List<BusinessObject> users = DAOBusinessObject.loadAll(User.class);
		assertTrue(users.size() > 0);
	}
	
	@Test 
	public void shouldInsertUser() {
		assertEquals(1, DAOBusinessObject.insert(user1));
	}
	
	@Test 
	public void shouldGetNewUser() {
		List<BusinessObject> records;
		User rec;
		
		assertEquals(1, DAOBusinessObject.insert(user1));
		assertEquals(1, (records = DAOBusinessObject.load(user1)).size());
	
		rec = (User)records.get(0);
		assertEquals(user1.getUsername(), rec.getUsername());
		assertEquals(user1.getPassword(), rec.getPassword());
	}
	
	@Test 
	public void shouldUpdateUser() {
		List<BusinessObject> records;
		User updated = new User(null, null, "b", "l");
		User rec;
		
		// Prepare data
		DAOBusinessObject.insert(user1);
		records = DAOBusinessObject.load(user1);
		
		// perform update
		rec = (User)records.get(0);
		assertEquals(1, DAOBusinessObject.update(user1, updated));
	
		user1.setUsername(updated.getUsername());
		user1.setPassword(updated.getPassword());
		
		// test old against new
		records = DAOBusinessObject.load(updated);
		updated = (User)records.get(0);
		assertEquals(updated.getId(), rec.getId());
		assertEquals(updated.getRoleId(), rec.getRoleId());
	}
}
