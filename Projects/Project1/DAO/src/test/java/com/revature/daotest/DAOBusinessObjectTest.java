package com.revature.daotest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.User;
import com.revature.dao.DAOBusinessObject;

public class DAOBusinessObjectTest {
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldLoadAllUsers() {
		List<BusinessObject> users = DAOBusinessObject.loadAll(User.class);
		assertTrue(users.size() > 0);
	}
	
}
