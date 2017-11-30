package com.revature.test.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.service.util.ServiceUtil;

public class ServiceUtilTest {

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
}
