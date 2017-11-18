package com.revature.test.persistence;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.persistence.database.util.ConnectionUtil;



public class DatabasePersistenceTest {
	Connection connection;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		connection = ConnectionUtil.getConnection();
	}

	@After
	public void tearDown() throws Exception {
		ConnectionUtil.close(connection);
	}

	@Test
	public void shouldGetConnectionToDataBase() {
		assertNotNull(connection);
	}

}
