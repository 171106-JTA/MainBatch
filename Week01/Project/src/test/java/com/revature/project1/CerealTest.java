package com.revature.project1;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

/**
 * Tests object is properly (de)serialized.
 */
public class CerealTest implements Serializable{

	private static final long serialVersionUID = -5200106932265909576L;
	
	User t1 = new User("testUser1","testing",100,false,1);

	@Test
	public void testDeserialize() {
		assertNotNull(t1);
	}

	@Test
	public void testSerialize() {
		fail("Don't know how to implemented"); // TODO
		
	}

}
