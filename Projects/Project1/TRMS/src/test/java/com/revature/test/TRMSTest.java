package com.revature.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.banana.dao.EmployeeDAOImpl;
import com.banana.dao.ReimbursementDAOImpl;
import com.banana.dao.UpdateDAOImpl;
import com.banana.service.InsertReimbursement;

public class TRMSTest {
	EmployeeDAOImpl empdao;
	ReimbursementDAOImpl rdao;
	UpdateDAOImpl udao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		empdao = new EmployeeDAOImpl();
		rdao = new ReimbursementDAOImpl();
		udao = new UpdateDAOImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getEmployeeByUsernameTest() {
		assertNotNull(empdao.getEmployeeByUsername("han"));
	}
	
	@Test
	public void getEmployeeByIdTest() {
		assertEquals(empdao.getEmployeeById(141).getUsername(), "jeff");
	}
	
	@Test
	public void getSingleRequestTest() {
		assertNull(rdao.getSingleRequest(10));
	}
	
	@Test
	public void getAllRequestsTest() {
		assertNotNull(rdao.getAllRequests());
	}
	
	@Test
	public void getPercentageTest() {
		assertEquals(.75, udao.getPercentage(3), 0D);
	}
	
	@Test
	public void convertTest() {
		assertNotNull(InsertReimbursement.convert("10-10-1000 10:20"));
	}

}
