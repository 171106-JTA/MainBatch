package com.revature.daotest;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.businessobject.User;
import com.revature.dao.factory.BusinessObjectFactory;

public class BusinessObjectFactoryTest {
	static BusinessObjectFactory factory = BusinessObjectFactory.getFactory();
	static User user = new User(1,1,"billy", "bob");
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
	public void shouldGetUserFieldData() {
		Map<String, Map<String, Object>> data = factory.getParams(user);
		
		
		assertEquals(2, data.size());	
	}

}
