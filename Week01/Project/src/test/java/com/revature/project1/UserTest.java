package com.revature.project1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.revature.main.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/**
 * Tests corresponding user object is returned, based on id.
 */
public class UserTest {

	User t1 = new User("Bobbert","testing",100,false,1);
	List<User> list = new ArrayList<User>();
	User t2;
//	list.add(t1);
	
	
	@Test
	public void testReturnExisting() {
		String testID = "Bobbert";
		assertEquals(t1, User.returnExisting(testID));
		//junit won't allow adding to list
	}

}
