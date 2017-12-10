package com.revature.trms.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {

	private String username;
	
	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;
	
	@Mock
	private HttpSession session;

	@Test
	public void usernameAndEmpIdSavedInSession() {
	}
}
