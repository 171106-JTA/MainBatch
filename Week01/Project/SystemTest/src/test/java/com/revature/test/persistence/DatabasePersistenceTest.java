package com.revature.test.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.factory.FieldParamsFactory;
import com.revature.persistence.database.DatabaseManager;
import com.revature.persistence.database.util.ConnectionUtil;

public class DatabasePersistenceTest {
	static DatabaseManager manager = DatabaseManager.getManager();
	static FieldParamsFactory fieldParamsFactory = FieldParamsFactory.getFactory();
	static Connection connection;
	static User user;
	static long userId;
	static String username;
	static String password;
	static Resultset resultset;
	static FieldParams params;
	static FieldParams values;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userId = 10;
		username = "test";
		password = "test";
	}

	@Before
	public void setUp() throws Exception {
		user = new User(userId, username, password, Checkpoint.NONE);
	}

	@After
	public void tearDown() throws Exception {
		// Remove user if exists 
		params = fieldParamsFactory.getFieldParams(user);
		params.remove(User.ID);
		params.remove(User.CHECKPOINT);
		manager.delete(BusinessClass.USER, params);
	}

	@Test
	public void shouldGetUsersFromDB() {
		resultset = manager.select("user", null);
		assertNotNull(resultset);
		assertTrue(resultset.size() > 0);
	}
	
	@Test
	public void shouldInsertUserIntoDatabaseWithFieldParams() {
		User created;
		
		// Prepare data
		params = fieldParamsFactory.getFieldParams(user);
		params.remove(User.ID);
		params.remove(User.CHECKPOINT);
		
		assertEquals("Should insert user into database", 1, manager.insert(BusinessClass.USER, params));
		assertNotNull("Should find new user", resultset = manager.select(BusinessClass.USER, params));
		assertEquals("Should have found new user", 1, resultset.size());
		
		created = (User)resultset.get(0);

		assertEquals("Should have same username", user.getUsername(), created.getUsername());
		assertEquals("Should have same password", user.getPassword(), created.getPassword());
	}
	
	@Test
	public void shouldUpdateUserData() {
		User updated;
		
		// Prepare data
		params = fieldParamsFactory.getFieldParams(user);
		params.remove(User.ID);
		params.remove(User.CHECKPOINT);
		manager.insert(BusinessClass.USER, params);
		
		user.setUsername("updatedTest");
		values = fieldParamsFactory.getFieldParams(user);
		values.remove(User.ID);
		values.remove(User.CHECKPOINT);
		
		
		
		assertEquals("Should insert user into database", 1, manager.update(BusinessClass.USER, params, values));
		assertNotNull("Should find new user", resultset = manager.select(BusinessClass.USER, values));
		assertEquals("Should have found new user", 1, resultset.size());
		
		updated = (User)resultset.get(0);

		assertEquals("Should have same username", user.getUsername(), updated.getUsername());
		assertEquals("Should have same password", user.getPassword(), updated.getPassword());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
