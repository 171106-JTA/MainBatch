package com.revature.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.businessobject.User;
import com.revature.service.util.ServiceUtil;

public class ServiceUtilTest {
	static String strUser;
	static User user;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		strUser = "{\"id\":1,\"roleId\":1,\"username\":\"a\",\"password\":\"a\"}";
		user = new User(1,1, "a", "a");
	}
	
	@Test
	public void shouldBeEmail() {
		assertTrue(ServiceUtil.validateEmail("b@g.com"));
		assertTrue(ServiceUtil.validateEmail("ki.lkdf@gmail.com"));
		assertTrue(ServiceUtil.validateEmail("dadb@gddd.com"));
	}

	@Test
	public void shouldNotBeEmail() {
		assertFalse(ServiceUtil.validateEmail("bg.com"));
		assertFalse(ServiceUtil.validateEmail("ki.lkdfgmail.com"));
		assertFalse(ServiceUtil.validateEmail("dadb@gddd"));
	}
	
	@Test
	public void shouldConvertJavaToJSON() {
		String json = ServiceUtil.toJson(user);
		assertEquals(strUser, json);
	}
	
	@Test
	public void shouldConvertJSONToJava() {
	    User object = ServiceUtil.toJava(strUser, User.class);
		assertEquals(user, object);
	}
}
